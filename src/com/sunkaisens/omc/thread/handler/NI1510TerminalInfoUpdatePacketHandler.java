package com.sunkaisens.omc.thread.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sunkaisens.omc.component.SpringContextUtil;
import com.sunkaisens.omc.context.core.OmcServerContext;
import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.po.northInterface.Limit;
import com.sunkaisens.omc.po.northInterface.Property;
import com.sunkaisens.omc.po.northInterface.Session;
import com.sunkaisens.omc.po.northInterface.Terminal;
import com.sunkaisens.omc.po.northInterface.TerminalInfoConfigFileEntity;
import com.sunkaisens.omc.service.hss.HssService;
import com.sunkaisens.omc.thread.cncpMsg.CncpProtoType;
import com.sunkaisens.omc.thread.cncpMsg.CncpTaskExecutor;
import com.sunkaisens.omc.thread.cncpMsg.SendCncpMsgFactory;
import com.sunkaisens.omc.thread.messageEncapsulation.CncpBaseResponseMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.OmcToNIMsg;
import com.sunkaisens.omc.thread.observerProcess.ObserverObject;
import com.sunkaisens.omc.thread.udpMsgEntity.Packet;
import com.sunkaisens.omc.util.hss.XMLUtil;
import com.sunkaisens.omc.vo.hss.HssBusinessVo;
/**
 * 接收北向接口的终端信息更新消息
 */
@Component
public class NI1510TerminalInfoUpdatePacketHandler implements ObserverObject {
	
	private Logger logger = LoggerFactory.getLogger(NI1510TerminalInfoUpdatePacketHandler.class);
	private List<TerminalInfoConfigFileEntity> terminalInfoList = new ArrayList<TerminalInfoConfigFileEntity>();
	
	// 创建对象(单例模式)
	private NI1510TerminalInfoUpdatePacketHandler() {
		
	}
	public static NI1510TerminalInfoUpdatePacketHandler getInstance() {
		return NI1510TerminalInfoUpdatePacketHandlerInstance.instance;
	}
	private static class NI1510TerminalInfoUpdatePacketHandlerInstance {
		private static NI1510TerminalInfoUpdatePacketHandler instance = new NI1510TerminalInfoUpdatePacketHandler();
	}

	@Override
	public synchronized void processCncpMsg(Object msg) {
		//首先清空数据
		terminalInfoList.clear();
		logger.info("Dispatch on thread : [ NI1510TerminalInfoUpdatePacketHandler ]");
		Packet packet = (Packet)msg;
		byte[] bytes = packet.getMsg();
		//获取消息消息的内容
		OmcToNIMsg omcToNIMsg = new OmcToNIMsg();
		omcToNIMsg = omcToNIMsg.readFromOmcToNIMsg(bytes, omcToNIMsg);
		if(omcToNIMsg.getMessageType() != CncpProtoType.CNCP_NI_OMC_MSG){
			return;
		}
		String respStr = omcToNIMsg.getData();
		logger.info("接收到1510-OMC发送XML字符串数据:\n"+respStr);
		
		//将XML字符串解析出来封装成对象
		Session convertSessionObject = (Session)XMLUtil.convertXmlStrToObject(Session.class, respStr);
		//获取需要修改的用户List
		List<Terminal> recvTerminals = convertSessionObject.getCmd().getRoutings().get(0).getTermInfos().get(0).getTerminals();
		for (Terminal terminal : recvTerminals) {
			TerminalInfoConfigFileEntity terminalInfoConfigFileEntity = new TerminalInfoConfigFileEntity();
			//用户的电话号码
			String userNumber = terminal.getDeviceNumber();
			terminalInfoConfigFileEntity.setDeviceNumber(userNumber);
			//用户的IMSI和优先级
			List<Property> propertys = terminal.getProperties().get(0).getPropertys();
			//用户的优先级
			for (Property property : propertys) {
				if(property.getName().equals("IMSI")){
					terminalInfoConfigFileEntity.setImsi(property.getValue());
				}else if(property.getName().equals("Priority")){
					terminalInfoConfigFileEntity.setPriority(property.getValue());
				}
			}
			//用户的出局呼叫权(也就是OMC的国际业务)
			List<Limit> limits = terminal.getLimits().get(0).getLimits();
			for (Limit limit : limits) {
				if(limit.getName().equals("PermitCallOut")){
					terminalInfoConfigFileEntity.setPermitCallOut(limit.getValue());
				}
			}
			//添加到List集合中
			terminalInfoList.add(terminalInfoConfigFileEntity);
		}
		
		try {
			for (TerminalInfoConfigFileEntity terminalInfoConfig : terminalInfoList) {
				//查询出该终端用户信息
				HssService service = (HssService)SpringContextUtil.getApplicationContext().getBean(HssService.class);
				HssBusinessVo hssVo = service.getById(terminalInfoConfig.getImsi());
				hssVo.setPriority(Integer.valueOf(terminalInfoConfig.getPriority()));
				hssVo.setInternationality(Integer.valueOf(terminalInfoConfig.getPermitCallOut()));
				hssVo.setMdn(terminalInfoConfig.getDeviceNumber());
				service.update(hssVo,4);
			}
			//发送给北向接口更新之后结果(成功或失败)
			sendUpdateResult1510North(4,"",omcToNIMsg.getTransId());
		} catch (CustomException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送给1510的北向接口
	 */
	private void sendUpdateResult1510North(Integer userOperation,String reason,int transId) throws CustomException {
		SendCncpMsgFactory sendCncpMsgFactory = (SendCncpMsgFactory)SpringContextUtil.getApplicationContext().getBean(SendCncpMsgFactory.class);
		CncpBaseResponseMsg msg = sendCncpMsgFactory.createCncpBaseResponseMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.NI_HEADER, CncpProtoType.CNCP_NI_OMC_ACK_MSG,transId, 255, 1,0, 0, 0,0);
		//1510北向接口的地址
		CncpTaskExecutor cncpTaskExecutor = (CncpTaskExecutor)SpringContextUtil.getApplicationContext().getBean(CncpTaskExecutor.class);
		cncpTaskExecutor.invokeCncpResponseMsg(msg, OmcServerContext.getInstance().getNorthIp(), Integer.valueOf(OmcServerContext.getInstance().getNorthPort()));
	}
	
}
