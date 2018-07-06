package com.sunkaisens.omc.po.core;

import java.sql.Timestamp;
import java.util.Date;
/**
 * 
 * 
 * 
 * 
 * 
 * Alarm实体类
 *
 */
public class Alarm {
	// 告警id
	private Integer id;
	// 告警级别
	private Integer level;
	// 告警描述
	private String description;
	// 告警产生时间
	private Timestamp createDate=new Timestamp(new Date().getTime());
	/**
	 * 无参数构造器
	 */
	public Alarm(){}
	/**
	 * 
	 * 
	 * 
	 * 有参数构造器
	 * @param level
	 * @param description
	 */
	public Alarm(Integer level, String description) {
		this.level = level;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	
}
