package com.sunkaisens.omc.mapper.hss.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunkaisens.omc.mapper.hss.HssGroupMapper;
import com.sunkaisens.omc.po.hss.HssGroup;

public class HssGroupMapperImpl extends HssSqlSessionDaoSupport implements
		HssGroupMapper {

	@Override
	public void insert(HssGroup group) {
		int index=getHssDatabaseIndex(group.getId());
		getSqlSession(index).insert(namespace+".insert", group);
	}

	@Override
	public void deleteById(String id) {
		int index=getHssDatabaseIndex(id);
		getSqlSession(index).delete(namespace+".deleteById", id);
	}

	@Override
	public void update(HssGroup group) {
		int index=getHssDatabaseIndex(group.getId());
		getSqlSession(index).update(namespace+".update", group);
	}

	@Override
	public HssGroup selectById(String id) {
		return getSqlSession().selectOne(namespace+".selectById", id);
	}

	@Override
	public List<HssGroup> select(int jumpNum, int limit, HssGroup group) {
		Map<String,Object> param=new HashMap<>();
		param.put("jumpNum", jumpNum);
		param.put("limit", limit);
		param.put("group", group);
		return getSqlSession().selectList(namespace+".select", param);
	}

	@Override
	public int selectCount(HssGroup group) {
		Map<String,Object> param=new HashMap<>();
		param.put("group", group);
		return getSqlSession().selectOne(namespace+".selectCount", param);
	}

	@Override
	public List<Map<String, ?>> getGroupNumber() {
		return getSqlSession().selectOne(namespace+".getGroupNumber");
	}

}
