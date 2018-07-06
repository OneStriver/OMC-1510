package com.sunkaisens.omc.mapper.core;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sunkaisens.omc.po.core.Alarm;
/**
 * 
 * 
 * 
 * 
 * 
 *定义Alarm接口
 */
public interface AlarmMapper {
	Integer insert(Alarm alarm);
	
	List<Alarm> select(@Param("jumpNum") Integer jumpNum,@Param("limit") Integer limit,
			@Param("sort") String sort,@Param("order") String order);
	
	void deleteByIds(Integer[] ids);

	int selectCount();
	
	void deleteBefore(Timestamp date);
}
