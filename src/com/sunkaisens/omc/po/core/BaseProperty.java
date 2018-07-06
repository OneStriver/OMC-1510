package com.sunkaisens.omc.po.core;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * 
 * 
 * 
 * 
 * 基础属性抽象类
 *
 */
public abstract class BaseProperty {
	private Integer id;
	private String name;
	private String value;
	private String description;
	//表单样式
	private String formtype;
	//是否是多行配置
	private Boolean multiline=false;
	private Integer min;
	private Integer max;
	private Integer minLen=1;
	private Integer maxLen=9999;
	//是否是必须的
	private Boolean required=false;
	//序列号
	private Integer orderNum;
	private List<Options> optiones=new ArrayList<Options>();
	/**
	 * 
	 * 
	 * 无参数构造器
	 */
	public BaseProperty(){}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 有参数构造器
	 * @param name
	 * @param value
	 * @param description
	 * @param formtype
	 * @param multiline
	 * @param min
	 * @param max
	 * @param minLen
	 * @param maxLen
	 * @param required
	 * @param orderNum
	 * @param optiones
	 */
	public BaseProperty(String name, String value, String description,
			String formtype, Boolean multiline, Integer min, Integer max,
			Integer minLen, Integer maxLen, Boolean required, Integer orderNum,
			List<Options> optiones) {
		this.name = name;
		this.value = value;
		this.description = description;
		this.formtype = formtype;
		this.multiline = multiline;
		this.min = min;
		this.max = max;
		this.minLen = minLen;
		this.maxLen = maxLen;
		this.required = required;
		this.orderNum = orderNum;
		this.optiones = optiones;
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFormtype() {
		return formtype;
	}
	public void setFormtype(String formtype) {
		this.formtype = formtype;
	}
	public Boolean getMultiline() {
		return multiline;
	}
	public void setMultiline(Boolean multiline) {
		this.multiline = multiline;
	}
	public Integer getMin() {
		return min;
	}
	public void setMin(Integer min) {
		this.min = min;
	}
	public Integer getMax() {
		return max;
	}
	public void setMax(Integer max) {
		this.max = max;
	}
	public Integer getMinLen() {
		return minLen;
	}
	public void setMinLen(Integer minLen) {
		this.minLen = minLen;
	}
	public Integer getMaxLen() {
		return maxLen;
	}
	public void setMaxLen(Integer maxLen) {
		this.maxLen = maxLen;
	}
	public Boolean getRequired() {
		return required;
	}
	public void setRequired(Boolean required) {
		this.required = required;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public List<Options> getOptiones() {
		return optiones;
	}
	public void setOptiones(List<Options> optiones) {
		this.optiones = optiones;
	}
}
