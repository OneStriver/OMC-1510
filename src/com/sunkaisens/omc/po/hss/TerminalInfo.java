package com.sunkaisens.omc.po.hss;

import java.sql.Timestamp;
/**
 * 
 * 
 * 
 * 
 * 终端信息实体类
 */
public class TerminalInfo {
	//终端IMSI号
	private String imsi;
	//终端Id号
	private String terminalId;
	//终端类型
	private Integer terminalType;
	//终端功率等级
	private Integer powerLevel;
	//终端支持业务类型
	private Integer suportBuss;//msprofile
	//终端xgzId
	private Integer gwId;
	//终端所属部门
	private String department;
	//终端用户类型
	private Integer userType;
	//终端用户信息
	private String username;
	//终端用户信息
	private String userInfo;
	//服务类型
	private Integer servicePriority;
	//终端创建时间
	private Timestamp createTime;
	
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	public Integer getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}
	public Integer getPowerLevel() {
		return powerLevel;
	}
	public void setPowerLevel(Integer powerLevel) {
		this.powerLevel = powerLevel;
	}
	public Integer getSuportBuss() {
		return suportBuss;
	}
	public void setSuportBuss(Integer suportBuss) {
		this.suportBuss = suportBuss;
	}
	public Integer getGwId() {
		return gwId;
	}
	public void setGwId(Integer gwId) {
		this.gwId = gwId;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}
	public Integer getServicePriority() {
		return servicePriority;
	}
	public void setServicePriority(Integer servicePriority) {
		this.servicePriority = servicePriority;
	}
}
