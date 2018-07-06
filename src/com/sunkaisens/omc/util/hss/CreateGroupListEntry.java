package com.sunkaisens.omc.util.hss;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "entry")
public class CreateGroupListEntry {

	@XmlAttribute(name = "idx")
	private String idx;

	@XmlAttribute(name = "uri")
	private String uri;

	@XmlAttribute(name = "displayName")
	private String displayName;

	@XmlAttribute(name = "userType")
	private String userType;

	@XmlAttribute(name = "deviceType")
	private String deviceType;

	public CreateGroupListEntry() {
		super();
	}

	public CreateGroupListEntry(String idx, String uri, String displayName, String userType, String deviceType) {
		super();
		this.idx = idx;
		this.uri = uri;
		this.displayName = displayName;
		this.userType = userType;
		this.deviceType = deviceType;
	}

	public String getIdx() {
		return idx;
	}

	public void setIdx(String idx) {
		this.idx = idx;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

}
