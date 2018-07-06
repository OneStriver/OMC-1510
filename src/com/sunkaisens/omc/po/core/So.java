package com.sunkaisens.omc.po.core;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * 
 * 
 * 
 * 
 *So实体类
 *
 */
public class So implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Date updateDate;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 无参数构造器
	 */
	public So(){}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 有参数构造器
	 * @param name
	 * @param updateDate
	 */
	public So(String name, Date updateDate) {
		this.name = name;
		this.updateDate = updateDate;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
