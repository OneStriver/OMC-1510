package com.sunkaisens.omc.service.impl.core;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.sunkaisens.omc.context.core.OmcServerContext;
import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.mapper.core.CardMapper;
import com.sunkaisens.omc.service.core.HostAddrService;
import com.sunkaisens.omc.thread.cncpMsg.CncpProtoType;
import com.sunkaisens.omc.thread.cncpMsg.CncpTaskExecutor;
import com.sunkaisens.omc.thread.cncpMsg.SendCncpMsgFactory;
import com.sunkaisens.omc.thread.messageEncapsulation.QueryMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.QueryResponseMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.SetUpMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.SetUpResponseMsg;
import com.sunkaisens.omc.util.CncpStatusTransUtil;
import com.sunkaisens.omc.vo.core.HostAddr;

/**
 *HostAddrService接口实现类
 */
@Service
public class HostAddrServiceImpl  implements HostAddrService {

	@Resource
	private CncpTaskExecutor cncpTaskExecutor;
	@Resource
	private SendCncpMsgFactory sendCncpMsgFactory;
	@Resource 
	private CardMapper mapper;
	
	private final File hosts=new File("/etc/hosts");
	@Override
	public List<HostAddr> list() throws CustomException {
		List<HostAddr> list=new LinkedList<>();
		try {
			List<String> lines=FileUtils.readLines(hosts);
			for(String line:lines){
				if(StringUtils.isNotBlank(line)){
					if(line.toLowerCase().contains("localhost"))
						continue;
					String[] ip_host=line.split("\\s",2);
					list.add(new HostAddr(ip_host[0],ip_host[1]));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	protected List<HostAddr> getHostAddrsByCardNum(Integer cardNum) throws CustomException{
		List<HostAddr> list=new LinkedList<>();
		String data="PageHeader:1\n";
		QueryMsg msg = sendCncpMsgFactory.createSendQueryCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_GET_MSG, 
				CncpProtoType.SUB_OAM, cardNum, CncpProtoType.OAM_GET_TABLE_HOSTS, data);
		QueryResponseMsg respMsg = cncpTaskExecutor.invokeQueryMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
		if(respMsg.getResult()==0){
			String returnData=respMsg.getData();
			String newHeader=parseReturnData(cardNum,returnData,list);
			while(newHeader!=null){
				msg = sendCncpMsgFactory.createSendQueryCncpMsg(
						CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_GET_MSG, 
						CncpProtoType.SUB_OAM, cardNum, CncpProtoType.OAM_GET_TABLE_HOSTS, data);
				returnData = cncpTaskExecutor.invokeQueryMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort()).getData();
				newHeader=parseReturnData(cardNum,returnData,list);
			}
			return list;
		}else{
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(respMsg.getResult()));
		}
		
	}
	
	private String parseReturnData(Integer cardNum,String returnData, List<HostAddr> list) {
		String[] lines=returnData.trim().split("\n");
		String[] header=lines[0].substring(lines[0].indexOf(":")+1).split(",");
		for (int i = 1; i < lines.length; i++) {
			String[] record = lines[i].trim().split(",",-1);
			String hostNames=StringUtils.join(Arrays.copyOfRange(record, 1, record.length), ",");
			HostAddr e = new HostAddr(record[0],hostNames);
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
	
	private void notifyOam(String restore) throws CustomException {
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
				CncpProtoType.SUB_OAM, 0, CncpProtoType.OAM_SET_HOST_SYNC, "");
		SetUpResponseMsg respMsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
		if(respMsg.getResult()!=0){
			try {
				FileUtils.write(hosts, restore);
			} catch (IOException e) {
				e.printStackTrace();
			}
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(respMsg.getResult()));
		}
	}
	
	@Override
	public void update(HostAddr host) throws CustomException {
		try {
			if(!hosts.exists()){
				hosts.createNewFile();
			}
			String restore=FileUtils.readFileToString(hosts);
			String ip=host.getIp().trim();
			String name=host.getHost().trim();
			List<String> lines=FileUtils.readLines(hosts);
			boolean isFind=false;
			for(int i=0;i<lines.size();i++){
				if(StringUtils.isBlank(lines.get(i))) continue;
				String[] ip_host=lines.get(i).trim().split("\\s", 2);
				if(name.equals(ip_host[1].trim())){
					lines.set(i, ip+" "+name);
					isFind=true;
					break;
				}
			}
			FileUtils.writeLines(hosts, lines);
			if(isFind){
				notifyOam(restore);
			}else{
				throw new CustomException("此IP对应的域名不存在");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void save(HostAddr host) throws CustomException {
		try {
			if(!hosts.exists()){
				hosts.createNewFile();
			}
			String restore=FileUtils.readFileToString(hosts);
			String ip=host.getIp().trim();
			String name=host.getHost().trim();
			List<String> lines=FileUtils.readLines(hosts);
			for(String line:lines){
				if(StringUtils.isBlank(line)||line.trim().startsWith("#")){
					continue;
				}
				String[] ip_host=line.trim().split("\\s", 2);
				if(name.equals(ip_host[1])){
					if(ip.equals(ip_host[0])&&name.equals(ip_host[1])){
						throw new CustomException("此IP对应的域名已存在");
					}else{
						throw new CustomException("此域名对应的IP已存在");
					}
				}
			}
			FileUtils.write(hosts,"\n"+ip+" "+name, true);
			notifyOam(restore);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(String[] ips,String[] hostNames)throws CustomException {
		try {
			if(!hosts.exists()){
				hosts.createNewFile();
			}
			String restore=FileUtils.readFileToString(hosts);
			List<String> lines=FileUtils.readLines(hosts);
			Iterator<String> itreator=lines.iterator();
			while(itreator.hasNext()){
				String line=itreator.next().trim();
				if(StringUtils.isNotBlank(line)){
					String[] ip_host=line.split("\\s", 2);
					for(int i=0;i<ips.length;i++){
						String ip=ips[i].trim();
						String name=hostNames[i].trim();
						if(ip_host[0].equals(ip)&&ip_host[1].equals(name)){
							itreator.remove();
						}
					}
				}
			}
			FileUtils.writeLines(hosts, lines);
			notifyOam(restore);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
