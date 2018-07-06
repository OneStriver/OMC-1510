package com.sunkaisens.omc.po.core;

import java.io.Serializable;
/**
 * 
 * 
 * 
 * 
 * 
 * Entity实体类
 */
public class Entity implements Serializable{
	private static final long serialVersionUID = 1L;
	//网元实体id
	private Integer id;
	//网元实体名称
	private String name;
	//网元实例id
	private Integer instId;
	//网元实例状态
	private Integer status;
	//网元实体类型
	private Integer type;
	//网元实体描述
	private String description;
	//网元实体属于那个Module
	private Module module;
	//网元实体属于那个Card
	private Card card;
	/**
	 * 无参数构造器
	 */
	public Entity(){
		
	}
	
	/**
	 * 有参数构造器
	 */
	public Entity(String name, Integer instId, Integer status, Integer type,
			String description, Module module, Card card) {
		this.name = name;
		this.instId = instId;
		this.status = status;
		this.type = type;
		this.description = description;
		this.module = module;
		this.card = card;
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
	public Integer getInstId() {
		return instId;
	}
	public void setInstId(Integer instId) {
		this.instId = instId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
		this.module = module;
	}
	public Card getCard() {
		return card;
	}
	public void setCard(Card card) {
		this.card = card;
	}
	@Override
	public String toString() {
		return "Entity [id=" + id + ", name=" + name + ", instId=" + instId
				+ ", status=" + status + ", type=" + type + ", description="
				+ description + ", module=" + module + ", card=" + card + "]";
	}
}
