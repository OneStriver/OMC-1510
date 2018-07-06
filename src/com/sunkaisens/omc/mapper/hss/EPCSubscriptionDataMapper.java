package com.sunkaisens.omc.mapper.hss;

import org.apache.ibatis.annotations.Param;

import com.sunkaisens.omc.po.hss.EPCSubscriptionData;
/**
 * 
 * 
 * 
 * 
 * 
 * 定义EPCSubscriptionData接口
 *
 */
public interface EPCSubscriptionDataMapper {
	void insert(EPCSubscriptionData epc);
	void deleteById(String imsi);
	void update(EPCSubscriptionData epc);
	EPCSubscriptionData selectById(String imsi);
	void batchAdd(@Param("count")int count,@Param("imsi")String imsi);
}
