package com.sunkaisens.omc.po.core;

import java.io.Serializable;

/**
 * 
 * 
 * 
 * 
 * 
 * Card实体类
 *
 */
public class Card implements Serializable{
	private static final long serialVersionUID = 1L;
	//板卡id
	private Integer id;
	//板卡名称
	private String name;
	private Integer cardNum=0;
	//槽位id
	private Integer slotId;
	//板卡ip
	private String ip;
	//板卡序列号序列号
	private String serial;
	//OAM状态
	private Integer oamStatus;
	/**
	 * 
	 * 
	 * 
	 * 无参数构造器
	 */
	public Card(){}
    /**
     * 
     * 
     * 
     * 
     * 有参数构造器
     * @param name
     * @param cardNum
     * @param slotId
     * @param ip
     */
	public Card(String name, Integer cardNum, Integer slotId, String ip) {
		super();
		this.name = name;
		this.cardNum = cardNum;
		this.slotId = slotId;
		this.ip = ip;
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
	public Integer getCardNum() {
		return cardNum;
	}
	public void setCardNum(Integer cardNum) {
		this.cardNum = cardNum;
	}
	public Integer getSlotId() {
		return slotId;
	}
	public void setSlotId(Integer slotId) {
		this.slotId = slotId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getOamStatus() {
		return oamStatus;
	}

	public void setOamStatus(Integer oamStatus) {
		this.oamStatus = oamStatus;
	}
	
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
}
