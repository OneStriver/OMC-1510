package com.sunkaisens.omc.po.hss;

import java.io.Serializable;
/**
 * 
 * 
 * 
 * 
 * 
 * EPCSubscriptionData 实体类
 *
 */
public class EPCSubscriptionData implements Serializable{
	private static final long serialVersionUID = 1L;

	private String imsi;
	private String apn="sunkaisens";
	private Integer erabId=5;
	private Integer qci=9;
	private Integer arPriority=3;
	private Integer aggregateMaxULBitRate=3500000;
	private Integer aggregateMaxDLBitRate=2500000;
	private Integer guaranteedULBitRate=0;
	private Integer guaranteedDLBitRate=0;
	private Integer maxULBitRate=0;
	private Integer maxDLBitRate=0;
    /**
     * 
     * 
     * 
     * 无参数构造器
     */
	public EPCSubscriptionData(){}
    /**
     * 
     * 
     * 
     * 
     * 有参数构造器
     * @param imsi
     * @param apn
     * @param erabId
     * @param qci
     * @param arPriority
     * @param aggregateMaxULBitRate
     * @param aggregateMaxDLBitRate
     * @param guaranteedULBitRate
     * @param guaranteedDLBitRate
     * @param maxULBitRate
     * @param maxDLBitRate
     */
	public EPCSubscriptionData(String imsi, String apn, Integer erabId, Integer qci, Integer arPriority,
			Integer aggregateMaxULBitRate, Integer aggregateMaxDLBitRate, Integer guaranteedULBitRate,
			Integer guaranteedDLBitRate, Integer maxULBitRate, Integer maxDLBitRate) {
		this.imsi = imsi;
		this.apn = apn;
		this.erabId = erabId;
		this.qci = qci;
		this.arPriority = arPriority;
		this.aggregateMaxULBitRate = aggregateMaxULBitRate;
		this.aggregateMaxDLBitRate = aggregateMaxDLBitRate;
		this.guaranteedULBitRate = guaranteedULBitRate;
		this.guaranteedDLBitRate = guaranteedDLBitRate;
		this.maxULBitRate = maxULBitRate;
		this.maxDLBitRate = maxDLBitRate;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
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

	public void setAggregateMaxULBitrate(Integer aggregateMaxULBitRate) {
		this.aggregateMaxULBitRate = aggregateMaxULBitRate;
	}

	public Integer getAggregateMaxDLBitRate() {
		return aggregateMaxDLBitRate;
	}

	public void setAggregateMaxDLBitrate(Integer aggregateMaxDLBitRate) {
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
}	
