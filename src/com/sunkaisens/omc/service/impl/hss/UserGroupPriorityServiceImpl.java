package com.sunkaisens.omc.service.impl.hss;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunkaisens.omc.mapper.hss.UserGroupPriorityMapper;
import com.sunkaisens.omc.po.hss.UserGroupPriority;
import com.sunkaisens.omc.service.hss.UserGroupPriorityService;

@Service
public class UserGroupPriorityServiceImpl implements UserGroupPriorityService{
	
	@Resource
	private UserGroupPriorityMapper userGroupPriorityMapper;
	
	@Override
	public List<UserGroupPriority> readUserGroupPriority() {
		List<UserGroupPriority> userGroups = userGroupPriorityMapper.selectAll();
		return userGroups;
	}

	@Override
	public void updateUserGroupInfo(UserGroupPriority userGroup) {
		
		userGroupPriorityMapper.updateUserGroupConfig(userGroup);
	}

}
