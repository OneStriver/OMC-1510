package com.sunkaisens.omc.mapper.core;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sunkaisens.omc.po.core.Module;

/**
 * 
 * 
 * 
 * 
 * 
 * 
 *定义Module接口
 */
public interface ModuleMapper {
	Integer insert(Module module);
	
	Module selectById(Integer id);
	
	List<Module> selectAll();
	List<Module> select(@Param("jumpNum") Integer jumpNum,
			@Param("limit") Integer limit);
	void update(Module module);

	void deleteById(Integer id);

	Module selectByName(String name);

	Integer selectMaxId();
	Integer selectCount();
}
