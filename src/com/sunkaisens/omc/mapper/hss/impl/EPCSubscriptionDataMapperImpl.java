package com.sunkaisens.omc.mapper.hss.impl;

import java.util.HashMap;
import java.util.Map;

import com.sunkaisens.omc.mapper.hss.EPCSubscriptionDataMapper;
import com.sunkaisens.omc.po.hss.EPCSubscriptionData;

public class EPCSubscriptionDataMapperImpl extends HssSqlSessionDaoSupport
		implements EPCSubscriptionDataMapper {

	@Override
	public void insert(EPCSubscriptionData epc) {
		int index=getHssDatabaseIndex(epc.getImsi());
		getSqlSession(index).insert(namespace+".insert", epc);
	}

	@Override
	public void deleteById(String imsi) {
		int index=getHssDatabaseIndex(imsi);
		getSqlSession(index).delete(namespace+".deleteById", imsi);
	}

	@Override
	public void update(EPCSubscriptionData epc) {
		int index=getHssDatabaseIndex(epc.getImsi());
		getSqlSession(index).update(namespace+".update", epc);
	}

	@Override
	public EPCSubscriptionData selectById(String imsi) {
		return getSqlSession().selectOne(namespace+".selectById", imsi);
	}

	@Override
	public void batchAdd(int count, String imsi) {
		Map<String,Object> param=new HashMap<>();
		param.put("count", count);
		param.put("imsi", imsi);
		getSqlSession().insert(namespace+".batchAdd", imsi);
	}

}
