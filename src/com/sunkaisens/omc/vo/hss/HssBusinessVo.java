package com.sunkaisens.omc.vo.hss;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 终端用户业务Bean 注: 目前没有计费方案业务实现
 */
public class HssBusinessVo {

	private static Logger logger = LoggerFactory.getLogger(HssBusinessVo.class);
	public static Map<Integer, String> DEVICE_MAP = new HashMap<>();
	public static Map<Integer, String> VOCODEC_MAP = new HashMap<>();
	static {
		DEVICE_MAP.put(0, "未知类型");
		DEVICE_MAP.put(1, "SIP");
		DEVICE_MAP.put(2, "CDMA");
		DEVICE_MAP.put(3, "GSM");
		DEVICE_MAP.put(4, "WCDMA");
		DEVICE_MAP.put(5, "LTE/TD");
		DEVICE_MAP.put(6, "YD");
		DEVICE_MAP.put(7, "TR");
		DEVICE_MAP.put(8, "DISP");
		DEVICE_MAP.put(9, "TETRA");
		DEVICE_MAP.put(10, "ANALOG");
		DEVICE_MAP.put(11, "RADIO");
		DEVICE_MAP.put(12, "IMS");
		DEVICE_MAP.put(13, "LTE-CPE");

		VOCODEC_MAP.put(0, "PCMU");
		VOCODEC_MAP.put(8, "PCMA");
		VOCODEC_MAP.put(18, "G_729");
		VOCODEC_MAP.put(35, "EVRC");
		VOCODEC_MAP.put(38, "AHELP");
		VOCODEC_MAP.put(39, "ACELP");
		VOCODEC_MAP.put(40, "AMR");
		VOCODEC_MAP.put(42, "VC40");
		VOCODEC_MAP.put(43, "VC24");
		VOCODEC_MAP.put(44, "VC12");
		VOCODEC_MAP.put(45, "VC06");
	}
	
	/**
	 * 基本业务
	 */
	/*IMSI*/
	private String imsi; 					
	/*MDN*/
	private String mdn; 					
	/*设备类型*/
	private Integer deviceType;
	/*设备类型名称*/
	private String deviceName = "";
	/*语音编码*/
	private Integer msvocodec; 	
	/*语音编码的名称*/
	private String vocodecName;
	/*电子序列号(ESN)*/
	private String esn = "";
	
	/*CS域的携带的TMSI*/
	private String tmsi;
	
	/*保存(基本业务)的数值*/
	private Integer msprofile = 270239232;
	/*保存(保密/监听/摇晕/摇毙/对空监听)的数值*/
	private Integer msprofile_extra = 0;
	
	/*MscAddr地址和GateWayAddr地址*/
	private String currloc = "@";
	/*当前用户的状态(在线和离线)*/
	private Integer status;
	/*CS域的携带的vlraddr*/
	private String vlraddr = "@";
	
	/**
	 MsProfile值中代表的业务
	*/
	// 优先级(前四位0,1,2,3)调度业务(优先级)
	private Integer priority = 0; 						
	// 双网(8)
	private Integer pairNet = 0; 
	// 电路数据(9)
	private Integer circuitData = 0; 		
	// 半双工单呼(10)
	private Integer halfDuplexSingleCall = 0; 
	// 双工单呼(11)
	private Integer duplexSingleCall = 0;
	// 国际业务或OSPF组播(12)
	private Integer internationality = 0; 
	//国内业务(1510中是:出入局属性)或OSPF广播(13)
	private Integer nationality = 0; 
	// 短信业务(14)
	private Integer shortMsg = 0; 
	// 不知道这一位表示什么业务(15)
	private Integer data = 0;
	// 组呼业务(16)
	private Integer groupCallBusiness = 0;
	// 无应答前转(17)
	private Integer fwdNoAnswer = 0;
	// 遇忙前转(18)
	private Integer fwdOnBusy = 0;
	// 无条件前转(19)
	private Integer directFwd = 0;
	// 分组业务中(动态分配,静态分配)(20)
	private Integer voiceMailSwitch = 0;
	// 三方通话(21)
	private Integer threeWay = 0;
	// 呼叫等待(22)
	private Integer callWaitting = 0; 
	// 不可达前转(23)
	private Integer fwdNA = 0;
	// 呼出限制(25)
	private Integer callOutLimit = 0; 
	// 呼入限制(26)
	private Integer callInLimit = 0; 
	// 主叫号码隐藏 0-关 1-开(30)
	private Integer callerHide = 0; 
	// 来电显示 0-关 1-开 (前台，后台已反转)(31)
	private Integer callerAllow = 0; 
	
	/**
	  MsProfile_Extra值代表的业务
	*/
	// 摇晕
	private Integer swoon = 0; 
	// 摇毙
	private Integer destroy = 0; 
	// 对空监听
	private Integer airMonitor = 0; 
	// 保密业务
	private Integer secrecy = 0; 
	
	/**
	 * 监听业务
	 */
	// 控制开关
	private Integer monitorSwitch = 0;
	// 监听业务Ip地址
	private String monitorIP = ""; 
	// 监听业务Port
	private String monitorPort = "";
	
	/**
	 * IMS信息
	 */
	private String domain;
	private String uePassword;
	
	/**
	 * 分组业务
	 */
	/*是否有PS域*/
	private Integer region = 0;
	// PS域静态IP
	private String voiceMailNum = ""; 
	// OSPF组播开关
	private Integer ospfMulticast = 0;
	// OSPF广播开关
	private Integer ospfBroadcast = 0; 
	
	/**
	 * 补充业务
	 */
	private String fwdNoAnswerNum = "";
	private String fwdOnBusyNum = "";
	private String directFwdNum = "";
	private String fwdNANum = "";
	private String wireTapAddr = "";

	/**
	 * GroupInfo信息
	 */
	private String[] groups;
	private String group1;
	private String group2;
	private String group3;
	private String group4;
	private String group5;
	private String group6;
	private String group7;
	private String group8;
	private String group9;
	private String group10;
	private String group11;
	private String group12;
	private String group13;
	private String group14;
	private String group15;
	private String group16;
	
