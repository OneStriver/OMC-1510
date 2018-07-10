package com.sunkaisens.omc.service.impl.core;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.lf5.util.StreamUtils;
import org.springframework.stereotype.Service;

import com.google.gxp.com.google.common.io.ZipFiles;
import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.mapper.core.ConfigMapper;
import com.sunkaisens.omc.mapper.core.EntityMapper;
import com.sunkaisens.omc.mapper.core.ItemMapper;
import com.sunkaisens.omc.mapper.core.ModuleMapper;
import com.sunkaisens.omc.mapper.core.OptionsMapper;
import com.sunkaisens.omc.po.core.Config;
import com.sunkaisens.omc.po.core.Item;
import com.sunkaisens.omc.po.core.Module;
import com.sunkaisens.omc.service.core.ModuleService;
import com.sunkaisens.omc.util.FileUtil;
import com.sunkaisens.omc.util.ServletContextUtil;
import com.sunkaisens.omc.vo.core.PageBean;
/**
 * 
 * 
 * 
 * 
 * 
 * ModuleService接口实现类
 *
 */
@Service
public class ModuleServiceImpl implements ModuleService {

	@Resource
	private ModuleMapper moduleMapper;
	@Resource
	private ConfigMapper confMapper;
	@Resource 
	private ItemMapper itemMapper;
	@Resource
	private EntityMapper entityMapper;
	@Resource
	private OptionsMapper optionsMapper; 

	@Override
	public PageBean getPageBean(int pageNum, Integer pageSize) {
		int count =moduleMapper.selectCount();
		List<Module> modules=moduleMapper.select((pageNum-1)*pageSize, Math.min(pageSize, count));
		PageBean pageBean=new PageBean(pageNum, pageSize, modules, count);
		return pageBean;
	}
	
	/**
	 * 上传ZIP包操作
	 */
	@Override
	public void saveUploadModule(String fileName, File file,Integer moduleId)
			throws CustomException, IOException {
		if(moduleId!=null && moduleMapper.selectById(moduleId)!=null){
			throw new CustomException("此网元ID已存在！");
		}
		if (!fileName.toLowerCase().endsWith(".zip")){
			throw new CustomException("请以.zip命名！");
		}
		if (!FileUtil.isZip(file)){
			throw new CustomException("此文件不是ZIP格式！");
		}
		String moduleName = fileName.substring(0, fileName.lastIndexOf("."));
		if (null!=moduleMapper.selectByName(moduleName)){
			throw new CustomException("网元【"+moduleName+"】已存在,请勿重复上传！");
		}
		//获取上下文
		ServletContext sc = ServletContextUtil.getServletContext();
		//配置文件配置路径
		InputStream is=getClass().getResourceAsStream("/omc.properties");
		Properties p=new Properties();
		p.load(is);
		String omcPath=p.getProperty("omcDir");
		String path = sc.getRealPath(omcPath);
		File destination = new File(path, moduleName);
		try {
			FileUtils.deleteDirectory(destination);
		} catch (Exception e) {
			throw new CustomException("请检查包的冗余性！");
		}
		destination.mkdirs();
		try {
			ZipFiles.unzipFile(file, destination);
		} catch (Exception e) {
			throw new CustomException("上传资源包文件内容有问题,请检查上传资源包文件的内容！");
		}
		// MANIFEST.MF
		File manifest = new File(destination, "manifest.mf");
		if (!manifest.exists()){
			throw new CustomException("此压缩包缺少清单文件");
		}
		parseManifest(moduleId,moduleName,manifest,destination,path);
	}

