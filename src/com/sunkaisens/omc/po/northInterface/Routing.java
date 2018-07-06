package com.sunkaisens.omc.po.northInterface;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "Routing")
public class Routing {

	private String systemName;
	// 终端信息配置文件
	private List<TermInfo> termInfos;

	public String getSystemName() {
		return systemName;
	}

	@XmlAttribute
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public List<TermInfo> getTermInfos() {
		return termInfos;
	}

	@XmlElement(name = "termInfo")
	public void setTermInfos(List<TermInfo> termInfos) {
		this.termInfos = termInfos;
	}

}
