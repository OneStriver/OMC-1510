package com.sunkaisens.omc.service.impl.hss;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.sunkaisens.omc.vo.hss.HssBusinessVo;

/**
 * 
 * 
 * 
 * 
 * 
 * 解析从服务器返回的字符串对象
 */
class ParseUserMsg {
	private void convertZh(String[] vs) {
		switch (vs[6].trim()) {
		case "0":
			vs[6] = "关机";
			break;
		case "1":
			vs[6] = "开机";
			break;
		case "2":
			vs[6] = "找不到";
			break;
		case "3":
			vs[6] = "漫游出去";
			break;
		case "4":
			vs[6] = "漫游进来";
			break;
		}
	}
//	private void convertPsZh(String[] vs) {
//		switch (vs[6].trim()) {
//		case "0":
//			vs[6] = "未连接";
//			break;
//		case "1":
//			vs[6] = "连接";
//			break;
//		case "2":
//			vs[6] = "找不到";
//			break;
//		case "3":
//			vs[6] = "漫游出去";
//			break;
//		case "4":
//			vs[6] = "漫游进来";
//			break;
//		}
//	}
	private void convertPsZh1(String[] vs) {
		if(!vs[6].trim().equals("1")){
			vs[6] = "未激活";
		}
		if(vs[6].trim().equals("1")){
			if(vs[11].equals("0.0.0.0")){
				vs[6] = "未激活";
			}else{
				vs[6] = "激活";
			}
		}
//		if(Integer.valueOf(vs[6].trim())==1&&vs[11].equals("0.0.0.0")){
//			vs[6] = "未激活";
//		}
//		if(Integer.valueOf(vs[6].trim())==1&&!vs[11].equals("0.0.0.0")){
//			vs[6] = "激活";
//		}
	}
	private String getBaseServ(HssBusinessVo vo,ResourceBundle bundle){
		StringBuilder sb=new StringBuilder();
//		if(vo.getPayType()==1) sb.append(bundle.getString("PayType")).append("|");
		if(vo.getInternationality()==1) sb.append(bundle.getString("InternationalTraffic")).append("|");
		if(vo.getShortMsg()==1) sb.append(bundle.getString("MessageTraffic")).append("|");
		if(vo.getData()==1) sb.append(bundle.getString("DataTraffic")).append("|");
		if(vo.getGroupCallBusiness()==1) sb.append(bundle.getString("Groupcall")).append("|");
		if(vo.getThreeWay()==1) sb.append(bundle.getString("Three-way-calling")).append("|");
		if(vo.getCallWaitting()==1) sb.append(bundle.getString("CallTraffic")).append("|");
		if(vo.getCallOutLimit()==1) sb.append(bundle.getString("Outgoing-Call-Barring")).append("|");
		if(vo.getCallInLimit()==1) sb.append(bundle.getString("Incoming-Call-Barring")).append("|");
		if(vo.getPairNet()==1) sb.append(bundle.getString("Dual-Network-Business")).append("|");
		return sb.toString();
	}
	
	private String getSupplyServ(HssBusinessVo vo,ResourceBundle bundle){
		StringBuilder sb=new StringBuilder();
		if(vo.getFwdNoAnswer()==1) sb.append(bundle.getString("NoResponseForwarding")).append("|");
		if(vo.getFwdOnBusy()==1) sb.append(bundle.getString("CallForwarding")).append("|");
		if(vo.getDirectFwd()==1) sb.append(bundle.getString("UnconditionalForwarding")).append("|");
		if(vo.getFwdNA()==1) sb.append(bundle.getString("NoReachableForwarding"));
		return sb.toString();
	}
	
	private HssBusinessVo convert2HssBusinessVo(int msprofile){
		HssBusinessVo vo=new HssBusinessVo();
		vo.setMsprofile(msprofile);
		return vo;
	}
	
