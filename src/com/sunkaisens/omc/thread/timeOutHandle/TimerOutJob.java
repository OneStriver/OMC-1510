package com.sunkaisens.omc.thread.timeOutHandle;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunkaisens.omc.context.core.OmcServerContext;
import com.sunkaisens.omc.thread.cncpMsg.CncpProtoType;
import com.sunkaisens.omc.thread.messageEncapsulation.CncpBaseResponseMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.OmcToNIResponseMsg;
import com.sunkaisens.omc.thread.observerProcess.UDPNetComm;
import com.sunkaisens.omc.thread.udpMsgEntity.Packet;
import com.sunkaisens.omc.util.CodecUtil;
import com.sunkaisens.omc.util.HexDump;

/**
 * 定义TimerHandlerProxy类，指定需要执行的调度任务
 */
public class TimerOutJob implements Job {

	private static Logger logger = LoggerFactory.getLogger(TimerOutJob.class);
	public final static String USERDATA = "NI_TASKUSERDATA";

	public TimerOutJob() {
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		CncpBaseResponseMsg reqCncp = (CncpBaseResponseMsg)context.getJobDetail().getJobDataMap().remove(USERDATA);
		int ackType = 0;
		if (reqCncp.getMessageType() == CncpProtoType.CNCP_GET_MSG) {
			ackType = CncpProtoType.CNCP_GET_ACK_MSG;
		} else if (reqCncp.getMessageType() == CncpProtoType.CNCP_SET_MSG) {
			ackType = CncpProtoType.CNCP_SET_ACK_MSG;
		}else if(reqCncp.getMessageType() == CncpProtoType.CNCP_NI_OMC_MSG){
			ackType = CncpProtoType.CNCP_NI_OMC_ACK_MSG;
		}else if(reqCncp.getMessageType() == CncpProtoType.CNCP_OMC_NI_MSG){
			ackType = CncpProtoType.CNCP_OMC_NI_ACK_MSG;
		}
		//封装超时回复消息
		OmcToNIResponseMsg timeOutCncp = new OmcToNIResponseMsg(reqCncp.getDestType(), reqCncp.getSrcType(), 
				reqCncp.getMessageLength(), ackType, reqCncp.getTransId(), reqCncp.getNeType(), reqCncp.getInstId(),
				reqCncp.getSubType(), reqCncp.getHoldBit(), (char) CncpProtoType.TIMEOUT, (char)0);
		byte[] timeOutBytes = new byte[20];
		timeOutBytes = writeOmcToNIResponseMsg(timeOutBytes,timeOutCncp);
		logger.info("打印超时消息:\n" + HexDump.dump(timeOutBytes, 0, 0));
		Packet pack = new Packet();
		pack.setLen(20);
		pack.setMsg(timeOutBytes);
		pack.setIpAddress(OmcServerContext.getInstance().getOmcIp());
		pack.setPort(OmcServerContext.getInstance().getOmcPort());
		try {
			UDPNetComm.responseMsgQueue.put(pack);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置消息
	 */
	public byte[] writeOmcToNIResponseMsg(byte[] bytes, OmcToNIResponseMsg omcToNIResponseMsg) {
		// 设置消息的源地址
		CodecUtil.putChar(bytes, omcToNIResponseMsg.getSrcType(), 0);
		// 设置消息的目的地址
		CodecUtil.putChar(bytes, omcToNIResponseMsg.getDestType(), 1);
		// 消息总长度
		CodecUtil.putUnsignedShort(bytes, omcToNIResponseMsg.getMessageLength(), 2);
		// 消息类型
		CodecUtil.putUnsignedShort(bytes, omcToNIResponseMsg.getMessageType(), 4);
		// 事务ID
		CodecUtil.putUnsignedShort(bytes, omcToNIResponseMsg.getTransId(), 6);
		// 网元类型
		CodecUtil.putUnsignedInt(bytes, omcToNIResponseMsg.getNeType(), 8);
		// 实例ID
		CodecUtil.putUnsignedInt(bytes, omcToNIResponseMsg.getInstId(), 12);
		// 子类型
		CodecUtil.putChar(bytes, omcToNIResponseMsg.getSubType(), 16);
		//保留位
		CodecUtil.putChar(bytes, omcToNIResponseMsg.getHoldBit(), 17);
		//数据长度
		CodecUtil.putChar(bytes, omcToNIResponseMsg.getResult(), 18);
		// data
		CodecUtil.putChar(bytes, omcToNIResponseMsg.getResultHoldBit(), 19);
		
		return bytes;
	}
	

}
