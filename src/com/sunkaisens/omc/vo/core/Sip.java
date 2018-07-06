package com.sunkaisens.omc.vo.core;

public class Sip {
	
	private String localSipIP="192.168.1.1";
	private String localSipPort="3304";
	private String routingNum="0";
	private String remoteSipIP="192.168.1.2";
	private String remoteSipPort="3333";
	private String voiceEncoding="";
	private String heartBeat="";
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
	public String getRoutingNum() {
		return routingNum;
	}
	public void setRoutingNum(String routingNum) {
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
	
	
}
