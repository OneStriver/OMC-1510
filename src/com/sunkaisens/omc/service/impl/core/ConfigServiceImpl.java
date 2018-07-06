package com.sunkaisens.omc.service.impl.core;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.mapper.core.ConfigMapper;
import com.sunkaisens.omc.mapper.core.ItemMapper;
import com.sunkaisens.omc.mapper.core.ModuleMapper;
import com.sunkaisens.omc.po.core.Config;
import com.sunkaisens.omc.po.core.Item;
import com.sunkaisens.omc.po.core.Module;
import com.sunkaisens.omc.service.core.ConfigService;
import com.sunkaisens.omc.vo.core.PageBean;
/**
 * 
 * 
 * 
 * 
 * 
 *ConfigService接口实现类
 *
 */
@Service
public class ConfigServiceImpl implements ConfigService {

	@Resource
	private ConfigMapper mapper;
	@Resource
	private ItemMapper itemMapper;
	@Resource
	private ModuleMapper muduleMapper;
	@Override
	public PageBean getPageBean(Integer page, Integer pageSize) {
		int count = mapper.selectCount();
		List<Config> configs=mapper.select((page-1)*pageSize, Math.min(pageSize, count));
		
		PageBean pageBean=new PageBean(page, pageSize, configs, count);
		return pageBean;
	}

	@Override
	public void update(Config config) {
		mapper.update(config);
	}

	@Override
	public Config findById(Integer id) {
		return mapper.selectById(id);
	}
	
	@Override
	public void delete(Integer[] ids){
		if(ids==null) return ;
		for(Integer id:ids){
			itemMapper.deleteByConfigId(id);
			mapper.deleteById(id);
		}
	}

	@Override
	public List<Config> findAll() {
		return mapper.selectAll();
	}

	@Override
	public void saveUpload(Integer moduleId,String name, String content) throws Exception {
		Module module=muduleMapper.selectById(moduleId);
		for(Config c:mapper.selectByModuleId(moduleId)){
			if(c.getName().equals(name)){
				throw new CustomException("此网元已存在该配置文件【"+name+"】");
			}
		}
		Config config = new Config(name, null, null, false,module);
		String kvSep=getConfKvSeparator(name);
		if(kvSep==null) config.setSole(true);
		mapper.insert(config);
		if(config.getSole()){//单独配置文件项
			Item item=new Item();
			item.setName(config.getName());
			item.setDescription(config.getName());
			item.setFormtype("textbox");
			item.setMultiline(true);
			item.setConfig(config);
			item.setValue(content.replaceAll("[\t\n\r]+", "\n"));
			if(StringUtils.isNotBlank(item.getValue()))
				item.setRequired(true);
			itemMapper.insert(item);
		}else{//Key-Value形式
			parseItem(content, config,kvSep,itemMapper);
		}
	}
	
	public static void parseItem(String content, Config config, String kvSep,ItemMapper itemMapper)
			throws IOException, CustomException {
		List<String> lines = Arrays.asList(content.split("\n"));
//		lines.sort(new Comparator<String>() {
//			public int compare(String o1, String o2) {
//				return o1.compareTo(o2);
//			}
//		});
		Item item=null;
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i).trim();
			if (StringUtils.isEmpty(line) || line.startsWith("#"))
				continue;
			String[] kv = line.split(kvSep, 2);
			if (kv.length != 2)
				throw new CustomException(config.getName() + " 存在非法配置在第"
						+ (i + 1) + "行！");
			if(item!=null&&kv[0].trim().equals(item.getName())){
				item.setMultiline(true);
				item.setValue(item.getValue()+"\n"+kv[1].trim());
				itemMapper.update(item);
			}else{
				item=new Item();
				item.setFormtype("textbox");
				item.setMultiline(false);
				item.setName(kv[0].trim());
				item.setDescription(kv[0].trim());
				item.setValue(kv[1].trim());
				item.setConfig(config);
				if(StringUtils.isNotBlank(item.getValue()))
					item.setRequired(true);
				itemMapper.insert(item);
			}
		}
	}
	
	public static String getConfKvSeparator(String confName) throws CustomException {
		String suffix = confName.substring(confName.lastIndexOf("."))
				.toLowerCase();
		switch (suffix) {
		case ".config":
			return ":";
		case ".properties":
			return "=";
		default:
			return null;//throw new CustomException("不支持【" + confName + "】的扩展名！");
		}
	}

	@Override
	public Config findByModuleIdAndName(Integer moduleId, String name) {
		return mapper.selectByModuleIdAndName(moduleId,name);
	}
}
