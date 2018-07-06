package com.sunkaisens.omc.service.core;

import java.util.List;

import com.sunkaisens.omc.po.core.Config;
import com.sunkaisens.omc.vo.core.PageBean;
/**
 * 
 * 
 * 
 * 
 * 定义ConfigService接口 
 *
 */
public interface ConfigService  {

	/**
	 * 
	 * 
	 * 
	 * 
	 * 分页显示配置文件
	 * @param page
	 * @param pageSize
	 * @return
	 */
	PageBean getPageBean(Integer page, Integer pageSize);

	/**
	 * 
	 * 
	 * 
	 * 
	 * 修改配置文件
	 * @param config
	 */
	void update(Config config);

	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回配置文件BY id
	 * @param id
	 * @return
	 */
	Config findById(Integer id);

	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 删除配置文件
	 * @param ids
	 */
	void delete(Integer[] ids);

	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回所有配置文件
	 * @return
	 */
	List<Config> findAll();

	/**
	 * 
	 * 
	 * 
	 * 
	 * 上传新配置文件
	 * @param moduleId
	 * @param name
	 * @param content
	 * @throws Exception
	 */
	void saveUpload(Integer moduleId, String name, String content) throws Exception;
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回配置文件ByModuleIdAndName
	 * @param moduleId
	 * @param name
	 * @return
	 */
	Config findByModuleIdAndName(Integer moduleId, String name);
}
