package com.sunkaisens.omc.component;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sunkaisens.omc.context.core.OmcServerContext;
import com.sunkaisens.omc.mapper.core.ConfigMapper;
import com.sunkaisens.omc.mapper.core.EntityMapper;
import com.sunkaisens.omc.mapper.core.HssMetaMapper;
import com.sunkaisens.omc.mapper.core.ModuleMapper;
import com.sunkaisens.omc.mapper.hss.UserGroupPriorityMapper;
import com.sunkaisens.omc.po.core.Card;
import com.sunkaisens.omc.po.core.Config;
import com.sunkaisens.omc.po.core.Entity;
import com.sunkaisens.omc.po.core.Module;
import com.sunkaisens.omc.po.core.Privilege;
import com.sunkaisens.omc.po.core.User;
import com.sunkaisens.omc.service.core.CardService;
import com.sunkaisens.omc.service.core.PrivilegeService;
import com.sunkaisens.omc.service.core.UserService;
/**
 *初始化数据库实体类
 */
@Component
public class Installer {
	//注入privilegeService Bean
	@Resource private PrivilegeService privilegeService;
	//注入userService Bean
	@Resource private UserService userService;
	//注入cardService Bean
	@Resource private CardService cardService;
	//注入moduleMapper Bean
	@Resource private ModuleMapper moduleMapper;
	//注入entityMapper Bean
	@Resource private EntityMapper entityMapper;
	//注入configMapper Bean
	@Resource private ConfigMapper configMapper;
	//注入hssMetaMapper Bean
	@Resource private HssMetaMapper hssMetaMapper;
	//注入userGroupPriorityMapper
	@Resource private UserGroupPriorityMapper userGroupPriorityMapper;
	/**
	 * 
	 * 
	 * 初始化数据库权限数据
	 */
	protected void initPrivilege(){
		Privilege p1=null,p2=null,p3=null,p4=null,p5=null,p6=null,p7=null;
		Privilege p8=null,p9=null,p10=null,p11=null,p12=null,p13=null,p14=null,p15=null;
		
		//===============第二节点============
		p1=new Privilege("TerminalUserManager",null,null,null,false,null);
		privilegeService.save(p1);
		p2=new Privilege("HSSManager","hss/listUI", null,null, false, p1);
		privilegeService.save(p2);
			p5=new Privilege("AddTerminalUser","hss/add", null, null, true, p2);
			privilegeService.save(p5);
			p6=new Privilege("DeleteTerminalUser","hss/delete", null, null, true, p2);
			privilegeService.save(p6);
			p7=new Privilege("UpdateTerminalUser","hss/update", null, null, true, p2);
			privilegeService.save(p7);
			p8=new Privilege("ViewTerminalUser","hss/list", null, null, true, p2);
			privilegeService.save(p8);

		p4=new Privilege("HSSReduancyManager", "hss/reduancyListUI", null, null, true, p1);
		privilegeService.save(p4);

			p9=new Privilege("UeDestroy","hss/ueDestroy", null, null, true, p2);
			privilegeService.save(p9);
			p10=new Privilege("UeRestore","hss/ueRestore", null, null, true, p2);
			privilegeService.save(p10);
			p11=new Privilege("UeSwoon","hss/ueSwoon", null, null, true, p2);
			privilegeService.save(p11);
			p12=new Privilege("AirMonitor","hss/airMonitor", null, null, true, p2);
			privilegeService.save(p12);

		p3=new Privilege("IMSUserManager","ims/listUI", null,null, false, p1);
		privilegeService.save(p3);
			p5=new Privilege("AddIMSUser","ims/add", null, null, true, p3);
			privilegeService.save(p5);
			p6=new Privilege("DeleteIMSUser","ims/delete", null, null, true, p3);
			privilegeService.save(p6);
			p7=new Privilege("UpdateIMSUser","ims/update", null, null, true, p3);
			privilegeService.save(p7);
			p8=new Privilege("ViewIMSUser","ims/list", null, null, true, p3);
			privilegeService.save(p8);
		p4=new Privilege("GroupCallGroupManage","hssGroup/listUI", null,null, false, p1);
		privilegeService.save(p4);
			p5=new Privilege("AddGroupCallGroup","hssGroup/add", null, null, true, p4);
			privilegeService.save(p5);
			p6=new Privilege("DeleteGroupCallGroup","hssGroup/delete", null, null, true, p4);
			privilegeService.save(p6);
			p7=new Privilege("UpdateGroupCallGroup","hssGroup/update", null, null, true, p4);
			privilegeService.save(p7);
			p8=new Privilege("ViewGroupCallGroup","hssGroup/list", null, null, true, p4);
			privilegeService.save(p8);

//		p5=new Privilege("GroupMemberManage","groupMember/listUI", null,null, false, p1);
//		privilegeService.save(p5);
//			p6=new Privilege("AddGroupMember","groupMember/add", null, null, true, p5);
//			privilegeService.save(p6);
//			p7=new Privilege("DeleteGroupMember","groupMember/delete", null, null, true, p5);
//			privilegeService.save(p7);
//			p8=new Privilege("UpdateGroupMember","groupMember/update", null, null, true, p5);
//			privilegeService.save(p8);
//			p9=new Privilege("ViewGroupMember","groupMember/list", null, null, true, p5);
//			privilegeService.save(p9);
		//--------------------------会议组管理------------------------
		p4=new Privilege("MeetingGroupManage","meetingGroup/listUI2", null,null, false, p1);
		privilegeService.save(p4);
			p5=new Privilege("AddMeetingGroup","meetingGroup/add", null, null, true, p4);
			privilegeService.save(p5);
			p6=new Privilege("DeleteMeetingGroup","meetingGroup/delete", null, null, true, p4);
			privilegeService.save(p6);
			p7=new Privilege("UpdateMeetingGroup","meetingGroup/update", null, null, true, p4);
			privilegeService.save(p7);
			p8=new Privilege("ViewMeetingGroup","meetingGroup/list", null, null, true, p4);
			privilegeService.save(p8);
//		p5=new Privilege("MeetingMemberManage","meetingMember/listUI2", null,null, false, p1);
//		privilegeService.save(p5);
//			p6=new Privilege("AddMeetingMember","meetingMember/add", null, null, true, p5);
//			privilegeService.save(p6);
//			p7=new Privilege("DeleteMeetingMember","meetingMember/delete", null, null, true, p5);
//			privilegeService.save(p7);
//			p8=new Privilege("UpdateMeetingMember","meetingMember/update", null, null, true, p5);
//			privilegeService.save(p8);
//			p9=new Privilege("ViewMeetingMember","meetingMember/list", null, null, true, p5);
//			privilegeService.save(p9);
		
		p5=new Privilege("priorityManage","hss/listMdnUI",null,null, false, p1);
		privilegeService.save(p5);
			p6=new Privilege("priorityModifity","hss/updatePriority",null,null, true, p5);
			privilegeService.save(p6);
		p9=new Privilege("HssItemConf","hssParam/listUI", null, null, false, p1);
		privilegeService.save(p9);
			p10=new Privilege("HssParamUpdate","hssParam/update",null,null, true, p9);
			privilegeService.save(p10);
		p11 = new Privilege("userGroupPriority","hss/userGroupListUI",null,null, false, p1);
		privilegeService.save(p11);
			
			
		//===============SBC系统配置节点============
		if(OmcServerContext.getInstance().getProject().startsWith("810-4")){
		p1=new Privilege("SBCSystemConfig", null, null,null, false, null);
		privilegeService.save(p1);
		p2=new Privilege("NetworkParameterCnfig", "sbc/netUI", null,null, false, p1);
		privilegeService.save(p2);
			p9=new Privilege("SetNetworkParameter","sbc/updateNetScalar", null, null, true, p2);
			privilegeService.save(p9);
			p10=new Privilege("ViewNetworkParameter","sbc/loadNetScalar", null, null, true, p2);
			privilegeService.save(p10);
		p3=new Privilege("TLSParameterConfig", "sbc/tlsUI", null, null, false, p1);
		privilegeService.save(p3);
			p9=new Privilege("SetTLSParameter","sbc/updateTlsScalar", null, null, true, p3);
			privilegeService.save(p9);
			p10=new Privilege("ViewTLSParameter","sbc/loadTlsScalar", null, null, true, p3);
			privilegeService.save(p10);
		p4=new Privilege("PerformanceParameterConfig", "sbc/performanceUI", null, null, false, p1);
		privilegeService.save(p4);
			p9=new Privilege("SetPerformanceParameter","sbc/updatePerformanceScalar", null, null, true, p4);
			privilegeService.save(p9);
			p10=new Privilege("ViewPerformanceParameter","sbc/loadPerformanceScalar", null, null, true, p4);
			privilegeService.save(p10);
		p5=new Privilege("BasicServiceConfig", "sbc/serviceUI", null, null, false, p1);
		privilegeService.save(p5);
			p9=new Privilege("SetBasicService","sbc/updateServiceScalar", null, null, true, p5);
			privilegeService.save(p9);
			p10=new Privilege("ViewBasicService","sbc/loadServiceScalar", null, null, true, p5);
			privilegeService.save(p10);
		p6=new Privilege("WorkingParameterConfig", "sbc/runtimeUI", null,null, false, p1);
		privilegeService.save(p6);
			p9=new Privilege("SetWorkingParameter","sbc/updateRuntimeScalar", null, null, true, p6);
			privilegeService.save(p9);
			p10=new Privilege("ViewWorkingParameter","sbc/loadRuntimeScalar", null, null, true, p6);
			privilegeService.save(p10);
		p7=new Privilege("CardLayoutConfig", "sbc/boardUI", null,null, false, p1);
		privilegeService.save(p7);
			p9=new Privilege("SetCardLayout","sbc/updateBoardScalar", null, null, true, p7);
			privilegeService.save(p9);
			p10=new Privilege("ViewCardLayout","sbc/loadBoardScalar", null, null, true, p7);
			privilegeService.save(p10);
		p8=new Privilege("Start-UpConfig", "sbc/startupUI", null,null, false, p1);
		privilegeService.save(p8);
			p9=new Privilege("SetStart-UpParameter","sbc/updateStartupScalar", null, null, true, p8);
			privilegeService.save(p9);
			p10=new Privilege("ViewStart-UpParameter","sbc/loadStartupScalar", null, null, true, p8);
			privilegeService.save(p10);
		//===============SBC CAC配置策略节点============
		p1=new Privilege("CACPolicyConfig", null, null,null, false, null);
		privilegeService.save(p1);
		p2=new Privilege("GroupManager", "sbc/groupManageUI", null,null, false, p1);
		privilegeService.save(p2);
			p5=new Privilege("AddGroupConfig", "sbc/addGroupManage", null, null, true, p2);
			privilegeService.save(p5);
			p6=new Privilege("DeleteGroupConfig", "sbc/deleteGroupManage", null, null, true, p2);
			privilegeService.save(p6);
			p7=new Privilege("UpdateGroupConfig", "sbc/updateGroupManage", null, null, true, p2);
			privilegeService.save(p7);
			p8=new Privilege("ViewGroupConfig", "sbc/loadGroupManageGrid", null, null, true, p2);
			privilegeService.save(p8);
		p3=new Privilege("GroupPolicy", "sbc/groupPolicyUI", null,null, false, p1);
		privilegeService.save(p3);
			p5=new Privilege("AddGroupPolicy", "sbc/addGroupPolicy", null, null, true, p3);
			privilegeService.save(p5);
			p6=new Privilege("DeleteGroupPolicy", "sbc/deleteGroupPolicy", null, null, true, p3);
			privilegeService.save(p6);
			p7=new Privilege("UpdateGroupPolicy", "sbc/updateGroupPolicy", null, null, true, p3);
			privilegeService.save(p7);
			p8=new Privilege("ViewGroupPolicy", "sbc/loadGroupPolicyGrid", null, null, true, p3);
			privilegeService.save(p8);
		p4=new Privilege("UserPriorityEnsurePolicy", "sbc/groupEnsureUI", null,null, false, p1);
		privilegeService.save(p4);
			p5=new Privilege("SetEnsurePolicy","sbc/updateGroupEnsureScalar", null, null, true, p4);
			privilegeService.save(p5);
			p6=new Privilege("ViewEnsurePolicy","sbc/loadGroupEnsureScalar", null, null, true, p4);
			privilegeService.save(p6);
		//===============SBC 访问控制策略节点============
		p1=new Privilege("SBCAccessControlPolicy", null, null,null, false, null);
		privilegeService.save(p1);
		p2=new Privilege("ACLAccessControl", "sbc/aclUI", null,null, false, p1);
		privilegeService.save(p2);
			p6=new Privilege("AddAccessControl", "sbc/addAcl", null, null, true, p2);
			privilegeService.save(p6);
			p7=new Privilege("DeleteAccessControl", "sbc/deleteAcl", null, null, true, p2);
			privilegeService.save(p7);
			p8=new Privilege("ViewAccessControl", "sbc/loadAclGrid", null, null, true, p2);
			privilegeService.save(p8);
		p3=new Privilege("BlackList", "sbc/blacklistUI", null,null, false, p1);
		privilegeService.save(p3);
			p6=new Privilege("AddBlacklist", "sbc/addBlacklist", null, null, true, p3);
			privilegeService.save(p6);
			p7=new Privilege("DeleteBlacklist", "sbc/deleteBlacklist", null, null, true, p3);
			privilegeService.save(p7);
			p8=new Privilege("ViewBlacklist", "sbc/loadBlacklistGrid", null, null, true, p3);
			privilegeService.save(p8);
		p4=new Privilege("MaliciousAccessCheckPolicy", "sbc/spiteUI", null,null, false, p1);
		privilegeService.save(p4);
			p6=new Privilege("SetCheckPolicy","sbc/updateSpiteScalar", null, null, true, p4);
			privilegeService.save(p6);
			p7=new Privilege("ViewCheckPolicy","sbc/loadSpiteScalar", null, null, true, p4);
			privilegeService.save(p7);
		p5=new Privilege("BusinessLimitedPolicy", "sbc/businessUI", null,null, false, p1);
		privilegeService.save(p5);
			p6=new Privilege("AddLimitedPolicy", "sbc/addBusiness", null, null, true, p5);
			privilegeService.save(p6);
			p7=new Privilege("DeleteLimitedPolicy", "sbc/deleteBusiness", null, null, true, p5);
			privilegeService.save(p7);
			p8=new Privilege("ViewLimitedPolicy", "sbc/loadBusinessGrid", null, null, true, p5);
			privilegeService.save(p8);
		//===============SBC 状态查询节点============
		p1=new Privilege("SBCStatusQuery", null, null, null, false, null);
		privilegeService.save(p1);
		p2=new Privilege("MediaLinkInformation", "sbc/mediaUI", null,null, false, p1);
		privilegeService.save(p2);
			p4=new Privilege("ViewLinkInformation", "sbc/loadMediaGrid", null, null, true, p2);
			privilegeService.save(p4);
		p3=new Privilege("UserRegisterStatus", "sbc/statusUI", null, null, false, p1);
		privilegeService.save(p3);
			p4=new Privilege("ViewUserRegisterStatus", "sbc/loadStatusGrid", null, null, true, p3);
			privilegeService.save(p4);
		//===============SBC 系统状态节点============
		p1=new Privilege("SBCSystemStatus", null, null,null, false, null);
		privilegeService.save(p1);
		p2=new Privilege("CardStatus", "sbc/boardStatusUI", null,null, false, p1);
		privilegeService.save(p2);
			p5=new Privilege("ViewCardStatus", "sbc/loadBoardStatusGrid", null, null, true, p2);
			privilegeService.save(p5);
		p3=new Privilege("RunningAlarm", "sbc/alarmUI", null,null, false, p1);
		privilegeService.save(p3);
			p5=new Privilege("ViewRunningAlarm", "sbc/loadAlarmGrid", null, null, true, p3);
			privilegeService.save(p5);
		p4=new Privilege("StatisticInformation", "sbc/statisticsUI", null,null, false, p1);
		privilegeService.save(p4);
			p5=new Privilege("ViewStatisticInformation", "sbc/loadStatisticsScalar", null, null, true, p4);
			privilegeService.save(p5);
		}
		//============= SBC  结束================
		
		//===============第四节点============
		p1=new Privilege("SystemManager", null, null, null, false, null);
		privilegeService.save(p1);
		p2=new Privilege("NetworkManager", "eth/listUI", null,null, false, p1);
		privilegeService.save(p2);
			p10=new Privilege("ViewableDns", "omc/viewableDns", null, null, true, p2);
			privilegeService.save(p10);
			p11=new Privilege("EthActivateUpdate", "eth/activateUpdate", null, null, true, p2);
			privilegeService.save(p11);
			p12=new Privilege("EthActivateAdd", "eth/activateAdd", null, null, true, p2);
			privilegeService.save(p12);
			p13=new Privilege("EthStaticAdd", "eth/staticAdd", null, null, true, p2);
			privilegeService.save(p13);
			p14=new Privilege("EthStaticUpdate", "eth/staticUpdate", null, null, true, p2);
			privilegeService.save(p14);
			p15=new Privilege("EthDelete", "eth/delete", null, null, true, p2);
			privilegeService.save(p15);
			p10=new Privilege("Active", "eth/active", null, null, true, p2);
			privilegeService.save(p10);
			p11=new Privilege("goActive", "eth/goActive", null, null, true, p2);
			privilegeService.save(p11);
			p12=new Privilege("EthViewData", "eth/viewData", null, null, true, p2);
			privilegeService.save(p12);
		p3=new Privilege("RouteManager", "route/listUI", null,null, false, p1);
		privilegeService.save(p3);
		p4=new Privilege("HostAddressManager", "hostaddr/listUI", null,null, false, p1);
		privilegeService.save(p4);
		p12=new Privilege("AddHost", "hostaddr/add", null,null, true, p4);
		privilegeService.save(p12);
		p13=new Privilege("DeleteHost", "hostaddr/delete", null,null, true, p4);
		privilegeService.save(p13);
		p14=new Privilege("ViewData", "hostaddr/view", null,null, true, p4);
		privilegeService.save(p14);
		p8=new Privilege("DefaultRoute", "route/defaultRoute", null,null, true, p3);
		privilegeService.save(p8);
		p9=new Privilege("AddRoute", "route/addRoute", null,null, true, p3);
		privilegeService.save(p9);
		p12=new Privilege("RouteViewData", "route/viewData", null,null, true, p3);
		privilegeService.save(p12);
		p10=new Privilege("DeleteRoute", "route/deleteRoute", null,null, true, p3);
		privilegeService.save(p10);
		p11=new Privilege("OSPF", "route/ospf", null,null, true, p3);
		privilegeService.save(p11);
		p5=new Privilege("HostNameAndDNSManager", "dns/listUI", null,null, false, p1);
		privilegeService.save(p5);
		p6=new Privilege("CardManager", "card/listUI", null,null, false, p1);
		privilegeService.save(p6);
		p7=new Privilege("DnsManager", "dnsServer/listUI", null,null, false, p1);
		privilegeService.save(p7);
//		p8=new Privilege("BoardSerialNumber", "serialNumber/listUI", null,null, false, p1);
//		privilegeService.save(p8);
//		p9=new Privilege("ResetLfConfig", "resetLf/listUI", null,null, false, p1);
//		privilegeService.save(p9);
		
		//===============第五节点============
		p1=new Privilege("ResourceLibraryManager", null, null, null, false, null);
		privilegeService.save(p1);
		p2=new Privilege("ResourcePackageManager", "module/listUI", null, null, false, p1);
		privilegeService.save(p2);
			p8=new Privilege("SinglePackageUpload", "module/upload", null, null, true, p2);
			privilegeService.save(p8);
			p9=new Privilege("MultiplePackageUpload", "module/uploadAll", null, null, true, p2);
			privilegeService.save(p9);
			p10=new Privilege("DeleteResourcePackage", "module/delete", null, null, true, p2);
			privilegeService.save(p10);
			p11=new Privilege("UpdateBasicInformation", "module/update", null, null, true, p2);
			privilegeService.save(p11);
			p12=new Privilege("ViewResourcePackage", "module/list", null, null, true, p2);
			privilegeService.save(p12);
		p3=new Privilege("DynamicLibraryManager", "so/listUI", null, null, false, p1);
		privilegeService.save(p3);
			p8=new Privilege("UploadDynamicLibrary", "so/upload", null, null, true, p3);
			privilegeService.save(p8);
			p9=new Privilege("DeleteDynamicLibrary", "so/delete", null, null, true, p3);
			privilegeService.save(p9);
			p10=new Privilege("ViewDynamicLibrary", "so/list", null, null, true, p3);
			privilegeService.save(p10);
		p4=new Privilege("ConfigPageManager", "common/listUI", null, null, false, p1);
		privilegeService.save(p4);
			p8=new Privilege("AddConfigPage", "common/save", null, null, true, p4);
			privilegeService.save(p8);
			p9=new Privilege("DeleteConfigPage", "common/delete", null, null, true, p4);
			privilegeService.save(p9);
			p10=new Privilege("UpdateConfigPage", "common/update", null, null, true, p4);
			privilegeService.save(p10);
			p11=new Privilege("ViewConfigPage", "common/list", null, null, true, p4);
			privilegeService.save(p11);
			p12=new Privilege("ViewThisPageConfigItem", "common/show", null, null, true, p4);
			privilegeService.save(p12);
		p5=new Privilege("ConfigParameterManager", "relevance/listUI", null, null, false, p1);
		privilegeService.save(p5);
			p8=new Privilege("AddConfigParameter", "relevance/save", null, null, true, p5);
			privilegeService.save(p8);
			p9=new Privilege("DeleteConfigParameter", "relevance/delete", null, null, true, p5);
			privilegeService.save(p9);
			p10=new Privilege("UpdateConfigParameter", "relevance/update", null, null, true, p5);
			privilegeService.save(p10);
			p11=new Privilege("ViewConfigParameter", "relevance/list", null, null, true, p5);
			privilegeService.save(p11);
			p12=new Privilege("ViewAssociatedConfigItem", "relevance/show", null, null, true, p5);
			privilegeService.save(p12);
		p6=new Privilege("ConfigFileManager", "config/listUI", null, null, false, p1);
		privilegeService.save(p6);
			p8=new Privilege("UploadConfigFile", "config/upload", null, null, true, p6);
			privilegeService.save(p8);
			p9=new Privilege("DeleteConfigFile", "config/delete", null, null, true, p6);
			privilegeService.save(p9);
			p10=new Privilege("UpdateConfigFile", "config/update", null, null, true, p6);
			privilegeService.save(p10);
			p11=new Privilege("ViewConfigFile", "config/list", null, null, true, p6);
			privilegeService.save(p11);
		p7=new Privilege("ConfigItemManager", "item/listUI", null, null, false, p1);
		privilegeService.save(p7);
			p8=new Privilege("AddConfigItem", "item/save", null, null, true, p7);
			privilegeService.save(p8);
			p9=new Privilege("DeleteConfigItem", "item/delete", null, null, true, p7);
			privilegeService.save(p9);
			p10=new Privilege("UpdateConfigItem", "item/update", null, null, true, p7);
			privilegeService.save(p10);
			p11=new Privilege("ViewConfigItem", "item/list", null, null, true, p7);
			privilegeService.save(p11);
		//===============第六节点============
		p1=new Privilege("NEPlanningAndManager", null, null, null, false, null);
		privilegeService.save(p1);
		p2=new Privilege("ExecuteNEManager", "entity/listUI", null, null, false, p1);
		privilegeService.save(p2);
			p4=new Privilege("SendNE", "entity/save", null, null, true, p2);
			privilegeService.save(p4);
			p5=new Privilege("DeleteNE", "entity/delete", null, null, true, p2);
			privilegeService.save(p5);
//			p6=new Privilege("UpdateNEConfig", "entity/updateEntity", null, null, true, p2);
//			privilegeService.save(p6);
			p6=new Privilege("CdEntityLog", "entity/listLog", null, null, true, p2);
			privilegeService.save(p6);
			p7=new Privilege("UpdateNEBasicInformation", "entity/update", null, null, true, p2);
			privilegeService.save(p7);
			p8=new Privilege("StartNE", "entity/start", null, null, true, p2);
			privilegeService.save(p8);
			p9=new Privilege("StopNE", "entity/stop", null, null, true, p2);
			privilegeService.save(p9);
			p10=new Privilege("ReStartNE", "entity/restart", null, null, true, p2);
			privilegeService.save(p10);
			p11=new Privilege("StartupOnBoot", "entity/startup", null, null, true, p2);
			privilegeService.save(p11);
			p12=new Privilege("ShutdownOnBoot", "entity/shutdown", null, null, true, p2);
			privilegeService.save(p12);
		p3=new Privilege("NEParameterConfig", "common/page", null, null, false, p1);
		privilegeService.save(p3);
			p4=new Privilege("ViewNEConfigParameter", "common/component", null, null, true, p3);
			privilegeService.save(p4);
			p5=new Privilege("UpdateNEConfigParameter", "common/modify", null, null, true, p3);
			privilegeService.save(p5);
		
		//==============第七节点============
		p1=new Privilege("OneKeyImportAndExport", "onekey/view", null, null, false, null);
		privilegeService.save(p1);
		
		
		//==============第八节点============
		p1=new Privilege("AJuLinkStatus", null, null, null, false, null);
		privilegeService.save(p1);
			p2=new Privilege("PhysicsLink", "link/physics", null, null, false, p1);
			privilegeService.save(p2);
			p3=new Privilege("SignallingLink", "link/signalling", null, null, false, p1);
			privilegeService.save(p3);
		
		//==============第X节点============
		p1=new Privilege("LogAndAlarm", null, null, null, false, null);
		privilegeService.save(p1);
		p2=new Privilege("LogManager", "log/listUI", null, null, false, p1);
		privilegeService.save(p2);
			p4=new Privilege("DeleteLog","log/delete", null, null, true, p2);
			privilegeService.save(p4);
			p5=new Privilege("ViewLog","log/list", null, null, true, p2);
			privilegeService.save(p5);
		p3=new Privilege("AlarmManager", "alarm/listUI", null, null, false, p1);
		privilegeService.save(p3);
			p4=new Privilege("DeleteAlarm","alarm/delete", null, null, true, p3);
			privilegeService.save(p4);
			p5=new Privilege("ViewAlarm","alarm/list", null, null, true, p3);
			privilegeService.save(p5);
			
		
		//=============自检============
		p1=new Privilege("SelfCheck", null, null, null, false, null);
		privilegeService.save(p1);
			p2=new Privilege("StartupCheck", "selfCheck/startupCheck", null, null, true, p1);
			privilegeService.save(p2);
			p3=new Privilege("RtCheck", "selfCheck/rtCheck", null, null, true, p1);
			privilegeService.save(p3);
		
		//==============出局==================
		p1=new Privilege("Out", null, null, null, false, null);
		privilegeService.save(p1);
			p2=new Privilege("ISUP", "out/isup", null, null, true, p1);
			privilegeService.save(p2);
			p3=new Privilege("TUP", "out/tup", null, null, true, p1);
			privilegeService.save(p3);
			p4=new Privilege("ISUP2", "out/isup2", null, null, true, p1);
			privilegeService.save(p4);
			p5=new Privilege("TUP2", "out/tup2", null, null, true, p1);
			privilegeService.save(p5);
			p6=new Privilege("SIP", "out/sip", null, null, true, p1);
			privilegeService.save(p6);
			p7=new Privilege("FXO", "out/fxo", null, null, true, p1);
			privilegeService.save(p7);
		//==============射频参数==================	
		p1=new Privilege("RFParameterManager", null, null, null, false, null);
		privilegeService.save(p1);
			p2=new Privilege("RFParameterCheck", "rf/check", null, null, true, p1);
			privilegeService.save(p2);
			p3=new Privilege("RFParameterSet", "rf/setList", null, null, true, p1);
			privilegeService.save(p3);
		//==============交换参数==================
		p1=new Privilege("SwitchArgument", null, null, null, false, null);
		privilegeService.save(p1);
			p2=new Privilege("NumberTranslation", "switch/listUI", null, null, false, p1);
			privilegeService.save(p2);
				p3=new Privilege("AddSwitch", "switch/addSwitch", null, null, true, p2);
				privilegeService.save(p3);
				p4=new Privilege("DeleteSwitch", "switch/deleteSwitch", null, null, true, p2);
				privilegeService.save(p4);
				p5=new Privilege("SwitchViewData", "switch/switchViewData", null, null, true, p2);
				privilegeService.save(p5);
				p6=new Privilege("SwitchSave", "switch/switchSave", null, null, true, p2);
				privilegeService.save(p6);
		//===============第一节点============
		p1=new Privilege("OperatorsManager",null,null,null,false,null);
		privilegeService.save(p1);
		p2=new Privilege("UserManager","user/listUI",null,null,false,p1);
		privilegeService.save(p2);
			p4=new Privilege("AddUser","user/add", null, null, true, p2);
			privilegeService.save(p4);
			p5=new Privilege("DeleteUser","user/delete", null, null, true, p2);
			privilegeService.save(p5);
			p6=new Privilege("UpdateUser","user/update", null, null, true, p2);
			privilegeService.save(p6);
			p7=new Privilege("ViewUser","user/list", null, null, true, p2);
			privilegeService.save(p7);
		p3=new Privilege("RightsManager","role/listUI",null,null,false,p1);
		privilegeService.save(p3);
			p4=new Privilege("AddRights","role/add", null, null, true, p3);
			privilegeService.save(p4);
			p5=new Privilege("DeleteRights","role/delete", null, null, true, p3);
			privilegeService.save(p5);
			p6=new Privilege("UpdateRights","role/update", null, null, true, p3);
			privilegeService.save(p6);
			p7=new Privilege("ViewRights","role/list", null, null, true, p3);
			privilegeService.save(p7);
		//===============第六节点============
	}
	
