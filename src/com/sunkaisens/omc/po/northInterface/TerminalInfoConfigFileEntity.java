package com.sunkaisens.omc.po.northInterface;

public class TerminalInfoConfigFileEntity {

	private String deviceType;
	private String deviceNumber;
	private String type;
	private String imsi;
	private String priority;
	private String permitCallOut;

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getPermitCallOut() {
		return permitCallOut;
	}

	public void setPermitCallOut(String permitCallOut) {
		this.permitCallOut = permitCallOut;
	}

}
