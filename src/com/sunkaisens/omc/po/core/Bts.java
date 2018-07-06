package com.sunkaisens.omc.po.core;

import java.io.Serializable;

public class Bts  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	//bts.config
	private String carrierId;
	private String carrierType;
	private String cellId;
	private String carrierFreq;
	private String pn;
	private String btsId;
	private String zoneId;
	private String bandClass;
	private String txGain;
	private String pilotChGain;
	private String syncChGain;
	private String pagingChGain;
	private String numOfPch;
	private String numOfQpch;
	private String qpchRate;
	private String qpchCci;
	private String qpchPwrPage;
	private String qpchPwrCfg;
	private String numAchPerpch;
	private String cdmaChannelList;
	
	
	public String getCarrierId() {
		return carrierId;
	}
	public void setCarrierId(String carrierId) {
		this.carrierId = carrierId;
	}
	public String getCarrierType() {
		return carrierType;
	}
	public void setCarrierType(String carrierType) {
		this.carrierType = carrierType;
	}
	public String getCellId() {
		return cellId;
	}
	public void setCellId(String cellId) {
		this.cellId = cellId;
	}
	public String getCarrierFreq() {
		return carrierFreq;
	}
	public void setCarrierFreq(String carrierFreq) {
		this.carrierFreq = carrierFreq;
	}
	public String getPn() {
		return pn;
	}
	public void setPn(String pn) {
		this.pn = pn;
	}
	public String getBtsId() {
		return btsId;
	}
	public void setBtsId(String btsId) {
		this.btsId = btsId;
	}
	public String getZoneId() {
		return zoneId;
	}
	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}
	public String getTxGain() {
		return txGain;
	}
	public void setTxGain(String txGain) {
		this.txGain = txGain;
	}
	public String getPilotChGain() {
		return pilotChGain;
	}
	public void setPilotChGain(String pilotChGain) {
		this.pilotChGain = pilotChGain;
	}
	public String getSyncChGain() {
		return syncChGain;
	}
	public void setSyncChGain(String syncChGain) {
		this.syncChGain = syncChGain;
	}
	public String getPagingChGain() {
		return pagingChGain;
	}
	public void setPagingChGain(String pagingChGain) {
		this.pagingChGain = pagingChGain;
	}
	public String getNumOfPch() {
		return numOfPch;
	}
	public void setNumOfPch(String numOfPch) {
		this.numOfPch = numOfPch;
	}
	public String getNumOfQpch() {
		return numOfQpch;
	}
	public void setNumOfQpch(String numOfQpch) {
		this.numOfQpch = numOfQpch;
	}
	public String getQpchRate() {
		return qpchRate;
	}
	public void setQpchRate(String qpchRate) {
		this.qpchRate = qpchRate;
	}
	public String getQpchCci() {
		return qpchCci;
	}
	public void setQpchCci(String qpchCci) {
		this.qpchCci = qpchCci;
	}
	public String getQpchPwrPage() {
		return qpchPwrPage;
	}
	public void setQpchPwrPage(String qpchPwrPage) {
		this.qpchPwrPage = qpchPwrPage;
	}
	public String getQpchPwrCfg() {
		return qpchPwrCfg;
	}
	public void setQpchPwrCfg(String qpchPwrCfg) {
		this.qpchPwrCfg = qpchPwrCfg;
	}
	public String getNumAchPerpch() {
		return numAchPerpch;
	}
	public void setNumAchPerpch(String numAchPerpch) {
		this.numAchPerpch = numAchPerpch;
	}
	public String getCdmaChannelList() {
		return cdmaChannelList;
	}
	public void setCdmaChannelList(String cdmaChannelList) {
		this.cdmaChannelList = cdmaChannelList;
	}
	public String getBandClass() {
		return bandClass;
	}
	public void setBandClass(String bandClass) {
		this.bandClass = bandClass;
	}
	
	

}