	/**
	 * 初始化超级管理员
	 */
	protected void initUser(){
		User user = new User("nouser", "nochange");
		userService.save(user);
	}
	
	/**
	 * 初始化板卡
	 */
	protected void initCard(){
		Card card=new Card("主控板", 0, 1, "127.0.0.1");
		cardService.save(card);
	}
	
	/**
	 * 初始化模块
	 */
	protected void initModule(){
		Module hss=new Module(1, "hss", 1, "hss", "1", "./m_hss -x", "Log", null);
		moduleMapper.insert(hss);
		hss.setId(1);
		Entity hss1=new Entity("hss1", 1, 0, 0, "hss1", hss, null);
		entityMapper.insert(hss1);
	}
	
	/**
	 * 初始化配置信息
	 */
	protected void initConfig(){
		//=============SBC 开始================
		Config c=null;
		//----------系统配置-----------
		c=new Config("net", null, "网络配置", false, null);
		configMapper.insert(c);
		c=new Config("tls", null, "TLS参数配置", false, null);
		configMapper.insert(c);
		c=new Config("performance", null, "性能参数配置", false, null);
		configMapper.insert(c);
		c=new Config("service", null, "基本服务配置", false, null);
		configMapper.insert(c);
		c=new Config("runtime", null, "运行参数配置", false, null);
		configMapper.insert(c);
		c=new Config("board", null, "板卡规划配置", false, null);
		configMapper.insert(c);
		//----------CAC 策略配置-----------
		c=new Config("groupManage", null, "组配置", false, null);
		configMapper.insert(c);
		c=new Config("groupPolicy", null, "组策略配置", false, null);
		configMapper.insert(c);
		c=new Config("groupEnsure", null, "用户优先保障策略", false, null);
		configMapper.insert(c);
		//----------访问控制策略-------------
		c=new Config("acl", null, "ACLInfo", false, null);
		configMapper.insert(c);
		c=new Config("blacklist", null, "黑名单", false, null);
		configMapper.insert(c);
		c=new Config("spite", null, "恶意访问检测策略", false, null);
		configMapper.insert(c);
		c=new Config("business", null, "业务限制策略", false, null);
		configMapper.insert(c);
		//---------状态查询------------------
		c=new Config("media", null, "媒体链路信息", false, null);
		configMapper.insert(c);
		c=new Config("status", null, "用户注册状态", false, null);
		configMapper.insert(c);
		//---------系统状态------------------
		c=new Config("boardStatus", null, "板卡状态", false, null);
		configMapper.insert(c);
		c=new Config("alarm", null, "运行告警", false, null);
		configMapper.insert(c);
		c=new Config("statistics", null, "统计信息", false, null);
		configMapper.insert(c);
		//=============SBC 结束================
	}
	
