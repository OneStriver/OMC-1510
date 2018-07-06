package com.sunkaisens.omc.mapper.hss.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunkaisens.omc.mapper.hss.GroupMemberMapper;
import com.sunkaisens.omc.po.hss.GroupMember;

public class GroupMemberMapperImpl extends HssSqlSessionDaoSupport implements
		GroupMemberMapper {

	@Override
	public void insert(GroupMember member) {
		int index=getHssDatabaseIndex(member.getMdn());
		getSqlSession(index).insert(namespace+".insert", member);
	}

	@Override
	public void deleteById(String mdn, String groupId) {
		int index=getHssDatabaseIndex(mdn);
		Map<String,Object> param=new HashMap<>();
		param.put("mdn", mdn);
		param.put("groupId", groupId);
		getSqlSession(index).delete(namespace+".deleteById", param);
	}

	@Override
	public void update(GroupMember member) {
		int index=getHssDatabaseIndex(member.getMdn());
		getSqlSession(index).update(namespace+".update", member);
	}

	@Override
	public GroupMember selectById(String mdn, String groupId) {
		Map<String,Object> param=new HashMap<>();
		param.put("mdn", mdn);
		param.put("groupId", groupId);
		return getSqlSession().selectOne(namespace+".selectById", param);
	}

	@Override
	public List<GroupMember> select(int jumpNum, int limit, GroupMember member) {
		Map<String,Object> param=new HashMap<>();
		param.put("jumpNum", jumpNum);
		param.put("limit", limit);
		param.put("member", member);
		return getSqlSession().selectList(namespace+".select", param);
	}

	@Override
	public int selectCount(GroupMember member) {
		Map<String,Object> param=new HashMap<>();
		param.put("member", member);
		return getSqlSession().selectOne(namespace+".selectCount", param);
	}

	@Override
	public void deleteByMdn(String mdn) {
		int index=getHssDatabaseIndex(mdn);
		getSqlSession(index).delete(namespace+".deleteByMdn", mdn);
	}

	@Override
	public List<GroupMember> selectBelongGroup(String mdn) {
		return getSqlSession().selectList(namespace+".selectBelongGroup", mdn);
	}
}
