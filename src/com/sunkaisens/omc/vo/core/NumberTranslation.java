package com.sunkaisens.omc.vo.core;
/**
 * 
 * 
 * 
 * 
 *NumberTranslation实体类
 *
 */
public class NumberTranslation {
	/**
	 * 
	 * 
	 * 无参数构造器
	 */
	public NumberTranslation() {
	}
     /**
      * 
      * 
      * 
      * 有参数构造器
      * @param ori
      * @param des
      */
	public NumberTranslation(String ori, String des) {
		this.ori = ori;
		this.des = des;
	}

	private String ori;
	private String des;

	public String getOri() {
		return ori;
	}

	public void setOri(String ori) {
		this.ori = ori;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}
}