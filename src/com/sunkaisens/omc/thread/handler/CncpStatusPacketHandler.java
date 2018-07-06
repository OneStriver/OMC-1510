package com.sunkaisens.omc.thread.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sunkaisens.omc.thread.cncpMsg.CncpProtoType;
import com.sunkaisens.omc.thread.messageEncapsulation.StateReportMsg;
import com.sunkaisens.omc.thread.observerProcess.ObserverObject;
import com.sunkaisens.omc.thread.udpMsgEntity.Packet;
/**
 * Cncp私有消息状态消息处理线程
 */
@Component
public class CncpStatusPacketHandler implements ObserverObject {
	
	private Logger logger = LoggerFactory.getLogger(NI1510TerminalInfoUpdatePacketHandler.class);
	
	// 创建对象(单例模式)
	private CncpStatusPacketHandler() {
		
	}
	public static CncpStatusPacketHandler getInstance() {
		return CncpStatusPacketHandlerInstance.instance;
	}
	private static class CncpStatusPacketHandlerInstance {
		private static CncpStatusPacketHandler instance = new CncpStatusPacketHandler();
	}

	@Override
	public synchronized void processCncpMsg(Object msg) {
		//首先清空数据
		Packet packet = (Packet)msg;
		byte[] bytes = packet.getMsg();
		//获取消息消息的内容
		StateReportMsg stateReportMsg = new StateReportMsg();
		stateReportMsg = stateReportMsg.readFromSignalTrackReport(bytes, stateReportMsg);
		if(stateReportMsg.getMessageType() != CncpProtoType.CNCP_STATUS_MSG){
			return;
		}
		logger.info("Dispatch on thread : [ ===================网元状态上报处理================== ]");
	}
	
}
