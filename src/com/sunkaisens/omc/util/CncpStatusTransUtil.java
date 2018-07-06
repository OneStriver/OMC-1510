package com.sunkaisens.omc.util;

import com.sunkaisens.omc.thread.cncpMsg.CncpProtoType;


public abstract class CncpStatusTransUtil{
	
	public static String transLocaleStatusMessage(int stat) {
		String str = null;
		switch(stat) {
			case CncpProtoType.SUCCESS:
				str = "操作成功";
				break;
			case CncpProtoType.FAILURE:
				str = "操作失败";
				break;
			case CncpProtoType.ERR_NE_EXIST:
				str = "已规划网元";
				break;
			case CncpProtoType.ERR_NE_NOTEXIST:
				str = "未规划网元";
				break;
			case CncpProtoType.ERR_NE_RUNNING:
				str = "网元运行中";
				break;
			case CncpProtoType.ERR_NE_STOPED:
				str = "网元已终止";
				break;
			case CncpProtoType.ERR_MESSAGE:
				str = "消息格式错误";
				break;
			case CncpProtoType.ERR_FTP_UPLOAD:
				str = "FTP上传失败";
				break;
			case CncpProtoType.ERR_FTP_DOWNLOAD:
				str = "FTP下载失败";
				break;
			case CncpProtoType.ERR_EXTRACT:
				str = "解压失败";
				break;
			case CncpProtoType.ERR_REMOVE:
				str = "删除失败";
				break;
			case CncpProtoType.ERR_HOST_LOST:
				str = "宿主丧失";
				break;
			case CncpProtoType.TIMEOUT:
				str = "OAM消息超时，请检查OAM";//消息超时
				break;
			case CncpProtoType.ERR_CHANNEL_BUSY:
				str = "消息拥挤";
				break;
			case CncpProtoType.ERR_NE_LOST:
				str = "网元丧失";
				break;
			case CncpProtoType.ERR_REPEAT_SETUP:
				str = "重复设置";
				break;
			case CncpProtoType.ERR_TOO_BIG:
				str = "数据过大";
				break;
			case CncpProtoType.ERR_NE_STARTUP:
				str = "网元启动失败";
				break;
			case CncpProtoType.ERR_ETH_NOOPT:
				str = "不允许的主网卡操作";
				break;
			default :
				str = "OAM未知错误";
				break;
		}
		return str;
	}
	
}
