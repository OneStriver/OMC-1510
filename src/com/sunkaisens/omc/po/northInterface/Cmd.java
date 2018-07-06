package com.sunkaisens.omc.po.northInterface;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "cmd")
public class Cmd {

	// 查询终端信息配置 终端信息更新通知  终端信息更新结果
	private List<Routing> routings;


	public List<Routing> getRoutings() {
		return routings;
	}

	@XmlElement(name = "Routing")
	public void setRoutings(List<Routing> routings) {
		this.routings = routings;
	}

}
