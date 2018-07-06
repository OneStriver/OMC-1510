package com.sunkaisens.omc.service.core;

import java.util.ResourceBundle;

import com.sunkaisens.omc.vo.core.PageBean;
/**
 * 
 * 
 * 
 * 
 *  定义LogService接口
 *
 */
public interface LogService {
	/**
	 * 
	 * 
	 * 
	 * 
	 * 获取数据分页列表
	 * @param page
	 * @param pageSize
	 * @param sort
	 * @param order
	 * @return
	 */
	PageBean getPageBean(Integer page, Integer pageSize, String sort,
			String order,ResourceBundle bundle);
	/**
	 * 
	 * 
	 * 
	 * 
	 * 删除数据
	 * @param ids
	 */
	void deleteByIds(Integer[] ids);

}
