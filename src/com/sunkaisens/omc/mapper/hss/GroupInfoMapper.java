package com.sunkaisens.omc.mapper.hss;

import com.sunkaisens.omc.po.hss.GroupInfo;
/**
 * 
 * 
 * 
 * 
 * 
 * 定义GroupInfo接口
 *
 */
public interface GroupInfoMapper {
	void insert(GroupInfo groupInfo);
	void deleteById(String imsi	);
	void update(GroupInfo groupInfo);
	GroupInfo selectById(String imsi);
}
