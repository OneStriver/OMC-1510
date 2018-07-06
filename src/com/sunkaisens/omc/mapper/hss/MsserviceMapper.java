package com.sunkaisens.omc.mapper.hss;

import org.apache.ibatis.annotations.Param;

import com.sunkaisens.omc.po.hss.Msservice;
/**
 * 
 * 
 * 
 * 
 * 
 * 定义Msservice接口
 *
 */
public interface MsserviceMapper {
	void insert(Msservice msservice);
	void deleteById(String imsi);
	void update(Msservice msservice);
	Msservice selectById(String imsi);
	void batchAdd(@Param("count")int count,@Param("imsi")String imsi,@Param("WireTapAddr")String WireTapAddr);
}
