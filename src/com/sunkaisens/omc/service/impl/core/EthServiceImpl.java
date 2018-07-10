package com.sunkaisens.omc.service.impl.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.sunkaisens.omc.context.core.OmcServerContext;
import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.mapper.core.CardMapper;
import com.sunkaisens.omc.mapper.core.IpDnsMapper;
import com.sunkaisens.omc.po.core.Card;
import com.sunkaisens.omc.po.core.IpDns;
import com.sunkaisens.omc.service.core.EthService;
import com.sunkaisens.omc.thread.cncpMsg.CncpProtoType;
import com.sunkaisens.omc.thread.cncpMsg.CncpTaskExecutor;
import com.sunkaisens.omc.thread.cncpMsg.SendCncpMsgFactory;
import com.sunkaisens.omc.thread.messageEncapsulation.QueryMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.QueryResponseMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.SetUpMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.SetUpResponseMsg;
import com.sunkaisens.omc.util.CncpStatusTransUtil;
import com.sunkaisens.omc.vo.core.Eth;
import com.sunkaisens.omc.vo.core.PageBean;

/**
 *EthService接口实现类
 */
@Service
public class EthServiceImpl implements EthService {

	@Resource
	private CncpTaskExecutor cncpTaskExecutor;
	@Resource
	private SendCncpMsgFactory sendCncpMsgFactory;
	@Resource 
	private CardMapper cardMapper;
	@Resource 
	private IpDnsMapper ipDnsMapper;
	
	@Override
	public PageBean listActivate(Integer cardNum, Integer page, Integer rows) throws CustomException {
		List<Eth> list=new LinkedList<Eth>();
		if(cardNum==null||cardNum==0){
			for(Card card:cardMapper.selectAll()){
				list.addAll(getEthActivateByCardNum(card.getCardNum()));
			}
		}else{
			list.addAll(getEthActivateByCardNum(cardNum));
		}
		Collections.sort(list, new Comparator<Eth>() {
			public int compare(Eth eth1, Eth eth2) {
				if(eth1.getCardNum()==eth2.getCardNum()){
					return eth1.getName().compareToIgnoreCase(eth2.getName());
				}else{
					return eth1.getCardNum()-eth2.getCardNum();
				}
			}
		});
		List<Eth> newList=new LinkedList<Eth>();
		int start=(page-1)*rows;
		int limit=Math.min((page-1)*rows+rows, list.size()-start);
		for(int i=start;i<start+limit;i++){
			newList.add(list.get(i));
		}
		PageBean pageBean=new PageBean(page, rows, newList, list.size());
		return pageBean;
	}
	
