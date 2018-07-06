package com.sunkaisens.omc.service.core;

import java.util.List;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.po.core.Item;
import com.sunkaisens.omc.vo.core.PageBean;
/**
 * 
 * 
 * 
 * 
 *  ItemService接口
 *
 */
public interface ItemService  {
	/**
	 * 
	 * 
	 * 
	 * 
	 * 修改配置项
	 * @param item
	 */
	void update(Item item);

	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回配置项
	 * @param id
	 * @return
	 */
	Item findById(Integer id);

	/**
	 * 
	 * 
	 * 
	 * 
	 * 删除配置项
	 * @param ids
	 */
	void delete(Integer[] ids);

	/**
	 * 
	 * 
	 * 
	 * 
	 * 添加配置项
	 * @param item
	 * @throws CustomException 
	 */
	void save(Item item) throws CustomException;
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回此配置文件所有配置项
	 * @param configId
	 * @return
	 */
	List<Item> findByConfigId(Integer configId);

	/**
	 * 
	 * 
	 * 
	 * 
	 * 分页
	 * @param page
	 * @param pageSize
	 * @param configId
	 * @param sort
	 * @param order
	 * @return
	 */
	PageBean getPageBean(Integer page, Integer pageSize, Integer configId,
			String sort, String order);
	
	
}
