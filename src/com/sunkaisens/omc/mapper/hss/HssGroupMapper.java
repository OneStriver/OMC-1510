package com.sunkaisens.omc.mapper.hss;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sunkaisens.omc.po.hss.HssGroup;
/**
 * 
 * 
 * 
 * 
 * 
 * 定义HssGroup接口
 *
 */
public interface HssGroupMapper {
	void insert(HssGroup group);
	
	void deleteById(String id);
	
	void update(HssGroup group);
	
	HssGroup selectById(String id);
	
	List<HssGroup> select(@Param("jumpNum") int jumpNum,@Param("limit") int limit,
			@Param("group")HssGroup group);
	
	int selectCount(@Param("group")HssGroup group);
	
	List<Map<String,?>> getGroupNumber();
}