	/**
	 * 鉴权参数
	 */
	//AMF
	private String amf = "72 4C";
	private String amf1 = "72";
	private String amf2 = "4C";
	//SQN
	private String sqn = "00 00 00 00 00 00";
	private String sqn1 = "00";
	private String sqn2 = "00";
	private String sqn3 = "00";
	private String sqn4 = "00";
	private String sqn5 = "00";
	private String sqn6 = "00";
	//K
	private String k = "FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF";
	private String k1 = "FF";
	private String k2 = "FF";
	private String k3 = "FF";
	private String k4 = "FF";
	private String k5 = "FF";
	private String k6 = "FF";
	private String k7 = "FF";
	private String k8 = "FF";
	private String k9 = "FF";
	private String k10 = "FF";
	private String k11 = "FF";
	private String k12 = "FF";
	private String k13 = "FF";
	private String k14 = "FF";
	private String k15 = "FF";
	private String k16 = "FF";
	//OPC
	private String opc = "FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF";
	private String opc1 = "FF";
	private String opc2 = "FF";
	private String opc3 = "FF";
	private String opc4 = "FF";
	private String opc5 = "FF";
	private String opc6 = "FF";
	private String opc7 = "FF";
	private String opc8 = "FF";
	private String opc9 = "FF";
	private String opc10 = "FF";
	private String opc11 = "FF";
	private String opc12 = "FF";
	private String opc13 = "FF";
	private String opc14 = "FF";
	private String opc15 = "FF";
	private String opc16 = "FF";
	//OP
	private String op = "00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00";
	private String op1 = "00";
	private String op2 = "00";
	private String op3 = "00";
	private String op4 = "00";
	private String op5 = "00";
	private String op6 = "00";
	private String op7 = "00";
	private String op8 = "00";
	private String op9 = "00";
	private String op10 = "00";
	private String op11 = "00";
	private String op12 = "00";
	private String op13 = "00";
	private String op14 = "00";
	private String op15 = "00";
	private String op16 = "00";
	//参数时间
	private String rand = "00 00 00 00 00 00";
	private Time start = Time.valueOf("00:00:00");
	private Time stop = Time.valueOf("23:59:59");

	/**
	 * TFT设置
	 */
	private String srcIp = "0.0.0.0";
	private String dstIp = "0.0.0.0";
	private Integer srcPortStart = 0;
	private Integer srcPortEnd = 0;
	private Integer dstPortStart = 0;
	private Integer dstPortEnd = 0;
	private Integer diffServStart = 0;
	private Integer diffServEnd = 0;
	private Integer pktLenMin = 0;
	private Integer pktLenMax = 0;

	/**
	 * 终端信息
	 */
	private String terminalId;
	private Integer terminalType = 1;
	private Integer gwId = 2;
	private Integer powerLevel = 1;
	private String powerLevelText;
	private String department;
	private Integer userType;
	private String username;
	private String userInfo;
	// 服务类型
	private Integer servicePriority = 5;
	//默认是MsProfile
	private Integer suportBuss;
	private Date _currTime = new Date();
	private Timestamp createTime = new Timestamp(_currTime.getTime());
	private Timestamp tstamp = new Timestamp(_currTime.getTime());

	/**
	 * EPC分组数据
	 */
	private String apn;
	private Integer erabId;
	private Integer qci;
	private Integer arPriority;
	private Integer aggregateMaxULBitRate;
	private Integer aggregateMaxDLBitRate;
	private Integer guaranteedULBitRate;
	private Integer guaranteedDLBitRate;
	private Integer maxULBitRate;
	private Integer maxDLBitRate;

	public HssBusinessVo() {
		
	}
	
	public Integer encodeMsProfileCtx(int flag) {
		int[] msprofile = new int[32];
		Arrays.fill(msprofile, 0);
		// 优先级转换
		String p = Integer.toBinaryString(priority);
		int p_len = p.length();
		for (int i = 0; i < (4 - p_len); i++) {
			p = "0" + p;
		}

		byte[] b = p.getBytes();
		msprofile[0] = b[0] - 48;
		msprofile[1] = b[1] - 48;
		msprofile[2] = b[2] - 48;
		msprofile[3] = b[3] - 48;

		msprofile[8] = getPairNet();
		msprofile[9] = getCircuitData();
		msprofile[10] = getHalfDuplexSingleCall();
		msprofile[11] = getDuplexSingleCall();
		if (flag == 1) {
			msprofile[12] = getInternationality();
			msprofile[13] = getNationality();
		} else {
			msprofile[12] = getOspfMulticast();
			msprofile[13] = getOspfBroadcast();
		}
		msprofile[14] = getShortMsg();
		msprofile[15] = getData();
		msprofile[16] = getGroupCallBusiness();
		msprofile[17] = getFwdNoAnswer();
		msprofile[18] = getFwdOnBusy();
		msprofile[19] = getDirectFwd();
		msprofile[20] = getVoiceMailSwitch();
		msprofile[21] = getThreeWay();
		msprofile[22] = getCallWaitting();
		msprofile[23] = getFwdNA();
		msprofile[25] = getCallOutLimit();
		msprofile[26] = getCallInLimit();
		msprofile[30] = getCallerHide();
		if (getCallerAllow() == 0) {
			msprofile[31] = 1;
		} else {
			msprofile[31] = 0;
		}
		return countMsProfile(msprofile);
	}

	public static int countMsProfile(int[] a) {
		String result = "";
		for (int i : a) {
			result += i;
		}
		return Integer.parseUnsignedInt(result, 2);
	}
	
