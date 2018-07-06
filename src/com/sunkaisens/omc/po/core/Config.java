package com.sunkaisens.omc.po.core;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * 
 * 
 * 
 * 
 * Config实体类
 */
public class Config implements Serializable{
	private static final long serialVersionUID = 1L;
	//配置文件id
	private Integer id;
	//配置文件名称
	private String name;
	//配置文件路径
	private String path;
	//配置文件描述
	private String description;
	//是否是独立配置项
	private Boolean sole;
	//配置文件属于那个Module
	private Module module;
	//配置文件包含配置项
	private List<Item> items;
	/**
	 * 
	 * 
	 * 
	 * 无参数构造器
	 */
	public Config(){}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 有参数构造器
	 * @param name
	 * @param path
	 * @param description
	 * @param sole
	 * @param module
	 */
	public Config(String name, String path, String description, Boolean sole,
			Module module) {
		this.name = name;
		this.path = path;
		this.description = description;
		this.sole = sole;
		this.module = module;
	}
	public Config(Integer id,String name, String path, String description, Boolean sole,
			Module module) {
		this.id=id;
		this.name = name;
		this.path = path;
		this.description = description;
		this.sole = sole;
		this.module = module;
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
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getSole() {
		return sole;
	}
	public void setSole(Boolean sole) {
		this.sole = sole;
	}
	public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
		this.module = module;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	
}
