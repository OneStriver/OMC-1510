package com.sunkaisens.omc.vo.hss;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sunkaisens.omc.po.hss.UserGroupPriority;

public class PriorityList implements Serializable {

	private static final long serialVersionUID = 1423432L;
	
	private List<UserGroupPriority> priorityList  = new ArrayList<UserGroupPriority>();

	public List<UserGroupPriority> getPriorityList() {
		return priorityList;
	}

	public void setPriorityList(List<UserGroupPriority> priorityList) {
		this.priorityList = priorityList;
	}
}
