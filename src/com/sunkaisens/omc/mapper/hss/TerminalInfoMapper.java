package com.sunkaisens.omc.mapper.hss;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Param;

import com.sunkaisens.omc.po.hss.TerminalInfo;

/**
 * 
 * 
 * 
 * 
 * 
 * 定义TerminalInfo接口
 *
 */
public interface TerminalInfoMapper {
	void insert(TerminalInfo info);

	void deleteById(String imsi);

	void update(TerminalInfo info);

	TerminalInfo selectById(String imsi);

	void batchAddTerminal(@Param("count") int batchCount, @Param("imsi") String imsi, @Param("terminalId") String terminalId,
			@Param("terminalType") int terminalType, @Param("powerLevel") int powerLevel,
			@Param("suportBuss") int suportBuss, @Param("gwId") int gwId, @Param("department") String department, @Param("userType") int userType,
			@Param("createTime") Timestamp createTime, @Param("servicePriority") int servicePriority);
}
