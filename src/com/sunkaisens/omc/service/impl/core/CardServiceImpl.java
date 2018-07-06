package com.sunkaisens.omc.service.impl.core;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunkaisens.omc.context.core.OmcServerContext;
import com.sunkaisens.omc.mapper.core.CardMapper;
import com.sunkaisens.omc.mapper.core.EntityMapper;
import com.sunkaisens.omc.mapper.core.ModuleMapper;
import com.sunkaisens.omc.po.core.Card;
import com.sunkaisens.omc.service.core.CardService;
import com.sunkaisens.omc.thread.cncpMsg.CncpProtoType;
import com.sunkaisens.omc.thread.cncpMsg.CncpTaskExecutor;
import com.sunkaisens.omc.thread.cncpMsg.SendCncpMsgFactory;
import com.sunkaisens.omc.thread.messageEncapsulation.QueryMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.QueryResponseMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.SetUpMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.SetUpResponseMsg;
import com.sunkaisens.omc.vo.core.PageBean;
/**
 * 
 *CardService接口实现类(板卡管理service层)
 *
 */
@Service
public class CardServiceImpl implements CardService {
	@Resource
	private ModuleMapper moduleMapper;
	@Resource
	private EntityMapper entityMapper;
	@Resource
	private CardMapper mapper;
	@Resource
	private CncpTaskExecutor cncpTaskExecutor;
	@Resource
	private SendCncpMsgFactory sendCncpMsgFactory;
	
	private static int OAM_ERROR_SUCCESS = 0x00;
	private static int OAM_ERROR_HOST_NEXIST = 0x14;
	private static int OAM_ERROR_EXIST = 0x17;
	private static int OAM_ERROR_IN_USE = 0x18;
	
	public SetUpResponseMsg sendAddRemoveMsg(int cardNum,int addRemoveFlag) {
		SetUpResponseMsg invoke = null;
		SetUpMsg msg = null;
		if(addRemoveFlag == 1){
			msg = sendCncpMsgFactory.createSendSetCncpMsg(
					CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
					CncpProtoType.SUB_OAM, cardNum, CncpProtoType.OAM_SET_SLAVE_ADD, "");
			System.err.println("添加板卡发送消息");
		}
		if(addRemoveFlag == 0){
			msg = sendCncpMsgFactory.createSendSetCncpMsg(
					CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
					CncpProtoType.SUB_OAM, cardNum, CncpProtoType.OAM_SET_SLAVE_RMV, "");
			System.err.println("删除板卡发送消息");
		}
		invoke = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
		return invoke;
	}
	
	@Override
	public int save(Card card) {
		int cardNum = card.getCardNum();
		System.err.println("cardNum:"+cardNum);
		//添加之前发送消息  1表示保存操作
		SetUpResponseMsg responseMsg = sendAddRemoveMsg(cardNum, 1);
		int result = responseMsg.getResult();
		System.err.println("返回消息的子类型:"+Integer.valueOf(result));
		//根据返回消息的类型判断是否操作数据库
		
		if(result == OAM_ERROR_SUCCESS){
			mapper.insert(card);
			
		}else if((result == OAM_ERROR_EXIST)){
			System.err.println("不需要修改数据库");
		}
		return result;
	}

	@Override
	public void update(Card card) {
		mapper.update(card);
	}

	@Override
	public int deleteById(Integer id){
		Card selectCard = mapper.selectById(id);
		int cardNum = selectCard.getCardNum();
		System.err.println("cardNum:"+cardNum);
		//删除之前发送消息   0表示删除操作
		SetUpResponseMsg responseMsg = sendAddRemoveMsg(cardNum, 0);
		int result = responseMsg.getResult();
		System.err.println("返回消息的子类型:"+Integer.valueOf(result));
		if(result == OAM_ERROR_SUCCESS){
			mapper.deleteById(id);
		}else if((result == OAM_ERROR_HOST_NEXIST) || (result == OAM_ERROR_IN_USE)){
			System.err.println("不需要修改数据库");
		}
		return result;
	}
	
	//查询出所有的板卡信息
	@Override
	public List<Card> findAll(){
		List<Card> cards=mapper.selectAll();
		for(Card card:cards){
			QueryMsg msg = sendCncpMsgFactory.createSendQueryCncpMsg(
					CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_GET_MSG, 
					CncpProtoType.SUB_OAM, card.getCardNum(), CncpProtoType.OAM_NE_PING, "");
			QueryResponseMsg resmsg = cncpTaskExecutor.invokeQueryMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
			int oamStatus=resmsg.getResult()==0?1:0;
			card.setOamStatus(oamStatus);
			if(oamStatus!=1){
				continue;
			} 
			msg = sendCncpMsgFactory.createSendQueryCncpMsg(
					CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_GET_MSG, 
					CncpProtoType.SUB_OAM, card.getCardNum(), CncpProtoType.OAM_GET_HD_SERIAL_NO, "");
			QueryResponseMsg respMsg = cncpTaskExecutor.invokeQueryMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
			int result=respMsg.getResult();
			if(result==CncpProtoType.SUCCESS){
				card.setSerial(respMsg.getData());
			}
		}
		return cards;
	}

	@Override
	public Card getById(Integer id) {
		return mapper.selectById(id);
	}

	@Override
	public PageBean getPageBean(Integer requestPage, Integer pageSize) {
		return new PageBean(requestPage, pageSize, findAll(), findAll().size());
	}

	@Override
	public List<Integer> deleteByIds(Integer[] ids) {
		//将所有返回消息的子类型
		List<Integer> list = new ArrayList<Integer>();
		for(Integer id:ids){
			int result = deleteById(id);
			list.add(result);
		}
		return list;
	}

	@Override
	public void rebootAll() {
		List<Card> cards=mapper.selectAll();
		for(int i=cards.size()-1;i>=0;i--){
			Card card=cards.get(i);
			SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
					CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_GET_MSG, 
					CncpProtoType.SUB_OAM, card.getCardNum(), CncpProtoType.OAM_SET_SYS_REBOOT, "");
			cncpTaskExecutor.invokeSetUpMsg(msg, false, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
		}
	}
}
