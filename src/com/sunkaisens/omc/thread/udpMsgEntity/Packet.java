package com.sunkaisens.omc.thread.udpMsgEntity;

public class Packet {
	private int len;
	private byte[] msg;
	private String ipAddress;
	private int port;

	private int nMsgType;
	private Object obj;

	public int getMsgType() {
		return this.nMsgType;
	}

	public void setMsgType(int type) {
		this.nMsgType = type;
	}

	public Object getObj() {
		return this.obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public static final int REQUEST = -1;

	private int sessionId = REQUEST;

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	public boolean isRequestMsg() {
		return getSessionId() == REQUEST;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public byte[] getMsg() {
		return msg;
	}

	public void setMsg(byte[] msg) {
		this.msg = msg;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
