package com.sunkaisens.omc.thread.messageEncapsulation;

import com.sunkaisens.omc.util.CodecUtil;

/**
 * OMC发送消息给NI北向接口 (0x8010)
 */
public class OmcToNIMsg extends CncpBaseMsg {

	private char subType;
	private char holdBit;
	private int dataLength;
	private String data;
	
	public OmcToNIMsg() {
		super();
	}
	
	public OmcToNIMsg(char srcType,char destType,int messageLength,int messageType,int transId,int neType,int instId,char subType, char holdBit, int dataLength, String data) {
		super(srcType,destType,messageLength,messageType,transId,neType,instId);
		this.subType = subType;
		this.holdBit = holdBit;
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

	public void setHoleBit(char holdBit) {
		this.holdBit = holdBit;
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

	public int getLength() {
		return (CNCP_HEAD_LEN + CNCP_META_LEN + getDataLength());
	}

	/**
	 * 设置消息
	 */
	public byte[] writeToOmcToNIMsg(byte[] bytes, OmcToNIMsg omcToNIMsg) {
		// 设置消息的源地址
		CodecUtil.putChar(bytes, omcToNIMsg.getSrcType(), 0);
		// 设置消息的目的地址
		CodecUtil.putChar(bytes, omcToNIMsg.getDestType(), 1);
		// 消息总长度
		CodecUtil.putUnsignedShort(bytes, omcToNIMsg.getMessageLength(), 2);
		// 消息类型
		CodecUtil.putUnsignedShort(bytes, omcToNIMsg.getMessageType(), 4);
		// 事务ID
		CodecUtil.putUnsignedShort(bytes, omcToNIMsg.getTransId(), 6);
		// 网元类型
		CodecUtil.putUnsignedInt(bytes, omcToNIMsg.getNeType(), 8);
		// 实例ID
		CodecUtil.putUnsignedInt(bytes, omcToNIMsg.getInstId(), 12);
		// 子类型
		CodecUtil.putChar(bytes, omcToNIMsg.getSubType(), 16);
		//保留位
		CodecUtil.putChar(bytes, omcToNIMsg.getHoldBit(), 17);
		//数据长度
		CodecUtil.putUnsignedShort(bytes, omcToNIMsg.getDataLength(), 18);
		// data
		CodecUtil.putStringToChars(bytes, omcToNIMsg.getData(), 20);
		
		return bytes;
	}
	
	/**
	 * 设置消息
	 */
	public OmcToNIMsg readFromOmcToNIMsg(byte[] bytes, OmcToNIMsg omcToNIMsg) {
		// 设置消息的源地址
		omcToNIMsg.setSrcType(CodecUtil.getChar(bytes, 0));
		// 设置消息的目的地址
		omcToNIMsg.setDestType(CodecUtil.getChar(bytes, 1));
		// 消息总长度
		omcToNIMsg.setMessageLength(CodecUtil.getUnsignedShort(bytes, 2));
		// 消息类型
		omcToNIMsg.setMessageType(CodecUtil.getUnsignedShort(bytes, 4));
		// 事务ID
		omcToNIMsg.setTransId(CodecUtil.getUnsignedShort(bytes, 6));
		// 网元类型
		omcToNIMsg.setNeType(CodecUtil.getInt(bytes, 8));
		// 实例ID
		omcToNIMsg.setInstId(CodecUtil.getInt(bytes, 12));
		// 子类型
		omcToNIMsg.setSubType(CodecUtil.getChar(bytes, 16));
		//保留位
		omcToNIMsg.setHoleBit(CodecUtil.getChar(bytes, 17));
		//数据长度
		omcToNIMsg.setDataLength(CodecUtil.getUnsignedShort(bytes, 18));
		// data
		omcToNIMsg.setData(CodecUtil.getString(bytes, 20));
		
		return omcToNIMsg;
	}

}