package com.sunkaisens.omc.thread.messageEncapsulation;

import com.sunkaisens.omc.util.CodecUtil;

/**
 * 网元状态上报(0x7005)
 */
public class StateReportMsg extends CncpBaseMsg {

	private char action;
	private char status;
	private int port;
	private String ip;
	private String desc;
	private String version;
	
	public char getAction() {
		return action;
	}

	public void setAction(char action) {
		this.action = action;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * 状态上报
	 */
	public StateReportMsg readFromSignalTrackReport(byte[] bytes, StateReportMsg stateReportMsg) {
		// 上报消息的源地址
		stateReportMsg.setSrcType(CodecUtil.getChar(bytes, 0));
		// 上报消息的目的地址
		stateReportMsg.setDestType(CodecUtil.getChar(bytes, 1));
		// 消息总长度
		int dataLength = CodecUtil.getUnsignedShort(bytes, 2);
		stateReportMsg.setMessageLength(dataLength);
		// 消息类型
		stateReportMsg.setMessageType(CodecUtil.getUnsignedShort(bytes, 4));
		// 事务ID
		stateReportMsg.setTransId(CodecUtil.getUnsignedShort(bytes, 6));
		// 网元类型
		stateReportMsg.setNeType(CodecUtil.getInt(bytes, 8));
		// 实例ID
		stateReportMsg.setInstId(CodecUtil.getInt(bytes, 12));
		// action
		stateReportMsg.setAction(CodecUtil.getChar(bytes, 16));
		// status
		stateReportMsg.setStatus(CodecUtil.getChar(bytes, 17));
		//port
		stateReportMsg.setPort(CodecUtil.getUnsignedShort(bytes, 18));
		//ip
		stateReportMsg.setIp(CodecUtil.getString(bytes, 20, 4));
		//desc
		stateReportMsg.setDesc(CodecUtil.getString(bytes, 24, 32));
		//version
		stateReportMsg.setVersion(CodecUtil.getString(bytes, 56));

		return stateReportMsg;
	}

}
