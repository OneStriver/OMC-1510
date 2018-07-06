package com.sunkaisens.omc.po.core;

import java.io.Serializable;
/**
 * 
 * 
 * 
 * 
 * 
 * Common实体类
 */
public class Common implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	/**
	 * 
	 * 
	 * 
	 * 无参数构造器
	 */
	public Common(){}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 有参数构造器
	 * @param name
	 */
	public Common(String name) {
		this.name = name;
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
	
	
}
