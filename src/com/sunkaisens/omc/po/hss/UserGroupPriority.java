package com.sunkaisens.omc.po.hss;

public class UserGroupPriority {

	private int id;
	private int level;
	private int priority;
	private int enable;
	private int userType;

	public UserGroupPriority() {
	}

	public UserGroupPriority(int id, int level, int priority, int enable, int userType) {
		super();
		this.id = id;
		this.level = level;
		this.priority = priority;
		this.enable = enable;
		this.userType = userType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

}