	/**
	 * 对hss数据库添加一些初始值
	 */
	protected void initHssData(){
		hssMetaMapper.insertDeviceType(1, "SIP");
		hssMetaMapper.insertDeviceType(2, "CDMA");
		hssMetaMapper.insertDeviceType(3, "GSM");
		hssMetaMapper.insertDeviceType(4, "WCDMA");
		hssMetaMapper.insertDeviceType(5, "LTE/TD");
		hssMetaMapper.insertDeviceType(6, "YD");
		hssMetaMapper.insertDeviceType(7, "TR");
		hssMetaMapper.insertDeviceType(8, "DISP");
		hssMetaMapper.insertDeviceType(9, "TETRA");
		hssMetaMapper.insertDeviceType(10, "ANALOG");
		hssMetaMapper.insertDeviceType(11, "RADIO");
		hssMetaMapper.insertDeviceType(12, "IMS");
		hssMetaMapper.insertDeviceType(13, "LTE-CPE");
		
		hssMetaMapper.insertVoiceType(0 , "PCMU");
		hssMetaMapper.insertVoiceType(8 , "PCMA");
		hssMetaMapper.insertVoiceType(18, "G_729");
		hssMetaMapper.insertVoiceType(35, "EVRC");
		hssMetaMapper.insertVoiceType(38, "AHELP");
		hssMetaMapper.insertVoiceType(39, "ACELP");
		hssMetaMapper.insertVoiceType(40, "AMR");
		hssMetaMapper.insertVoiceType(42, "VC40");
		hssMetaMapper.insertVoiceType(43, "VC24");
		hssMetaMapper.insertVoiceType(44, "VC12");
		hssMetaMapper.insertVoiceType(45, "VC06");
		
		hssMetaMapper.insertBusiness("shortMsg", "MessageTraffic",1);					//短信业务
		hssMetaMapper.insertBusiness("internationality", "InternationalTraffic",1);		//国际业务
		hssMetaMapper.insertBusiness("nationality", "NationalTraffic",1);				//出入局属性
		hssMetaMapper.insertBusiness("callWaitting", "CallTraffic",1);					//呼叫等待
		hssMetaMapper.insertBusiness("threeWay", "Three-way-calling",1);				//三方通话
		hssMetaMapper.insertBusiness("callInLimit", "Incoming-Call-Barring",1);			//呼入限制
		hssMetaMapper.insertBusiness("callOutLimit", "Outgoing-Call-Barring",1);		//呼出限制
		hssMetaMapper.insertBusiness("pairNet", "Dual-Network-Business",1);				//双网业务
		hssMetaMapper.insertBusiness("secrecy", "Secrecy-Business",1);					//保密业务
		hssMetaMapper.insertBusiness("callerAllow", "callerAllow", 1);					//来电显示
		hssMetaMapper.insertBusiness("callerHide", "callerHide", 1);					//主叫号码隐藏
		hssMetaMapper.insertBusiness("groupCallBusiness", "groupCallBusiness", 1);		//组呼业务
		hssMetaMapper.insertBusiness("circuitData", "CircuitData", 1);					//电路数据
		hssMetaMapper.insertBusiness("halfDuplexSingleCall", "HalfDuplexSingleCall", 1);//半双工单呼
		hssMetaMapper.insertBusiness("duplexSingleCall", "HuplexSingleCall", 1);		//双工单呼
		
		
		hssMetaMapper.insertBusiness("ims", "IMSInformation",0);
		hssMetaMapper.insertBusiness("jtyw", "ListeningBusiness",0);
		hssMetaMapper.insertBusiness("fzye", "GroupingBusiness",0);
		hssMetaMapper.insertBusiness("ddyw", "ScheduingBusiness",0);
		hssMetaMapper.insertBusiness("bcyw", "SupplementaryServices",0);
		hssMetaMapper.insertBusiness("groupInfo", "GroupMessage",0);
		hssMetaMapper.insertBusiness("auc", "AUC",0);
		hssMetaMapper.insertBusiness("tft", "TFTSetting",0);
		hssMetaMapper.insertBusiness("zdxx", "TerminalInformation",0);
		hssMetaMapper.insertBusiness("epc", "EPCInfo",0);
		
		hssMetaMapper.insertViewItem(0, "IMSI");
		hssMetaMapper.insertViewItem(1, "MDN");
		hssMetaMapper.insertViewItem(2, "DeviceName");
		hssMetaMapper.insertViewItem(3, "SpeechCodec");
		hssMetaMapper.insertViewItem(4, "MscAddr");
		hssMetaMapper.insertViewItem(5, "GateWayAddr");
		hssMetaMapper.insertViewItem(6, "bmNum");
		hssMetaMapper.insertViewItem(7, "UeDestroy");
		hssMetaMapper.insertViewItem(8, "UeSwoon");
		hssMetaMapper.insertViewItem(9, "AirMonitor");
		hssMetaMapper.insertViewItem(10, "LastUpdateTime");
		hssMetaMapper.insertViewItem(11, "Update");
	}
	
