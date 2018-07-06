package com.sunkaisens.omc.mapper.core;

import java.util.List;

import com.sunkaisens.omc.po.core.Role;

/**
 * 
 * 
 * 
 * 
 * 
 * 定义Role接口
 *
 */
public interface RoleMapper {
	Integer insert(Role role);
	
	void deleteById(Integer id);
	
	void deleteRolePrivileges(Integer roleId);
	
	void deleteRoleUsers(Integer roleId);
	
	void update(Role role);
	
	Role selectById(Integer id);
	
	void insertRolePrivileges(Role role);
	
	List<Role> selectAll();

	Role selectByName(String name);
}
