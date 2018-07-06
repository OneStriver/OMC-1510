package com.sunkaisens.omc.po.cscf;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * 
 * 
 * 
 * 
 * Ue实体类
 *
 */
public class Ue implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String ueUri;
	private String ueName;
	private String ueScscfDomain;
	private String ueScscfAddr;
	private String uePcscfDomain;
	private String uePcscfAddr;
	private String ueAddr;
	private Integer ueStatus;
	private Integer ueUpdateType;
	private Date ueUpdateTstamp;
	private String ueHomeScscfDomain;
	private String ueHomeScscfAddr;
	private String uePassword;
    /**
     * 
     * 
     * 
     * 
     * 无参数构造器
     */
	public Ue(){}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 有参数构造器
	 * @param ueUri
	 * @param ueName
	 * @param ueScscfDomain
	 * @param ueScscfAddr
	 * @param uePcscfDomain
	 * @param uePcscfAddr
	 * @param ueAddr
	 * @param ueStatus
	 * @param ueUpdateType
	 * @param ueUpdateTstamp
	 * @param ueHomeScscfDomain
	 * @param ueHomeScscfAddr
	 * @param uePassword
	 */
	public Ue(String ueUri, String ueName, String ueScscfDomain,
			String ueScscfAddr, String uePcscfDomain, String uePcscfAddr,
			String ueAddr, Integer ueStatus, Integer ueUpdateType,
			Date ueUpdateTstamp, String ueHomeScscfDomain,
			String ueHomeScscfAddr, String uePassword) {
		this.ueUri = ueUri;
		this.ueName = ueName;
		this.ueScscfDomain = ueScscfDomain;
		this.ueScscfAddr = ueScscfAddr;
		this.uePcscfDomain = uePcscfDomain;
		this.uePcscfAddr = uePcscfAddr;
		this.ueAddr = ueAddr;
		this.ueStatus = ueStatus;
		this.ueUpdateType = ueUpdateType;
		this.ueUpdateTstamp = ueUpdateTstamp;
		this.ueHomeScscfDomain = ueHomeScscfDomain;
		this.ueHomeScscfAddr = ueHomeScscfAddr;
		this.uePassword = uePassword;
	}

	public String getUeUri() {
		return ueUri;
	}

	public void setUeUri(String ueUri) {
		this.ueUri = ueUri;
	}

	public String getUeName() {
		return ueName;
	}

	public void setUeName(String ueName) {
		this.ueName = ueName;
	}

	public String getUeScscfDomain() {
		return ueScscfDomain;
	}

	public void setUeScscfDomain(String ueScscfDomain) {
		this.ueScscfDomain = ueScscfDomain;
	}

	public String getUeScscfAddr() {
		return ueScscfAddr;
	}

	public void setUeScscfAddr(String ueScscfAddr) {
		this.ueScscfAddr = ueScscfAddr;
	}

	public String getUePcscfDomain() {
		return uePcscfDomain;
	}

	public void setUePcscfDomain(String uePcscfDomain) {
		this.uePcscfDomain = uePcscfDomain;
	}

	public String getUePcscfAddr() {
		return uePcscfAddr;
	}

	public void setUePcscfAddr(String uePcscfAddr) {
		this.uePcscfAddr = uePcscfAddr;
	}

	public String getUeAddr() {
		return ueAddr;
	}

	public void setUeAddr(String ueAddr) {
		this.ueAddr = ueAddr;
	}

	public Integer getUeStatus() {
		return ueStatus;
	}

	public void setUeStatus(Integer ueStatus) {
		this.ueStatus = ueStatus;
	}

	public Integer getUeUpdateType() {
		return ueUpdateType;
	}

	public void setUeUpdateType(Integer ueUpdateType) {
		this.ueUpdateType = ueUpdateType;
	}

	public Date getUeUpdateTstamp() {
		return ueUpdateTstamp;
	}

	public void setUeUpdateTstamp(Date ueUpdateTstamp) {
		this.ueUpdateTstamp = ueUpdateTstamp;
	}

	public String getUeHomeScscfDomain() {
		return ueHomeScscfDomain;
	}

	public void setUeHomeScscfDomain(String ueHomeScscfDomain) {
		this.ueHomeScscfDomain = ueHomeScscfDomain;
	}

	public String getUeHomeScscfAddr() {
		return ueHomeScscfAddr;
	}

	public void setUeHomeScscfAddr(String ueHomeScscfAddr) {
		this.ueHomeScscfAddr = ueHomeScscfAddr;
	}

	public String getUePassword() {
		return uePassword;
	}

	public void setUePassword(String uePassword) {
		this.uePassword = uePassword;
	}

}