	public void decodeMsprofileCtx(int flag) {
		priority = ((msprofile & 0xF0000000)) >>> 28;
		pairNet = ((msprofile << 8) >>> 31);
		circuitData = (msprofile << 9) >>> 31;
		halfDuplexSingleCall = (msprofile << 10) >>> 31;
		duplexSingleCall = (msprofile << 11) >>> 31;
		if (flag == 1) {
			internationality = ((msprofile & 0x00080000) << 12) >>> 31;
			nationality = ((msprofile & 0x00040000) << 13) >>> 31;
		} else {
			ospfMulticast = ((msprofile & 0x00080000) << 12) >>> 31;
			ospfBroadcast = ((msprofile & 0x00040000) << 13) >>> 31;
		}
		shortMsg = ((msprofile & 0x00020000) << 14) >>> 31;
		data = ((msprofile & 0x00010000) << 15) >>> 31;
		groupCallBusiness = ((msprofile & 0x00008000) << 16) >>> 31;
		fwdNoAnswer = ((msprofile & 0x00004000) << 17) >>> 31;
		fwdOnBusy = ((msprofile & 0x00002000) << 18) >>> 31;
		directFwd = ((msprofile & 0x00001000) << 19) >>> 31;
		voiceMailSwitch = ((msprofile & 0x00000800) << 20) >>> 31;
		threeWay = ((msprofile & 0x00000400) << 21) >>> 31;
		callWaitting = ((msprofile & 0x00000200) << 22) >>> 31;
		fwdNA = ((msprofile & 0x00000100) << 23) >>> 31;
		callOutLimit = ((msprofile & 0x00000040) << 25) >>> 31;
		callInLimit = ((msprofile & 0x00000020) << 26) >>> 31;
		callerHide = ((msprofile & 0x00000002) << 30) >>> 31;
		callerAllow = ((msprofile & 0x00000001));
		if (callerAllow == 1) {
			callerAllow = 0;
		} else {
			callerAllow = 1;
		}
	}

	public Integer getDirectFwd() {
		return directFwd;
	}

	public void setDirectFwd(Integer directFwd) {
		this.directFwd = directFwd;
	}

	public Integer getFwdNoAnswer() {
		return fwdNoAnswer;
	}

	public void setFwdNoAnswer(Integer fwdNoAnswer) {
		this.fwdNoAnswer = fwdNoAnswer;
	}

	public Integer getFwdOnBusy() {
		return fwdOnBusy;
	}

	public void setFwdOnBusy(Integer fwdOnBusy) {
		this.fwdOnBusy = fwdOnBusy;
	}

	public Integer getFwdNA() {
		return fwdNA;
	}

	public void setFwdNA(Integer fwdNA) {
		this.fwdNA = fwdNA;
	}

	public Integer getVoiceMailSwitch() {
		return voiceMailSwitch;
	}

	public void setVoiceMailSwitch(Integer voiceMailSwitch) {
		this.voiceMailSwitch = voiceMailSwitch;
	}

	public String getVoiceMailNum() {
		return voiceMailNum;
	}

