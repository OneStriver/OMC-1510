package com.sunkaisens.omc.thread.cncpMsg;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import com.sunkaisens.omc.thread.messageEncapsulation.CncpBaseResponseMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.CncpBaseSendMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.OmcToNIMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.QueryMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.SetUpMsg;

/**
 * 发送消息封装类
 */
@Component
public class SendCncpMsgFactory {

	private static final AtomicInteger transactionIds = new AtomicInteger();
	
	// 创建对象(单例模式)
	private SendCncpMsgFactory() {
		
	}
	public static SendCncpMsgFactory getInstance() {
		return SimpleCncpMsgFactoryInstance.instance;
	}
	private static class SimpleCncpMsgFactoryInstance {
		private static SendCncpMsgFactory instance = new SendCncpMsgFactory();
	}
	
	// 发送消息不需要回复的封装
	public CncpBaseResponseMsg createCncpBaseResponseMsg(char srcType,char destType, int messageType,int transId, int neType, int instId, int subType, int holdBit, int result, int resultHoldBit) {
		int messageLength = 20;
		CncpBaseResponseMsg msg = new CncpBaseResponseMsg(srcType, destType, messageLength, messageType, transId, neType, instId, (char)subType, (char)holdBit, (char)result, (char)resultHoldBit);;
		return msg;
	}
	
	// 发送消息不需要回复的封装
	public CncpBaseSendMsg createCncpBaseSendMsg(char srcType,char destType, int messageType, int neType, int instId, int subType,String data) {
		int dataLength = data.getBytes().length;
		int messageLength = 20 + dataLength;
		CncpBaseSendMsg msg = new CncpBaseSendMsg(srcType, destType, messageLength, messageType, getTransactionId(), neType, instId, (char)subType, (char)0, dataLength, data);
		return msg;
	}
	
	// OMC发送给北向接口NI创建的消息
	public OmcToNIMsg createOmcToNICncpMsg(char srcType,char destType, int messageType, int neType, int instId, int subType,String data) {
		int dataLength = data.getBytes().length;
		int messageLength = 20 + dataLength;
		OmcToNIMsg msg = new OmcToNIMsg(srcType, destType, messageLength, messageType, getTransactionId(), neType, instId, (char)subType, (char)0, dataLength, data);
		return msg;
	}
	
	// 设置消息
	public SetUpMsg createSendSetCncpMsg(char srcType,char destType, int messageType, int neType, int instId, int subType,String data) {
		int dataLength = data.getBytes().length;
		int messageLength = 20 + dataLength;
		SetUpMsg msg = new SetUpMsg(srcType, destType, messageLength, messageType, getTransactionId(), neType, instId, (char)subType, (char)0, dataLength, data);
		return msg;
	}
	
	// 查询消息
	public QueryMsg createSendQueryCncpMsg(char srcType,char destType, int messageType, int neType, int instId, int subType,String data) {
		int dataLength = data.getBytes().length;
		int messageLength = 20 + dataLength;
		QueryMsg msg = new QueryMsg(srcType, destType, messageLength, messageType, getTransactionId(), neType, instId, (char)subType, (char)0, dataLength, data);
		return msg;
	}
	
	protected synchronized int getTransactionId() {
		if (transactionIds.get() > 65535){
			transactionIds.set(0);
		}
		return transactionIds.getAndIncrement();
	}
	
}
