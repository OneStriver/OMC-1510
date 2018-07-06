package com.sunkaisens.omc.po.hss;

import java.io.Serializable;

/**
 * 
 * 
 * 
 * 
 * 
 * 组信息实体类
 */
public class GroupInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String imsi;
	private String group1="";
	private String group2="";
	private String group3="";
	private String group4="";
	private String group5="";
	private String group6="";
	private String group7="";
	private String group8="";
	private String group9="";
	private String group10="";
	private String group11="";
	private String group12="";
	private String group13="";
	private String group14="";
	private String group15="";
	private String group16="";
	/**
	 * 
	 * 
	 * 
	 * 
	 * 无参数构造器
	 */
	public GroupInfo() {
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 有参数构造器
	 * @param imsi
	 * @param group1
	 * @param group2
	 * @param group3
	 * @param group4
	 * @param group5
	 * @param group6
	 * @param group7
	 * @param group8
	 * @param group9
	 * @param group10
	 * @param group11
	 * @param group12
	 * @param group13
	 * @param group14
	 * @param group15
	 * @param group16
	 */
	public GroupInfo(String imsi, String group1, String group2, String group3,
			String group4, String group5, String group6, String group7,
			String group8, String group9, String group10, String group11,
			String group12, String group13, String group14, String group15,
			String group16) {
		this.imsi = imsi;
		this.group1 = group1;
		this.group2 = group2;
		this.group3 = group3;
		this.group4 = group4;
		this.group5 = group5;
		this.group6 = group6;
		this.group7 = group7;
		this.group8 = group8;
		this.group9 = group9;
		this.group10 = group10;
		this.group11 = group11;
		this.group12 = group12;
		this.group13 = group13;
		this.group14 = group14;
		this.group15 = group15;
		this.group16 = group16;
	}

	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getGroup1() {
		return group1;
	}
	public void setGroup1(String group1) {
		this.group1 = group1;
	}
	public String getGroup2() {
		return group2;
	}
	public void setGroup2(String group2) {
		this.group2 = group2;
	}
	public String getGroup3() {
		return group3;
	}
	public void setGroup3(String group3) {
		this.group3 = group3;
	}
	public String getGroup4() {
		return group4;
	}
	public void setGroup4(String group4) {
		this.group4 = group4;
	}
	public String getGroup5() {
		return group5;
	}
	public void setGroup5(String group5) {
		this.group5 = group5;
	}
	public String getGroup6() {
		return group6;
	}
	public void setGroup6(String group6) {
		this.group6 = group6;
	}
	public String getGroup7() {
		return group7;
	}
	public void setGroup7(String group7) {
		this.group7 = group7;
	}
	public String getGroup8() {
		return group8;
	}
	public void setGroup8(String group8) {
		this.group8 = group8;
	}
	public String getGroup9() {
		return group9;
	}
	public void setGroup9(String group9) {
		this.group9 = group9;
	}
	public String getGroup10() {
		return group10;
	}
	public void setGroup10(String group10) {
		this.group10 = group10;
	}
	public String getGroup11() {
		return group11;
	}
	public void setGroup11(String group11) {
		this.group11 = group11;
	}
	public String getGroup12() {
		return group12;
	}
	public void setGroup12(String group12) {
		this.group12 = group12;
	}
	public String getGroup13() {
		return group13;
	}
	public void setGroup13(String group13) {
		this.group13 = group13;
	}
	public String getGroup14() {
		return group14;
	}
	public void setGroup14(String group14) {
		this.group14 = group14;
	}
	public String getGroup15() {
		return group15;
	}
	public void setGroup15(String group15) {
		this.group15 = group15;
	}
	public String getGroup16() {
		return group16;
	}
	public void setGroup16(String group16) {
		this.group16 = group16;
	}
}
