package com.sunkaisens.omc.mapper.hss;

import org.apache.ibatis.annotations.Param;

import com.sunkaisens.omc.po.hss.EPCSubscriptionTFT;
/**
 * 
 * 
 * 
 * 
 * 
 * 定义EPCSubscriptionTFT接口
 *
 */
public interface EPCSubscriptionTFTMapper {
	void insert(EPCSubscriptionTFT epcSubscriptionTFT);
	void deleteById(String imsi);
	void update(EPCSubscriptionTFT epcSubscriptionTFT);
	EPCSubscriptionTFT selectById(String imsi);
	void batchAdd(@Param("count")int count,@Param("imsi")String imsi);
}
