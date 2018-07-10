package com.sunkaisens.omc.vo.core;

import java.util.List;

public class Sip {

	private String localSipIP = "192.168.1.1";
	private String localSipPort = "3304";
	private List<String> routingNum;
	private String remoteSipIP = "192.168.1.2";
	private String remoteSipPort = "3333";
	private String voiceEncoding = "";
	private String heartBeat = "";

	private String flag;

	private String sipRoutNumCount;

	public String getLocalSipIP() {
		return localSipIP;
	}

	public void setLocalSipIP(String localSipIP) {
		this.localSipIP = localSipIP;
	}

	public String getLocalSipPort() {
		return localSipPort;
	}

	public void setLocalSipPort(String localSipPort) {
		this.localSipPort = localSipPort;
	}

	public List<String> getRoutingNum() {
		return routingNum;
	}

	public void setRoutingNum(List<String> routingNum) {
		this.routingNum = routingNum;
	}

	public String getRemoteSipIP() {
		return remoteSipIP;
	}

	public void setRemoteSipIP(String remoteSipIP) {
		this.remoteSipIP = remoteSipIP;
	}

	public String getRemoteSipPort() {
		return remoteSipPort;
	}

	public void setRemoteSipPort(String remoteSipPort) {
		this.remoteSipPort = remoteSipPort;
	}

	public String getVoiceEncoding() {
		return voiceEncoding;
	}

	public void setVoiceEncoding(String voiceEncoding) {
		this.voiceEncoding = voiceEncoding;
	}

	public String getHeartBeat() {
		return heartBeat;
	}

	public void setHeartBeat(String heartBeat) {
		this.heartBeat = heartBeat;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getSipRoutNumCount() {
		return sipRoutNumCount;
	}

	public void setSipRoutNumCount(String sipRoutNumCount) {
		this.sipRoutNumCount = sipRoutNumCount;
	}

}
