package com.sunkaisens.omc.thread.cncpMsg;

/**
 * Cncp私有协议操作类型定义接口
 */
public interface CncpProtoType {
	//-- nms type --//
	char NMS 						= 0xE4;
	char SUB_OAM 					= 0xE5;
	char OAM_HEADER 				= 0x04;
	char NI_HEADER					= 0xE8;
	char OMC_HEADER					= 0xE0;
	
	//添加  删除板卡
	char OAM_SET_SLAVE_ADD            = 0x3D;
	char OAM_SET_SLAVE_RMV            = 0x3E;
	//错误码
	int OAM_ERRNO_SUCCESS   		= 0x00; // 操作成功
	int OAM_ERRNO_HOST_NEXIST   	= 0x14; // 未规划宿主
	int OAM_ERRNO_HOST_EXIST    	= 0x17; // 已规划宿主
	int OAM_ERRNO_HOST_IN_USE   	= 0x18; // 在用的宿主
	
	//1510-OMC发送给1510北向接口的消息类型
	int CNCP_OMC_NI_MSG				= 0x8010;
	int CNCP_OMC_NI_ACK_MSG			= 0x8011;
	//1510北向接口发送给1510-OMC的消息类型
	int CNCP_NI_OMC_MSG				= 0x8012;
	int	CNCP_NI_OMC_ACK_MSG			= 0x8013;
	
	//-- message type --//
	int CNCP_SET_MSG 			    = 0x7001;		
	int CNCP_SET_ACK_MSG 		    = 0x7002;		
	int CNCP_GET_MSG 			    = 0x7003;
	int CNCP_GET_ACK_MSG 		    = 0x7004;
	int CNCP_STATUS_MSG 		    = 0x7005;
	int CNCP_STATISTICS_MSG 	    = 0x7006;
	int CNCP_LINK_STATUS_MSG 	    = 0x7007;
	int CNCP_ALARM_MSG 			    = 0x7008;
	int CNCP_NOTIFY_MSG 		    = 0x700B;
	
	// sub type of CNCP_GET
	int CNCP_GET_NE_RT_CONF    		= 0x00;
	int CNCP_GET_NE_CONF        	= 0x01; // reserved
	int CNCP_GET_SYSTEM         	= 0x02;
	int CNCP_GET_NE_LOG         	= 0x03;
	int CNCP_GET_NE_LOGLIST     	= 0x04;
	int OAM_GET_IF   				= 0x0A;
	int OAM_GET_IF_CONF   			= 0x0B;
	
