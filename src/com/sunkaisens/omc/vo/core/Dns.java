package com.sunkaisens.omc.vo.core;

import com.sunkaisens.omc.po.core.Card;

/**
 * DNS
 * @author shenchao
 *
 */
public class Dns extends Card{
	private static final long serialVersionUID = 1L;
	/**
	 * 主机名
	 */
	private String host;
	/**
	 * dns1
	 */
	private String dns1;
	/**
	 * dns2
	 */
	private String dns2;
	/**
	 * dns3
	 */
	private String dns3;
	/**
	 * 
	 * 
	 * 无参数构造器
	 */
	public Dns(){}
	/**
	 * 
	 * 
	 * 有参数构造器
	 * @param host
	 * @param dns1
	 * @param dns2
	 * @param dns3
	 */
	public Dns(String host, String dns1, String dns2, String dns3) {
		this.host = host;
		this.dns1 = dns1;
		this.dns2 = dns2;
		this.dns3 = dns3;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getDns1() {
		return dns1;
	}
	public void setDns1(String dns1) {
		this.dns1 = dns1;
	}
	public String getDns2() {
		return dns2;
	}
	public void setDns2(String dns2) {
		this.dns2 = dns2;
	}
	public String getDns3() {
		return dns3;
	}
	public void setDns3(String dns3) {
		this.dns3 = dns3;
	}
}
