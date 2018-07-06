package com.sunkaisens.omc.thread.messageEncapsulation;

import com.sunkaisens.omc.util.CodecUtil;

/**
 * 基本的回复消息(只是回复成功或者失败)
 */
public class CncpBaseResponseMsg extends CncpBaseMsg {

	private char subType;
	private char holdBit;
	private char result;
	private char resultHoldBit;
	
	public CncpBaseResponseMsg() {
		super();
	}
	
	public CncpBaseResponseMsg(char srcType,char destType,int messageLength,int messageType,int transId,int neType,int instId,char subType,char holdBit,char result,char resultHoldBit) {
		super(srcType,destType,messageLength,messageType,transId,neType,instId);
		this.subType = subType;
		this.holdBit = holdBit;
		this.result = result;
		this.resultHoldBit = resultHoldBit;
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

	/**
	 * 设置响应消息
	 */
	public byte[] writeCncpBaseResponseMsg(byte[] bytes, CncpBaseResponseMsg responseMsg) {
    	// 设置消息的源地址
		CodecUtil.putChar(bytes, responseMsg.getSrcType(), 0);
		// 设置消息的目的地址
		CodecUtil.putChar(bytes, responseMsg.getDestType(), 1);
		// 消息总长度
		CodecUtil.putUnsignedShort(bytes, responseMsg.getMessageLength(), 2);
		// 消息类型
		CodecUtil.putUnsignedShort(bytes, responseMsg.getMessageType(), 4);
		// 事务ID
		CodecUtil.putUnsignedShort(bytes, responseMsg.getTransId(), 6);
		// 网元类型
		CodecUtil.putUnsignedInt(bytes, responseMsg.getNeType(), 8);
		// 实例ID
		CodecUtil.putUnsignedInt(bytes, responseMsg.getInstId(), 12);
		// 子类型
		CodecUtil.putChar(bytes, responseMsg.getSubType(), 16);
		//保留位
		CodecUtil.putChar(bytes, responseMsg.getHoldBit(), 17);
		//结果
		CodecUtil.putChar(bytes, responseMsg.getResult(), 18);
		// 结果保留位
		CodecUtil.putChar(bytes, responseMsg.getResultHoldBit(), 19);
		
		return bytes;
    	
	}

}