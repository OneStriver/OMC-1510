package com.sunkaisens.omc.mapper.core;

import java.util.List;

import com.sunkaisens.omc.po.core.So;

/**
 * 
 * 
 * 
 * 
 * 
 * 定义So接口
 *
 */
public interface SoMapper {
	Integer insert(So so);
	
	void deleteByName(String name);
	
	List<So> selectAll();
	
	So selectById(Integer id);
	
	So selectByName(String name);
	Integer selectCount();
}
