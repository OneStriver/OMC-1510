package com.sunkaisens.omc.service.impl.core;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunkaisens.omc.context.core.OmcServerContext;
import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.mapper.core.CardMapper;
import com.sunkaisens.omc.po.core.Card;
import com.sunkaisens.omc.service.core.SerialNumberService;
import com.sunkaisens.omc.thread.cncpMsg.CncpProtoType;
import com.sunkaisens.omc.thread.cncpMsg.CncpTaskExecutor;
import com.sunkaisens.omc.thread.cncpMsg.SendCncpMsgFactory;
import com.sunkaisens.omc.thread.messageEncapsulation.QueryMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.QueryResponseMsg;
import com.sunkaisens.omc.util.CncpStatusTransUtil;
import com.sunkaisens.omc.vo.core.SerialNumber;

/**
 * SerialNumberService接口实现类
 */
@Service
public class SerialNumberServiceImpl implements SerialNumberService {

	@Resource
	private CardMapper cardMapper;
	@Resource
	private CncpTaskExecutor cncpTaskExecutor;
	@Resource
	private SendCncpMsgFactory sendCncpMsgFactory;
	
	@Override
	public List<SerialNumber> readSerial() throws CustomException{
		List<SerialNumber> list=new ArrayList<SerialNumber>();
		List<Card> cards=cardMapper.selectAll();
		for(Card card:cards){
			QueryMsg msg = sendCncpMsgFactory.createSendQueryCncpMsg(
					CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_GET_MSG, 
					CncpProtoType.SUB_OAM, card.getCardNum(), CncpProtoType.OAM_GET_HD_SERIAL_NO, "");
			QueryResponseMsg respMsg = cncpTaskExecutor.invokeQueryMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
			int result=respMsg.getResult();
			if(result==CncpProtoType.SUCCESS){
				SerialNumber ser=new SerialNumber(respMsg.getData());
				ser.setName(card.getName());
				list.add(ser);
			}else if(result!=CncpProtoType.ERR_HOST_LOST){
				throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(result));
			}
		}
		return list;
	}
	
}
