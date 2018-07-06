package com.sunkaisens.omc.service.core;

import com.sunkaisens.omc.vo.core.PageBean;
/**
 * 
 * 
 * 
 * 
 *  定义AlarmService接口
 *
 */
public interface AlarmService {
	/**
	 * 
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
			String order);
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
