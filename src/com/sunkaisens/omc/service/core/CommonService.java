package com.sunkaisens.omc.service.core;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONArray;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.po.core.Common;
import com.sunkaisens.omc.po.core.Relevance;
import com.sunkaisens.omc.vo.core.PageBean;
/**
 * 
 * 
 * 
 *  定义CommonService接口
 *
 */
public interface CommonService {
	/**
	 * 
	 * 
	 * 
	 * 添加配置页
	 * @param common
	 * @throws CustomException
	 */
	void save(Common common) throws CustomException;
	
	/**
	 * 
	 * 
	 * 
	 * 修改配置页
	 * @param common
	 */
	void update(Common common);
	
	/**
	 * 
	 * 
	 * 
	 * 删除配置页
	 * @param id
	 */
	void deleteById(Integer id);
	
	/**
	 * 
	 * 
	 * 
	 * 返回所有配置页
	 * @return
	 */
	List<Common> findAll();
	
	/**
	 * 
	 * 
	 * 
	 * 返回一页
	 * @param id
	 * @return
	 */
	Common getById(Integer id);
	
	/**
	 * 
	 * 
	 * 
	 * 分页显示配置页
	 * @param requestPage
	 * @param pageSize
	 * @return
	 */
	PageBean getPageBean(Integer requestPage, Integer pageSize);
	
	/**
	 * 
	 * 
	 * 
	 * 删除
	 * @param ids
	 */
	void deleteByIds(Integer[] ids);
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回该配置页配置项
	 * @param id
	 * @return
	 * @throws IOException
	 * @throws CustomException
	 */
	List<Relevance> generateComponent(Integer id) throws IOException, CustomException;
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 修改配置项
	 * @param arr
	 * @throws IOException
	 * @throws CustomException
	 */
	void modifyConf(JSONArray arr) throws IOException, CustomException;
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回关联配置项
	 * @param id
	 * @return
	 */
	List<Relevance> getRelevanceById(Integer id);
}
