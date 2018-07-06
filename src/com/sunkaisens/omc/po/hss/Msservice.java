package com.sunkaisens.omc.po.hss;

import java.io.Serializable;
/**
 * 
 * 
 * 
 * 
 * Msservice 实体类
 *
 */
public class Msservice implements Serializable{
	private static final long serialVersionUID = 1L;
	private String imsi="";
	private String status="";
	private String directFwdNum="";
	private String fwdOnBusyNum="";
	private String fwdNoAnswerNum="";
	private String voiceMailNum="";
	private String fwdNANum="";
	private String wireTapAddr="";
	/**
	 * 
	 * 
	 * 
	 * 
	 * 无参数构造器
	 */
	public Msservice() {
		super();
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 有参数构造器
	 * @param imsi
	 * @param status
	 * @param directFwdNum
	 * @param fwdOnBusyNum
	 * @param fwdNoAnswerNum
	 * @param voiceMailNum
	 * @param fwdNANum
	 * @param wireTapAddr
	 */
	public Msservice(String imsi, String status, String directFwdNum,
			String fwdOnBusyNum, String fwdNoAnswerNum, String voiceMailNum,
			String fwdNANum, String wireTapAddr) {
		this.imsi = imsi;
		this.status = status;
		this.directFwdNum = directFwdNum;
		this.fwdOnBusyNum = fwdOnBusyNum;
		this.fwdNoAnswerNum = fwdNoAnswerNum;
		this.voiceMailNum = voiceMailNum;
		this.fwdNANum = fwdNANum;
		this.wireTapAddr = wireTapAddr;
	}

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * Getter and Setter 
	 */
	
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDirectFwdNum() {
		return directFwdNum;
	}
	public void setDirectFwdNum(String directFwdNum) {
		this.directFwdNum = directFwdNum;
	}
	public String getFwdOnBusyNum() {
		return fwdOnBusyNum;
	}
	public void setFwdOnBusyNum(String fwdOnBusyNum) {
		this.fwdOnBusyNum = fwdOnBusyNum;
	}
	public String getFwdNoAnswerNum() {
		return fwdNoAnswerNum;
	}
	public void setFwdNoAnswerNum(String fwdNoAnswerNum) {
		this.fwdNoAnswerNum = fwdNoAnswerNum;
	}
	public String getVoiceMailNum() {
		return voiceMailNum;
	}
	public void setVoiceMailNum(String voiceMailNum) {
		this.voiceMailNum = voiceMailNum;
	}
	public String getFwdNANum() {
		return fwdNANum;
	}
	public void setFwdNANum(String fwdNANum) {
		this.fwdNANum = fwdNANum;
	}
	public String getWireTapAddr() {
		return wireTapAddr;
	}
	public void setWireTapAddr(String wireTapAddr) {
		this.wireTapAddr = wireTapAddr;
	}
}
