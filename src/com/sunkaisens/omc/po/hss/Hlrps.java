package com.sunkaisens.omc.po.hss;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 
 * 
 * 
 * Hlrps 实体类
 *
 */
public class Hlrps implements Serializable {

	private static final long serialVersionUID = 1L;
	// Fields

	private String imsi;
	private String mdn;
	private String esn;
	private String tmsi;
	private String ptmsi;
	private String gtmsi;
	private Integer msprofile;
	private Integer msprofile_extra;
	private Integer msvocodec;
	private String acount_num;
	private String currloc;
	private Timestamp tstamp;
	private Integer numOfSms;
	private Integer numOfVm;
	private Integer status;
	private String vlraddr;
	private Integer deviceType;

	// Constructors

	/**
	 * 
	 * 
	 * default constructor
	 * 
	 */
	public Hlrps() {
	}

	/**
	 * full constructor
	 * 
	 * 
	 */
	public Hlrps(String imsi, String mdn, String esn, String tmsi, String ptmsi, String gtmsi, Integer msprofile,
			Integer msprofile_extra, Integer msvocodec, String acount_num, String currloc, Timestamp tstamp,
			Integer numOfSms, Integer numOfVm, Integer status, String vlraddr, Integer deviceType) {
		this.imsi = imsi;
		this.mdn = mdn;
		this.esn = esn;
		this.tmsi = tmsi;
		this.ptmsi = ptmsi;
		this.gtmsi = gtmsi;
		this.msprofile = msprofile;
		this.msprofile_extra = msprofile_extra;
		this.msvocodec = msvocodec;
		this.acount_num = acount_num;
		this.currloc = currloc;
		this.tstamp = tstamp;
		this.numOfSms = numOfSms;
		this.numOfVm = numOfVm;
		this.status = status;
		this.vlraddr = vlraddr;
		this.deviceType = deviceType;
	}

	// Property accessors

	public String getImsi() {
		return this.imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getMdn() {
		return this.mdn;
	}

	public void setMdn(String mdn) {
		this.mdn = mdn;
	}

	public String getEsn() {
		return this.esn;
	}

	public void setEsn(String esn) {
		this.esn = esn;
	}

	public String getTmsi() {
		return this.tmsi;
	}

	public void setTmsi(String tmsi) {
		this.tmsi = tmsi;
	}

	public String getPtmsi() {
		return this.ptmsi;
	}

	public void setPtmsi(String ptmsi) {
		this.ptmsi = ptmsi;
	}

	public String getGtmsi() {
		return this.gtmsi;
	}

	public void setGtmsi(String gtmsi) {
		this.gtmsi = gtmsi;
	}

	public Integer getMsprofile() {
		return this.msprofile;
	}

	public void setMsprofile(Integer msprofile) {
		this.msprofile = msprofile;
	}

	public Integer getMsprofile_extra() {
		return this.msprofile_extra;
	}

	public void setMsprofile_extra(Integer msprofile_extra) {
		this.msprofile_extra = msprofile_extra;
	}

	public Integer getMsvocodec() {
		return this.msvocodec;
	}

	public void setMsvocodec(Integer msvocodec) {
		this.msvocodec = msvocodec;
	}

	public String getAcount_num() {
		return this.acount_num;
	}

	public void setAcount_num(String acount_num) {
		this.acount_num = acount_num;
	}

	public String getCurrloc() {
		return this.currloc;
	}

	public void setCurrloc(String currloc) {
		this.currloc = currloc;
	}

	public Timestamp getTstamp() {
		return this.tstamp;
	}

	public void setTstamp(Timestamp tstamp) {
		this.tstamp = tstamp;
	}

	public Integer getNumOfSms() {
		return this.numOfSms;
	}

	public void setNumOfSms(Integer numOfSms) {
		this.numOfSms = numOfSms;
	}

	public Integer getNumOfVm() {
		return this.numOfVm;
	}

	public void setNumOfVm(Integer numOfVm) {
		this.numOfVm = numOfVm;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getVlraddr() {
		return this.vlraddr;
	}

	public void setVlraddr(String vlraddr) {
		this.vlraddr = vlraddr;
	}

	public Integer getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

}