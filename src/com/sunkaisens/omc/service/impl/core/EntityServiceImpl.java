package com.sunkaisens.omc.service.impl.core;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sunkaisens.omc.context.core.OmcServerContext;
import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.mapper.core.CardMapper;
import com.sunkaisens.omc.mapper.core.ConfigMapper;
import com.sunkaisens.omc.mapper.core.EntityMapper;
import com.sunkaisens.omc.mapper.core.ItemMapper;
import com.sunkaisens.omc.mapper.core.ModuleMapper;
import com.sunkaisens.omc.po.core.Config;
import com.sunkaisens.omc.po.core.Entity;
import com.sunkaisens.omc.po.core.Item;
import com.sunkaisens.omc.po.core.Module;
import com.sunkaisens.omc.service.core.EntityService;
import com.sunkaisens.omc.thread.cncpMsg.CncpProtoType;
import com.sunkaisens.omc.thread.cncpMsg.CncpTaskExecutor;
import com.sunkaisens.omc.thread.cncpMsg.SendCncpMsgFactory;
import com.sunkaisens.omc.thread.messageEncapsulation.SetUpMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.SetUpResponseMsg;
import com.sunkaisens.omc.util.CncpStatusTransUtil;
import com.sunkaisens.omc.util.FileUtil;
import com.sunkaisens.omc.util.ServletContextUtil;
import com.sunkaisens.omc.util.ZipCompressUtil;
import com.sunkaisens.omc.vo.core.PageBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *EntityService接口实现类
 */
