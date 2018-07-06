package com.sunkaisens.omc.vo.core;

import com.sunkaisens.omc.po.core.Card;
/**
 * 
 * 主机地址实体类
 *
 */
public class HostAddr extends Card{
	private static final long serialVersionUID = 1L;
	//主机ip
	private String ip;
	//主机名
	private String host;
	/**
	 * 
	 * 
	 * 无参数构造器
	 */
	public HostAddr(){}
	/**
	 * 
	 * 
	 * 
	 * 有参数构造器
	 * @param ip
	 * @param host
	 */
	public HostAddr(String ip, String host) {
		this.ip = ip;
		this.host = host;
	}

	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
}
