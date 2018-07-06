package com.sunkaisens.omc.mapper.hss.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunkaisens.omc.mapper.hss.UserGroupPriorityMapper;
import com.sunkaisens.omc.po.hss.UserGroupPriority;

public class UserGroupPriorityMapperImpl extends HssSqlSessionDaoSupport implements UserGroupPriorityMapper {

	@Override
	public List<UserGroupPriority> selectAll() {
		Map<String,Object> param=new HashMap<>();
		return getSqlSession().selectList(namespace+".selectAll",param);
	}

	@Override
	public void updateUserGroupConfig(UserGroupPriority userGroup) {
			getSqlSession().update(namespace+".updateUserGroupConfig",userGroup);
	}

	@Override
	public void insertUserGroupPriority(int level, int priority, int enable, int userType) {
		Map<String,Object> param=new HashMap<>();
		param.put("level", level);
		param.put("priority", priority);
		param.put("enable", enable);
		param.put("userType", userType);
		getSqlSession().insert(namespace+".insertUserGroupPriority", param);
		
	}

}
