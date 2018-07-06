package com.sunkaisens.omc.vo.core;

import com.sunkaisens.omc.po.core.Card;

/**
 * 路由
 * @author shenchao
 *
 */
public class Route extends Card{
	private static final long serialVersionUID = 1L;
	//地址
	private String destination;
	//网关
	private String gateway;
	//掩码
	private String genmask;
	//标志
	private String flags;
	//metric
	private String metric;
	//ref
	private String ref;
	//use
	private String use;
	//iface
	private String iface;
	/**
	 * 
	 * 
	 * 无参构造器
	 */
	public Route(){}
	/**
	 * 
	 * 
	 * 有参数构造器
	 * @param destination
	 * @param gateway
	 * @param genmask
	 * @param flags
	 * @param metric
	 * @param ref
	 * @param use
	 * @param iface
	 */
	public Route(String destination, String gateway, String genmask,
			String flags, String metric, String ref, String use, String iface) {
		this.destination = destination;
		this.gateway = gateway;
		this.genmask = genmask;
		this.flags = flags;
		this.metric = metric;
		this.ref = ref;
		this.use = use;
		this.iface = iface;
	}

	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	public String getGenmask() {
		return genmask;
	}
	public void setGenmask(String genmask) {
		this.genmask = genmask;
	}
	public String getFlags() {
		return flags;
	}
	public void setFlags(String flags) {
		this.flags = flags;
	}
	public String getMetric() {
		return metric;
	}
	public void setMetric(String metric) {
		this.metric = metric;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public String getUse() {
		return use;
	}
	public void setUse(String use) {
		this.use = use;
	}
	public String getIface() {
		return iface;
	}
	public void setIface(String iface) {
		this.iface = iface;
	}
	@Override
	public String toString() {
		return "Route [destination=" + destination + ", gateway=" + gateway
				+ ", genmask=" + genmask + ", flags=" + flags + ", metric="
				+ metric + ", ref=" + ref + ", use=" + use + ", iface=" + iface
				+ "]";
	}
	
}
