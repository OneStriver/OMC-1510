package com.sunkaisens.omc.po.core;

import java.util.Date;
/**
 * 
 * 
 * 
 * 
 * 
 *Log实体类
 *
 */
public class Log {
	//日志id
	private Integer id;
	//日志描述
	private String description;
	//操作者
	private String user;
	//操作是否成功
	private Boolean success;
	//成功或者失败原因
	private String reason;
	//日志产生时间
	private Date createDate=new Date();
	/**
	 * 
	 * 
	 * 
	 * 
	 * 无参数构造器
	 */
	public Log(){}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 有参数构造器
	 * @param description
	 * @param user
	 */
	public Log(String description, String user) {
		this.description = description;
		this.user = user;
	}

	public Boolean getSuccess() {
		return success;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
