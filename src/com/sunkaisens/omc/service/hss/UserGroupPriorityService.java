package com.sunkaisens.omc.service.hss;

import java.util.List;

import com.sunkaisens.omc.po.hss.UserGroupPriority;

public interface UserGroupPriorityService {

	List<UserGroupPriority> readUserGroupPriority();
	
	void updateUserGroupInfo(UserGroupPriority userGroupPriority);
}