	/**
	 * 获取活动网卡的信息
	 */
	private List<Eth> getEthActivateByCardNum(Integer cardNum) throws CustomException{
		List<Eth> list=new LinkedList<Eth>();
		String data="PageHeader:1\n";
		QueryMsg msg = sendCncpMsgFactory.createSendQueryCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_GET_MSG, 
				CncpProtoType.SUB_OAM, cardNum, CncpProtoType.OAM_GET_IF, data);
		QueryResponseMsg respMsg = cncpTaskExecutor.invokeQueryMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
		if(respMsg.getResult()==0){
			System.err.println("========获取活动网卡的信息========");
			String returnData=respMsg.getData();
			//将返回消息中的数据封装到List集合中
			String newHeader=parseReturnData(cardNum,returnData,list);
			while(newHeader!=null){
				msg = sendCncpMsgFactory.createSendQueryCncpMsg(
						CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_GET_MSG,
						CncpProtoType.SUB_OAM, cardNum, CncpProtoType.OAM_GET_IF, newHeader);
				returnData = cncpTaskExecutor.invokeQueryMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort()).getData();
				newHeader=parseReturnData(cardNum,returnData,list);
			}
		}else if(respMsg.getResult()!=CncpProtoType.ERR_HOST_LOST){
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(respMsg.getResult()));
		}
		return list;
	}

	//将returnData数据解析封装到List<Eth> list中
	private String parseReturnData(Integer cardNum,String returnData,List<Eth> list) {
		String[] lines=returnData.trim().split("\n");
		String[] header=lines[0].substring(lines[0].indexOf(":")+1).split(",");
		for(int i=1;i<lines.length;i++){
			String[] record=lines[i].trim().split(",",-1);
			Eth eth=null;
			if(record.length==12){
				//活动网卡
				eth=new Eth(record[0], record[1], record[4], record[5], record[3], record[6], record[2], record[7], record[11]);
				eth.setSpeed(record[8]);
				eth.setDuplex(record[9]);
				eth.setAutoNeg(record[10]);
				
				eth.setCardNum(cardNum);
			}else if(record.length==10){
				eth=new Eth(record[0], record[1], record[4], record[5], record[3], record[2], record[8],record[6],record[7],record[9]);
				//ospf
				//e.setOspf(record[9]);
				//ospfd
				eth.setOspfd(header[header.length-1]);
				
				eth.setCardNum(cardNum);
			}
			
			boolean isShow=true;
			String[] hideEths=OmcServerContext.getInstance().getHideEth().split(",");
			for(String name:hideEths){
				if(name.contains("-")){
					String[] n_c=name.split("-", 2);
					if(eth.getName().toLowerCase().equals(n_c[0].trim().toLowerCase())&&
							cardNum==Integer.parseInt(n_c[1].trim())){
						isShow=false;
						break;
					}
				}else{
					String a = eth.getName().toLowerCase();
					String b = name.trim().toLowerCase();
					System.out.println("a========"+a+",b========"+b);
					if(eth.getName().toLowerCase().equals(name.trim().toLowerCase())){
						isShow=false;
						break;
					}
				}
			}
			
			if(isShow){
				//根据板卡编号查询出板卡名称
				Card selectCard = cardMapper.selectByNum(eth.getCardNum());
				eth.setCardName(selectCard.getName());
				list.add(eth);
			}
		}
		if(new Long(header[0])+new Long(header[2])>new Long(header[3])||lines.length==1){
			//再无记录
			return null;
		}else{
			Long newHeader=new Long(header[0])+new Long(header[1]);
			return "PageHeader:"+newHeader+"\n";
		}
	}

	@Override
	public void activateUpdate(Eth eth) throws CustomException {
		String data="DEVICE:"+eth.getName()+"\nIPADDR:"+eth.getIp()+"\nNETMASK:"+eth.getMask()+"\nMTU:"+eth.getMtu()+"\n";
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
				CncpProtoType.SUB_OAM, eth.getCardNum(), CncpProtoType.OAM_SET_IF_UPDATE, data);
		SetUpResponseMsg respMsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
		if(respMsg.getResult()!=0){
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(respMsg.getResult()));
		}
	}
	
	@Override
	public void addVirtualEth(Eth eth) throws CustomException{
		String data="DEVICE:"+eth.getName()+"\nIPADDR:"+eth.getIp()+"\nNETMASK:"+eth.getMask()+"\nMTU:"+eth.getMtu()+"\n";
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
				CncpProtoType.SUB_OAM, eth.getCardNum(), CncpProtoType.OAM_SET_IF_UPDATE, data);
		SetUpResponseMsg respMsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
		if(respMsg.getResult()!=0){
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(respMsg.getResult()));
		}
	}
	
	@Override
	public void addConfEth(Eth eth,String[] dns) throws CustomException{
		//开始 判断是否有对应的网卡存在，存在才能添加相应的虚拟网卡
		List<Eth> list1=new LinkedList<>();
		List<String> list2=new LinkedList<>();
		String nameStr = eth.getName().split(":")[0];
		for(Card card:cardMapper.selectAll()){
			list1.addAll(getEthStaticByCardNum(card.getCardNum()));
		}
		for (Eth ethList : list1) {
			list2.add(ethList.getName());
		}
		if(!list2.contains(nameStr)){
			throw new CustomException("不能添加不存在的虚拟网卡");
		}
		//结束
		if(dns!=null&&dns.length>0){
			StringBuilder sb=new StringBuilder();
			for(String d:dns){
				List<IpDns> ipDnses=ipDnsMapper.exist(d,eth.getName(),eth.getCardNum());
				for(IpDns ipDns:ipDnses){
					String str=ipDns.getDnsStr();
					if(StringUtils.isNotEmpty(str)){
						if(Arrays.asList(str.split(",")).contains(d)){
							sb.append(d+"已关联到板卡编号为"+ipDns.getCardNum()+"的"+ipDns.getEth()+"<br/>");
						}
					}
				}
			}
			if(StringUtils.isNotBlank(sb.toString())){
				throw new CustomException(sb.toString());
			}
		}
		
		IpDns po=null;
		if(dns==null||dns.length==0){
			po=new IpDns(eth.getCardNum(),eth.getName(),eth.getIp(), "");
		}else{
			po=new IpDns(eth.getCardNum(),eth.getName(),eth.getIp(),StringUtils.join(dns, ","));
		}
		IpDns ipDns=ipDnsMapper.selectByEthAndCardNum(eth.getName(), eth.getCardNum());
		if(ipDns==null){
			ipDnsMapper.insert(po);
		}else{
			ipDnsMapper.update(po);
		}
		
		String dnsServerIp=OmcServerContext.getInstance().getDnsServerIp();
		/*+"\nOSPF:"+eth.getOspf()*/
		String data="DEVICE:"+eth.getName()+"\nIPADDR:"+eth.getIp()+"\nNETMASK:"+
				eth.getMask()+"\nMTU:"+eth.getMtu()+"\nBOOTPROTO:static\nTYPE:Ethernet\n"+"DN:"+StringUtils.join(dns, ",")+"\nDNS:"+dnsServerIp+"\nOSPF:"+eth.getOspf()+"\n";
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
				CncpProtoType.SUB_OAM, eth.getCardNum(), CncpProtoType.OAM_SET_IF_CONF_CREATE, data);
		SetUpResponseMsg respMsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
		if(respMsg.getResult()!=0){
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(respMsg.getResult()));
		}
	}


	@Override
	public PageBean listStatic(Integer cardNum, Integer page, Integer rows) throws CustomException {
		List<Eth> list=new LinkedList<>();
		if(cardNum==null||cardNum==0){
			for(Card card:cardMapper.selectAll()){
				list.addAll(getEthStaticByCardNum(card.getCardNum()));
			}
		}else{
			list.addAll(getEthStaticByCardNum(cardNum));
		}
		Collections.sort(list, new Comparator<Eth>() {
			public int compare(Eth o1, Eth o2) {
				if(o1.getCardNum()==o2.getCardNum()){
					return o1.getName().compareToIgnoreCase(o2.getName());
				}else{
					return o1.getCardNum()-o2.getCardNum();
				}
			}
		});
		List<Eth> newList=new LinkedList<>();
		int start=(page-1)*rows;
		int limit=Math.min((page-1)*rows+rows, list.size()-start);
		for(int i=start;i<start+limit;i++){
			newList.add(list.get(i));
		}
		PageBean pageBean=new PageBean(page, rows, newList, list.size());
		return pageBean;
	}
		
	/**
	 * 获取静态网卡信息
	 */
	private List<Eth> getEthStaticByCardNum(Integer cardNum) throws CustomException{
		List<Eth> list=new LinkedList<>();
		String data="PageHeader:1\n";
		QueryMsg msg = sendCncpMsgFactory.createSendQueryCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_GET_MSG, 
				CncpProtoType.SUB_OAM, cardNum, CncpProtoType.OAM_GET_IF_CONF, data);
		QueryResponseMsg respMsg = cncpTaskExecutor.invokeQueryMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
		if(respMsg.getResult()==0){
			System.err.println("========获取静态网卡的信息========");
			String returnData=respMsg.getData();
			String newHeader=parseReturnData(cardNum,returnData,list);
			while(newHeader!=null){
				msg = sendCncpMsgFactory.createSendQueryCncpMsg(
						CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_GET_MSG, 
						CncpProtoType.SUB_OAM, cardNum, CncpProtoType.OAM_GET_IF_CONF, data);
				returnData = cncpTaskExecutor.invokeQueryMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort()).getData();
				newHeader=parseReturnData(cardNum,returnData,list);
			}
		}else if(respMsg.getResult() != CncpProtoType.ERR_HOST_LOST){
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(respMsg.getResult()));
		}
		return list;
	}

	@Override
	public void deactivate(String[] names,Integer cardNum) throws CustomException {
		StringBuilder sb=new StringBuilder();
		for(String name:names){
			sb.append("DEVICE:").append(name).append("\n");
		}
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
				CncpProtoType.SUB_OAM, cardNum, CncpProtoType.OAM_SET_IF_DOWN, sb.toString());
		SetUpResponseMsg respMsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
		if(respMsg.getResult()!=0){
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(respMsg.getResult()));
		}
	}

	@Override
	public void delete(Integer cardNum, String eth) throws CustomException {
		String data="DEVICE:"+eth+"\n";
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
				CncpProtoType.SUB_OAM, cardNum, CncpProtoType.OAM_SET_IF_CONF_DELETE, data);
		SetUpResponseMsg respMsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
		if(respMsg.getResult()!=0){
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(respMsg.getResult()));
		}else{
				ipDnsMapper.deleteByEthAndCardNum(eth, cardNum);
		}
	}

	@Override
	public void staticUpdate(Eth eth,String[] dns) throws CustomException {
		if(dns!=null&&dns.length>0){
			StringBuilder sb=new StringBuilder();
			//
			for(String d:dns){
				
				List<IpDns> ipDnses=ipDnsMapper.exist(d,eth.getName(),eth.getCardNum());
				for(IpDns ipDns:ipDnses){
					String str=ipDns.getDnsStr();
					if(StringUtils.isNotEmpty(str)){
						if(Arrays.asList(str.split(",")).contains(d)){
							sb.append(d+"已关联到板卡编号为"+ipDns.getCardNum()+"的"+ipDns.getEth()+"<br/>");
						}
					 }
				  }
			   
			}
			//
			if(StringUtils.isNotBlank(sb.toString())){
				throw new CustomException(sb.toString());
			}
			
		}
		
		IpDns ipDns=ipDnsMapper.selectByEthAndCardNum(eth.getName(), eth.getCardNum());
		IpDns po=null;
		if(dns==null||dns.length==0){
			po=new IpDns(eth.getCardNum(),eth.getName(),eth.getIp(), "");
		}else{
			po=new IpDns(eth.getCardNum(),eth.getName(),eth.getIp(),StringUtils.join(dns, ","));
		}

		if (ipDns != null) {

			ipDnsMapper.update(po);
		} else {
			ipDnsMapper.insert(po);
		}

		String dnsServerIp = OmcServerContext.getInstance().getDnsServerIp();
		String data = "DEVICE:" + eth.getName() + "\nIPADDR:" + eth.getIp()
				+ "\nNETMASK:" + eth.getMask() + "\nMTU:" + eth.getMtu()
				+"\nONBOOT:"+eth.getOnBoot()
				+"\nBOOTPROTO:static\nTYPE:Ethernet\n"
				+ "DN:" + po.getDnsStr() + "\nDNS:" + dnsServerIp + "\nHWADDR:"+eth.getMac()+"\nOSPF:"+eth.getOspf();
		/*+"\nOSPF:"+eth.getOspf()*/
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
				CncpProtoType.SUB_OAM, eth.getCardNum(), CncpProtoType.OAM_SET_IF_CONF_UPDATE, data);
		SetUpResponseMsg respMsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
		if(respMsg.getResult()!=0){
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(respMsg.getResult()));
		}
	}
}
