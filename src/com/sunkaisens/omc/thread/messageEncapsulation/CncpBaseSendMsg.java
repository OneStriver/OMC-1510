package com.sunkaisens.omc.thread.messageEncapsulation;

import com.sunkaisens.omc.util.CodecUtil;

/**
 * OMC收到的NI的更新消息
 */
public class CncpBaseSendMsg extends CncpBaseMsg {

	private char subType;
	private char holdBit;
	private int dataLength;
	private String data;
	
	public CncpBaseSendMsg() {
		super();
	}
	
	public CncpBaseSendMsg(char srcType,char destType,int messageLength,int messageType,int transId,int neType,int instId,char subType, char holdBit, int dataLength, String data) {
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
	public byte[] writeToCncpBaseSendMsg(byte[] bytes, CncpBaseSendMsg cncpBaseSendMsg) {
		// 设置消息的源地址
		CodecUtil.putChar(bytes, cncpBaseSendMsg.getSrcType(), 0);
		// 设置消息的目的地址
		CodecUtil.putChar(bytes, cncpBaseSendMsg.getDestType(), 1);
		// 消息总长度
		CodecUtil.putUnsignedShort(bytes, cncpBaseSendMsg.getMessageLength(), 2);
		// 消息类型
		CodecUtil.putUnsignedShort(bytes, cncpBaseSendMsg.getMessageType(), 4);
		// 事务ID
		CodecUtil.putUnsignedShort(bytes, cncpBaseSendMsg.getTransId(), 6);
		// 网元类型
		CodecUtil.putUnsignedInt(bytes, cncpBaseSendMsg.getNeType(), 8);
		// 实例ID
		CodecUtil.putUnsignedInt(bytes, cncpBaseSendMsg.getInstId(), 12);
		// 子类型
		CodecUtil.putChar(bytes, cncpBaseSendMsg.getSubType(), 16);
		//保留位
		CodecUtil.putChar(bytes, cncpBaseSendMsg.getHoldBit(), 17);
		//数据长度
		CodecUtil.putUnsignedShort(bytes, cncpBaseSendMsg.getDataLength(), 18);
		// data
		CodecUtil.putStringToChars(bytes, cncpBaseSendMsg.getData(), 20);
		
		return bytes;
	}
	
	/**
	 * 设置消息
	 */
	public CncpBaseSendMsg readFromCncpBaseSendMsg(byte[] bytes, CncpBaseSendMsg cncpBaseSendMsg) {
		// 设置消息的源地址
		cncpBaseSendMsg.setSrcType(CodecUtil.getChar(bytes, 0));
		// 设置消息的目的地址
		cncpBaseSendMsg.setDestType(CodecUtil.getChar(bytes, 1));
		// 消息总长度
		cncpBaseSendMsg.setMessageLength(CodecUtil.getUnsignedShort(bytes, 2));
		// 消息类型
		cncpBaseSendMsg.setMessageType(CodecUtil.getUnsignedShort(bytes, 4));
		// 事务ID
		cncpBaseSendMsg.setTransId(CodecUtil.getUnsignedShort(bytes, 6));
		// 网元类型
		cncpBaseSendMsg.setNeType(CodecUtil.getInt(bytes, 8));
		// 实例ID
		cncpBaseSendMsg.setInstId(CodecUtil.getInt(bytes, 12));
		// 子类型
		cncpBaseSendMsg.setSubType(CodecUtil.getChar(bytes, 16));
		//保留位
		cncpBaseSendMsg.setHoleBit(CodecUtil.getChar(bytes, 17));
		//数据长度
		cncpBaseSendMsg.setDataLength(CodecUtil.getUnsignedShort(bytes, 18));
		// data
		cncpBaseSendMsg.setData(CodecUtil.getString(bytes, 20));
		
		return cncpBaseSendMsg;
	}

}