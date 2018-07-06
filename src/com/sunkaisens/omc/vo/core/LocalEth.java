package com.sunkaisens.omc.vo.core;
/**
 * 
 * 
 * 
 * 本地Eth实体类
 *
 */
public class LocalEth {
	/**
	 * 
	 * 
	 * 
	 * 无参数构造器
	 */
	public LocalEth(){}
	
	private String name;
	private String ip;
	private String mask;
	private String gateway;
	private String broadcast;
	private String hardAddr;
	private Integer mtu;
	private Boolean up;
	private Boolean virtual;
	/**
	 * 
	 * 
	 * 
	 * 有参数构造器
	 * @param name
	 * @param ip
	 * @param mask
	 * @param gateway
	 * @param broadcast
	 * @param hardAddr
	 * @param mtu
	 * @param up
	 * @param virtual
	 */
	public LocalEth(String name, String ip, String mask, String gateway,
			String broadcast, String hardAddr, Integer mtu, Boolean up,
			Boolean virtual) {
		this.name = name;
		this.ip = ip;
		this.mask = mask;
		this.gateway = gateway;
		this.broadcast = broadcast;
		this.hardAddr = hardAddr;
		this.mtu = mtu;
		this.up = up;
		this.virtual = virtual;
	}

	public Boolean getVirtual() {
		return virtual;
	}

	public void setVirtual(Boolean virtual) {
		this.virtual = virtual;
	}

	public Boolean getUp() {
		return up;
	}

	public void setUp(Boolean up) {
		this.up = up;
	}

	public Integer getMtu() {
		return mtu;
	}

	public void setMtu(Integer mtu) {
		this.mtu = mtu;
	}

	public String getHardAddr() {
		return hardAddr;
	}

	public void setHardAddr(String hardAddr) {
		this.hardAddr = hardAddr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMask() {
		return mask;
	}
	public void setMask(String mask) {
		this.mask = mask;
	}
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	
	public String getBroadcast() {
		return broadcast;
	}
	public void setBroadcast(String broadcast) {
		this.broadcast = broadcast;
	}

	@Override
	public String toString() {
		return "Eth [name=" + name + ", ip=" + ip + ", mask=" + mask
				+ ", gateway=" + gateway + ", broadcast=" + broadcast
				+ ", hardAddr=" + hardAddr + ", mtu=" + mtu + ", up=" + up
				+ ", virtual=" + virtual + "]";
	}

}
