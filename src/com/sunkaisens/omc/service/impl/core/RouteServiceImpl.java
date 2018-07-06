package com.sunkaisens.omc.service.impl.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunkaisens.omc.context.core.OmcServerContext;
import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.mapper.core.CardMapper;
import com.sunkaisens.omc.po.core.Card;
import com.sunkaisens.omc.service.core.RouteService;
import com.sunkaisens.omc.thread.cncpMsg.CncpProtoType;
import com.sunkaisens.omc.thread.cncpMsg.CncpTaskExecutor;
import com.sunkaisens.omc.thread.cncpMsg.SendCncpMsgFactory;
import com.sunkaisens.omc.thread.messageEncapsulation.QueryMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.QueryResponseMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.SetUpMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.SetUpResponseMsg;
import com.sunkaisens.omc.util.CncpStatusTransUtil;
import com.sunkaisens.omc.vo.core.PageBean;
import com.sunkaisens.omc.vo.core.Route;

/**
 * RouteService接口实现类
 */
@Service
public class RouteServiceImpl implements RouteService {
	@Resource
	private CncpTaskExecutor cncpTaskExecutor;
	@Resource
	private SendCncpMsgFactory sendCncpMsgFactory;
	@Resource
	private CardMapper mapper;

	@Override
	public void add(Route route) throws CustomException {
		String data = "DESTINATION:" + route.getDestination() + "\nGATEWAY:"
				+ route.getGateway() + "\nGENMASK:" + route.getGenmask()+"\n";
//				+ "\nFLAGS:" + route.getFlags() + "\nMETRIC:"
//				+ route.getMetric() + "\nREF:" + route.getRef() + "\nUSE:"
//				+ route.getUse() + "\nIFACE:" + route.getUse() + "\n";
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
				CncpProtoType.SUB_OAM, route.getCardNum(), CncpProtoType.OAM_SET_STATIC_ROUTE_CREATE, data);
		SetUpResponseMsg respMsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
		if(respMsg.getResult() != 0){
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(respMsg.getResult()));
		}
	}

	@Override
	public void delete(Integer[] cardNums, String[] destinations,String[] gateways,String[] genmasks) throws CustomException {
		for (int i=0;i<destinations.length;i++){
			StringBuilder sb = new StringBuilder();
			sb.append("DESTINATION:").append(destinations[i]).append("\n");
			sb.append("GATEWAY:").append(gateways[i]).append("\n");
			sb.append("GENMASK:").append(genmasks[i]).append("\n");
			SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
					CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
					CncpProtoType.SUB_OAM, cardNums[i], CncpProtoType.OAM_SET_STATIC_ROUTE_DELETE, sb.toString());
			SetUpResponseMsg respMsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
			if(respMsg.getResult() != 0){
				throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(respMsg.getResult()));
			}
		}
	}

	@Override
	public void update(Route route) {
		//不用做
	}

	@Override
	public PageBean list(Integer cardNum, Integer page, Integer rows) throws CustomException {
		List<Route> list = new ArrayList<>();
		if(cardNum==null||cardNum==0){
			for(Card card:mapper.selectAll()){
				List<Route> routesByCardNum = getRoutesByCardNum(card.getCardNum());
				List<Route> list1 = new ArrayList<Route>();
				for (Route route : routesByCardNum) {
					if(route.getDestination().equals("169.254.0.0")){
						continue;
					}
					list1.add(route);
				}
				list.addAll(list1);
			}
		}else{
			List<Route> routesByCardNum = getRoutesByCardNum(cardNum);
			List<Route> list1 = new ArrayList<Route>();
			for (Route route : routesByCardNum) {
				if(route.getDestination().equals("169.254.0.0")){
					continue;
				}
				list1.add(route);
			}
			list.addAll(list1);
		}
		
		List<Route> newList=new LinkedList<>();
		int start=(page-1)*rows;
		int limit=Math.min((page-1)*rows+rows, list.size()-start);
		for(int i=start;i<start+limit;i++){
			newList.add(list.get(i));
		}
		PageBean pageBean=new PageBean(page, rows, newList, list.size());
		return pageBean;
	}
	
	private List<Route> getRoutesByCardNum(Integer cardNum) throws CustomException{
		List<Route> list = new ArrayList<>();
		String data = "PageHeader:1\n";
		QueryMsg msg = sendCncpMsgFactory.createSendQueryCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_GET_MSG, 
				CncpProtoType.SUB_OAM, cardNum, CncpProtoType.OAM_GET_TABLE_GLOBAL_ROUTE, data);
		QueryResponseMsg respMsg = cncpTaskExecutor.invokeQueryMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
		if (respMsg.getResult() == 0) {
			String returnData = respMsg.getData();
			String newHeader = parseReturnData(cardNum,returnData, list);
			while (newHeader != null) {
				msg = sendCncpMsgFactory.createSendQueryCncpMsg(
						CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_GET_MSG, 
						CncpProtoType.SUB_OAM, cardNum, CncpProtoType.OAM_GET_TABLE_GLOBAL_ROUTE, newHeader);
				returnData = cncpTaskExecutor.invokeQueryMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort()).getData();
				
				newHeader = parseReturnData(cardNum,returnData, list);
			}
		}else if(respMsg.getResult()!=CncpProtoType.ERR_HOST_LOST){
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(respMsg.getResult()));
		}
		return list;
	}

	private String parseReturnData(Integer cardNum,String returnData, List<Route> list) {
		String[] lines=returnData.trim().split("\n");
		String[] header=lines[0].substring(lines[0].indexOf(":")+1).split(",");
		for (int i = 1; i < lines.length; i++) {
			String[] record = lines[i].trim().split(",",-1);
			Route e = new Route(record[0], record[1], record[2],record[3],
					record[4],record[5], record[6], record[7]);
			e.setCardNum(cardNum);
			list.add(e);
		}
		if (new Long(header[0]) + new Long(header[2]) > new Long(header[3])
				|| lines.length == 1) {
			// 再无记录
			return null;
		} else {
			Long newHeader = new Long(header[0]) + new Long(header[1]);
			return "PageHeader:" + newHeader + "\n";
		}
	}
}
