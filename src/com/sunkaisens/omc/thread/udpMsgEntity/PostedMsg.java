package com.sunkaisens.omc.thread.udpMsgEntity;

public class PostedMsg {
	private int type;
	private Object msg;

	public PostedMsg(int type, Object msg) {
		this.type = type;
		this.msg = msg;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Object getMsg() {
		return msg;
	}

	public void setMsg(Object msg) {
		this.msg = msg;
	}
}
