package com.sunkaisens.omc.mapper.core;

import java.util.List;

import com.sunkaisens.omc.po.core.Privilege;

/**
 * 
 * 
 * 
 * 
 * 
 * 定义Privilege接口
 *
 */
public interface PrivilegeMapper {
	Integer insert(Privilege privilege);
	
	Privilege selectById(Integer id);
	
	List<Privilege> selectAll();

	List<Privilege> selectMenuPrivileges();
	
	List<Privilege> selectByParentId(Integer parentId);

	List<Privilege> selectByIds(Integer[] ids);
	
	Privilege selectByUrl(String url);

	List<String> selectAllUrl();
}