	// sub type of CNCP_SET
	int CNCP_SET_NE_RT_CONF_CREATE	= 0x00;
	int CNCP_SET_NE_RT_CONF_DELETE	= 0x01;
	int CNCP_SET_NE_RT_CONF_UPDATE	= 0x02;
	int CNCP_SET_NE_CONF_CREATE   	= 0x03; // reserved
	int CNCP_SET_NE_CONF_DELETE   	= 0x04; // reserved
	int CNCP_SET_NE_CONF_UPDATE   	= 0x05; // reserved
	int CNCP_SET_NE_PLAN_CREATE   	= 0x06;
	int CNCP_SET_NE_PLAN_DELETE   	= 0x07;
	int CNCP_SET_NE_PLAN_UPDATE   	= 0x08;
	int CNCP_SET_NE_LOG_DELETE    	= 0x09;
	int CNCP_SET_NE_FILE_DELETE   	= 0x0A;
	int CNCP_SET_NE_FILE_UPDATE  	= 0x0B;
	int CNCP_SET_NE_PROC_START   	= 0x0C;
	int CNCP_SET_NE_PROC_STOP     	= 0x0D;
	int CNCP_SET_NE_PROC_RESTART  	= 0x0E;
	int CNCP_SET_NE_BOOT_ACTIVE   	= 0x0F;
	int CNCP_SET_NE_BOOT_INACTIVE 	= 0x10;
	int CNCP_SET_OS_EXEC           	= 0x11;
	//网卡 路由  Host 等
	int OAM_GET_THRESHOLD        	= 0x08;
	int OAM_GET_NE_CONF         	= 0x09;
	int OAM_GET_TABLE_IF         	= 0x0A;
	int OAM_GET_TABLE_IF_CONF    	= 0x0B;
	int OAM_GET_SERVICE_STATUS   	= 0x0C;
	int OAM_GET_SERVICE_BOOT_STATUS = 0x0D;
	int OAM_GET_NAMESERVERS         = 0x0E;
	int OAM_GET_HOSTNAME            = 0x0F;
	int OAM_GET_TABLE_GLOBAL_ROUTE  = 0x10;
	int OAM_GET_TABLE_STATIC_ROUTE  = 0x11;
	int OAM_GET_TABLE_HOSTS         = 0x12;
	int OAM_GET_SELF_CHECKING		= 0x17;
	int OAM_SET_THRESHOLD         	= 0x16;
	int OAM_SET_NE_CONF           	= 0x17;
	int OAM_SET_IF_UP             	= 0x18;
	int OAM_SET_IF_DOWN           	= 0x19;
	int OAM_SET_OBSOLETE1A        	= 0x1A;
	int OAM_SET_IF_UPDATE         	= 0x1B;
	int OAM_SET_IF_CONF_CREATE    	= 0x1C;
	int OAM_SET_IF_CONF_DELETE    	= 0x1D;
	int OAM_SET_IF_CONF_UPDATE   	= 0x1E;
	int OAM_SET_SERVICE_START     	= 0x1F;
	int OAM_SET_SERVICE_STOP      	= 0x20;
	int OAM_SET_SERVICE_RESTART   	= 0x21;
	int OAM_SET_SERVICE_BOOT_ACTIVE = 0x22;
	int OAM_SET_SERVICE_BOOT_INACTIVE=0x23;
	int OAM_SET_NAMESERVERS         = 0x24;
	int OAM_SET_HOSTNAME            = 0x25;
	int OAM_SET_STATIC_ROUTE_CREATE = 0x26;
	int OAM_SET_STATIC_ROUTE_DELETE = 0x27;
	int OAM_SET_HOSTS_DELETE        = 0x28;
	int OAM_SET_HOSTS_UPDATE        = 0x29;
	int OAM_GET_HD_SERIAL_NO		= 0x18;
	int OAM_SET_RESET				= 0x36;
	int OAM_SET_SYS_REBOOT			= 0x37;
	int OAM_SET_HOST_SYNC			= 0x38;
	
	
	
	//HSS 消息
	int OAM_GET_UE					= 0X14;
	int OAM_SET_UE_DELETE			= 0X2A;
	int OAM_SET_UE_UPDATE			= 0X2B;
	int OAM_SET_UE_SYNC				= 0x30;
	int OAM_SET_HSS_CREATE			= 0X31;
	int OAM_SET_HSS_DELETE			= 0X32;
	int OAM_SET_HSS_UPDATE			= 0X33;
	
	int OAM_SET_UE_DESTROY			= 0X34;//遥毁
	int OAM_SET_UE_RESTORE			= 0X35;//遥毁恢复
	int OAM_SET_UE_SWOON			= 0X3A;//摇晕
	int OAM_SET_UE_NORMAL           = 0X3B;//摇晕恢复
	
	int OAM_GET_GROUP				= 0X16;
	int OAM_SET_GROUP_DELETE		= 0X2E;
	int OAM_SET_GROUP_UPDATE		= 0X2F;
//	int OAM_SET_MEMBER_CREATE		= 0;
//	int OAM_SET_MEMBER_DELETE		= 0;
//	int OAM_SET_MEMBER_UPDATE		= 0;
	
	// 动态库
	int OAM_SET_LIB_DELETE   		= 0x14;
	int OAM_SET_LIB_UPDATE  		= 0x15;
	
