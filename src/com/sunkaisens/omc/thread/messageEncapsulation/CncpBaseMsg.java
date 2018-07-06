package com.sunkaisens.omc.thread.messageEncapsulation;

/**
 *  DataForm
 *  _________________________________________________________ 
 * |                 Message Header                          |
 * | _______________________________________________________ |
 * | SRC.Type| Dest.Type| Length    | MessageType | Trans.Id |
 * | <8bit>  | <8bit>   | <16bit>   | <16bit>     | <16bit>  |
 * |_________|__________|___________|_____________|__________|
 *  _________________________________________________________
 * |                 Message Meta                    		 |
 * |_________________________________________________________|
 * |    		Ne.Type        		| 		Inst id    		 |
 * |    		<32bit>        		| 		<32bit>    		 |
 * |________________________________|________________________|
 * 
 */
public abstract class CncpBaseMsg {
	
	//:UDP协议头消息长度(Message Header+Message Meta之后的长度(字节数))
	public static final int CNCP_HEAD_LEN = 0x08;
	public static final int CNCP_META_LEN = 0x08;
	
	// 源地址
	public static final char OMC_INTERFACE_IDENTIFY = 0xE0;
	protected char srcType = OMC_INTERFACE_IDENTIFY;
	// 目的地址
	protected char destType;
	// 消息总长度
	protected int messageLength;
	// 消息类型
	protected int messageType;
	// 事务Id
	protected int transId;
	// 网元类型
	protected int neType;
	// 实例ID
	protected int instId;
	
	//无参构造器
	public CncpBaseMsg(){
		
	}
	
	//初始化构造器
	public CncpBaseMsg(char srcType,char destType,int messageLength,int messageType,int transId,int neType,int instId){
		this.srcType = srcType;
		this.destType = destType;
		this.messageLength = messageLength;
		this.messageType = messageType;
		this.transId = transId;
		this.neType = neType;
		this.instId = instId;
	}
	
    public char getSrcType(){
    	return srcType;
    }
    
    public void setSrcType(char srcType) {
		this.srcType = srcType;
	}

	public char getDestType() {
		return destType;
	}
    
    public void setDestType(char type){
    	this.destType = type;
    }
    
	public int getMessageLength() {
		return messageLength;
	}

	public void setMessageLength(int messageLength) {
		this.messageLength = messageLength;
	}

	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	public int getTransId() {
		return transId;
	}

	public void setTransId(int transId) {
		this.transId = transId;
	}

	public int getNeType() {
		return neType;
	}

	public void setNeType(int neType) {
		this.neType = neType;
	}

	public int getInstId() {
		return instId;
	}

	public void setInstId(int instId) {
		this.instId = instId;
	}
    
}
