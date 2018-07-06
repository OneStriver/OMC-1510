package com.sunkaisens.omc.mapper.hss.impl;

import java.util.HashMap;
import java.util.Map;

import com.sunkaisens.omc.mapper.hss.AucMapper;
import com.sunkaisens.omc.po.hss.Auc;

public class AucMapperImpl extends HssSqlSessionDaoSupport implements AucMapper {

	@Override
	public void insert(Auc auc) {
		int index=getHssDatabaseIndex(auc.getImsi());
		getSqlSession(index).insert(namespace+".insert", auc);
	}

	@Override
	public void deleteById(String imsi) {
		int index=getHssDatabaseIndex(imsi);
		getSqlSession(index).delete(namespace+".deleteById", imsi);
	}

	@Override
	public void update(Auc auc) {
		int index=getHssDatabaseIndex(auc.getImsi());
		getSqlSession(index).update(namespace+".update", auc);
	}

	@Override
	public Auc selectById(String imsi) {
		return getSqlSession().selectOne(namespace+".selectById", imsi);
	}

	@Override
	public void batchAdd(int count, String imsi) {
		Map<String,Object> param=new HashMap<>();
		param.put("count", count);
		param.put("imsi", imsi);
		getSqlSession().insert(namespace+".batchAdd",param);
	}

}