	//-- status type --//
	int CNCP_STATUS_ACTION_PERIODIC = 0x00;
	int CNCP_STATUS_ACTION_MAIN 	= 0x01;
	int CNCP_STATUS_ACTION_BACKUP   = 0x02;
	//-- status value --//
	int CNCP_STATUS_UP 				= 0x01;
	int CNCP_STATUS_DOWN			= 0x00;
	int OAM_NE_PING					= 0x05;
	
	//-- errors --//
	int CNCP_ERRNO_SUCCESS 			= 0X01;
	
	//-- response type --//
	int SUCCESS						= 0x00;	// 操作成功
	int FAILURE 					= 0x01;	// 操作失败
	int TIMEOUT 					= 0x0C; // 超时
	int ERR_NE_EXIST 				= 0x02;	// 已规划的网元
	int ERR_NE_NOTEXIST 			= 0X03;	// 未规划的网元
	int ERR_NE_RUNNING 				= 0x04;	// 网元已运行
	int ERR_NE_STOPED 				= 0X05;	// 网元已停止
	int ERR_MESSAGE 				= 0x06;	// 消息格式错误
	int ERR_FTP_UPLOAD 				= 0x07;	// FTP上传失败
	int ERR_FTP_DOWNLOAD 			= 0x08;	// FTP下载失败
	int ERR_EXTRACT 				= 0x09;	// 解压失败
	int ERR_REMOVE 					= 0x0A;	// 删除失败
	int ERR_HOST_LOST 				= 0x0B;	// 宿主丧失
	int ERR_CHANNEL_BUSY 			= 0x0D;	// 消息拥挤
	int ERR_NE_LOST 				= 0x0E;	// 网元丧失
	int ERR_REPEAT_SETUP 			= 0x0F;	// 重复设置
	int ERR_TOO_BIG 				= 0x10;	// 数据过大
	int ERR_NE_STARTUP				= 0x11;	// 网元启动失败
	int ERR_ETH_NOOPT				= 0x13;	// 不允许的网卡操作
	
	//-- SBC subtype(m_type)--------
	int OAM_SBC_GET					= 0x03;	//SBC查询消息
	int OAM_SBC_ADD					= 0x00;	//SBC增加消息
	int OAM_SBC_SET					= 0x02;	//SBC设置消息
	int OAM_SBC_DELETE				= 0x01;	//SBC删除消息
	//-- SBC packID(m-sub_type)-----------
	int OAM_SBC_NET					= 0x01;	//网络配置
	int OAM_SBC_RUNTIME				= 0x02;	//运行参数配置
	int OAM_SBC_PERFORMANCE			= 0x03;	//性能参数配置
	int OAM_SBC_SERVICE				= 0x04;	//基本服务配置
	int OAM_SBC_GROUPMANAGE			= 0x05;	//组管理配置
	int OAM_SBC_GROUPPOLICY			= 0x06;	//组策略配置
	int OAM_SBC_ACL					= 0x07; //ACL配置
	int OAM_SBC_BLACKLIST			= 0x08; //黑名单
	int OAM_SBC_SPITE				= 0x09; //恶意访问检测策略
	int OAM_SBC_BUSINESS			= 0x0A; //业务限制策略
	int OAM_SBC_MEDIA				= 0x0B; //媒体链路信息
	int OAM_SBC_STATUS				= 0x0C; //用户注册状态
	int OAM_SBC_TLS					= 0x02; //TLS
	int OAM_SBC_BOARD				= 0x13; //板卡规划配置
	int OAM_SBC_STARTUP				= 0x11; //开机启动
	int OAM_SBC_GROUPENSURE			= 0x10; //用户优先保障策略
	int OAM_SBC_STATISTICS			= 0x0f; //统计信息
	int OAM_SBC_ALARM				= 0x0e; //运行告警
	
