package com.sunkaisens.omc.mapper.core;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sunkaisens.omc.po.core.Log;
/**
 * 
 * 
 * 
 * 
 * 
 * 定义Log接口
 *
 */
public interface LogMapper {
	Integer insert(Log log);
	
	List<Log> select(@Param("jumpNum") Integer jumpNum,@Param("limit") Integer limit,
			@Param("sort") String sort,@Param("order") String order);
	
	void deleteByIds(Integer[] ids);

	int selectCount();
	
	void deleteBefore(Timestamp date);
}
