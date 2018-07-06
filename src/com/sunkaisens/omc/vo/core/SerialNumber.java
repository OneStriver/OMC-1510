package com.sunkaisens.omc.vo.core;

import com.sunkaisens.omc.po.core.Card;
/**
 * 
 * 
 * 
 * 序列号实体类
 *
 */
public class SerialNumber extends Card{
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * 
	 * 无参数构造器
	 */
	public SerialNumber(){}
	//板卡序列号
	private String serial;
	/**
	 * 
	 * 
	 * 
	 * 有参数构造器
	 * @param serial
	 */
	public SerialNumber(String serial) {
		this.serial = serial;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}
}
