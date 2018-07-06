package com.sunkaisens.omc.mapper.core;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sunkaisens.omc.po.core.Common;

/**
 * 
 * 
 * 
 * 
 * 
 * 定义Common接口
 *
 */
public interface CommonMapper {
	Integer insert(Common common);
	
	Common selectById(Integer id);
	
	List<Common> selectAll();
	
	void update(Common common);

	void deleteById(Integer id);

	Common selectByName(String name);
	Integer selectCount();
	List<Common> select(@Param("jumpNum") Integer jumpNum,
			@Param("limit") Integer limit);
}