	//解析GET_UE消息返回的数据(readStatus)
	public Map<String, String> parseString(String string,ResourceBundle bundle,boolean isResion) {
		Map<String, String> map = new HashMap<String, String>();
		String flag = "no";
		String[] lines = string.split("\n");
		for(String line : lines){
			if(line.startsWith("PS_Data:")){
				flag = "yes";
			}
		}
		for (String line : lines) {
			line = line.trim();
			if (line.startsWith("UE:")) {
				line = line.substring(3);
				String[] vs = line.split("\\|",0);
				map.put("UE-IMSI", vs[0]);
				map.put("UE-MDN", vs[1]);
				map.put("UE-BM", vs[2]);
			} else if (line.startsWith("CS_Data:")) {
				line = line.substring(8);
				String[] vs = line.split("\\|", 0);
				map.put("CS-TMSI", vs[1]);
				map.put("CS-RncLoc", vs[2]);
				map.put("CS-GeoLo", vs[3]);
				convertZh(vs);
				map.put("CS-Status", vs[6]);
				map.put("VLR", vs[7]);
				map.put("FwdUNC", vs[11]);
				map.put("FwdOnBusyNum", vs[12]);
				map.put("FwdNoAnswer", vs[13]);
				map.put("FwdNANum", vs[14]);
				int msprofile=Integer.parseInt(vs[9]);
				HssBusinessVo vo=convert2HssBusinessVo(msprofile);
				map.put("CS-BaseServ", getBaseServ(vo,bundle));
				map.put("CS-SupplyServ", getSupplyServ(vo,bundle));
			} else if (line.startsWith("PS_Data:")) {
				line = line.substring(8);
				String[] vs = line.split("\\|", 0);
				map.put("PS-TMSI", vs[1]);
				map.put("PS-RncLoc", vs[2]);
				map.put("PS-GeoLo", vs[3]);
				convertPsZh1(vs);
				map.put("ESN", vs[0]);
				map.put("PS-Status", vs[6]);
				map.put("SGSN", vs[7]);
				map.put("AssignedIP",vs[11]);
				map.put("GGSNADDR", vs[12]);
				if(isResion){
					map.put("PS-BaseServ",bundle.getString("GroupingBusiness"));
				}
			}
		}
		map.put("PS-Exist", flag);
		return map;
	}

	public String hssVo2str(HssBusinessVo vo) {
		final String END = "END\n";
		final String DIV = "|";
		StringBuilder sb = new StringBuilder();
		sb.append("UE:" + vo.getImsi() + DIV + vo.getMdn() + DIV
				+ vo.getDeviceType() + DIV + END);
		sb.append("AuC_Data:" + vo.getK() + DIV + vo.getOp() + DIV
				+ vo.getOpc() + DIV + vo.getAmf() + DIV + vo.getRand() + DIV
				+ vo.getSqn() + DIV + vo.getStart() + DIV + vo.getStop() + DIV
				+ END);
		sb.append("CS_Data:" + vo.getEsn() + DIV + "" + DIV + "" + DIV + ""
				+ DIV + "" + DIV + "" + DIV + "" + DIV + "" + DIV
				+ vo.getMsvocodec() + DIV + vo.getMsprofile() + DIV
				+ vo.getMsprofile_extra() + DIV + vo.getDirectFwdNum() + DIV
				+ vo.getFwdOnBusyNum() + DIV + vo.getFwdNoAnswerNum() + DIV
				+ vo.getFwdNANum() + DIV + vo.getVoiceMailNum() + DIV
				+ vo.getWireTapAddr() + DIV + END);
		sb.append("GROUP_Data:" + vo.getGroup1() + DIV + vo.getGroup2() + DIV
				+ vo.getGroup3() + DIV + vo.getGroup4() + DIV + vo.getGroup5()
				+ DIV + vo.getGroup6() + DIV + vo.getGroup7() + DIV
				+ vo.getGroup8() + DIV + vo.getGroup9() + DIV + vo.getGroup10()
				+ DIV + vo.getGroup11() + DIV + vo.getGroup12() + DIV
				+ vo.getGroup13() + DIV + vo.getGroup14() + DIV
				+ vo.getGroup15() + DIV + vo.getGroup16() + DIV + END);
		sb.append("PS_Data:" + vo.getEsn() + DIV + "" + DIV + "" + DIV + ""
				+ DIV + "" + DIV + "" + DIV + "" + DIV + "" + vo.getMsprofile()
				+ DIV + vo.getMsprofile_extra() + DIV + "" + DIV + END);
		sb.append("EPC_Data:" + "" + DIV + "" + DIV + "" + DIV + "" + DIV + ""
				+ DIV + "" + DIV + "" + DIV + "" + DIV + "" + DIV + "" + DIV
				+ END);
		sb.append("EPC_TFT:" + vo.getSrcIp() + DIV + vo.getDstIp() + DIV
				+ vo.getSrcPortStart() + DIV + vo.getSrcPortEnd() + DIV
				+ vo.getDstPortStart() + DIV + vo.getDstPortEnd() + DIV
				+ vo.getDiffServStart() + DIV + vo.getDiffServEnd() + DIV
				+ vo.getPktLenMin() + DIV + vo.getPktLenMax() + DIV + END);
		sb.append("Terminal:" + vo.getTerminalId() + DIV + vo.getTerminalType()
				+ DIV + vo.getPowerLevel() + DIV + vo.getMsprofile() + DIV
				+ vo.getGwId() + DIV + vo.getDepartment() + DIV
				+ vo.getUserType() + DIV + vo.getUsername() + DIV
				+ vo.getUserInfo() + DIV + vo.getCreateTime() + DIV + END);
		sb.append("Cscf:"+vo.getMdn()+"@"+vo.getDomain()+DIV+END);
		return sb.toString();
	}
}
