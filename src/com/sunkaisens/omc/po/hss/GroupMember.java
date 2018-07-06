package com.sunkaisens.omc.po.hss;

import java.util.Date;
/**
 * 
 * 
 * 
 * 
 * 
 * 组成员实体类
 */
public class GroupMember {
	//组成员MDN
	private String mdn;
	//组成员的groupId
	private String groupId;
	//组成员优先级
	private Integer priority;
	//组成员角色
	private Integer role;
	//组成员服务
	private Integer service;
	//组成员addtion
	private Integer addtion;
	//组成员创建时间
	private Date createTime;
	//组成员更新时间
	private Date updateTime=new Date();
	
	public String getMdn() {
		return mdn;
	}
	public void setMdn(String mdn) {
		this.mdn = mdn;
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
	public Integer getService() {
		return service;
	}
	public void setService(Integer service) {
		this.service = service;
	}
	public Integer getAddtion() {
		return addtion;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public void setAddtion(Integer addtion) {
		this.addtion = addtion;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 重写hashcode方法
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		result = prime * result + ((mdn == null) ? 0 : mdn.hashCode());
		return result;
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 重写equals方法
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GroupMember other = (GroupMember) obj;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		if (mdn == null) {
			if (other.mdn != null)
				return false;
		} else if (!mdn.equals(other.mdn))
			return false;
		return true;
	}
}
