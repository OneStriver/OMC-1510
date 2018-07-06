package com.sunkaisens.omc.mapper.core;

import java.util.List;

import com.sunkaisens.omc.po.core.Options;

/**
 * 
 * 
 * 
 * 
 * 
 * 定义Options接口
 *
 */
public interface OptionsMapper {
	Integer insert(Options options);
	
	Options selectById(Integer id);
	
	List<Options> selectAll();
	
	void update(Options options);

	void deleteByIds(Integer[] ids);

	List<Options> selectByItemId(Integer itemId);
	
	void deleteByItemId(Integer itemId);
}
