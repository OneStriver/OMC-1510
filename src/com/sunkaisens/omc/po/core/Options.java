package com.sunkaisens.omc.po.core;

import java.io.Serializable;
/**
 * 
 * 
 * 
 * 
 * 
 *Options实体类
 *
 */
public class Options implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String text;
	private String val;
	private Integer itemId;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 无参数构造器
	 */
	public Options(){}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 有参数构造器
	 * @param text
	 * @param val
	 */
	public Options(String text, String val) {
		this.text = text;
		this.val = val;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
}
