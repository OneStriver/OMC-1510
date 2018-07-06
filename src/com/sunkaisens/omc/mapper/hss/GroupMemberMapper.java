package com.sunkaisens.omc.mapper.hss;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sunkaisens.omc.po.hss.GroupMember;
/**
 * 
 * 
 * 
 * 
 * 
 * 定义GroupMember接口
 *
 */
public interface GroupMemberMapper {
	void insert(GroupMember member);
	void deleteById(@Param("mdn")String mdn,@Param("groupId")String groupId);
	void update(GroupMember member);
	GroupMember selectById(@Param("mdn")String mdn,@Param("groupId")String groupId);
	List<GroupMember> select(@Param("jumpNum") int jumpNum,@Param("limit") int limit,
			@Param("member") GroupMember member);
	int selectCount(@Param("member")GroupMember member);
	void deleteByMdn(String mdn);
	
	List<GroupMember> selectBelongGroup(String mdn);
}
