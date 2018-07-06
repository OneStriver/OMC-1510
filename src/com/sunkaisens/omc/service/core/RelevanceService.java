package com.sunkaisens.omc.service.core;

import java.util.List;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.po.core.Item;
import com.sunkaisens.omc.po.core.Relevance;
import com.sunkaisens.omc.vo.core.PageBean;
/**
 * 
 * 
 * 
 * 
 *定义RevanceService接口  
 *
 */
public interface RelevanceService {
	/**
	 * 
	 * 
	 * 
	 * 
	 * 添加配置参数
	 * @param relevance
	 * @throws CustomException
	 */
	void save(Relevance relevance) throws CustomException;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 修改配置参数
	 * @param relevance
	 * @throws Exception
	 */
	void update(Relevance relevance) throws Exception;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 删除
	 * @param id
	 */
	void deleteById(Integer id);
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 返回所有配置参数
	 * @return
	 */
	List<Relevance> findAll();
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 返回配置参数
	 * @param id
	 * @return
	 */
	Relevance getById(Integer id);
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 分页
	 * @param requestPage
	 * @param pageSize
	 * @param sort
	 * @param order
	 * @return
	 */
	PageBean getPageBean(Integer requestPage, Integer pageSize,String sort,String order);
	/**
	 * 
	 * 
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
	 * 
	 * 返回关联配置项
	 * @param id
	 * @return
	 */
	List<Item> getItemById(Integer id);
}
