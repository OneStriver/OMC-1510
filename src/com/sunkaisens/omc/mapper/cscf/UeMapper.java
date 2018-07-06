package com.sunkaisens.omc.mapper.cscf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sunkaisens.omc.po.cscf.Ue;
/**
 * 
 * 
 * 
 * 
 * 
 * 定义Ue接口
 *
 */
public interface UeMapper {
	void insert(Ue ue);
	void deleteById(String ueUri);
	void update(Ue ue);
	Ue selectById(String ueUri);
	Ue selectByName(String ueName);
	List<Ue> select(@Param("jumpNum")Integer jumpNum,
			@Param("limit")Integer limit, @Param("ue")Ue ue);
	Integer selectCount(@Param("ue")Ue ue);
	void deleteByName(String name);
	void batchAdd(@Param("count")Integer count,@Param("mdn")String mdn,
			@Param("domain")String domain,@Param("uePassword")String uePassword);
	void batchDelete(@Param("count")Integer count,@Param("mdn")String mdn);
}
