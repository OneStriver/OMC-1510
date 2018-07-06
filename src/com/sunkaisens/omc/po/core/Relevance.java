package com.sunkaisens.omc.po.core;

import java.io.Serializable;
/**
 * 
 * 
 * 
 * 
 * 
 *Relevance实体类
 *
 */
public class Relevance extends BaseProperty implements Serializable {
	private static final long serialVersionUID = 1L;
	private Common common;
	public Common getCommon() {
		return common;
	}
	public void setCommon(Common common) {
		this.common = common;
	}
}
