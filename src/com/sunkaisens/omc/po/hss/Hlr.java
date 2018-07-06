package com.sunkaisens.omc.po.hss;

import java.io.Serializable;
import java.sql.Timestamp;

import com.sunkaisens.omc.vo.hss.HssBusinessVo;

/**
 * 
 * 
 * 
 * 
 * HLR实体类
 *
 */
public class Hlr implements Serializable {

	private static final long serialVersionUID = 1L;
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

	private Integer swoon;
	private Integer destroy;
	private Integer airMonitor;

	private Integer swoonFlag = 0; // 根据项目是否显示右键遥晕选项 默认 1 是开启
	private Integer destroyFlag = 0; // 根据项目是否显示右键遥毙选项 默认 1 是开启
	private Integer airMonitorFlag = 0; // 根据项目是否显示右键对空监听选项 默认 1 是开启

	/**
	 * 
	 * 
	 * 
	 * 无参数构造器
	 */
	public Hlr() {
	}

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
		return msprofile_extra;
	}

	public void setMsprofile_extra(Integer msprofile_extra) {
		this.msprofile_extra = msprofile_extra;
	}

	public String getAcount_num() {
		return acount_num;
	}

	public void setAcount_num(String acount_num) {
		this.acount_num = acount_num;
	}

	public Integer getMsvocodec() {
		return this.msvocodec;
	}

	public void setMsvocodec(Integer msvocodec) {
		this.msvocodec = msvocodec;
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

	/**
	 * 重写toString 方法
	 */
	@Override
	public String toString() {
		return "Hlr [imsi=" + imsi + ", mdn=" + mdn + ", esn=" + esn + ", tmsi=" + tmsi + ", ptmsi=" + ptmsi
				+ ", gtmsi=" + gtmsi + ", msprofile=" + msprofile + ", msprofileExtra=" + msprofile_extra
				+ ", msvocodec=" + msvocodec + ", acountNum=" + acount_num + ", currloc=" + currloc + ", tstamp="
				+ tstamp + ", numOfSms=" + numOfSms + ", numOfVm=" + numOfVm + ", status=" + status + ", vlraddr="
				+ vlraddr + ", deviceType=" + deviceType + "]";
	}

	public String getDeviceName() {
		String deviceName = HssBusinessVo.DEVICE_MAP.get(deviceType);
		return deviceName;
	}

	public String getVocodecName() {
		String vName = HssBusinessVo.VOCODEC_MAP.get(msvocodec);
		return vName;
	}

	public Integer getSwoon() {
		return swoon;
	}

	public void setSwoon(Integer swoon) {
		this.swoon = swoon;
	}

	public Integer getDestroy() {
		return destroy;
	}

	public void setDestroy(Integer destroy) {
		this.destroy = destroy;
	}

	public Integer getAirMonitor() {
		return airMonitor;
	}

	public void setAirMonitor(Integer airMonitor) {
		this.airMonitor = airMonitor;
	}

	public Integer getSwoonFlag() {
		return swoonFlag;
	}

	public void setSwoonFlag(Integer swoonFlag) {
		this.swoonFlag = swoonFlag;
	}

	public Integer getDestroyFlag() {
		return destroyFlag;
	}

	public void setDestroyFlag(Integer destroyFlag) {
		this.destroyFlag = destroyFlag;
	}

	public Integer getAirMonitorFlag() {
		return airMonitorFlag;
	}

	public void setAirMonitorFlag(Integer airMonitorFlag) {
		this.airMonitorFlag = airMonitorFlag;
	}

}