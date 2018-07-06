package com.sunkaisens.omc.po.hss;

import java.util.Date;
/**
 * HSSGroup实体类
 */
public class HssGroup {
    //HssGroup Id
	private String id;
	//HssGroup groupType
	private Integer groupType;
	//HssGroup 呼叫类型
	private Integer callType;
	//HssGroup 业务类型
	private Integer busiType;
	//HssGroup 优先级
	private Integer priority;
	//HssGroup 当前位置信息
	private String location;
	//HssGroup 状态
	private Integer status;
	//HssGroup 创建时间
	private Date createTime;
	//HssGroup 更新时间
	private Date updateTime=new Date();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getGroupType() {
		return groupType;
	}
	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}
	public Integer getCallType() {
		return callType;
	}
	public void setCallType(Integer callType) {
		this.callType = callType;
	}
	public Integer getBusiType() {
		return busiType;
	}
	public void setBusiType(Integer busiType) {
		this.busiType = busiType;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
}
