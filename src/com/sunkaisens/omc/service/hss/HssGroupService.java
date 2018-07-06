package com.sunkaisens.omc.service.hss;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.po.hss.HssGroup;
import com.sunkaisens.omc.vo.core.PageBean;
/**
 * 
 * 
 * 
 * 
 * 
 *定义HssGroupService接口
 *
 */
public interface HssGroupService {

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 组分页
	 * @param page
	 * @param pageSize
	 * @param group
	 * @return
	 */
	PageBean getPageBean(Integer page, Integer pageSize, HssGroup group);

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 添加组
	 * @param group
	 * @throws CustomException
	 */
	void save(HssGroup group) throws CustomException;

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 修改组
	 * @param group
	 * @throws CustomException
	 */
	void update(HssGroup group) throws CustomException;

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 删除组
	 * @param ids
	 * @throws CustomException
	 */
	void delete(String[] ids) throws CustomException;

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 同步删除消息
	 * @param ids
	 */
	void syncGroupDel(int flag,String[] ids);
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 同步更新消息
	 * @param ids
	 */
	void syncGroupUpdate(int flag,long groupId);
}
