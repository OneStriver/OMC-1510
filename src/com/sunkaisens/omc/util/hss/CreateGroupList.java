package com.sunkaisens.omc.util.hss;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "list")
public class CreateGroupList {

	@XmlAttribute(name = "idx")
	private String idx;

	@XmlAttribute(name = "name")
	private String name;

	@XmlAttribute(name = "uri")
	private String uri;

	@XmlAttribute(name = "displayName")
	private String displayName;

	@XmlAttribute(name = "serviceType")
	private String serviceType;

	@XmlAttribute(name = "serviceLevel")
	private String serviceLevel;

	@XmlAttribute(name = "creator")
	private String creator;

	@XmlElement(name = "entry")
	private CreateGroupListEntry createGroupListEntry;

	@XmlElement(name = "entry")
	private String createGroupListEntryStr;

	public CreateGroupList() {
		super();
	}

	public CreateGroupList(String idx, String name, String uri, String displayName, String serviceType,
			String serviceLevel, String creator) {
		super();
		this.idx = idx;
		this.name = name;
		this.uri = uri;
		this.displayName = displayName;
		this.serviceType = serviceType;
		this.serviceLevel = serviceLevel;
		this.creator = creator;
	}

	public CreateGroupList(String idx, String name, String uri, String displayName, String serviceType,
			String serviceLevel, String creator, CreateGroupListEntry createGroupListEntry) {
		super();
		this.idx = idx;
		this.name = name;
		this.uri = uri;
		this.displayName = displayName;
		this.serviceType = serviceType;
		this.serviceLevel = serviceLevel;
		this.creator = creator;
		this.createGroupListEntry = createGroupListEntry;
	}

	public String getIdx() {
		return idx;
	}

	public void setIdx(String idx) {
		this.idx = idx;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceLevel() {
		return serviceLevel;
	}

	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public CreateGroupListEntry getCreateGroupListEntry() {
		return createGroupListEntry;
	}

	public void setCreateGroupListEntry(CreateGroupListEntry createGroupListEntry) {
		this.createGroupListEntry = createGroupListEntry;
	}

	public String getCreateGroupListEntryStr() {
		return createGroupListEntryStr;
	}

	public void setCreateGroupListEntryStr(String createGroupListEntryStr) {
		this.createGroupListEntryStr = createGroupListEntryStr;
	}

}