	private void parseManifest(Integer moduleId,String moduleName, File manifest,
			File destination, String modulePath) throws IOException,CustomException {
		try (InputStreamReader read = new InputStreamReader(new FileInputStream(manifest), "UTF-8")) {
			File thisDir = new File(modulePath + File.separator + moduleName);
			
			Properties property = new Properties();
			property.load(read);
			String belong = property.getProperty("belong", "0");
			if (!Pattern.matches("^\\d$", belong)){
				throw new CustomException("清单文件belong 0=其他,1=核心网,2=业务 其余非法");
			}
			String required=property.getProperty("required","");
			String description = property.getProperty("description");
			if(StringUtils.isEmpty(description)){
				description=moduleName;
			} 
			String version = property.getProperty("version","");
			String confstr = property.getProperty("config","");
			String exe=property.getProperty("exe","");
			String log=property.getProperty("log","");
			
			if (StringUtils.isEmpty(log)){
				throw new CustomException("清单文件中log属性名称不正确! OR 清单文件中必须有log属性,描述日志目录");
			}
			if (StringUtils.isEmpty(exe)){
				throw new CustomException("清单文件中exe属性名称不正确! OR 清单文件中必须有exe属性,描述启动命令");
			}
			if (StringUtils.isEmpty(confstr)){
				throw new CustomException("清单文件中config属性名称不正确! OR 清单文件中必须有config属性以【,】分隔的配置文件路径！");
			}
			if (StringUtils.isEmpty(version)){
				throw new CustomException("清单文件中version属性名称不正确! OR 清单文件中必须有版本号！");
			}
			if (StringUtils.isEmpty(required)){
				throw new CustomException("清单文件中required属性名称不正确! OR 清单文件中必须有required属性,以【,】分隔指定zip包中必须有的文件(夹)！");
			}
			if(!new File(thisDir,log).exists()){
				throw new CustomException("日志目录【"+log+"】不存在！");
			}
			
			String[] requireds=required.split(",");
			for(String filename:requireds){
				if(!Arrays.asList(thisDir.list()).contains(filename)){
					throw new CustomException("不存在资源【"+filename+"】！");
				}
			}
			for(String filename:thisDir.list()){
				if(filename.equalsIgnoreCase("manifest.mf")){
					continue;
				}else if(!Arrays.asList(requireds).contains(filename)){
					try {
						FileUtils.deleteDirectory(destination);
					} catch (Exception e) {
						throw new CustomException("请检查包的冗余性！");
					}
					throw new CustomException("资源【"+filename+"】在required属性中没有记录！");
				}
			}
			if(moduleId==null){
				Integer maxId=moduleMapper.selectMaxId();
				if(maxId==null||maxId<256) maxId=255;
				moduleId=maxId+1;
			}
			Module module = new Module(moduleId,moduleName, new Integer(belong),description, version,exe,log,null);
			moduleMapper.insert(module);
			module.setId(moduleId);
			String[] conf = confstr.split(",");
			// confName为全路径
			for (String confName : conf) {
				confName=confName.trim();
				File confF = new File(thisDir, confName);
				if(!confF.exists()){ 
					try {
						FileUtils.deleteDirectory(destination);
					} catch (Exception e) {
						throw new CustomException("上传文件名不能包含中文！");
					}
					throw new CustomException("配置文件【"+confName+"】不存在！");
				}
				Config config = new Config(confName, null, null, false,module);
				String kvSep=ConfigServiceImpl.getConfKvSeparator(confName);
				if(kvSep==null) config.setSole(true);
				confMapper.insert(config);
				if(config.getSole()){//单独配置文件项
					Item item=new Item();
					item.setName(config.getName());
					item.setDescription(config.getName());
					item.setFormtype("textbox");
					item.setMultiline(true);
					item.setConfig(config);
					item.setValue(FileUtils.readFileToString(confF).replaceAll("[\t\n\r]+", "\n"));
					itemMapper.insert(item);
				}else{//Key-Value形式
					ConfigServiceImpl.parseItem(FileUtils.readFileToString(confF), config,kvSep,itemMapper);
				}
			}
			
		}
		
	}

	@Override
	public void update(Module module) {
		moduleMapper.update(module);
	}
	
	@Override
	public void delete(Integer[] ids) throws IOException, CustomException{
		for(Integer id:ids){
			Integer maxInstId=entityMapper.selectMaxIstdIdByModuleId(id);
			if(maxInstId!=null) throw new CustomException("不可删除已下发的网元");
			List<Config> confs=confMapper.selectByModuleId(id);
			for(Config c:confs){
				for(Item item:itemMapper.selectByConfigId(c.getId())){
					optionsMapper.deleteByItemId(item.getId());
				}
				itemMapper.deleteByConfigId(c.getId());
				confMapper.deleteById(c.getId());
			}
			Module module=moduleMapper.selectById(id);
			ServletContext sc = ServletContextUtil.getServletContext();
			String path = sc.getRealPath("/WEB-INF/repository");
			FileUtils.deleteDirectory(new File(path,module.getName()));
			moduleMapper.deleteById(id);
		}
	}

	@Override
	public List<Module> findAll() {
		return moduleMapper.selectAll();
	}

	@Override
	public void saveUploadAllModule(InputStream is) throws Exception {
		ZipInputStream zis=new ZipInputStream(is);
		ZipEntry zipEntry=null;
		while((zipEntry=zis.getNextEntry())!=null){
			String fileName=zipEntry.getName();
			File f=Files.createTempFile(new Date().getTime()+"", ".tmp").toFile();
			try(FileOutputStream outputStream=new FileOutputStream(f)){
				StreamUtils.copy(zis, outputStream);
			}
			readZipFile(f);
			saveUploadModule(fileName, f,null);
		}
	}
	
	public void readZipFile(File file) { 
		try(ZipFile zf = new ZipFile(file);
			InputStream in = new BufferedInputStream(new FileInputStream(file)); 
			ZipInputStream zin = new ZipInputStream(in);
			) {
			ZipEntry ze; 
			while ((ze = zin.getNextEntry()) != null) { 
				if (ze.isDirectory()) {
					System.err.println("文件名:" + ze.getName());
				} else { 
					System.err.println("文件名:" + ze.getName() + ",大小:"+ ze.getSize() + "Bytes."); 
					
				}
				if(ze.getName().toLowerCase().endsWith(".zip")){
					throw new CustomException("单个网元ZIP包中不能包含ZIP包！OR 不能上传批量的网元ZIP包！");
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Module getByName(String name) {
		return moduleMapper.selectByName(name);
	}

	@Override
	public Module findById(Integer id) {
		return moduleMapper.selectById(id);
	}
}
