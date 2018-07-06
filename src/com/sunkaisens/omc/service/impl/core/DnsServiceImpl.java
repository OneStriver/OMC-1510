package com.sunkaisens.omc.service.impl.core;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunkaisens.omc.context.core.OmcServerContext;
import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.mapper.core.CardMapper;
import com.sunkaisens.omc.po.core.Card;
import com.sunkaisens.omc.service.core.DnsService;
import com.sunkaisens.omc.thread.cncpMsg.CncpProtoType;
import com.sunkaisens.omc.thread.cncpMsg.CncpTaskExecutor;
import com.sunkaisens.omc.thread.cncpMsg.SendCncpMsgFactory;
import com.sunkaisens.omc.thread.messageEncapsulation.QueryMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.QueryResponseMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.SetUpMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.SetUpResponseMsg;
import com.sunkaisens.omc.util.CncpStatusTransUtil;
import com.sunkaisens.omc.vo.core.Dns;

/**
 * DnsService接口实现类
 */
@Service
public class DnsServiceImpl  implements DnsService {

	@Resource
	private CncpTaskExecutor cncpTaskExecutor;
	@Resource
	private SendCncpMsgFactory sendCncpMsgFactory;
	@Resource 
	private CardMapper mapper;
	
	@Override
	public List<Dns> query(Integer cardNum) throws CustomException {
		List<Dns> list=new ArrayList<Dns>();
		if(cardNum==null||cardNum==0)
			for(Card card:mapper.selectAll())
				list.add(getDnsByCardNum(card.getCardNum()));
		else
			list.add(getDnsByCardNum(cardNum));
		return list;
	}
	
	private Dns getDnsByCardNum(Integer cardNum) throws CustomException{
		Dns dns=new Dns();
		dns.setCardNum(cardNum);
		QueryMsg msg = sendCncpMsgFactory.createSendQueryCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_GET_MSG, 
				CncpProtoType.SUB_OAM, cardNum, CncpProtoType.OAM_GET_HOSTNAME, "");
		QueryResponseMsg respMsg = cncpTaskExecutor.invokeQueryMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
		if(respMsg.getResult()==0){
			dns.setHost(respMsg.getData());
			msg = sendCncpMsgFactory.createSendQueryCncpMsg(
					CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_GET_MSG, 
					CncpProtoType.SUB_OAM, cardNum, CncpProtoType.OAM_GET_NAMESERVERS, "");
			respMsg = cncpTaskExecutor.invokeQueryMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
			
			if(respMsg.getResult()==0){
				String response=respMsg.getData();
				if(response!=null){
					String[] dnses=response.split("\n");
					if(dnses.length>=1) dns.setDns1(dnses[0]);
					if(dnses.length>=2) dns.setDns2(dnses[1]);
					if(dnses.length==3) dns.setDns3(dnses[2]);
				}
			}else{
				throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(respMsg.getResult()));
			}
		}else{
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(respMsg.getResult()));
		}
		return dns;
	}

	@Override
	public void update(Dns dns) throws CustomException {
		StringBuilder data=new StringBuilder(dns.getHost());
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
				CncpProtoType.SUB_OAM, dns.getCardNum(), CncpProtoType.OAM_SET_HOSTNAME, data.toString());
		SetUpResponseMsg respMsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
		if(respMsg.getResult()==0){
			data=new StringBuilder();
			if(dns.getDns1()!=null) data.append(dns.getDns1()).append("\n");
			if(dns.getDns2()!=null) data.append(dns.getDns2()).append("\n");
			if(dns.getDns3()!=null) data.append(dns.getDns3()).append("\n");
			
			msg = sendCncpMsgFactory.createSendSetCncpMsg(
					CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
					CncpProtoType.SUB_OAM, dns.getCardNum(), CncpProtoType.OAM_SET_NAMESERVERS, data.toString());
			respMsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
			if(respMsg.getResult()!=0){
				throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(respMsg.getResult()));
			}
		}else{
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(respMsg.getResult()));
		}
	}

}
