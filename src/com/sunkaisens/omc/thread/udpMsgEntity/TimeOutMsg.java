package com.sunkaisens.omc.thread.udpMsgEntity;

/**
 * 封装超时消息的类
 */
public class TimeOutMsg {
	private String timerId;
	private int timerType;
	private Object userData;

	public TimeOutMsg(String timerId, int timerType, Object userData) {
		this.timerId = timerId;
		this.timerType = timerType;
		this.userData = userData;
	}

	public String getTimerId() {
		return timerId;
	}

	public void setTimerId(String timerId) {
		this.timerId = timerId;
	}

	public Object getUserData() {
		return userData;
	}

	public void setUserData(Object userData) {
		this.userData = userData;
	}

	public int getTimerType() {
		return timerType;
	}

	public void setTimerType(int timerType) {
		this.timerType = timerType;
	}
}