	protected void initUserGroupPriority(){
		userGroupPriorityMapper.insertUserGroupPriority(1, 1, 1, 0);
		userGroupPriorityMapper.insertUserGroupPriority(2, 2, 1, 0);
		userGroupPriorityMapper.insertUserGroupPriority(3, 3, 1, 0);
		userGroupPriorityMapper.insertUserGroupPriority(4, 4, 1, 0);
		userGroupPriorityMapper.insertUserGroupPriority(5, 5, 1, 0);
		userGroupPriorityMapper.insertUserGroupPriority(6, 6, 1, 0);
		userGroupPriorityMapper.insertUserGroupPriority(7, 7, 1, 0);
		userGroupPriorityMapper.insertUserGroupPriority(8, 8, 1, 0);
		userGroupPriorityMapper.insertUserGroupPriority(9, 9, 1, 0);
		userGroupPriorityMapper.insertUserGroupPriority(10, 10, 1, 0);
		userGroupPriorityMapper.insertUserGroupPriority(11, 11, 1, 0);
		userGroupPriorityMapper.insertUserGroupPriority(12, 12, 1, 0);
		userGroupPriorityMapper.insertUserGroupPriority(13, 13, 1, 0);
		userGroupPriorityMapper.insertUserGroupPriority(14, 14, 1, 0);
		userGroupPriorityMapper.insertUserGroupPriority(15, 15, 1, 0);
	}
	
	protected void initTerminalType(){
		hssMetaMapper.insertTerminalType(0, "卫星普通型手持终端");
		hssMetaMapper.insertTerminalType(1, "卫星增强型手持终端");
		hssMetaMapper.insertTerminalType(3, "卫星便携终端");
		hssMetaMapper.insertTerminalType(5, "卫星车载终端");
	}
	
	/**
	 * 统一调用初始化接口
	 */
	public void install(){
		if(userService.getCount()>0) return ;
		initUser();
		initPrivilege();
		initHssData();
		//initCard();
		initTerminalType();
		initUserGroupPriority();
	}
}
