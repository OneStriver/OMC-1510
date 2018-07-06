package com.sunkaisens.omc.mapper.hss.impl;

import java.util.HashMap;
import java.util.Map;

import com.sunkaisens.omc.mapper.hss.EPCSubscriptionTFTMapper;
import com.sunkaisens.omc.po.hss.EPCSubscriptionTFT;

public class EPCSubscriptionTFTMapperImpl extends HssSqlSessionDaoSupport
		implements EPCSubscriptionTFTMapper {

	@Override
	public void insert(EPCSubscriptionTFT epcSubscriptionTFT) {
		int index=getHssDatabaseIndex(epcSubscriptionTFT.getImsi());
		getSqlSession(index).insert(namespace+".insert", epcSubscriptionTFT);
	}

	@Override
	public void deleteById(String imsi) {
		int index=getHssDatabaseIndex(imsi);
		getSqlSession(index).delete(namespace+".deleteById", imsi);
	}

	@Override
	public void update(EPCSubscriptionTFT epcSubscriptionTFT) {
		int index=getHssDatabaseIndex(epcSubscriptionTFT.getImsi());
		getSqlSession(index).update(namespace+".update", epcSubscriptionTFT);
	}

	@Override
	public EPCSubscriptionTFT selectById(String imsi) {
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
