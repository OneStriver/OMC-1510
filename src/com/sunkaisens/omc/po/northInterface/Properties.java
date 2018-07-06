package com.sunkaisens.omc.po.northInterface;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "properties")
public class Properties {

	private List<Property> propertys;

	public List<Property> getPropertys() {
		return propertys;
	}

	@XmlElement(name = "property")
	public void setPropertys(List<Property> propertys) {
		this.propertys = propertys;
	}

}
