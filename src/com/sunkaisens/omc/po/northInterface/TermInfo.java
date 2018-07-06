package com.sunkaisens.omc.po.northInterface;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "termInfo")
public class TermInfo {

	private String type;
	private String version;
	private List<Terminal> terminals;

	public String getType() {
		return type;
	}

	@XmlAttribute
	public void setType(String type) {
		this.type = type;
	}

	public String getVersion() {
		return version;
	}

	@XmlAttribute
	public void setVersion(String version) {
		this.version = version;
	}

	public List<Terminal> getTerminals() {
		return terminals;
	}

	@XmlElement(name="terminal")
	public void setTerminals(List<Terminal> terminals) {
		this.terminals = terminals;
	}

}
