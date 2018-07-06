package com.sunkaisens.omc.po.northInterface;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "limits")
public class Limits {

	private List<Limit> limits;

	public List<Limit> getLimits() {
		return limits;
	}

	@XmlElement(name="limit")
	public void setLimits(List<Limit> limits) {
		this.limits = limits;
	}

}
