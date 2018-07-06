package com.sunkaisens.omc.mapper.hss;

import org.apache.ibatis.annotations.Param;

import com.sunkaisens.omc.po.hss.Auc;
/**
 * 
 * 
 * 
 * 
 * 
 *定义AUC接口
 */
public interface AucMapper {
	void insert(Auc auc);
	void deleteById(String imsi);
	void update(Auc auc);
	Auc selectById(String imsi);
	void batchAdd(@Param("count")int count,@Param("imsi")String imsi);
}
