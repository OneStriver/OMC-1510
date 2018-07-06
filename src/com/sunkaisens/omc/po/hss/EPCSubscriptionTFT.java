package com.sunkaisens.omc.po.hss;

import java.io.Serializable;
/**
 * 
 * 
 * 
 * 
 * EPCSubscriptionTFT 实体类
 *
 */
public class EPCSubscriptionTFT implements Serializable{
	private static final long serialVersionUID = 1L;
	private String imsi;
	private String srcIp;
	private String dstIp;
	private Integer srcPortStart;
	private Integer srcPortEnd;
	private Integer dstPortStart;
	private Integer dstPortEnd;
	private Integer diffServStart;
	private Integer diffServEnd;
	private Integer pktLenMin;
	private Integer pktLenMax;
	/**
	 * 
	 * 
	 * 
	 * 无参数构造器
	 */
	public EPCSubscriptionTFT(){}
	/**
	 * 
	 * 
	 * 
	 * 有参数构造器
	 * @param imsi
	 * @param srcIp
	 * @param dstIp
	 * @param srcPortStart
	 * @param srcPortEnd
	 * @param dstPortStart
	 * @param dstPortEnd
	 * @param diffServStart
	 * @param diffServEnd
	 * @param pktLenMin
	 * @param pktLenMax
	 */
	public EPCSubscriptionTFT(String imsi, String srcIp, String dstIp,
			Integer srcPortStart, Integer srcPortEnd, Integer dstPortStart,
			Integer dstPortEnd, Integer diffServStart, Integer diffServEnd,
			Integer pktLenMin, Integer pktLenMax) {
		this.imsi = imsi;
		this.srcIp = srcIp;
		this.dstIp = dstIp;
		this.srcPortStart = srcPortStart;
		this.srcPortEnd = srcPortEnd;
		this.dstPortStart = dstPortStart;
		this.dstPortEnd = dstPortEnd;
		this.diffServStart = diffServStart;
		this.diffServEnd = diffServEnd;
		this.pktLenMin = pktLenMin;
		this.pktLenMax = pktLenMax;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
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

}
