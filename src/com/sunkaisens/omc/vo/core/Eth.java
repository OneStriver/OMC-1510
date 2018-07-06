package com.sunkaisens.omc.vo.core;

import com.sunkaisens.omc.po.core.Card;
/**
 * 
 * 
 * Eth实体类
 * 
 *
 */
public class Eth extends Card{
	private static final long serialVersionUID = 1L;
	//网卡名称
	private String name;
	//网卡类型
	private String type;
	//IP地址
	private String ip;
	//掩码
	private String mask;
	//MAC地址
	private String mac;
	//最小传输单元
	private String mtu;
	//网卡状态
	private String state;
	//广播地址
	private String broadcast;
	//对端地址
	private String destination;
	//速率
	private String speed;
	//单双工
	private String duplex;
	//自动协速
	private String autoNeg;
	//网卡开机自启动
	private String onBootorNo;
	
	//OSPF
	private String ospf="false";
    
	public String getOspf() {
		return ospf;
	}
	public void setOspf(String ospf) {
		this.ospf = ospf;
	}
	//ospfd头信息
	private String ospfd="false";
	
	public String getOspfd() {
		return ospfd;
	}
	public void setOspfd(String ospfd) {
		this.ospfd = ospfd;
	}
	//板卡名称
	private String cardName;
	
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	/**
     * 
     * 
     * 无参数构造器
     */
	public Eth(){}
	/**
	 * 
	 * 
	 * 
	 * 有参数构造器
	 * @param name
	 * @param type
	 * @param ip
	 * @param mask
	 * @param mac
	 * @param mtu
	 * @param state
	 * @param broadcast
	 * @param destination
	 * @param speed
	 * @param duplex
	 * @param autoNeg
	 */
	public Eth(String name, String type, String ip, String mask, String mac,
			String mtu, String state, String broadcast, String destination,
			String speed, String duplex, String autoNeg) {
		this.name = name;
		this.type = type;
		this.ip = ip;
		this.mask = mask;
		this.mac = mac;
		this.mtu = mtu;
		this.state = state;
		this.broadcast = broadcast;
		this.destination = destination;
		this.speed = speed;
		this.duplex = duplex;
		this.autoNeg = autoNeg;
	}

	public Eth(String name, String type, String ip, String mask, String mac,String broadcast,
			String mtu,String destination,String state) {
		this.name = name;
		this.type = type;
		this.ip = ip;
		this.mask = mask;
		this.mac = mac;
		this.mtu = mtu;
		this.state = state;
		this.broadcast=broadcast;
		this.destination=destination;
	}
	////
	public Eth(String name, String type, String ip, String mask, String mac,String mtu,String state,String broadcast,
			String destination,String ospf) {
		this.name = name;
		this.type = type;
		this.ip = ip;
		this.mask = mask;
		this.mac = mac;
		this.mtu = mtu;
		this.state = state;
		this.broadcast=broadcast;
		this.destination=destination;
		this.ospf = ospf;
	}
	////
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getMtu() {
		return mtu;
	}

	public void setMtu(String mtu) {
		this.mtu = mtu;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBroadcast() {
		return broadcast;
	}

	public void setBroadcast(String broadcast) {
		this.broadcast = broadcast;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getDuplex() {
		return duplex;
	}

	public void setDuplex(String duplex) {
		this.duplex = duplex;
	}

	public String getAutoNeg() {
		return autoNeg;
	}

	public void setAutoNeg(String autoNeg) {
		this.autoNeg = autoNeg;
	}

	public String getOnBoot() {
		return onBootorNo;
	}
	public void setOnBootorNo(String onBootorNo) {
		this.onBootorNo = onBootorNo;
	}
	@Override
	public String toString() {
		return "Eth [name=" + name + ", type=" + type + ", ip=" + ip
				+ ", mask=" + mask + ", mac=" + mac + ", mtu=" + mtu
				+ ", state=" + state + ", broadcast=" + broadcast
				+ ", destination=" + destination + ", speed=" + speed
				+ ", duplex=" + duplex + ", autoNeg=" + autoNeg + ", onBoot="
				+ onBootorNo + "]";
	}

	
}
