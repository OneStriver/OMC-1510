package com.sunkaisens.omc.thread.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sunkaisens.omc.thread.cncpMsg.CncpProtoType;
import com.sunkaisens.omc.thread.messageEncapsulation.AlarmReportMsg;
import com.sunkaisens.omc.thread.observerProcess.ObserverObject;
import com.sunkaisens.omc.thread.udpMsgEntity.Packet;
/**
 * Cncp私有消息告警消息处理线程
 */
@Component
public class CncpAlarmPacketHandler implements ObserverObject {
	
	private Logger logger = LoggerFactory.getLogger(NI1510TerminalInfoUpdatePacketHandler.class);
	
	// 创建对象(单例模式)
	private CncpAlarmPacketHandler() {
		
	}
	public static CncpAlarmPacketHandler getInstance() {
		return CncpAlarmPacketHandlerInstance.instance;
	}
	private static class CncpAlarmPacketHandlerInstance {
		private static CncpAlarmPacketHandler instance = new CncpAlarmPacketHandler();
	}

	@Override
	public synchronized void processCncpMsg(Object msg) {
		//首先清空数据
		Packet packet = (Packet)msg;
		byte[] bytes = packet.getMsg();
		//获取消息消息的内容
		AlarmReportMsg alarmReportMsg = new AlarmReportMsg();
		alarmReportMsg = alarmReportMsg.readFromAlarmReport(bytes, alarmReportMsg);
		if(alarmReportMsg.getMessageType() != CncpProtoType.CNCP_ALARM_MSG){
			return;
		}
		logger.info("Dispatch on thread : [ ===================告警上报处理================== ]");
	}
	
}
