package com.sunkaisens.omc.vo.core;
/**
 * 
 * 
 * 
 * DnsAxfr实例类
 *
 */
public class DnsAxfr {
	/**
	 * 
	 * 
	 * 无参数构造器
	 */
	public DnsAxfr(){}
	
	/**
	 * 
	 * 
	 * 
	 * 有参数构造器
	 * @param name
	 * @param ttl
	 * @param clazz
	 * @param type
	 * @param data
	 */
	public DnsAxfr(String name, String ttl, String clazz, String type, String data) {
		this.name = name;
		this.ttl = ttl;
		this.clazz = clazz;
		this.type = type;
		this.data = data;
	}
	private String zone;
	private String name;
	private String ttl;
	private String clazz;
	private String type;
	private String data;
	private String oldData;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTtl() {
		return ttl;
	}
	public void setTtl(String ttl) {
		this.ttl = ttl;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getOldData() {
		return oldData;
	}
	public void setOldData(String oldData) {
		this.oldData = oldData;
	}
}
