package com.sunkaisens.omc.mapper.core;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sunkaisens.omc.po.core.Item;
/**
 * 
 * 
 * 
 * 
 * 
 *定义Item接口
 */
public interface ItemMapper {
	Integer insert(Item item);
	
	Item selectById(Integer id);
	
	List<Item> selectAll();
	
	void update(Item item);

	void deleteById(Integer id);
	
	List<Item> select(@Param("jumpNum") Integer jumpNum,
			@Param("limit") Integer limit,@Param("configId") Integer configId,@Param("sort") String sort,@Param("order") String order);
	
	int selectCount(Integer configId);

	void deleteByConfigId(Integer id);
	
	List<Item> getItemByRelevanceId(Integer relevanceId);
	
	List<Item> select1Level(Integer configId);
	
	List<Item> selectByConfigId(Integer configId);

//	List<Item> selectBy(@Param("confName")String confName, 
//			@Param("name")String name,@Param("moduleId")Integer moduleId);
}
