package com.sunkaisens.omc.mapper.hss;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sunkaisens.omc.po.hss.UserGroupPriority;

public interface UserGroupPriorityMapper {
	
	List<UserGroupPriority> selectAll();
	
	void updateUserGroupConfig(UserGroupPriority userGroupPriority);
	
	void insertUserGroupPriority(@Param("level") int level,@Param("priority") int priority,@Param("enable") int enable,@Param("userType") int userType);
	
}
