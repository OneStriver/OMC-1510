package com.sunkaisens.omc.po.core;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * 
 * 
 * 
 * 
 *Module实体类
 *
 */
public class Module implements Serializable{
	private static final long serialVersionUID = 1L;
	//模块id
	private Integer id;
	//模块名称
	private String name;
	private Integer belong;
	//模块描述
	private String description;
	//模块版本
	private String version;
	private String exe;
	private String log;
	//模块下的配置文件
	private List<Config> configs;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 无参数构造器
	 */
	public Module(){}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 有参数构造器
	 * @param id
	 * @param name
	 * @param belong
	 * @param description
	 * @param version
	 * @param exe
	 * @param log
	 * @param configs
	 */
	public Module(Integer id,String name, Integer belong, String description,
			String version, String exe, String log, List<Config> configs) {
		this.id=id;
		this.name = name;
		this.belong = belong;
		this.description = description;
		this.version = version;
		this.exe = exe;
		this.log = log;
		this.configs = configs;
	}
	public Module(String name, Integer belong, String description,
			String version, String exe, String log, List<Config> configs) {
		this.name = name;
		this.belong = belong;
		this.description = description;
		this.version = version;
		this.exe = exe;
		this.log = log;
		this.configs = configs;
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
	public Integer getBelong() {
		return belong;
	}
	public void setBelong(Integer belong) {
		this.belong = belong;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getExe() {
		return exe;
	}
	public void setExe(String exe) {
		this.exe = exe;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	public List<Config> getConfigs() {
		return configs;
	}
	public void setConfigs(List<Config> configs) {
		this.configs = configs;
	}
	
}
