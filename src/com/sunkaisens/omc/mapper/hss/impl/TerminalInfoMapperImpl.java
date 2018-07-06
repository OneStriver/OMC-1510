package com.sunkaisens.omc.mapper.hss.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.sunkaisens.omc.mapper.hss.TerminalInfoMapper;
import com.sunkaisens.omc.po.hss.TerminalInfo;

public class TerminalInfoMapperImpl extends HssSqlSessionDaoSupport implements TerminalInfoMapper {

	@Override
	public void insert(TerminalInfo info) {
		int index = getHssDatabaseIndex(info.getImsi());
		getSqlSession(index).insert(namespace + ".insert", info);
	}

	@Override
	public void deleteById(String imsi) {
		int index = getHssDatabaseIndex(imsi);
		getSqlSession(index).delete(namespace + ".deleteById", imsi);
	}

	@Override
	public void update(TerminalInfo info) {
		int index = getHssDatabaseIndex(info.getImsi());
		getSqlSession(index).update(namespace + ".update", info);
	}

	@Override
	public TerminalInfo selectById(String imsi) {
		return getSqlSession().selectOne(namespace + ".selectById", imsi);
	}

	@Override
	public void batchAddTerminal(int batchCount, String imsi,String terminalId, int terminalType, int powerLevel,
			int suportBuss, int gwId, String department, int userType, 
			Timestamp createTime, int servicePriority) {
		Map<String, Object> param = new HashMap<>();
		param.put("count", batchCount);
		param.put("imsi", imsi);
		param.put("terminalId", terminalId);
		param.put("terminalType", terminalType);
		param.put("powerLevel", powerLevel);
		param.put("suportBuss", suportBuss);
		param.put("gwId", gwId);
		param.put("department", department);
		param.put("userType", userType);
		param.put("createTime", createTime);
		param.put("servicePriority", servicePriority);
		getSqlSession().insert(namespace + ".batchAddTerminal", param);

	}

}
