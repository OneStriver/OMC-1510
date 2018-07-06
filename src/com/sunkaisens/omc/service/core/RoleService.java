package com.sunkaisens.omc.service.core;

import java.util.List;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.po.core.Role;
/**
 * 
 * 
 * 
 * 
 * 
 *  定义RoleService接口
 *
 */
public interface RoleService {
	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回所有角色
	 * @return
	 */
	List<Role> getAll();
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回角色ByIds
	 * @param ids
	 * @return
	 */
	List<Role> getRolesByIds(Integer... ids);

	/**
	 * 
	 * 
	 * 
	 * 
	 * 添加角色
	 * @param role
	 * @param privilegeIds
	 * @throws CustomException
	 */
	void save(Role role, Integer[] privilegeIds) throws CustomException;

	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回角色
	 * @param id
	 * @return
	 */
	Role findById(Integer id);

	/**
	 * 
	 * 
	 * 
	 * 
	 * 删除角色
	 * @param ids
	 */
	void delete(Integer[] ids);

	/**
	 * 
	 * 
	 * 
	 * 
	 * 修改角色
	 * @param role
	 * @param privilegeIds
	 * @throws CustomException
	 */
	void update(Role role, Integer[] privilegeIds) throws CustomException;

}
