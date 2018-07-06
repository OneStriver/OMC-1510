package com.sunkaisens.omc.mapper.core;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sunkaisens.omc.po.core.Config;
/**
 * 
 * 
 * 
 * 
 * 
 * 定义Config接口
 *
 */
public interface ConfigMapper {
	Integer insert(Config config);
	
	Config selectById(Integer id);
	
	List<Config> selectAll();
	
	List<Config> select(@Param("jumpNum") Integer jumpNum,
			@Param("limit") Integer limit);
	
	void update(Config config);

	void deleteById(Integer id);
	
	List<Config> selectByModuleId(Integer moduleId);

	Config selectByModuleIdAndName(@Param("moduleId")Integer moduleId,@Param("name")String name);
	Integer selectCount();
}
