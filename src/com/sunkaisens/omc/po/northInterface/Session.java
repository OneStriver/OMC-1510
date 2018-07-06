package com.sunkaisens.omc.po.northInterface;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "session")
public class Session {

	private String sId;
	private String username;
	private String password;
	private Cmd cmd;

	public String getsId() {
		return sId;
	}

	@XmlAttribute
	public void setsId(String sId) {
		this.sId = sId;
	}

	public String getUsername() {
		return username;
	}

	@XmlAttribute
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	@XmlAttribute
	public void setPassword(String password) {
		this.password = password;
	}

	public Cmd getCmd() {
		return cmd;
	}

	@XmlElement(name = "cmd")
	public void setCmd(Cmd cmd) {
		this.cmd = cmd;
	}

}
