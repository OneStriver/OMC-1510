package com.sunkaisens.omc.mapper.core;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sunkaisens.omc.po.core.Entity;
/**
 * 
 * 
 * 
 * 
 * 
 *  定义Entity接口
 *
 */
public interface EntityMapper {
	Integer insert(Entity soft);
	
	Entity selectById(Integer id);
	
	List<Entity> selectAll(@Param("sort") String sort,@Param("order") String order);
	
	void update(Entity soft);

	void deleteById(Integer id);
	
	List<Entity> select(@Param("jumpNum") int jumpNum,@Param("limit") int limit);
	
	Integer selectCount();

	Integer selectMaxIstdIdByModuleId(Integer moduleId);

	Entity selectByModuleAndInstId(@Param("moduleId")Integer moduleId,@Param("instId") Integer instId);

	List<Entity> selectByModuleId(Integer moduleId);

}