	//-- alarm type definitions --//
	int OAM_ALARM_NE_START			= 0x06;
	int OAM_ALARM_NE_RESTART 		= 0x07;
	int OAM_ALARM_NE_STOP 			= 0x09;
	int OAM_ALARM_NE_LOSE 			= 0x0D;
	//-- alarm level definitions --//
	int OAM_ALARM_LEVEL_CRITICAL 	= 1;
	int OAM_ALARM_LEVEL_MAJOR 		= 2;
	int OAM_ALARM_LEVEL_MINOR 		= 3;
	int OAM_ALARM_LEVEL_WARNING 	= 4;
	int OAM_ALARM_LEVEL_INDETERMINATE=5;
	int OAM_ALARM_LEVEL_CLEARED 	= 6;
	
	int SBC_ALARM_BASE_ID = 					0x40;
	int SBC_ALARM_INVALID_PARAMETER = 			SBC_ALARM_BASE_ID;
	int SBC_ALARM_SYSINITIAL_FAILED = 			SBC_ALARM_BASE_ID + 1;
	int SBC_ALARM_ASPM_INITIAL_FAILED = 		SBC_ALARM_BASE_ID + 2;
	int SBC_ALARM_SCM_BOARD = 					SBC_ALARM_BASE_ID + 3;
	int SBC_ALARM_MEDIA_BOARD = 				SBC_ALARM_BASE_ID + 4;
	int SBC_ALARM_TLS_INITIAL = 				SBC_ALARM_BASE_ID + 5;
	int SBC_ALARM_PACKET_SEND = 				SBC_ALARM_BASE_ID + 6;
	int SBC_ALARM_OAM_DATACONFIGURATION = 		SBC_ALARM_BASE_ID + 7;
	int SBC_ALARM_CPUMEM_OVERLADED = 			SBC_ALARM_BASE_ID + 8;
	int SBC_ALARM_USER_REGNUM_OVERLADED = 		SBC_ALARM_BASE_ID + 9;
	int SBC_ALARM_INVITESESSION_OVERLADED = 	SBC_ALARM_BASE_ID + 10;
	int SBC_ALARM_GROUPSESSION_OVERLADED = 		SBC_ALARM_BASE_ID + 11;
	int SBC_ALARM_SIPMESSAGE_RETRANS = 			SBC_ALARM_BASE_ID + 12;
	int SBC_ALARM_UNEXPECTED_SIPMESSAGE = 		SBC_ALARM_BASE_ID + 13;
	int SBC_ALARM_INVALID_SIPMESSAGE = 			SBC_ALARM_BASE_ID + 14;
	int SBC_ALARM_NEW_BLKLIST = 				SBC_ALARM_BASE_ID + 15;
	int SBC_ALARM_SERVICE_FORBIDDEN = 			SBC_ALARM_BASE_ID + 16;
	int SBC_ALARM_MEDIALINK_CREATE = 			SBC_ALARM_BASE_ID + 17;
	int SBC_ALARM_DIALOG_TRANS_NOT_EXIST = 		SBC_ALARM_BASE_ID + 18;
	int SBC_ALARM_USER_NOT_REGISTERED = 		SBC_ALARM_BASE_ID + 19;
	int SBC_ALARM_MEMCACHED_ACCESS = 			SBC_ALARM_BASE_ID + 20;
	int SBC_ALARM_NO_SCM  = 					SBC_ALARM_BASE_ID + 21;
	int SBC_ALARM_NOSCHEDULED_CONTEXT = 		SBC_ALARM_BASE_ID + 22;
	int SBC_ALARM_LACKOF_NECESSFIELD = 			SBC_ALARM_BASE_ID + 23;
	int SBC_ALARM_GROUP_REGNUM_OVERLADED =  	SBC_ALARM_BASE_ID + 24;
	int SBC_ALARM_GROUP_BANDWIDTH_OVERLADED = 	SBC_ALARM_BASE_ID + 25;
	int SBC_ALARM_SYS_BANDWIDTH_OVERLADED = 	SBC_ALARM_BASE_ID + 26;
	int SBC_ALARM_REGRATE_LIMIT	 = 				SBC_ALARM_BASE_ID + 27;
	int SBC_ALARM_CALLRATE_LIMIT =				SBC_ALARM_BASE_ID + 28;
}
