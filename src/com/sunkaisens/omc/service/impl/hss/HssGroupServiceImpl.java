package com.sunkaisens.omc.service.impl.hss;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunkaisens.omc.context.core.OmcServerContext;
import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.mapper.core.EntityMapper;
import com.sunkaisens.omc.mapper.core.HssMetaMapper;
import com.sunkaisens.omc.mapper.core.ModuleMapper;
import com.sunkaisens.omc.mapper.hss.GroupMemberMapper;
import com.sunkaisens.omc.mapper.hss.HlrMapper;
import com.sunkaisens.omc.mapper.hss.HssGroupMapper;
import com.sunkaisens.omc.mapper.hss.impl.HlrMapperImpl;
import com.sunkaisens.omc.po.core.Entity;
import com.sunkaisens.omc.po.core.Module;
import com.sunkaisens.omc.po.hss.HssGroup;
import com.sunkaisens.omc.service.hss.HssGroupService;
import com.sunkaisens.omc.thread.cncpMsg.CncpProtoType;
import com.sunkaisens.omc.thread.cncpMsg.CncpTaskExecutor;
import com.sunkaisens.omc.thread.cncpMsg.SendCncpMsgFactory;
import com.sunkaisens.omc.thread.messageEncapsulation.SetUpMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.SetUpResponseMsg;
import com.sunkaisens.omc.util.hss.BeanToXmlUtil;
import com.sunkaisens.omc.util.hss.CreateGroup;
import com.sunkaisens.omc.util.hss.CreateGroupList;
import com.sunkaisens.omc.util.hss.DdtGroupUtil;
import com.sunkaisens.omc.util.hss.RemoteDbUtil;
import com.sunkaisens.omc.vo.core.PageBean;
import com.sunkaisens.omc.websocket.OamAlarmEndpoint;
/**
 *HssGroupService 接口实现类
 */
@Service
public class HssGroupServiceImpl implements HssGroupService {

	@Resource
	private HssGroupMapper mapper;
	@Resource
	private GroupMemberMapper groupMemberMapper;
	@Resource
	private SendCncpMsgFactory sendCncpMsgFactory;
	@Resource
	private CncpTaskExecutor cncpTaskExecutor;
	@Resource
	private ModuleMapper moduleMapper;
	@Resource
	private EntityMapper entityMapper;
	@Resource
	private HlrMapper hlrMapper;
	@Resource
	private HssMetaMapper hssMetaMapper;
	
	//分页使用
	private List<HssGroup> serviceGroupListTem = new ArrayList<HssGroup>();
	
