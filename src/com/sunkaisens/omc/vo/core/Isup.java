package com.sunkaisens.omc.vo.core;

/**
 * 
 * 
 * 出局vo
 * @author shenchao
 *
 */
public class Isup {
    /**
     * 
     * 
     * 无参数构造器
     */
	public Isup(){}
	/**
	 * 
	 * 
	 * 
	 * 有参数构造器
	 * @param prefixStr
	 * @param afterRoutingStripStr
	 * @param minNumOfDigits
	 * @param tSAssignMode
	 * @param slc
	 * @param cic
	 * @param opc
	 * @param dpc
	 * @param timeSlot
	 * @param voCoding
	 * @param standard
	 * @param isup_cpg
	 * @param isup_ai
	 * @param isup_sio
	 * @param routing
	 * @param remoteIp
	 * @param remotePort
	 */
	public Isup(String prefixStr, String afterRoutingStripStr, String minNumOfDigits, String tSAssignMode, String slc,
			String cic, String opc, String dpc, String timeSlot, String voCoding, String standard, String isup_cpg,
			String isup_ai, String isup_sio, String[] routing,String remoteIp,String remotePort) {
		this.prefixStr = prefixStr;
		this.afterRoutingStripStr = afterRoutingStripStr;
		this.minNumOfDigits = minNumOfDigits;
		this.tSAssignMode = tSAssignMode;
		this.slc = slc;
		this.cic = cic;
		this.opc = opc;
		this.dpc = dpc;
		this.timeSlot = timeSlot;
		this.voCoding = voCoding;
		this.standard = standard;
		this.isup_cpg = isup_cpg;
		this.isup_ai = isup_ai;
		this.isup_sio = isup_sio;
		this.routing = routing;
		this.remoteIp=remoteIp;
		this.remotePort=remotePort;
	}

	private String prefixStr;
	private String afterRoutingStripStr;
	private String minNumOfDigits;
	private String tSAssignMode;
	private String slc;
	private String cic;
	private String opc;
	private String dpc;
	private String timeSlot;
	private String voCoding;
	private String standard;
	private String isup_cpg;
	private String isup_ai;
	private String isup_sio;
	private String[] routing;
	private String remoteIp;
	private String remotePort;
	public String getPrefixStr() {
		return prefixStr;
	}
	public void setPrefixStr(String prefixStr) {
		this.prefixStr = prefixStr;
	}
	public String getAfterRoutingStripStr() {
		return afterRoutingStripStr;
	}
	public void setAfterRoutingStripStr(String afterRoutingStripStr) {
		this.afterRoutingStripStr = afterRoutingStripStr;
	}
	public String getMinNumOfDigits() {
		return minNumOfDigits;
	}
	public void setMinNumOfDigits(String minNumOfDigits) {
		this.minNumOfDigits = minNumOfDigits;
	}
	public String gettSAssignMode() {
		return tSAssignMode;
	}
	public void settSAssignMode(String tSAssignMode) {
		this.tSAssignMode = tSAssignMode;
	}
	public String getSlc() {
		return slc;
	}
	public void setSlc(String slc) {
		this.slc = slc;
	}
	public String getCic() {
		return cic;
	}
	public void setCic(String cic) {
		this.cic = cic;
	}
	public String getOpc() {
		return opc;
	}
	public void setOpc(String opc) {
		this.opc = opc;
	}
	public String getDpc() {
		return dpc;
	}
	public void setDpc(String dpc) {
		this.dpc = dpc;
	}
	public String getTimeSlot() {
		return timeSlot;
	}
	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}
	public String getVoCoding() {
		return voCoding;
	}
	public void setVoCoding(String voCoding) {
		this.voCoding = voCoding;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getIsup_cpg() {
		return isup_cpg;
	}
	public void setIsup_cpg(String isup_cpg) {
		this.isup_cpg = isup_cpg;
	}
	public String getIsup_ai() {
		return isup_ai;
	}
	public void setIsup_ai(String isup_ai) {
		this.isup_ai = isup_ai;
	}
	public String getIsup_sio() {
		return isup_sio;
	}
	public void setIsup_sio(String isup_sio) {
		this.isup_sio = isup_sio;
	}
	public String[] getRouting() {
		return routing;
	}
	public void setRouting(String[] routing) {
		this.routing = routing;
	}

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	public String getRemotePort() {
		return remotePort;
	}

	public void setRemotePort(String remotePort) {
		this.remotePort = remotePort;
	}
}
