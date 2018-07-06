package com.sunkaisens.omc.vo.core;

import com.sunkaisens.omc.po.core.Card;
/**
 * 
 * 
 * 
 *DnsDomain实体类
 *
 */
public class DnsDomain extends Card{
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * 
	 * 无参数构造器
	 */
	public DnsDomain(){}
	/**
	 * 
	 * 
	 * 
	 * 有参数构造器
	 * @param name
	 * @param clazz
	 * @param serial
	 */
	public DnsDomain(String name, String clazz, String serial) {
		this.name = name;
		this.clazz = clazz;
		this.serial = serial;
	}

	private String name;
	private String clazz;
	private String serial;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
}