	public void setVoiceMailNum(String voiceMailNum) {
		this.voiceMailNum = voiceMailNum;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getMdn() {
		return mdn;
	}

	public void setMdn(String mdn) {
		this.mdn = mdn;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	// TODO 国际业务和国内业务使用
	public Integer getMsprofile() {
		msprofile = encodeMsProfileCtx(1);
		return msprofile;
	}

	// TODO 组播和广播使用
	public Integer getMsprofileOspf() {
		msprofile = encodeMsProfileCtx(2);
		return msprofile;
	}

	// TODO 国际业务和国内业务使用
	public void setMsprofile(Integer msprofile) {
		this.msprofile = msprofile;
		decodeMsprofileCtx(1);
	}

	// TODO 组播和广播使用
	public void setMsprofile1(Integer msprofile) {
		this.msprofile = msprofile;
		decodeMsprofileCtx(2);
	}

	public String getEsn() {
		return esn;
	}

	public void setEsn(String esn) {
		this.esn = esn.toUpperCase();
	}

	public String getTmsi() {
		return tmsi;
	}

	public void setTmsi(String tmsi) {
		this.tmsi = tmsi;
	}

	// 编码Msprofile_extra
	public Integer encodeMsProfileExtraCtx(int operationFlag) {
		int[] msprofile_extra = new int[32];
		Arrays.fill(msprofile_extra, 0);
		// 对空监听开启
		int airMonitor = (operationFlag == 0) ? getAirMonitor(0) : getAirMonitor(1);
		int airMonitor_tmp = 0;
		int airMonitor_nack = 0;
		switch (airMonitor) {
		case 1:
			airMonitor_tmp = 0;
			airMonitor_nack = 0;
			break;// 恢复对空监听开启已确认（正常）
		case 2:
			airMonitor_tmp = 0;
			airMonitor_nack = 1;
			break;// 对空监听开启已确认
		case 3:
			airMonitor_tmp = 1;
			airMonitor_nack = 0;
			break;// 恢复对空监听开启未确认
		case 4:
			airMonitor_tmp = 1;
			airMonitor_nack = 1;
			break;// 对空监听开启未确认
		}
		msprofile_extra[22] = airMonitor_tmp;
		msprofile_extra[21] = airMonitor_nack;

		int swoon = (operationFlag == 0) ? getSwoon(0) : getSwoon(1);
		int swoon_tmp = 0;
		int swoon_nack = 0;
		switch (swoon) {
		case 1:
			swoon_tmp = 0;
			swoon_nack = 0;
			break;// 恢复已确认（正常）
		case 2:
			swoon_tmp = 0;
			swoon_nack = 1;
			break;// 摇晕已确认
		case 3:
			swoon_tmp = 1;
			swoon_nack = 0;
			break;// 恢复未确认
		case 4:
			swoon_tmp = 1;
			swoon_nack = 1;
			break;// 摇晕未确认
		}
		msprofile_extra[26] = swoon_tmp;
		msprofile_extra[25] = swoon_nack;
		int destroy = (operationFlag == 0) ? getDestroy(0) : getDestroy(1);
		;
		int destroy_tmp = 0;
		int destroy_nack = 0;
		switch (destroy) {
		case 1:
			destroy_tmp = 0;
			destroy_nack = 0;
			break;// 恢复已确认（正常）
		case 2:
			destroy_tmp = 0;
			destroy_nack = 1;
			break;// 摇毙已确认
		case 3:
			destroy_tmp = 1;
			destroy_nack = 0;
			break;// 恢复未确认
		case 4:
			destroy_tmp = 1;
			destroy_nack = 1;
			break;// 遥毙未确认
		}
		msprofile_extra[28] = destroy_tmp;
		msprofile_extra[27] = destroy_nack;

		msprofile_extra[30] = getMonitorSwitch();
		msprofile_extra[31] = getSecrecy();
		return countMsProfileExtra(msprofile_extra);
	}

	public static int countMsProfileExtra(int[] msprofile_extra) {
		String result = "";
		for (int i : msprofile_extra) {
			result += i;
		}
		System.err.println("》》》》》》》》》》》》》》测试result:"+result);
		return Integer.parseUnsignedInt(result, 2);
	}

	public Integer getMsprofile_extra() {
		msprofile_extra = encodeMsProfileExtraCtx(1);
		return msprofile_extra;
	}

	public void setMsprofile_extra(Integer msprofilextra) {
		this.msprofile_extra = msprofilextra;
	}
	
	public static void main(String[] args) {
		int t1 = 19;
		int airMonitor_tmp = ((t1 & 0x00000200) << 22) >>> 31;
		int airMonitor_nack = ((t1 & 0x00000400) << 21) >>> 31;
		int swoon_tmp = ((t1 & 0x00000020) << 26) >>> 31;
		int swoon_nack = ((t1 & 0x00000040) << 25) >>> 31;
		int destroy_tmp = ((t1 & 0x00000008) << 28) >>> 31;
		int destroy_nack = ((t1 & 0x00000010) << 27) >>> 31;
		int monitorSwitch = ((t1 & 0x00000002) << 30) >>> 31;
		int secrecy = ((t1 & 0x00000001) << 31) >>> 31;
		System.err.println("==================");
	}

	// 解码Msprofile_extra
	public void decodeMsprofileExtraCtx() {
		int airMonitor_tmp = ((msprofile_extra & 0x00000200) << 22) >>> 31;
		int airMonitor_nack = ((msprofile_extra & 0x00000400) << 21) >>> 31;
		if (airMonitor_tmp == 0 && airMonitor_nack == 0) {
			airMonitor = 1;
		} else if (airMonitor_tmp == 0 && airMonitor_nack == 1) {
			airMonitor = 2;
		} else if (airMonitor_tmp == 1 && airMonitor_nack == 0) {
			airMonitor = 3;
		} else if (airMonitor_tmp == 1 && airMonitor_nack == 1) {
			airMonitor = 4;
		}
		int swoon_tmp = ((msprofile_extra & 0x00000020) << 26) >>> 31;
		int swoon_nack = ((msprofile_extra & 0x00000040) << 25) >>> 31;
		if (swoon_tmp == 0 && swoon_nack == 0) {
			swoon = 1;
		} else if (swoon_tmp == 0 && swoon_nack == 1) {
			swoon = 2;
		} else if (swoon_tmp == 1 && swoon_nack == 0) {
			swoon = 3;
		} else if (swoon_tmp == 1 && swoon_nack == 1) {
			swoon = 4;
		}
		int destroy_tmp = ((msprofile_extra & 0x00000008) << 28) >>> 31;
		int destroy_nack = ((msprofile_extra & 0x00000010) << 27) >>> 31;
		if (destroy_tmp == 0 && destroy_nack == 0) {
			destroy = 1;
		} else if (destroy_tmp == 0 && destroy_nack == 1) {
			destroy = 2;
		} else if (destroy_tmp == 1 && destroy_nack == 0) {
			destroy = 3;
		} else if (destroy_tmp == 1 && destroy_nack == 1) {
			destroy = 4;
		}
		monitorSwitch = ((msprofile_extra & 0x00000002) << 30) >>> 31;
		secrecy = ((msprofile_extra & 0x00000001) << 31) >>> 31;
	}

	// 遥晕
	public Integer getSwoon(int flag) {
		if (flag == 1) {
			return swoon;
		}
		switch (msprofile_extra & 0x60) {
		case 0x00:
			swoon = 1;
			break;// 恢复摇晕已确认（正常）
		case 0x40:
			swoon = 2;
			break;// 摇晕已确认
		case 0x20:
			swoon = 3;
			break;// 恢复摇晕未确认
		case 0x60:
			swoon = 4;
			break;// 摇晕未确认
		default:
			swoon = 0;
		}
		return swoon;
	}

	public Integer getSwoon() {
		return swoon;
	}

	public void setSwoon(Integer swoon) {
		this.swoon = swoon;
	}

	// 遥毙
	public Integer getDestroy(int flag) {
		if (flag == 1) {
			return destroy;
		}
		switch (msprofile_extra & 0x18) {
		case 0x00:
			destroy = 1;
			break;// 恢复摇毙已确认（正常）
		case 0x10:
			destroy = 2;
			break;// 摇毙已确认
		case 0x08:
			destroy = 3;
			break;// 恢复摇毙未确认
		case 0x18:
			destroy = 4;
			break;// 遥毙未确认
		default:
			destroy = 0;
		}
		return destroy;
	}

	public Integer getDestroy() {
		return destroy;
	}

	// 对空监听
	public Integer getAirMonitor(int flag) {
		if (flag == 1) {
			return airMonitor;
		}
		switch (msprofile_extra & 0x600) {
		case 0x000:
			airMonitor = 1;
			break;// 恢复对空监听开启已确认（正常）
		case 0x400:
			airMonitor = 2;
			break;// 对空监听开启已确认
		case 0x200:
			airMonitor = 3;
			break;// 恢复对空监听开启未确认
		case 0x600:
			airMonitor = 4;
			break;// 对空监听开启未确认
		default:
			airMonitor = 0;
		}
		return airMonitor;
	}

	public Integer getAirMonitor() {
		return airMonitor;
	}

	public void setAirMonitor(Integer airMonitor) {
		this.airMonitor = airMonitor;
	}

	public void setDestroy(Integer destroy) {
		this.destroy = destroy;
	}

	public Integer getSecrecy() {
		return secrecy;
	}

	public void setSecrecy(Integer secrecy) {
		this.secrecy = secrecy;
	}

	public Integer getMonitorSwitch() {
		return monitorSwitch;
	}

	public void setMonitorSwitch(Integer monitorSwitch) {
		this.monitorSwitch = monitorSwitch;
	}

	public Integer getRegion() {
		return region;
	}

	public void setRegion(Integer region) {
		this.region = region;
	}

	public Integer getMsvocodec() {
		return msvocodec;
	}

	public void setMsvocodec(Integer msvocodec) {
		this.msvocodec = msvocodec;
	}

	public String getCurrloc() {
		return currloc;
	}

	public void setCurrloc(String currloc) {
		this.currloc = currloc;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getVlraddr() {
		return vlraddr;
	}

	public void setVlraddr(String vlraddr) {
		this.vlraddr = vlraddr;
	}

	public String getAmf1() {
		return amf1;
	}

	public void setAmf1(String amf1) {
		this.amf1 = amf1;
	}

	public String getAmf2() {
		return amf2;
	}

	public void setAmf2(String amf2) {
		this.amf2 = amf2;
	}

	public String getK1() {
		return k1;
	}

	public void setK1(String k1) {
		this.k1 = k1;
	}

	public String getK2() {
		return k2;
	}

	public void setK2(String k2) {
		this.k2 = k2;
	}

	public String getK3() {
		return k3;
	}

	public void setK3(String k3) {
		this.k3 = k3;
	}

	public String getK4() {
		return k4;
	}

	public void setK4(String k4) {
		this.k4 = k4;
	}

	public String getK5() {
		return k5;
	}

	public void setK5(String k5) {
		this.k5 = k5;
	}

	public String getK6() {
		return k6;
	}

	public void setK6(String k6) {
		this.k6 = k6;
	}

	public String getK7() {
		return k7;
	}

	public void setK7(String k7) {
		this.k7 = k7;
	}

	public String getK8() {
		return k8;
	}

	public void setK8(String k8) {
		this.k8 = k8;
	}

	public String getK9() {
		return k9;
	}

	public void setK9(String k9) {
		this.k9 = k9;
	}

	public String getK10() {
		return k10;
	}

	public void setK10(String k10) {
		this.k10 = k10;
	}

	public String getK11() {
		return k11;
	}

	public void setK11(String k11) {
		this.k11 = k11;
	}

	public String getK12() {
		return k12;
	}

	public void setK12(String k12) {
		this.k12 = k12;
	}

	public String getK13() {
		return k13;
	}

	public void setK13(String k13) {
		this.k13 = k13;
	}

	public String getK14() {
		return k14;
	}

	public void setK14(String k14) {
		this.k14 = k14;
	}

	public String getK15() {
		return k15;
	}

	public void setK15(String k15) {
		this.k15 = k15;
	}

	public String getK16() {
		return k16;
	}

	public void setK16(String k16) {
		this.k16 = k16;
	}

	public String getOpc1() {
		return opc1;
	}

	public void setOpc1(String opc1) {
		this.opc1 = opc1;
	}

	public String getOpc2() {
		return opc2;
	}

	public void setOpc2(String opc2) {
		this.opc2 = opc2;
	}

	public String getOpc3() {
		return opc3;
	}

	public void setOpc3(String opc3) {
		this.opc3 = opc3;
	}

	public String getOpc4() {
		return opc4;
	}

	public void setOpc4(String opc4) {
		this.opc4 = opc4;
	}

	public String getOpc5() {
		return opc5;
	}

	public void setOpc5(String opc5) {
		this.opc5 = opc5;
	}

	public String getOpc6() {
		return opc6;
	}

	public void setOpc6(String opc6) {
		this.opc6 = opc6;
	}

	public String getOpc7() {
		return opc7;
	}

	public void setOpc7(String opc7) {
		this.opc7 = opc7;
	}

	public String getOpc8() {
		return opc8;
	}

	public void setOpc8(String opc8) {
		this.opc8 = opc8;
	}

	public String getOpc9() {
		return opc9;
	}

	public void setOpc9(String opc9) {
		this.opc9 = opc9;
	}

	public String getOpc10() {
		return opc10;
	}

	public void setOpc10(String opc10) {
		this.opc10 = opc10;
	}

	public String getOpc11() {
		return opc11;
	}

	public void setOpc11(String opc11) {
		this.opc11 = opc11;
	}

	public String getOpc12() {
		return opc12;
	}

	public void setOpc12(String opc12) {
		this.opc12 = opc12;
	}

	public String getOpc13() {
		return opc13;
	}

	public void setOpc13(String opc13) {
		this.opc13 = opc13;
	}

	public String getOpc14() {
		return opc14;
	}

	public void setOpc14(String opc14) {
		this.opc14 = opc14;
	}

	public String getOpc15() {
		return opc15;
	}

	public void setOpc15(String opc15) {
		this.opc15 = opc15;
	}

	public String getOpc16() {
		return opc16;
	}

	public void setOpc16(String opc16) {
		this.opc16 = opc16;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
		String[] hexArr = toHexArray(op.trim(), 16);
		if (hexArr != null) {
			this.setOp1(hexArr[0]);
			this.setOp2(hexArr[1]);
			this.setOp3(hexArr[2]);
			this.setOp4(hexArr[3]);
			this.setOp5(hexArr[4]);
			this.setOp6(hexArr[5]);
			this.setOp7(hexArr[6]);
			this.setOp8(hexArr[7]);
			this.setOp9(hexArr[8]);
			this.setOp10(hexArr[9]);
			this.setOp11(hexArr[10]);
			this.setOp12(hexArr[11]);
			this.setOp13(hexArr[12]);
			this.setOp14(hexArr[13]);
			this.setOp15(hexArr[14]);
			this.setOp16(hexArr[15]);
		}
	}

	public String getRand() {
		return rand;
	}

	public void setRand(String rand) {
		this.rand = rand;
	}

	public String getSqn1() {
		if (sqn != null && sqn.length() > 0)
			return sqn.split(" ")[0];
		else
			return sqn1;
	}

	public void setSqn1(String sqn1) {
		this.sqn1 = sqn1;
	}

	public String getSqn2() {
		if (sqn != null && sqn.length() > 0)
			return sqn.split(" ")[1];
		else
			return sqn2;
	}

	public void setSqn2(String sqn2) {
		this.sqn2 = sqn2;
	}

	public String getSqn3() {
		if (sqn != null && sqn.length() > 0)
			return sqn.split(" ")[2];
		else
			return sqn3;
	}

	public void setSqn3(String sqn3) {
		this.sqn3 = sqn3;
	}

	public String getSqn4() {
		if (sqn != null && sqn.length() > 0)
			return sqn.split(" ")[3];
		else
			return sqn4;
	}

	public void setSqn4(String sqn4) {
		this.sqn4 = sqn4;
	}

	public String getSqn5() {
		if (sqn != null && sqn.length() > 0)
			return sqn.split(" ")[4];
		else
			return sqn5;
	}

	public void setSqn5(String sqn5) {
		this.sqn5 = sqn5;
	}

	public String getSqn6() {
		if (sqn != null && sqn.length() > 0)
			return sqn.split(" ")[5];
		else
			return sqn6;
	}

	public void setSqn6(String sqn6) {
		this.sqn6 = sqn6;
	}

	public String getDirectFwdNum() {
		return directFwdNum;
	}

	public void setDirectFwdNum(String directFwdNum) {
		this.directFwdNum = directFwdNum;
	}

	public String getFwdOnBusyNum() {
		return fwdOnBusyNum;
	}

	public void setFwdOnBusyNum(String fwdOnBusyNum) {
		this.fwdOnBusyNum = fwdOnBusyNum;
	}

	public String getFwdNoAnswerNum() {
		return fwdNoAnswerNum;
	}

	public void setFwdNoAnswerNum(String fwdNoAnswerNum) {
		this.fwdNoAnswerNum = fwdNoAnswerNum;
	}

	public String getFwdNANum() {
		return fwdNANum;
	}

	public void setFwdNANum(String fwdNANum) {
		this.fwdNANum = fwdNANum;
	}

	public String getWireTapAddr() {
		wireTapAddr = (getMonitorSwitch() == 1) ? getMonitorIP() + ":" + getMonitorPort() : "";
		return wireTapAddr;
	}

	public void setWireTapAddr(String wireTapAddr) {
		this.wireTapAddr = wireTapAddr;
		if (wireTapAddr.indexOf(":") != -1) {
			String[] ip_port = wireTapAddr.split(":", -2);
			setMonitorIP(ip_port[0]);
			setMonitorPort(ip_port[1]);
		}
	}

	public Timestamp getTstamp() {
		return tstamp;
	}

	public void setTstamp(Timestamp tstamp) {
		this.tstamp = tstamp;
	}

	public Integer getPriority() {
		return ((getMsprofile() & 0xF0000000)) >>> 28;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getCircuitData() {
		return circuitData;
	}

	public void setCircuitData(Integer circuitData) {
		this.circuitData = circuitData;
	}

	public Integer getHalfDuplexSingleCall() {
		return halfDuplexSingleCall;
	}

	public void setHalfDuplexSingleCall(Integer halfDuplexSingleCall) {
		this.halfDuplexSingleCall = halfDuplexSingleCall;
	}

	public Integer getDuplexSingleCall() {
		return duplexSingleCall;
	}

	public void setDuplexSingleCall(Integer duplexSingleCall) {
		this.duplexSingleCall = duplexSingleCall;
	}

	public Integer getInternationality() {
		return internationality;
	}

	public void setInternationality(Integer internationality) {
		this.internationality = internationality;
	}

	public Integer getNationality() {
		return nationality;
	}

	public void setNationality(Integer nationality) {
		this.nationality = nationality;
	}

	public Integer getShortMsg() {
		return shortMsg;
	}

	public void setShortMsg(Integer shortMsg) {
		this.shortMsg = shortMsg;
	}

	public Integer getData() {
		return data;
	}

	public void setData(Integer data) {
		this.data = data;
	}

	public Integer getGroupCallBusiness() {
		return groupCallBusiness;
	}

	public void setGroupCallBusiness(Integer groupCallBusiness) {
		this.groupCallBusiness = groupCallBusiness;
	}

	public Integer getThreeWay() {
		return threeWay;
	}

	public void setThreeWay(Integer threeWay) {
		this.threeWay = threeWay;
	}

	public Integer getCallWaitting() {
		return callWaitting;
	}

	public void setCallWaitting(Integer callWaitting) {
		this.callWaitting = callWaitting;
	}

	public Integer getCallOutLimit() {
		return callOutLimit;
	}

	public void setCallOutLimit(Integer callOutLimit) {
		this.callOutLimit = callOutLimit;
	}

	public Integer getCallInLimit() {
		return callInLimit;
	}

	public void setCallInLimit(Integer callInLimit) {
		this.callInLimit = callInLimit;
	}

	public Integer getPairNet() {
		return pairNet;
	}

	public void setPairNet(Integer pairNet) {
		this.pairNet = pairNet;
	}

	public String getMonitorIP() {
		return monitorIP;
	}

	public void setMonitorIP(String monitorIP) {
		this.monitorIP = monitorIP;
	}

	public String getMonitorPort() {
		return monitorPort;
	}

	public void setMonitorPort(String monitorPort) {
		this.monitorPort = monitorPort;
	}

	// add for view
	public String getDeviceName() {
		String deviceName = DEVICE_MAP.get(deviceType);
		return deviceName == null ? this.deviceName : deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getVocodecName() {
		String vName = VOCODEC_MAP.get(msvocodec);
		return vName == null ? vocodecName : vName;
	}

	public void setVocodecName(String vocodecName) {
		this.vocodecName = vocodecName;
	}

	public String getAmf() {
		return amf;
	}

	public void setAmf(String amf) {
		this.amf = amf;
		String[] hexArr = toHexArray(amf.trim(), 2);
		if (hexArr != null) {
			this.setAmf1(hexArr[0]);
			this.setAmf2(hexArr[1]);
		}
	}

	public String getSqn() {
		return sqn;
	}

	public void setSqn(String sqn) {
		this.sqn = sqn;
		String[] hexArr = toHexArray(sqn.trim(), 6);
		if (hexArr != null) {
			this.setSqn1(hexArr[0]);
			this.setSqn2(hexArr[1]);
			this.setSqn3(hexArr[2]);
			this.setSqn4(hexArr[3]);
			this.setSqn5(hexArr[4]);
			this.setSqn6(hexArr[5]);
		}
	}

	public String getK() {
		return k;
	}

	public void setK(String k) {
		this.k = k;
		String[] hexArr = toHexArray(k.trim(), 16);
		if (hexArr != null) {
			this.setK1(hexArr[0]);
			this.setK2(hexArr[1]);
			this.setK3(hexArr[2]);
			this.setK4(hexArr[3]);
			this.setK5(hexArr[4]);
			this.setK6(hexArr[5]);
			this.setK7(hexArr[6]);
			this.setK8(hexArr[7]);
			this.setK9(hexArr[8]);
			this.setK10(hexArr[9]);
			this.setK11(hexArr[10]);
			this.setK12(hexArr[11]);
			this.setK13(hexArr[12]);
			this.setK14(hexArr[13]);
			this.setK15(hexArr[14]);
			this.setK16(hexArr[15]);
		}
	}

	public String getOpc() {
		return opc;
	}

	public void setOpc(String opc) {
		this.opc = opc;
		String[] hexArr = toHexArray(opc.trim(), 16);
		if (hexArr != null) {
			this.setOpc1(hexArr[0]);
			this.setOpc2(hexArr[1]);
			this.setOpc3(hexArr[2]);
			this.setOpc4(hexArr[3]);
			this.setOpc5(hexArr[4]);
			this.setOpc6(hexArr[5]);
			this.setOpc7(hexArr[6]);
			this.setOpc8(hexArr[7]);
			this.setOpc9(hexArr[8]);
			this.setOpc10(hexArr[9]);
			this.setOpc11(hexArr[10]);
			this.setOpc12(hexArr[11]);
			this.setOpc13(hexArr[12]);
			this.setOpc14(hexArr[13]);
			this.setOpc15(hexArr[14]);
			this.setOpc16(hexArr[15]);
		}
	}

	private String[] toHexArray(String hex, int length) {
		String hexRegStr = "([a-fA-F0-9]{2})\\s*+", p = "";
		for (int i = 0; i < length; i++) {
			p += hexRegStr;
		}
		String[] result = null;
		Matcher m = Pattern.compile(p).matcher(hex);
		if (m.matches() && m.groupCount() == length) {
			result = new String[length];
			for (int i = 0; i < length; i++) {
				result[i] = m.group(i + 1);
			}
		}
		return result;
	}

	public Time getStart() {
		return start;
	}

	public Time getStop() {
		return stop;
	}

	public void setStop(Time stop) {
		this.stop = stop;
	}

	public void setStart(Time start) {
		this.start = start;
	}

	public void setStop(String stop) {
		this.stop = Time.valueOf(stop);
	}

	public void setStart(String start) {
		this.start = Time.valueOf(start);
	}

	public String getSrcIp() {
		return srcIp;
	}

	public void setSrcIp(String srcIp) {
		this.srcIp = srcIp;
	}

	public String getDstIp() {
		return dstIp;
	}

	public void setDstIp(String dstIp) {
		this.dstIp = dstIp;
	}

	public Integer getSrcPortStart() {
		return srcPortStart;
	}

	public void setSrcPortStart(Integer srcPortStart) {
		this.srcPortStart = srcPortStart;
	}

	public Integer getSrcPortEnd() {
		return srcPortEnd;
	}

	public void setSrcPortEnd(Integer srcPortEnd) {
		this.srcPortEnd = srcPortEnd;
	}

	public Integer getDstPortStart() {
		return dstPortStart;
	}

	public void setDstPortStart(Integer dstPortStart) {
		this.dstPortStart = dstPortStart;
	}

	public Integer getDstPortEnd() {
		return dstPortEnd;
	}

	public void setDstPortEnd(Integer dstPortEnd) {
		this.dstPortEnd = dstPortEnd;
	}

	public Integer getDiffServStart() {
		return diffServStart;
	}

	public void setDiffServStart(Integer diffServStart) {
		this.diffServStart = diffServStart;
	}

	public Integer getDiffServEnd() {
		return diffServEnd;
	}

	public void setDiffServEnd(Integer diffServEnd) {
		this.diffServEnd = diffServEnd;
	}

	public Integer getPktLenMin() {
		return pktLenMin;
	}

	public void setPktLenMin(Integer pktLenMin) {
		this.pktLenMin = pktLenMin;
	}

	public Integer getPktLenMax() {
		return pktLenMax;
	}

	public void setPktLenMax(Integer pktLenMax) {
		this.pktLenMax = pktLenMax;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}

	public Integer getPowerLevel() {
		return powerLevel;
	}

	public void setPowerLevel(Integer powerLevel) {
		this.powerLevel = powerLevel;
	}

	public Integer getSuportBuss() {
		return suportBuss;
	}

	public void setSuportBuss(Integer suportBuss) {
		this.suportBuss = suportBuss;
	}

	public Integer getGwId() {
		return gwId;
	}

	public void setGwId(Integer gwId) {
		this.gwId = gwId;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	public String getUePassword() {
		return uePassword;
	}

	public void setUePassword(String uePassword) {
		this.uePassword = uePassword;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String[] getGroups() {
		return groups;
	}

	public void setGroups(String[] groups) {
		this.groups = groups;
	}

	public String getGroup1() {
		return group1;
	}

	public void setGroup1(String group1) {
		this.group1 = group1;
	}

	public String getGroup2() {
		return group2;
	}

	public void setGroup2(String group2) {
		this.group2 = group2;
	}

	public String getGroup3() {
		return group3;
	}

	public void setGroup3(String group3) {
		this.group3 = group3;
	}

	public String getGroup4() {
		return group4;
	}

	public void setGroup4(String group4) {
		this.group4 = group4;
	}

	public String getGroup5() {
		return group5;
	}

	public void setGroup5(String group5) {
		this.group5 = group5;
	}

	public String getGroup6() {
		return group6;
	}

	public void setGroup6(String group6) {
		this.group6 = group6;
	}

	public String getGroup7() {
		return group7;
	}

	public void setGroup7(String group7) {
		this.group7 = group7;
	}

	public String getGroup8() {
		return group8;
	}

	public void setGroup8(String group8) {
		this.group8 = group8;
	}

	public String getGroup9() {
		return group9;
	}

	public void setGroup9(String group9) {
		this.group9 = group9;
	}

	public String getGroup10() {
		return group10;
	}

	public void setGroup10(String group10) {
		this.group10 = group10;
	}

	public String getGroup11() {
		return group11;
	}

	public void setGroup11(String group11) {
		this.group11 = group11;
	}

	public String getGroup12() {
		return group12;
	}

	public void setGroup12(String group12) {
		this.group12 = group12;
	}

	public String getGroup13() {
		return group13;
	}

	public void setGroup13(String group13) {
		this.group13 = group13;
	}

	public String getGroup14() {
		return group14;
	}

	public void setGroup14(String group14) {
		this.group14 = group14;
	}

	public String getGroup15() {
		return group15;
	}

	public void setGroup15(String group15) {
		this.group15 = group15;
	}

	public String getGroup16() {
		return group16;
	}

	public void setGroup16(String group16) {
		this.group16 = group16;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getOp1() {
		return op1;
	}

	public void setOp1(String op1) {
		this.op1 = op1;
	}

	public String getOp2() {
		return op2;
	}

	public void setOp2(String op2) {
		this.op2 = op2;
	}

	public String getOp3() {
		return op3;
	}

	public void setOp3(String op3) {
		this.op3 = op3;
	}

	public String getOp4() {
		return op4;
	}

	public void setOp4(String op4) {
		this.op4 = op4;
	}

	public String getOp5() {
		return op5;
	}

	public void setOp5(String op5) {
		this.op5 = op5;
	}

	public String getOp6() {
		return op6;
	}

	public void setOp6(String op6) {
		this.op6 = op6;
	}

	public String getOp7() {
		return op7;
	}

	public void setOp7(String op7) {
		this.op7 = op7;
	}

	public String getOp8() {
		return op8;
	}

	public void setOp8(String op8) {
		this.op8 = op8;
	}

	public String getOp9() {
		return op9;
	}

	public void setOp9(String op9) {
		this.op9 = op9;
	}

	public String getOp10() {
		return op10;
	}

	public void setOp10(String op10) {
		this.op10 = op10;
	}

	public String getOp11() {
		return op11;
	}

	public void setOp11(String op11) {
		this.op11 = op11;
	}

	public String getOp12() {
		return op12;
	}

	public void setOp12(String op12) {
		this.op12 = op12;
	}

	public String getOp13() {
		return op13;
	}

	public void setOp13(String op13) {
		this.op13 = op13;
	}

	public String getOp14() {
		return op14;
	}

	public void setOp14(String op14) {
		this.op14 = op14;
	}

	public String getOp15() {
		return op15;
	}

	public void setOp15(String op15) {
		this.op15 = op15;
	}

	public String getOp16() {
		return op16;
	}

	public void setOp16(String op16) {
		this.op16 = op16;
	}

	public String getApn() {
		return apn;
	}

	public void setApn(String apn) {
		this.apn = apn;
	}

	public Integer getErabId() {
		return erabId;
	}

	public void setErabId(Integer erabId) {
		this.erabId = erabId;
	}

	public Integer getQci() {
		return qci;
	}

	public void setQci(Integer qci) {
		this.qci = qci;
	}

	public Integer getArPriority() {
		return arPriority;
	}

	public void setArPriority(Integer arPriority) {
		this.arPriority = arPriority;
	}

	public Integer getAggregateMaxULBitRate() {
		return aggregateMaxULBitRate;
	}

	public void setAggregateMaxULBitRate(Integer aggregateMaxULBitRate) {
		this.aggregateMaxULBitRate = aggregateMaxULBitRate;
	}

	public Integer getAggregateMaxDLBitRate() {
		return aggregateMaxDLBitRate;
	}

	public void setAggregateMaxDLBitRate(Integer aggregateMaxDLBitRate) {
		this.aggregateMaxDLBitRate = aggregateMaxDLBitRate;
	}

	public Integer getGuaranteedULBitRate() {
		return guaranteedULBitRate;
	}

	public void setGuaranteedULBitRate(Integer guaranteedULBitRate) {
		this.guaranteedULBitRate = guaranteedULBitRate;
	}

	public Integer getGuaranteedDLBitRate() {
		return guaranteedDLBitRate;
	}

	public void setGuaranteedDLBitRate(Integer guaranteedDLBitRate) {
		this.guaranteedDLBitRate = guaranteedDLBitRate;
	}

	public Integer getMaxULBitRate() {
		return maxULBitRate;
	}

	public void setMaxULBitRate(Integer maxULBitRate) {
		this.maxULBitRate = maxULBitRate;
	}

	public Integer getMaxDLBitRate() {
		return maxDLBitRate;
	}

	public void setMaxDLBitRate(Integer maxDLBitRate) {
		this.maxDLBitRate = maxDLBitRate;
	}

	public Integer getCallerAllow() {
		return callerAllow;
	}

	public void setCallerAllow(Integer callerAllow) {
		// System.err.println("===================callerAllow" + callerAllow);
		// if(callerAllow == 1){
		// this.callerAllow = 0;
		// }else{
		// this.callerAllow = 1;
		// }
		this.callerAllow = callerAllow;
	}

	public Integer getCallerHide() {
		return callerHide;
	}

	public void setCallerHide(Integer callerHide) {
		this.callerHide = callerHide;
	}

	public Integer getOspfBroadcast() {
		return ospfBroadcast;
	}

	public void setOspfBroadcast(Integer ospfBroadcast) {
		this.ospfBroadcast = ospfBroadcast;
	}

	public Integer getOspfMulticast() {
		return ospfMulticast;
	}

	public void setOspfMulticast(Integer ospfMulticast) {
		this.ospfMulticast = ospfMulticast;
	}

	public Integer getServicePriority() {
		return servicePriority;
	}

	public void setServicePriority(Integer servicePriority) {
		this.servicePriority = servicePriority;
	}

	public String getPowerLevelText() {
		return powerLevelText;
	}

	public void setPowerLevelText(String powerLevelText) {
		this.powerLevelText = powerLevelText;
	}

}
