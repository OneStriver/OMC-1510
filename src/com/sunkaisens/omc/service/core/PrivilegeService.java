package com.sunkaisens.omc.service.core;

import java.util.List;

import com.sunkaisens.omc.po.core.Privilege;
/**
 * 
 * 
 * 
 * 
 * 定义PrivilegeService接口 
 *
 */
public interface PrivilegeService {
	/**
	 * 
	 * 
	 * 
	 * 
	 * 添加权限
	 * @param privilege
	 */
	void save(Privilege privilege);

	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回所有权限URL
	 * @return
	 */
	List<String> getAllPrivilegeUrl();

	/**
	 * 
	 * 
	 * 
	 * 
	 * 树形菜单
	 * @return
	 */
	List<Privilege> getPrivilegeMene();

	/**
	 * 
	 * 
	 * 
	 * 
	 * 权限树
	 * @return
	 */
	List<Privilege> getTree();

}
