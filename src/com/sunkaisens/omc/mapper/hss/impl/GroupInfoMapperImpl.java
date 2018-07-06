package com.sunkaisens.omc.mapper.hss.impl;

import com.sunkaisens.omc.mapper.hss.GroupInfoMapper;
import com.sunkaisens.omc.po.hss.GroupInfo;

public class GroupInfoMapperImpl extends HssSqlSessionDaoSupport implements GroupInfoMapper {

	@Override
	public void insert(GroupInfo groupInfo) {
		int index=getHssDatabaseIndex(groupInfo.getImsi());
		getSqlSession(index).insert(namespace+".insert", groupInfo);
	}

	@Override
	public void deleteById(String imsi) {
		int index=getHssDatabaseIndex(imsi);
		getSqlSession(index).delete(namespace+".deleteById", imsi);
	}

	@Override
	public void update(GroupInfo groupInfo) {
		int index=getHssDatabaseIndex(groupInfo.getImsi());
		getSqlSession(index).update(namespace+".update", groupInfo);
	}

	@Override
	public GroupInfo selectById(String imsi) {
		return getSqlSession().selectOne(namespace+".selectById", imsi);
	}

}
