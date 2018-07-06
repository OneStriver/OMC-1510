package com.sunkaisens.omc.mapper.hss.impl;

import java.util.HashMap;
import java.util.Map;

import com.sunkaisens.omc.mapper.hss.MsserviceMapper;
import com.sunkaisens.omc.po.hss.Msservice;

public class MsserviceMapperImpl extends HssSqlSessionDaoSupport implements
		MsserviceMapper {

	@Override
	public void insert(Msservice msservice) {
		int index=getHssDatabaseIndex(msservice.getImsi());
		getSqlSession(index).insert(namespace+".insert", msservice);
	}

	@Override
	public void deleteById(String imsi) {
		int index=getHssDatabaseIndex(imsi);
		getSqlSession(index).delete(namespace+".deleteById", imsi);
	}

	@Override
	public void update(Msservice msservice) {
		int index=getHssDatabaseIndex(msservice.getImsi());
		getSqlSession(index).update(namespace+".update", msservice);
	}

	@Override
	public Msservice selectById(String imsi) {
		return getSqlSession().selectOne(namespace+".selectById", imsi);
	}

	@Override
	public void batchAdd(int count, String imsi,String WireTapAddr) {	
		Map<String,Object> param=new HashMap<>();
		param.put("count", count);
		param.put("imsi", imsi);
		param.put("WireTapAddr", WireTapAddr);
		getSqlSession().insert(namespace+".batchAdd", param);
	}

}
