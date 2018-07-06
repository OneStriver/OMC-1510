package com.sunkaisens.omc.po.core;
/**
 * 
 * 
 * 
 * 
 * 
 * DNS实体类
 */
public class IpDns {
	private Integer cardNum;
	private String eth;
	private String ip;
	private String dnsStr;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 无参数构造器
	 */
	public IpDns(){}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 有参数构造器
	 * @param cardNum
	 * @param eth
	 * @param ip
	 * @param dnsStr
	 */
	public IpDns(Integer cardNum,String eth,String ip, String dnsStr) {
		this.cardNum=cardNum;
		this.eth=eth;
		this.ip = ip;
		this.dnsStr = dnsStr;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDnsStr() {
		return dnsStr;
	}

	public void setDnsStr(String dnsStr) {
		this.dnsStr = dnsStr;
	}
	public String getEth() {
		return eth;
	}
	public void setEth(String eth) {
		this.eth = eth;
	}
	public Integer getCardNum() {
		return cardNum;
	}
	public void setCardNum(Integer cardNum) {
		this.cardNum = cardNum;
	}
}
