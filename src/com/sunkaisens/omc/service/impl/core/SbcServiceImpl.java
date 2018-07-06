package com.sunkaisens.omc.service.impl.core;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.sunkaisens.omc.context.core.OmcServerContext;
import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.mapper.core.ItemMapper;
import com.sunkaisens.omc.po.core.Item;
import com.sunkaisens.omc.service.core.SbcService;
import com.sunkaisens.omc.thread.cncpMsg.CncpProtoType;
import com.sunkaisens.omc.thread.cncpMsg.CncpTaskExecutor;
import com.sunkaisens.omc.thread.cncpMsg.SendCncpMsgFactory;
import com.sunkaisens.omc.thread.messageEncapsulation.QueryMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.QueryResponseMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.SetUpMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.SetUpResponseMsg;
import com.sunkaisens.omc.util.CncpStatusTransUtil;
import com.sunkaisens.omc.vo.core.PageBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * SbcService接口实现类
 */
@Service
public class SbcServiceImpl implements SbcService {

	@Resource
	private CncpTaskExecutor cncpTaskExecutor;
	@Resource
	private SendCncpMsgFactory sendCncpMsgFactory;
	@Resource
	private ItemMapper mapper;
	private final static String KV_SPE=":";
	@Override
	public JSONObject loadScalar(String[] names,Integer packId) throws CustomException {
		String data=StringUtils.join(names, KV_SPE+"\n")+KV_SPE+"\n";
		int neType=1;
		QueryMsg msg = sendCncpMsgFactory.createSendQueryCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_GET_MSG, 
				neType, 0, CncpProtoType.OAM_SBC_GET, data);
		QueryResponseMsg resMsg = cncpTaskExecutor.invokeQueryMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
		if(resMsg.getResult()!=CncpProtoType.SUCCESS){
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(resMsg.getResult()));
		}
		JSONObject json=new JSONObject();
		data=resMsg.getData();
		if(data==null) return json;
		String[] values=StringUtils.split(data.trim(),"\n");
		for(String value:values){
			String[] kv=value.split(KV_SPE,2);
			json.element(kv[0], kv[1]);
		}
		return json;
	}
	
	public void updateScalar(Map<String,String> params,Integer packId) throws CustomException {
		StringBuilder sb = new StringBuilder();
		for(Map.Entry<String, String> entry:params.entrySet()){
			sb.append(entry.getKey()).append(KV_SPE).append(entry.getValue()).append("\n");
		}
		int neType=1;
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
				neType, 0, CncpProtoType.OAM_SBC_SET, sb.toString());
		SetUpResponseMsg resMsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
		if (resMsg.getResult() != CncpProtoType.SUCCESS){
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(resMsg.getResult()));
		}
	}

	@Override
	public PageBean loadGrid(Integer id,Integer packId,Integer page, Integer pageSize) throws CustomException {
		int neType=1;
		Item grid=mapper.selectById(id);
		List<Item> children=grid.getChildren();
		StringBuilder sb = new StringBuilder();
//		for(Item child:children){
//			sb.append(child.getName()).append(":\n");
//		}
		String baseStr=sb.toString();
		int recordStart=(page-1)*pageSize+1;
		int recordEnd=recordStart+2;
		int count=-1,start=1;
		int limit=children.size()+1;
		int end=start+limit;
		int lineCount=3;
		JSONArray arr=new JSONArray();
		while((count==-1||arr.size()<pageSize)&&lineCount>=recordEnd-recordStart){
			sb=new StringBuilder(baseStr);
			sb.append("start"+KV_SPE).append(recordStart).append("\n").
				append("end"+KV_SPE).append(recordEnd).append("\n").toString();
			QueryMsg msg = sendCncpMsgFactory.createSendQueryCncpMsg(
					CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_GET_MSG, 
					neType, 0, CncpProtoType.OAM_SBC_GET, sb.toString());
			QueryResponseMsg resMsg = cncpTaskExecutor.invokeQueryMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
			if(resMsg.getResult()!=CncpProtoType.SUCCESS){
				throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(resMsg.getResult()));
			}
			String data=resMsg.getData();
			if(data==null){
				PageBean pageBean=new PageBean(page, pageSize, arr,Math.max(count,arr.size()));
				return pageBean;
			}
//			String data="totalCount:2\nRecId:1\nLocation:127.0.0.1\nGroupStartMdn:123\nRecId:2\nLocation:127.0.0.1\nGroupStartMdn:123\n";
			List<String> lines=Arrays.asList(data.trim().split("\n"));
			count=Integer.parseInt(lines.get(0).split(KV_SPE, 2)[1]);
			lineCount=lines.size()/limit==0?lines.size()/limit+1:lines.size()/limit;
			for(int i=0;i<lineCount;i++){
				JSONObject json=new JSONObject();
				String[] columns=(String[])ArrayUtils.subarray(lines.toArray(new String[0]), start, end);
				for(String column:columns){
					String[] kv=column.split(KV_SPE,2);
					json.element(kv[0], kv[1]);
				}
				arr.add(json);
				start=end;end+=limit;
				if(arr.size()==pageSize)
					break;
			}
			start=1;end=start+limit;
			recordStart+=3;recordEnd+=3;
		}
		PageBean pageBean=new PageBean(page, pageSize, arr,count);
		pageBean.setRows(arr);
		return pageBean;
	}
	
	@Override
	public void add(Map<String,String> params,Integer packId)throws CustomException{
		StringBuilder sb = new StringBuilder();
		for(Map.Entry<String, String> entry:params.entrySet()){
			sb.append(entry.getKey()).append(KV_SPE).append(entry.getValue()).append("\n");
		}
		int neType=1;
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
				neType, 0, CncpProtoType.OAM_SBC_ADD, sb.toString());
		SetUpResponseMsg resMsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
		if (resMsg.getResult() != CncpProtoType.SUCCESS){
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(resMsg.getResult()));
		}
	}
	
	@Override
	public void update(Map<String,String> params,Integer packId)throws CustomException{
		StringBuilder sb = new StringBuilder();
		for(Map.Entry<String, String> entry:params.entrySet()){
			sb.append(entry.getKey()).append(KV_SPE).append(entry.getValue()).append("\n");
		}
		int neType=1;
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
				neType, 0, CncpProtoType.OAM_SBC_SET, sb.toString());
		SetUpResponseMsg resMsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
		if (resMsg.getResult() != CncpProtoType.SUCCESS){
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(resMsg.getResult()));
		}
	}
	
	@Override
	public void delete(Integer[] ids,Integer packId) throws CustomException {
		int neType=1;
		StringBuilder sb=new StringBuilder();
//		sb.append("ConfItem"+KV_SPE).append("\n");
		for(int id:ids){
			sb.append("RecId"+KV_SPE).append(id).append("\n");
		}
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
				neType, 0, CncpProtoType.OAM_SBC_DELETE, sb.toString());
		SetUpResponseMsg resMsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
		if (resMsg.getResult() != CncpProtoType.SUCCESS){
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(resMsg.getResult()));
		}
	}
	
	//--------------网络配置 开始-------------------
	@Override
	public List<Item> getNetItem() {
		List<Item> items=mapper.select1Level(1);
		return items;
	}

	@Override
	public JSONObject loadNetScalar(String[] names) throws CustomException {
		return loadScalar(names,CncpProtoType.OAM_SBC_NET);
	}
	
	@Override
	public void updateNetScalar(Map<String,String> params) throws CustomException {
		updateScalar(params,CncpProtoType.OAM_SBC_NET);
	}
	//--------------网络配置 结束-------------------
	//--------------TLS配置 开始-------------------
	@Override
	public List<Item> getTlsItem() {
		List<Item> items=mapper.select1Level(2);
		return items;
	}

	@Override
	public JSONObject loadTlsScalar(String[] names) throws CustomException {
		return loadScalar(names,CncpProtoType.OAM_SBC_TLS);
	}
	
	@Override
	public void updateTlsScalar(Map<String,String> params) throws CustomException {
		updateScalar(params,CncpProtoType.OAM_SBC_TLS);
	}
	//--------------TLS配置 结束-------------------
	//--------------性能参数配置 开始-------------------
	@Override
	public List<Item> getPerformanceItem() {
		List<Item> items=mapper.select1Level(3);
		return items;
	}

	@Override
	public JSONObject loadPerformanceScalar(String[] names) throws CustomException {
		return loadScalar(names,CncpProtoType.OAM_SBC_PERFORMANCE);
	}
	
	@Override
	public void updatePerformanceScalar(Map<String,String> params) throws CustomException {
		updateScalar(params,CncpProtoType.OAM_SBC_PERFORMANCE);
	}
	//--------------性能参数配置 结束-------------------
	//--------------基本服务配置 开始-------------------
	@Override
	public List<Item> getServiceItem() {
		List<Item> items=mapper.select1Level(4);
		return items;
	}

	@Override
	public JSONObject loadServiceScalar(String[] names) throws CustomException {
		return loadScalar(names,CncpProtoType.OAM_SBC_SERVICE);
	}
	
	@Override
	public void updateServiceScalar(Map<String,String> params) throws CustomException {
		updateScalar(params,CncpProtoType.OAM_SBC_SERVICE);
	}
	//--------------基本服务配置 结束-------------------
	//--------------运行参数配置 开始-------------------
	@Override
	public List<Item> getRuntimeItem() {
		List<Item> items=mapper.select1Level(5);
		return items;
	}

	@Override
	public JSONObject loadRuntimeScalar(String[] names) throws CustomException {
		return loadScalar(names,CncpProtoType.OAM_SBC_RUNTIME);
	}
	
	@Override
	public void updateRuntimeScalar(Map<String,String> params) throws CustomException {
		updateScalar(params,CncpProtoType.OAM_SBC_RUNTIME);
	}
	//--------------运行参数配置 结束-------------------
	//--------------板卡规划配置 开始-------------------
	@Override
	public List<Item> getBoardItem() {
		List<Item> items=mapper.select1Level(6);
		return items;
	}

	@Override
	public JSONObject loadBoardScalar(String[] names) throws CustomException {
		return loadScalar(names,CncpProtoType.OAM_SBC_BOARD);
	}
	
	@Override
	public void updateBoardScalar(Map<String,String> params) throws CustomException {
		updateScalar(params,CncpProtoType.OAM_SBC_BOARD);
	}
	//--------------板卡规划配置 结束-------------------
	//--------------开机启动配置 开始-------------------
	@Override
	public List<Item> getsStartupItem() {
		List<Item> items=mapper.select1Level(7);
		return items;
	}

	@Override
	public JSONObject loadStartupScalar(String[] names) throws CustomException {
		return loadScalar(names,CncpProtoType.OAM_SBC_STARTUP);
	}
	
	@Override
	public void updateStartupScalar(Map<String,String> params) throws CustomException {
		updateScalar(params,CncpProtoType.OAM_SBC_STARTUP);
	}
	//--------------开机启动配置 结束-------------------
	//--------------组配置 开始--------------------
	@Override
	public List<Item> getGroupManageItem() {
		List<Item> items=mapper.select1Level(8);
		return items;
	}

	@Override
	public PageBean loadGroupManageGrid(Integer id,Integer page, Integer pageSize) throws CustomException {
		return loadGrid(id, CncpProtoType.OAM_SBC_GROUPMANAGE,page,pageSize);
	}

	@Override
	public void deleteGroupManage(Integer[] ids) throws CustomException {
		delete(ids, CncpProtoType.OAM_SBC_GROUPMANAGE);
	}

	@Override
	public void addGroupManage(Map<String, String> map) throws CustomException {
		add(map, CncpProtoType.OAM_SBC_GROUPMANAGE);
	}

	@Override
	public void updateGroupManage(Map<String, String> map) throws CustomException {
		update(map, CncpProtoType.OAM_SBC_GROUPMANAGE);
	}
	//--------------组配置 结束--------------------
	//--------------组策略 开始--------------------
	@Override
	public List<Item> getGroupPolicyItem() {
		List<Item> items=mapper.select1Level(9);
		return items;
	}

	@Override
	public PageBean loadGroupPolicyGrid(Integer id,Integer page, Integer pageSize) throws CustomException {
		return loadGrid(id, CncpProtoType.OAM_SBC_GROUPPOLICY,page,pageSize);
	}

	@Override
	public void deleteGroupPolicy(Integer[] ids) throws CustomException {
		delete(ids, CncpProtoType.OAM_SBC_GROUPPOLICY);
	}

	@Override
	public void addGroupPolicy(Map<String, String> map) throws CustomException {
		add(map, CncpProtoType.OAM_SBC_GROUPPOLICY);
	}

	@Override
	public void updateGroupPolicy(Map<String, String> map) throws CustomException {
		update(map, CncpProtoType.OAM_SBC_GROUPPOLICY);
	}
	//--------------组策略 结束--------------------
	//--------------用户优先保障策略 开始-------------------
	@Override
	public List<Item> getGroupEnsureItem() {
		List<Item> items=mapper.select1Level(10);
		return items;
	}

	@Override
	public JSONObject loadGroupEnsureScalar(String[] names) throws CustomException {
		return loadScalar(names,CncpProtoType.OAM_SBC_GROUPENSURE);
	}
	
	@Override
	public void updateGroupEnsureScalar(Map<String,String> params) throws CustomException {
		updateScalar(params,CncpProtoType.OAM_SBC_GROUPENSURE);
	}
	//--------------用户优先保障策略 结束-------------------
	//--------------ACLInfo 开始--------------------
	@Override
	public List<Item> getAclItem() {
		List<Item> items=mapper.select1Level(11);
		return items;
	}

	@Override
	public PageBean loadAclGrid(Integer id,Integer page, Integer pageSize) throws CustomException {
		return loadGrid(id, CncpProtoType.OAM_SBC_ACL,page,pageSize);
	}

	@Override
	public void deleteAcl(Integer[] ids) throws CustomException {
		delete(ids, CncpProtoType.OAM_SBC_ACL);
	}

	@Override
	public void addAcl(Map<String, String> map) throws CustomException {
		add(map, CncpProtoType.OAM_SBC_ACL);
	}
	//--------------ACLInfo 结束--------------------
	//--------------黑名单 开始--------------------
	@Override
	public List<Item> getBlacklistItem() {
		List<Item> items=mapper.select1Level(12);
		return items;
	}

	@Override
	public PageBean loadBlacklistGrid(Integer id,Integer page, Integer pageSize) throws CustomException {
		return loadGrid(id, CncpProtoType.OAM_SBC_BLACKLIST,page,pageSize);
	}

	@Override
	public void deleteBlacklist(Integer[] ids) throws CustomException {
		delete(ids, CncpProtoType.OAM_SBC_BLACKLIST);
	}

	@Override
	public void addBlacklist(Map<String, String> map) throws CustomException {
		add(map, CncpProtoType.OAM_SBC_BLACKLIST);
	}
	//--------------黑名单 结束--------------------
	//--------------恶意访问检测策略 开始-------------------
	@Override
	public List<Item> getSpiteItem() {
		List<Item> items=mapper.select1Level(13);
		return items;
	}

	@Override
	public JSONObject loadSpiteScalar(String[] names) throws CustomException {
		return loadScalar(names,CncpProtoType.OAM_SBC_SPITE);
	}
	
	@Override
	public void updateSpiteScalar(Map<String,String> params) throws CustomException {
		updateScalar(params,CncpProtoType.OAM_SBC_SPITE);
	}
	//--------------恶意访问检测策略 结束-------------------
	//--------------业务限制策略 开始--------------------
	@Override
	public List<Item> getBusinessItem() {
		List<Item> items=mapper.select1Level(14);
		return items;
	}

	@Override
	public PageBean loadBusinessGrid(Integer id,Integer page, Integer pageSize) throws CustomException {
		int neType=1;
		Item item=mapper.selectById(id);
		StringBuilder sb = new StringBuilder();
		sb.append(item.getName()).append(KV_SPE+"\n");
		String baseStr=sb.toString();
		int recordStart=(page-1)*pageSize+1;
		int recordEnd=recordStart+2;
		int count=-1,start=1;
		int limit=1+1;
		int end=start+limit;
		int lineCount=3;
		JSONArray arr=new JSONArray();
		while((count==-1||arr.size()<pageSize)&&lineCount>=recordEnd-recordStart){
			sb=new StringBuilder(baseStr);
			sb.append("start"+KV_SPE).append(recordStart).append("\n").
				append("end"+KV_SPE).append(recordEnd).append("\n").toString();
			QueryMsg msg = sendCncpMsgFactory.createSendQueryCncpMsg(
					CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_GET_MSG, 
					neType, 0, CncpProtoType.OAM_SBC_BUSINESS, sb.toString());
			QueryResponseMsg resMsg = cncpTaskExecutor.invokeQueryMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
			if(resMsg.getResult()!=CncpProtoType.SUCCESS){
				throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(resMsg.getResult()));
			}
			String data=resMsg.getData();
			if(data==null){
				PageBean pageBean=new PageBean(page, pageSize, arr,Math.max(count,arr.size()));
				return pageBean;
			}
			List<String> lines=Arrays.asList(data.trim().split("\n"));
			count=Integer.parseInt(lines.get(0).split(KV_SPE, 2)[1]);
			lineCount=lines.size()/limit==0?lines.size()/limit+1:lines.size()/limit;
			for(int i=0;i<lineCount;i++){
				JSONObject json=new JSONObject();
				String[] columns=(String[])ArrayUtils.subarray(lines.toArray(new String[0]), start, end);
				for(String column:columns){
					String[] kv=column.split(KV_SPE,2);
					json.element(kv[0], kv[1]);
				}
				arr.add(json);
				start=end;end+=limit;
			}
			start=1;end=start+limit;
			recordStart+=3;recordEnd+=3;
		}
		PageBean pageBean=new PageBean(page, pageSize, arr,count);
		pageBean.setRows(arr);
		return pageBean;
	}

	@Override
	public void deleteBusiness(Integer[] ids,String name) throws CustomException {
		int neType=1;
		StringBuilder sb=new StringBuilder();
		sb.append(name).append(KV_SPE+"\n");
		sb.append("RecId"+KV_SPE).append(ids[0]).append("\n");
		//仅支持单个删除
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
				neType, 0, CncpProtoType.OAM_SBC_DELETE, sb.toString());
		SetUpResponseMsg resMsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
		if (resMsg.getResult() != CncpProtoType.SUCCESS){
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(resMsg.getResult()));
		}
		
	}

	@Override
	public void addBusiness(Map<String, String> map) throws CustomException {
		add(map, CncpProtoType.OAM_SBC_BUSINESS);
	}
	//--------------业务限制策略 结束--------------------
	//--------------媒体链路信息 开始--------------------
	@Override
	public List<Item> getMediaItem() {
		List<Item> items=mapper.select1Level(15);
		return items;
	}

	@Override
	public PageBean loadMediaGrid(Integer id,Integer page, Integer pageSize) throws CustomException {
		return loadGrid(id, CncpProtoType.OAM_SBC_MEDIA,page,pageSize);
	}
	//--------------媒体链路信息 结束--------------------
	//--------------用户注册状态 开始--------------------
	@Override
	public List<Item> getStatusItem() {
		List<Item> items=mapper.select1Level(16);
		return items;
	}

	@Override
	public PageBean loadStatusGrid(Integer id,Integer page, Integer pageSize) throws CustomException {
		return loadGrid(id, CncpProtoType.OAM_SBC_STATUS,page,pageSize);
	}
	//--------------用户注册状态 结束--------------------
	//--------------板卡状态 开始--------------------
	@Override
	public List<Item> getBoardStatusItem() {
		List<Item> items=mapper.select1Level(17);
		return items;
	}

	@Override
	public PageBean loadBoardStatusGrid(Integer id,Integer page, Integer pageSize) throws CustomException {
		return loadGrid(id, CncpProtoType.OAM_SBC_STATUS,page,pageSize);
	}
	//--------------板卡状态  结束--------------------
	//--------------运行告警 开始--------------------
	@Override
	public List<Item> getAlarmItem() {
		List<Item> items=mapper.select1Level(18);
		return items;
	}

	@Override
	public PageBean loadAlarmGrid(Integer id,Integer page, Integer pageSize) throws CustomException {
		return loadGrid(id, CncpProtoType.OAM_SBC_ALARM,page,pageSize);
	}
	//--------------运行告警  结束--------------------
	//--------------统计信息 开始--------------------
	@Override
	public List<Item> getStatisticsItem() {
		List<Item> items=mapper.select1Level(19);
		return items;
	}

	@Override
	public JSONObject loadStatisticsScalar(String[] names) throws CustomException {
		return loadScalar(names,CncpProtoType.OAM_SBC_STATISTICS);
	}
	//--------------统计信息  结束--------------------
}
