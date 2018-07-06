package com.sunkaisens.omc.thread.messageEncapsulation;

import com.sunkaisens.omc.util.CodecUtil;

/**
 * 设置响应消息 (0x7004)
 */
public class QueryResponseMsg extends CncpBaseMsg {

	private char subType;
	private char holdBit;
	private char result;
	private char resultHoldBit;
	private int dataLength;
	private String data;
	
	public QueryResponseMsg() {
		super();
	}
	
	public QueryResponseMsg(char srcType,char destType,int messageLength,int messageType,int transId,int neType,int instId,char subType,char holdBit,char result,char resultHoldBit,int dataLength,String data) {
		super(srcType,destType,messageLength,messageType,transId,neType,instId);
		this.subType = subType;
		this.holdBit = holdBit;
		this.result = result;
		this.resultHoldBit = resultHoldBit;
		this.dataLength = dataLength;
		this.data = data;
	}

	public char getSubType() {
		return subType;
	}

	public void setSubType(char subType) {
		this.subType = subType;
	}

	public char getHoldBit() {
		return holdBit;
	}

	public void setHoldBit(char holdBit) {
		this.holdBit = holdBit;
	}

	public char getResult() {
		return result;
	}

	public void setResult(char result) {
		this.result = result;
	}

	public char getResultHoldBit() {
		return resultHoldBit;
	}

	public void setResultHoldBit(char resultHoldBit) {
		this.resultHoldBit = resultHoldBit;
	}

	public int getDataLength() {
		return dataLength;
	}

	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	/**
	 * 设置响应消息
	 */
	public QueryResponseMsg readFromSetUpResponseMsg(byte[] bytes, QueryResponseMsg queryResponseMsg) {
		// 上报消息的源地址
		queryResponseMsg.setSrcType(CodecUtil.getChar(bytes, 0));
		// 上报消息的目的地址
		queryResponseMsg.setDestType(CodecUtil.getChar(bytes, 1));
		// 消息总长度
		int dataLength = CodecUtil.getUnsignedShort(bytes, 2);
		queryResponseMsg.setMessageLength(dataLength);
		// 消息类型
		queryResponseMsg.setMessageType(CodecUtil.getUnsignedShort(bytes, 4));
		// 事务ID
		queryResponseMsg.setTransId(CodecUtil.getUnsignedShort(bytes, 6));
		// 网元类型
		queryResponseMsg.setNeType(CodecUtil.getInt(bytes, 8));
		// 实例ID
		queryResponseMsg.setInstId(CodecUtil.getInt(bytes, 12));
		// SubType
		queryResponseMsg.setSubType(CodecUtil.getChar(bytes, 16));
		// holdBit
		queryResponseMsg.setHoldBit(CodecUtil.getChar(bytes, 17));
		// Result
		queryResponseMsg.setResult(CodecUtil.getChar(bytes, 18));
		// resultHoldBit
		queryResponseMsg.setResultHoldBit(CodecUtil.getChar(bytes, 19));
		// data Length
		queryResponseMsg.setDataLength(CodecUtil.getUnsignedShort(bytes, 20));
		// data
		queryResponseMsg.setData(CodecUtil.getString(bytes, 22));
		
		return queryResponseMsg;
	}

}