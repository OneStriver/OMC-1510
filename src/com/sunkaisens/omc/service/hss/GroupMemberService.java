package com.sunkaisens.omc.service.hss;

import java.util.List;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.po.hss.GroupMember;
import com.sunkaisens.omc.vo.core.PageBean;
/**
 * 
 * 
 * 
 * 
 * 定义GroupMemberService接口
 *
 */
public interface GroupMemberService {

	/**
	 * 
	 * 
	 * 
	 * 
	 * 成员列表分页
	 * @param page
	 * @param pageSize
	 * @param member
	 * @return
	 */
	PageBean getGroupMemberList(Integer page, Integer pageSize, GroupMember member);

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 添加成员
	 * @param member
	 * @throws CustomException
	 */
	void save(GroupMember member) throws CustomException;

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 修改成员
	 * @param member
	 * @throws CustomException
	 */
	void update(GroupMember member) throws CustomException;

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 删除成员
	 * @param mdn
	 * @param groupId
	 * @throws CustomException
	 */
	void delete(String[] mdn, String[] groupId) throws CustomException;
	
	//根据mdn查询该mdn所属的组有哪些
	List<GroupMember> selectBelongGroup(String mdn);

}
