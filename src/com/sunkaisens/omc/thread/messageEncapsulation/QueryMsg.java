package com.sunkaisens.omc.thread.messageEncapsulation;

import com.sunkaisens.omc.util.CodecUtil;

/**
 * 查询消息 (0x7003)
 */
public class QueryMsg extends CncpBaseMsg {

	private char subType;
	private char holdBit;
	private int dataLength;
	private String data;

	public QueryMsg() {
		super();
	}
	
	public QueryMsg(char srcType,char destType,int messageLength,int messageType,int transId,int neType,int instId,char subType, char holdBit, int dataLength, String data) {
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

	public void setHoldBit(char holdBit) {
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

	/**
	 * 查询消息(将对象中的值转换成bytes[]数组)
	 */
	public byte[] writeToQueryMsg(byte[] bytes, QueryMsg setUpMsg) {
		// 设置消息的源地址
		CodecUtil.putChar(bytes, setUpMsg.getSrcType(), 0);
		// 设置消息的目的地址
		CodecUtil.putChar(bytes, setUpMsg.getDestType(), 1);
		// 消息总长度
		CodecUtil.putUnsignedShort(bytes, setUpMsg.getMessageLength(), 2);
		// 消息类型
		CodecUtil.putUnsignedShort(bytes, setUpMsg.getMessageType(), 4);
		// 事务ID
		CodecUtil.putUnsignedShort(bytes, setUpMsg.getTransId(), 6);
		// 网元类型
		CodecUtil.putUnsignedInt(bytes, setUpMsg.getNeType(), 8);
		// 实例ID
		CodecUtil.putUnsignedInt(bytes, setUpMsg.getInstId(), 12);
		// 子类型
		CodecUtil.putChar(bytes, setUpMsg.getSubType(), 16);
		//保留位
		CodecUtil.putChar(bytes, setUpMsg.getHoldBit(), 17);
		//数据长度
		int dataLength = setUpMsg.getData().length();
		CodecUtil.putUnsignedShort(bytes, dataLength, 18);
		// data
		CodecUtil.putStringToChars(bytes, setUpMsg.getData(), 20);
		
		return bytes;
	}

}