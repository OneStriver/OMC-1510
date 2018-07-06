package com.sunkaisens.omc.util.hss;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "service-group")
public class CreateGroup {

	@XmlAttribute
	private String xmlns;

	@XmlElement(name = "list")
	private CreateGroupList createGroupList;

	@XmlElement(name = "list")
	private String createGroupListStr;

	public CreateGroup() {
		super();
	}

	public CreateGroup(String xmlns) {
		super();
		this.xmlns = xmlns;
	}

	public CreateGroup(String xmlns, CreateGroupList createGroupList) {
		super();
		this.xmlns = xmlns;
		this.createGroupList = createGroupList;
	}

	public String getXmlns() {
		return xmlns;
	}

	public void setXmlns(String xmlns) {
		this.xmlns = xmlns;
	}

	public CreateGroupList getCreateGroupList() {
		return createGroupList;
	}

	public void setCreateGroupList(CreateGroupList createGroupList) {
		this.createGroupList = createGroupList;
	}

	public String getCreateGroupListStr() {
		return createGroupListStr;
	}

	public void setCreateGroupListStr(String createGroupListStr) {
		this.createGroupListStr = createGroupListStr;
	}

}