@Service
public class EntityServiceImpl implements EntityService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private EntityMapper entityMapper;
	@Resource
	private CardMapper cardMapper;
	@Resource
	private ModuleMapper moduleMapper;
	@Resource
	private ConfigMapper configMapper;
	@Resource
	private ItemMapper itemMapper;
	@Resource
	private CncpTaskExecutor cncpTaskExecutor;
	@Resource
	private SendCncpMsgFactory sendCncpMsgFactory;

	@Override
	public void save(Integer moduleId,Integer instId, String jsonstr, Integer cardNum,
			String name) throws IOException, CustomException {
		String src = ServletContextUtil.getServletContext().getRealPath("/WEB-INF/repository");
		Integer maxId;
		if(instId==null){
			maxId = entityMapper.selectMaxIstdIdByModuleId(moduleId);
			if (maxId == null){
				maxId = 0;
			}
			maxId = maxId+1;
		}else{
			maxId = instId;
		}
		Module module = moduleMapper.selectById(moduleId);
		String ftpdir = OmcServerContext.getInstance().getFtpDir();
		JSONArray jsonarr = JSONArray.fromObject(jsonstr);
		File moduleDir = new File(ftpdir, module.getId() + "-" + maxId);
		moduleDir.mkdirs();
		File srcdir = new File(src, module.getName());
		FileUtils.copyDirectory(srcdir, moduleDir);

		File neStart = new File(moduleDir, "neStart.sh");
		FileUtils.writeStringToFile(neStart, "#!/bin/bash\n", false);
		FileUtils.writeStringToFile(neStart, "cfg_dir=\n", true);
		FileUtils.writeStringToFile(neStart, "log_dir=" + module.getLog()
				+ "\n", true);
		FileUtils.writeStringToFile(neStart, "lib_dir=\n", true);
		FileUtils.writeStringToFile(neStart, "sig_dir=\n", true);
		FileUtils.writeStringToFile(neStart, "display=" + name + "\n", true);
		FileUtils.writeStringToFile(neStart, "version=" + module.getVersion()
				+ "\n", true);
		FileUtils.writeStringToFile(neStart, "source ../oam.script/pre_launch\n", true);
		FileUtils.writeStringToFile(neStart, module.getExe(), true);

		File zip = convert2File(jsonarr, moduleDir, module,maxId);
		// 通知OAM下载
		String content = "file:" + module.getId() + "-" + maxId + ".zip";
		content += "\n" + "boot:true\n";
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
				CncpProtoType.SUB_OAM, cardNum, CncpProtoType.CNCP_SET_NE_PLAN_CREATE, content);
		SetUpResponseMsg resMsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
		if (resMsg.getResult() == CncpProtoType.SUCCESS) {
			Entity entity = new Entity(name, maxId, 0, 1, null, module,cardMapper.selectByNum(cardNum));
			entityMapper.insert(entity);
		} else {
			if (zip.exists())
				zip.delete();
			if (moduleDir.exists())
				FileUtils.deleteDirectory(moduleDir);
//				moduleDir.delete();
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(resMsg.getResult()));
		}
	}

	private File convert2File(JSONArray jsonarr, File moduleDir, Module module,Integer instId)
			throws CustomException, IOException {
		for (Object o : jsonarr) {
			JSONObject json = (JSONObject) o;
			// 配置文件路径
			String confName = json.getString("name");
			File confF = new File(moduleDir, confName);
			if (confF.exists())
				confF.delete();
			String kvSpe = ConfigServiceImpl.getConfKvSeparator(confName);
			JSONArray fieldarr = json.getJSONArray("fields");
			for (Object obj : fieldarr) {
				JSONObject kv = (JSONObject) obj;
				if (kvSpe == null) {// 或者 sole is true
					FileUtils.writeStringToFile(confF, kv.getString("value"),true);
				} else {
					String key = kv.getString("name");
//					Item item = itemMapper.selectBy(confName, key,module.getId());
					Item item = itemMapper.selectById(kv.getInt("id"));
					if (item.getMultiline()) {
						String values = kv.getString("value");
						for (String v : values.split("\n")) {
							String line = key + kvSpe + v.trim() + "\n";
							FileUtils.writeStringToFile(confF, line, true);
						}
					} else {
						String line = key + kvSpe + kv.getString("value")+ "\n";
						FileUtils.writeStringToFile(confF, line, true);
					}
				}
			}
		}
		File zip = new File(moduleDir + ".zip");
		ZipCompressUtil.zip(zip, moduleDir,null);
		return zip;
	}

	@Override
	public void update(String jsonstr, Integer id) throws CustomException,
			IOException {
		Map<File, String> restoreMap = new HashMap<>();
		Entity entity = entityMapper.selectById(id);
		Integer instId = entity.getInstId();
		Module module = entity.getModule();
		List<Config> configs = configMapper.selectByModuleId(module.getId());
		String ftpdir = OmcServerContext.getInstance().getFtpDir();
		File moduleDir = new File(ftpdir, module.getId() + "-" + instId);
		for (Config config : configs) {
			File confF = new File(moduleDir, config.getName());
			restoreMap.put(confF, FileUtils.readFileToString(confF));
		}
		JSONArray jsonarr = JSONArray.fromObject(jsonstr);
		File zip = convert2File(jsonarr, moduleDir, module,entity.getInstId());
		String content = "file:" + module.getId() + "-" + instId + ".zip\n";
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
				module.getId(), instId, CncpProtoType.CNCP_SET_NE_PLAN_UPDATE, content);
		SetUpResponseMsg resMsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
		if (resMsg.getResult() == CncpProtoType.SUCCESS) {

		} else {// 还原
			for (Entry<File, String> e : restoreMap.entrySet()) {
				FileUtils.writeStringToFile(e.getKey(), e.getValue(), false);
			}
			ZipCompressUtil.zip(zip, moduleDir,null);
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(resMsg.getResult()));
		}

	}

	@Override
	public void deleteById(Integer id) throws Exception {
		deleteByIds(new Integer[id]);
	}

	@Override
	public List<Entity> findAll() {
		return entityMapper.selectAll(null,null);
	}

	@Override
	public Entity getById(Integer id) {
		return entityMapper.selectById(id);
	}

	@Override
	public PageBean getPageBean(Integer requestPage, Integer pageSize,String sort,String order) {
		int count = entityMapper.selectCount();
		List<Entity> es=entityMapper.select((requestPage-1)*pageSize, Math.min(pageSize, count));
		return new PageBean(requestPage, pageSize,es ,count);
	}
	
	@Override
	public void deleteByIds(Integer[] ids) throws IOException, CustomException {
		String ftpdir = OmcServerContext.getInstance().getFtpDir();
		for (Integer id : ids) {
			Entity entity = entityMapper.selectById(id);
			Integer moduleId = entity.getModule().getId();
			Integer instId = entity.getInstId();
			SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
					CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
					moduleId, instId, CncpProtoType.CNCP_SET_NE_PLAN_DELETE, "");
			SetUpResponseMsg resMsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
			if (resMsg.getResult() == CncpProtoType.SUCCESS) {
				File file = new File(ftpdir, moduleId + "-" + instId);
				FileUtils.deleteDirectory(file);
				File zip = new File(ftpdir, moduleId + "-" + instId + ".zip");
				if (zip.exists()){
					zip.delete();
				}
				entityMapper.deleteById(id);
			} else {
				logger.info("============OAM消息回复失败==============");
				throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(resMsg.getResult()));
			}
		}
	}

	@Override
	public Module getComponentModule(Integer moduleId) {
		Module module = moduleMapper.selectById(moduleId);
		List<Config> configs = configMapper.selectByModuleId(moduleId);
		for (Config config : configs) {
			List<Item> items = itemMapper.selectByConfigId(config.getId());
			config.setItems(items);
		}
		module.setConfigs(configs);
		return module;
	}

	@Override
	public Module viewComponentModule(Integer entityId) throws CustomException,
			IOException {
		String ftpdir = OmcServerContext.getInstance().getFtpDir();
		Entity entity = entityMapper.selectById(entityId);
		Module module = entity.getModule();
		Integer moduleId = module.getId();
		Integer instId = entity.getInstId();
		List<Config> configs = configMapper.selectByModuleId(moduleId);
		for (Config config : configs) {
			String confName = config.getName();
			String kvSpe = ConfigServiceImpl.getConfKvSeparator(confName);
			File moduleDir = new File(ftpdir, moduleId + "-" + instId);
			File confF = new File(moduleDir, confName);
			if(!confF.exists()) confF.createNewFile();
			List<Item> items = itemMapper.selectByConfigId(config.getId());
			if (config.getSole()) {
				String value = FileUtils.readFileToString(confF).replaceAll(
						"[\t\n\r]+", "\n");
				items.get(0).setValue(value);
			} else {
				List<String> lines = FileUtils.readLines(confF);
				for (Item item : items) {
					Iterator<String> iterator = lines.iterator();
					String key = item.getName();
					String tmp = null;
					while (iterator.hasNext()) {
						String line = iterator.next();
						if (line.startsWith(key + kvSpe)) {
							String[] kv = line.split(kvSpe, 2);
							if (kv[0].equals(tmp)) {
								item.setValue(item.getValue() + "\n" + kv[1]);
							} else {
								item.setValue(kv[1]);
							}
							tmp = kv[0];
							iterator.remove();
							if(!item.getMultiline()) break;
						}
					}
				}
			}
			config.setItems(items);
		}
		module.setConfigs(configs);
		return module;
	}

	@Override
	public JSONArray readConfArr(Integer entityId) throws IOException {
		String ftpdir = OmcServerContext.getInstance().getFtpDir();
		Entity entity = entityMapper.selectById(entityId);
		Module module = entity.getModule();
		Integer instId = entity.getInstId();
		File moduleDir = new File(ftpdir, module.getId() + "-" + instId);
		List<Config> configs = configMapper.selectByModuleId(module.getId());
		JSONArray arr = new JSONArray();
		for (Config config : configs) {
			File confF = new File(moduleDir, config.getName());
			String content = FileUtils.readFileToString(confF);
			JSONObject json = new JSONObject();
			json.element("title", config.getName());
			json.element("content", content);
			arr.add(json);
		}
		return arr;
	}

	@Override
	public void start(Integer id) throws CustomException {
		Entity entity = entityMapper.selectById(id);
		Integer moduleId = entity.getModule().getId();
		Integer instId = entity.getInstId();
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
				moduleId, instId, CncpProtoType.CNCP_SET_NE_PROC_START, "");
		SetUpResponseMsg respMsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
		int result = respMsg.getResult();
		if (result == CncpProtoType.SUCCESS) {
			entity.setStatus(1);
			entityMapper.update(entity);
		} else {
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(result));
		}
	}

	@Override
	public void restart(Integer id) throws CustomException {
		Entity entity = entityMapper.selectById(id);
		Integer moduleId = entity.getModule().getId();
		Integer instId = entity.getInstId();
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
				moduleId, instId, CncpProtoType.CNCP_SET_NE_PROC_RESTART, "");
		SetUpResponseMsg respMsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
		int result = respMsg.getResult();
		if (result == CncpProtoType.SUCCESS) {
			entity.setStatus(1);
			entityMapper.update(entity);
		} else {
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(result));
		}
	}

	@Override
	public void stop(Integer id) throws CustomException {
		Entity entity = entityMapper.selectById(id);
		Integer moduleId = entity.getModule().getId();
		Integer instId = entity.getInstId();
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
				moduleId, instId, CncpProtoType.CNCP_SET_NE_PROC_STOP, "");
		SetUpResponseMsg respMsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
		
		int result = respMsg.getResult();
		if (result == CncpProtoType.SUCCESS) {
			entity.setStatus(0);
			entityMapper.update(entity);
		} else {
			throw new CustomException(
					CncpStatusTransUtil.transLocaleStatusMessage(result));
		}
	}

	@Override
	public Entity getByModuleAndInstId(Integer moduleId, Integer instId) {
		Entity entity = entityMapper.selectByModuleAndInstId(moduleId, instId);
		return entity;
	}

	@Override
	public void update(Entity entity) {
		entityMapper.update(entity);
	}

	@Override
	public void updateEntity(Integer id, Integer instId, Integer moduleId,
			File zipFile) throws Exception {
		if (!FileUtil.isZip(zipFile))
			throw new CustomException("此文件不是ZIP格式！");
		Map<File, byte[]> map = new HashMap<File, byte[]>();
		String ftpDir = OmcServerContext.getInstance().getFtpDir();
		File moduleDir = new File(ftpDir, moduleId + "-" + instId);
		try (ZipFile f = new ZipFile(zipFile)) {
			if (f.size() == 0) {
				throw new CustomException("压缩包为空！");
			} else{
				Enumeration<? extends ZipEntry> enums = f.entries();
				while (enums.hasMoreElements()) {
					ZipEntry entry = enums.nextElement();
					if (entry.isDirectory()) {
						throw new CustomException("请确保是网元程序，而不是文件夹");
					}
					String fileName = entry.getName();
					if (ArrayUtils.contains(moduleDir.list(), fileName)) {
						File file = new File(moduleDir, fileName);
						if (!file.canRead()) {
							throw new CustomException("原可执行文件不可读，请检查是否停掉此网元");
						}
						map.put(file, FileUtils.readFileToByteArray(file));
						InputStream input = f.getInputStream(entry);
						try (OutputStream output = new FileOutputStream(file)) {
							IOUtils.copy(input, output);
						}
						String content = "file:" + fileName + "\n";
						SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
								CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
								moduleId, instId, CncpProtoType.CNCP_SET_NE_PLAN_UPDATE, content);
						SetUpResponseMsg resMsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
						if (resMsg.getResult() == CncpProtoType.SUCCESS) {
							ServletContext servletContext = ServletContextUtil.getServletContext();
							String repositoryDir = servletContext.getRealPath("/WEB-INF/repository");
							String moduleName = moduleMapper.selectById(moduleId).getName();
							File repositoryFile = new File(new File(repositoryDir, moduleName), fileName);
							FileUtils.copyFile(file, repositoryFile);
						} else {
							for (Map.Entry<File, byte[]> e : map.entrySet()) {
								FileUtils.writeByteArrayToFile(e.getKey(),e.getValue());
							}
							throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(resMsg.getResult()));
						}
					} else {
						throw new CustomException("更新文件【"+fileName+"】应该与原文件名一致");
					}
				}
			}
		}
	}

	@Override
	public List<Entity> find(Integer moduleId) {
		return entityMapper.selectByModuleId(moduleId);
	}

	@Override
	public void setStartup(Integer id,boolean b) throws CustomException {
		Entity entity = entityMapper.selectById(id);
		Integer moduleId = entity.getModule().getId();
		Integer instId = entity.getInstId();
		int subtype=b?CncpProtoType.CNCP_SET_NE_BOOT_ACTIVE:CncpProtoType.CNCP_SET_NE_BOOT_INACTIVE;
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
				moduleId, instId, subtype, "");
		SetUpResponseMsg respMsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
		int result=respMsg.getResult();
		if(result==CncpProtoType.SUCCESS){
			entity.setType(b?1:0);
			entityMapper.update(entity);
		}else{
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(result));
		}
	}
}
