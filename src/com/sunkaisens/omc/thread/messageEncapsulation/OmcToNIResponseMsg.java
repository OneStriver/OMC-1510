package com.sunkaisens.omc.thread.messageEncapsulation;

import com.sunkaisens.omc.util.CodecUtil;

/**
 * OMC发送消息给北向接口,NI北向接口回复的消息 (0x8011)
 */
public class OmcToNIResponseMsg extends CncpBaseMsg {

	private char subType;
	private char holdBit;
	private char result;
	private char resultHoldBit;
	
	public OmcToNIResponseMsg() {
		super();
	}
	
	public OmcToNIResponseMsg(char srcType,char destType,int messageLength,int messageType,int transId,int neType,int instId,char subType,char holdBit,char result,char resultHoldBit) {
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
	public OmcToNIResponseMsg readFromSetUpResponseMsg(byte[] bytes, OmcToNIResponseMsg setUpMsg) {
		//上报消息的源地址
    	setUpMsg.setSrcType(CodecUtil.getChar(bytes, 0));
    	//上报消息的目的地址
    	setUpMsg.setDestType(CodecUtil.getChar(bytes, 1));
    	//消息总长度
    	int dataLength = CodecUtil.getUnsignedShort(bytes, 2);
    	setUpMsg.setMessageLength(dataLength);
    	//消息类型
    	setUpMsg.setMessageType(CodecUtil.getUnsignedShort(bytes, 4));
    	//事务ID
    	setUpMsg.setTransId(CodecUtil.getUnsignedShort(bytes, 6));
    	//网元类型
    	setUpMsg.setNeType(CodecUtil.getInt(bytes, 8));
    	//实例ID
    	setUpMsg.setInstId(CodecUtil.getInt(bytes, 12));
    	//SubType
    	setUpMsg.setSubType(CodecUtil.getChar(bytes, 16));
    	//holdBit
    	setUpMsg.setHoldBit(CodecUtil.getChar(bytes, 17));
    	//Result
    	setUpMsg.setResult(CodecUtil.getChar(bytes, 18));
    	//resultHoldBit
    	setUpMsg.setResultHoldBit(CodecUtil.getChar(bytes, 19));
    	
    	return setUpMsg;
	}

}