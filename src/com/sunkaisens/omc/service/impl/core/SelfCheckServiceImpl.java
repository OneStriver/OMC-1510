package com.sunkaisens.omc.service.impl.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import com.sunkaisens.omc.context.core.OmcServerContext;
import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.mapper.core.CardMapper;
import com.sunkaisens.omc.po.core.Card;
import com.sunkaisens.omc.service.core.SelfCheckService;
import com.sunkaisens.omc.thread.cncpMsg.CncpProtoType;
import com.sunkaisens.omc.thread.cncpMsg.CncpTaskExecutor;
import com.sunkaisens.omc.thread.cncpMsg.SendCncpMsgFactory;
import com.sunkaisens.omc.thread.messageEncapsulation.QueryMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.QueryResponseMsg;
import com.sunkaisens.omc.util.CncpStatusTransUtil;

import net.sf.json.JSONObject;

/**
 *SelfCheckServicek接口实现类
 */
@Service
public class SelfCheckServiceImpl implements SelfCheckService {
	@Resource
	private CncpTaskExecutor cncpTaskExecutor;
	@Resource
	private SendCncpMsgFactory sendCncpMsgFactory;
	@Resource
	private CardMapper cardMapper;

	@Override
	public List<Object> rtStatus() throws Exception {
		List<Object> list=new ArrayList<Object>();
		File dir=new File(OmcServerContext.getInstance().getFtpDir()+File.separator+"oam.stat");
		List<Card> cards = cardMapper.selectAll();
		for (Card card : cards) {
			QueryMsg msg = sendCncpMsgFactory.createSendQueryCncpMsg(
					CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_GET_MSG, 
					CncpProtoType.SUB_OAM, card.getCardNum(), CncpProtoType.OAM_GET_SELF_CHECKING, "");
			QueryResponseMsg resp = cncpTaskExecutor.invokeQueryMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
			if(resp.getResult()==CncpProtoType.SUCCESS){
				try {
					JSONObject json=JSONObject.fromObject(FileUtils.readFileToString(new File(dir,card.getCardNum()+"")));
					json.element("cardName", card.getName());
					list.add(json);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(resp.getResult()!=CncpProtoType.ERR_HOST_LOST){
				throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(resp.getResult()));
			}
		}
		return list;
	}
	
	@Override
	public List<Object> startupStatus()throws Exception {
		List<Object> arr=new ArrayList<Object>();
		File dir=new File(OmcServerContext.getInstance().getFtpDir()+File.separator+"oam.stat");
		List<Card> cards = cardMapper.selectAll();
		for (Card card : cards) {
			try {
				File f=new File(dir,card.getCardNum()+".init");
				if(!f.exists()){
					QueryMsg msg = sendCncpMsgFactory.createSendQueryCncpMsg(
							CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_GET_MSG, 
							CncpProtoType.SUB_OAM, card.getCardNum(), CncpProtoType.OAM_GET_SELF_CHECKING, "");
					QueryResponseMsg resp = cncpTaskExecutor.invokeQueryMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
					
					if(resp.getResult()==CncpProtoType.SUCCESS){
						try {
							JSONObject json=JSONObject.fromObject(FileUtils.readFileToString(f));
							json.element("cardName", card.getName());
							arr.add(json);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}else if(resp.getResult()!=CncpProtoType.ERR_HOST_LOST){
						throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(resp.getResult()));
					}
				}else{
					JSONObject json=JSONObject.fromObject(FileUtils.readFileToString(f));
					json.element("cardName", card.getName());
					arr.add(json);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return arr;
	}
}
