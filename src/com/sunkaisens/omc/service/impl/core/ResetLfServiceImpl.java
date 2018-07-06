package com.sunkaisens.omc.service.impl.core;


import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.sunkaisens.omc.context.core.OmcServerContext;
import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.mapper.core.CardMapper;
import com.sunkaisens.omc.po.core.Card;
import com.sunkaisens.omc.service.core.ResetLfService;
import com.sunkaisens.omc.thread.cncpMsg.CncpProtoType;
import com.sunkaisens.omc.thread.cncpMsg.CncpTaskExecutor;
import com.sunkaisens.omc.thread.cncpMsg.SendCncpMsgFactory;
import com.sunkaisens.omc.thread.messageEncapsulation.SetUpMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.SetUpResponseMsg;

/**
 * ResetLfService接口实现类
 */
@Service
public class ResetLfServiceImpl implements ResetLfService {

	@Resource
	private CardMapper cardMapper;
	@Resource
	private CncpTaskExecutor cncpTaskExecutor;
	@Resource
	private SendCncpMsgFactory sendCncpMsgFactory;
	
	@Override
	public void reset() throws CustomException {
		StringBuilder err=new StringBuilder();
		List<Card> cards=cardMapper.selectAll();
		for(Card card:cards){
			SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, CncpProtoType.SUB_OAM, card.getCardNum(), CncpProtoType.OAM_SET_RESET, "");
			SetUpResponseMsg respMsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
			int result=respMsg.getResult();
			if(result!=CncpProtoType.SUCCESS){
				err.append(card.getName()).append(",");
			}
		}
		if(StringUtils.isNotBlank(err.toString())){
			throw new CustomException("板卡"+err.deleteCharAt(err.lastIndexOf(","))+"恢复失败，其余板卡将在系统重启生效");
		}
	}

}