	// 1表示group 0表示groupMember
	@Override
	public PageBean getPageBean(Integer pageNum, Integer pageSize, HssGroup group) {
		serviceGroupListTem.clear();
		PageBean p = null;
		String groupId = group.getId();
		Integer groupCallType = group.getCallType();
		Integer groupBusiType = group.getBusiType();
		try {
			//获取业务组通讯录    
			List<HssGroup> serviceGroupList = DdtGroupUtil.getGroupList(OmcServerContext.getInstance().getSelectServiceUrl());
			System.err.println("<<<<<<<<<>>>>>>>>>>获取远端群组组号的个数:"+serviceGroupList.size());
			if(groupId!=null && !"".equals(groupId)){
				Iterator<HssGroup> iterators = serviceGroupList.iterator();
				while(iterators.hasNext()){
					HssGroup nextHssGroup = iterators.next();
					if(!nextHssGroup.getId().contains(groupId)){
						iterators.remove();
					}
				}
			}
			if(groupCallType!=null){
				Iterator<HssGroup> iterators = serviceGroupList.iterator();
				while(iterators.hasNext()){
					HssGroup nextHssGroup = iterators.next();
					if(!groupCallType.equals(nextHssGroup.getCallType())){
						iterators.remove();
					}
				}
			}
			if(groupBusiType!=null){
				Iterator<HssGroup> iterators = serviceGroupList.iterator();
				while(iterators.hasNext()){
					HssGroup nextHssGroup = iterators.next();
					if(!groupBusiType.equals(nextHssGroup.getBusiType())){
						iterators.remove();
					}
				}
			}
			int pageCount = (serviceGroupList.size() + pageSize - 1) / pageSize;
			if(pageNum > pageCount){
				pageNum = 1;
			}
			int beginIndex = (pageNum - 1) * pageSize;
			int endIndex = beginIndex + pageSize;
			if(endIndex >= serviceGroupList.size()){
				endIndex = serviceGroupList.size();
			}
			Object[] arrays = serviceGroupList.toArray();
			for (int i = beginIndex; i < endIndex; i++) {
				serviceGroupListTem.add((HssGroup)arrays[i]);
			}
	        p = new PageBean(pageNum, pageSize, serviceGroupListTem, serviceGroupList.size());
			return p;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//添加组（Group）
	@Override
	public void save(HssGroup group) throws CustomException {
		String groupNum = group.getId();
		//封装对象
		CreateGroupList createGroupList = new CreateGroupList();
		createGroupList.setName(groupNum);
		createGroupList.setUri("sip:"+groupNum+"@test.com");
		createGroupList.setDisplayName(groupNum);
		if(group.getGroupType()==1){
			createGroupList.setServiceType("dynamic");
		}else if(group.getGroupType()==2){
			createGroupList.setServiceType("trunk");
		}else if(group.getGroupType()==3){
			createGroupList.setServiceType("normal");
		}else if(group.getGroupType()==4){
			createGroupList.setServiceType("personal");
		}
		createGroupList.setServiceLevel(String.valueOf(group.getPriority()));
		CreateGroup createGroup = new CreateGroup("com:sunkaisens:service-group",createGroupList);
		String contentBody = BeanToXmlUtil.convertBeanToXmlStr(createGroup);
		System.err.println("参数:"+contentBody);
		//添加用户的时候需要判断该组号时候是否已经存在
		List<HssGroup> serviceGroupList = DdtGroupUtil.getGroupList(OmcServerContext.getInstance().getSelectServiceUrl());
		for (HssGroup hssGroup : serviceGroupList) {
			if(hssGroup.getId().equals(groupNum)){
				throw new CustomException("组号【" + groupNum + "】已存在");
			}
		}
		//发送Put请求
		DdtGroupUtil.addPutGroupList(OmcServerContext.getInstance().getAddServiceUrl()+groupNum,contentBody);
	}
	
	//修改组信息
	@Override
	public void update(HssGroup group) throws CustomException {
		String groupNum = group.getId();
		//封装对象
		CreateGroupList updateGroupList = new CreateGroupList();
		updateGroupList.setName(groupNum);
		updateGroupList.setUri("sip:"+groupNum+"@test.com");
		updateGroupList.setDisplayName(groupNum);
		if(group.getGroupType()==1){
			updateGroupList.setServiceType("dynamic");
		}else if(group.getGroupType()==2){
			updateGroupList.setServiceType("trunk");
		}else if(group.getGroupType()==3){
			updateGroupList.setServiceType("normal");
		}else if(group.getGroupType()==4){
			updateGroupList.setServiceType("personal");
		}
		updateGroupList.setServiceLevel(String.valueOf(group.getPriority()));
		CreateGroup createGroup = new CreateGroup("com:sunkaisens:service-group",updateGroupList);
		String contentBody = BeanToXmlUtil.convertBeanToXmlStr(createGroup);
		//发送Put请求
		DdtGroupUtil.updatePutGroupList(OmcServerContext.getInstance().getUpdateServiceUrl()+groupNum,contentBody);
	}
	
	//删除组
	@Override
	public void delete(String[] ids) throws CustomException {
		String deleteServiceUrl = OmcServerContext.getInstance().getDeleteServiceUrl();
		for (String id : ids) {
			DdtGroupUtil.deleteGroupList(deleteServiceUrl+id,id);
		}
	}
	
	@Override
	public void syncGroupDel(int flag,String[] ids){
//		Module module = moduleMapper.selectByName("hss");
		Module module = RemoteDbUtil.selectRemoteModule(flag,"hss");
		if (module == null) {
//			OamAlarmEndpoint.broadcast("网元【hss】还未下发");
//			return;
			if(flag==1){
				OamAlarmEndpoint.broadcast("主设备连接失败，请检查连接！！！");
				return;
			}else{
				OamAlarmEndpoint.broadcast("备设备连接失败，请检查连接！！！");
				return;
			}
		}
		Integer moduleId = module.getId();
		Integer instId = null;
//		List<Entity> entities = entityMapper.selectByModuleId(moduleId);
		List<Entity> entities = RemoteDbUtil.selectRemoteEntity(flag,moduleId);
		if (entities.size() == 0) {
//			OamAlarmEndpoint.broadcast("网元【hss】还未下发");
//			return;
			if(flag==1){
				OamAlarmEndpoint.broadcast("主设备连接失败,请检查连接！！！");
				return;
			}else{
				OamAlarmEndpoint.broadcast("备设备连接失败,请检查连接！！！");
				return;
			}
		}
		StringBuilder error = new StringBuilder();
		for (int i = 0; i < ids.length; i++) {
			String groupId = ids[i];
			if (entities.size() == 1) {
				instId = entities.get(0).getInstId();
			} else {
				HlrMapperImpl hlrMapperImpl = (HlrMapperImpl) hlrMapper;
				int count = hlrMapperImpl.getHssDataSourceCount();
				instId = Long.parseLong(groupId) % count == 0 ? count : (int) (Long.parseLong(groupId) % count);
			}
			SetUpResponseMsg resp = null;
			String message = "Group:" + groupId + "\n";
			for (int j = 0; j < 3; j++) {
				SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
						CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
						moduleId, instId, CncpProtoType.OAM_SET_GROUP_DELETE, message);
				if(flag==1){
					resp = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getMainDeviceIp(), OmcServerContext.getInstance().getMainDevicePort());
				}else{
					resp = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getBackUpDeviceIp(), OmcServerContext.getInstance().getBackUpDevicePort());
				}
				if (resp.getResult() != 1){
					break;
				}
			}
			if (resp.getResult() != 0) {
				if(flag==1){
					OamAlarmEndpoint.broadcast("主设备HSS同步失败,请检查配置！！！");
				}else{
					OamAlarmEndpoint.broadcast("备设备HSS同步失败,请检查配置！！！");
				}
			}
		}
		if (error.length() > 0)
			OamAlarmEndpoint.broadcast(error.toString());
	}
	
	@Override
	public void syncGroupUpdate(int flag,long groupId){
//		Module module = moduleMapper.selectByName("hss");
		Module module = RemoteDbUtil.selectRemoteModule(flag,"hss");
		if (module == null) {
//			OamAlarmEndpoint.broadcast("网元【hss】还未下发");
//			return;
			if(flag==1){
				OamAlarmEndpoint.broadcast("主设备连接失败，请检查连接！！！");
				return;
			}else{
				OamAlarmEndpoint.broadcast("备设备连接失败，请检查连接！！！");
				return;
			}
		}
		Integer moduleId = module.getId();
		Integer instId=null;
//		List<Entity> entities=entityMapper.selectByModuleId(moduleId);
		List<Entity> entities = RemoteDbUtil.selectRemoteEntity(flag,moduleId);
		if(entities.size()==0){
//			OamAlarmEndpoint.broadcast("网元【hss】还未下发");
//			return;
			if(flag==1){
				OamAlarmEndpoint.broadcast("主设备连接失败,请检查连接！！！");
				return;
			}else{
				OamAlarmEndpoint.broadcast("备设备连接失败,请检查连接！！！");
				return;
			}
		}else if(entities.size()==1){
			instId=entities.get(0).getInstId();
		}else{
			HlrMapperImpl hlrMapperImpl = (HlrMapperImpl) hlrMapper;
			int count = hlrMapperImpl.getHssDataSourceCount();
			instId =groupId% count == 0 ? count : (int) (groupId%count);
		}
		SetUpResponseMsg resmsg = null;
		String data = "Group:" + groupId + "\n";
		for (int i = 0; i < 3; i++) {
			SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
					CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
					moduleId, instId, CncpProtoType.OAM_SET_GROUP_UPDATE, data);
			if(flag==1){
				resmsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getMainDeviceIp(), OmcServerContext.getInstance().getMainDevicePort());
			}else{
				resmsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getBackUpDeviceIp(), OmcServerContext.getInstance().getBackUpDevicePort());
			}
			if(resmsg.getResult()==0){
				return;
			}else if(i==2){
//				OamAlarmEndpoint.broadcast("与【hss】同步失败");
				if(flag==1){
					OamAlarmEndpoint.broadcast("主设备HSS同步失败,请检查配置！！！");
				}else{
					OamAlarmEndpoint.broadcast("备设备HSS同步失败,请检查配置！！！");
				}
			}
		}
	}

}
