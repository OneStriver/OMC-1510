package com.sunkaisens.omc.po.northInterface;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "terminal")
public class Terminal {

	private String deviceType;
	private String deviceNumber;
	private String type;
	private List<Properties> properties;
	private List<Limits> limits;

	public String getDeviceType() {
		return deviceType;
	}

	@XmlAttribute
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceNumber() {
		return deviceNumber;
	}

	@XmlAttribute
	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public String getType() {
		return type;
	}

	@XmlAttribute
	public void setType(String type) {
		this.type = type;
	}

	public List<Properties> getProperties() {
		return properties;
	}

	@XmlElement(name="properties")
	public void setProperties(List<Properties> properties) {
		this.properties = properties;
	}

	public List<Limits> getLimits() {
		return limits;
	}

	@XmlElement(name="limits")
	public void setLimits(List<Limits> limits) {
		this.limits = limits;
	}

}
