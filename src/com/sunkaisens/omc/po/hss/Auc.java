package com.sunkaisens.omc.po.hss;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
/**
 * 
 * 
 * 
 * 
 * 
 * 鉴权实体类
 */
public class Auc implements Serializable{
	private static final long serialVersionUID = 1L;
	private String imsi;
	private String k="00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00";
	private String op="00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00";
	private String opc="00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00";
	private String amf="72 4C";
	private String sqn="00 00 00 00 00 00";
	private String rand="00 00 00 00 00 00";
	private Time start=Time.valueOf("00:00:00") ;
	private Time stop=Time.valueOf("23:59:59");
	private Timestamp tstamp=new Timestamp(new Date().getTime());
	/**
	 * 
	 * 
	 * 
	 * 
	 * 无参数构造器
	 */
	public Auc() {}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 有参数构造器
	 * @param imsi
	 * @param k
	 * @param op
	 * @param opc
	 * @param amf
	 * @param sqn
	 * @param rand
	 * @param tstamp
	 * @param start
	 * @param stop
	 */
	public Auc(String imsi, String k, String op, String opc, String amf,
			String sqn, String rand, Timestamp tstamp,Time start,Time stop) {
		super();
		this.imsi = imsi;
		this.k = k;
		this.op = op;
		this.opc = opc;
		this.amf = amf;
		this.sqn = sqn;
		this.rand = rand;
		this.tstamp = tstamp;
		this.start=start;
		this.stop=stop;
	}

	/*getter and setter */
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getK() {
		return k;
	}
	public void setK(String k) {
		this.k = k;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public String getOpc() {
		return opc;
	}
	public void setOpc(String opc) {
		this.opc = opc;
	}
	public String getAmf() {
		return amf;
	}
	public void setAmf(String amf) {
		this.amf = amf;
	}
	public String getSqn() {
		return sqn;
	}
	public void setSqn(String sqn) {
		this.sqn = sqn;
	}
	public String getRand() {
		return rand;
	}
	public void setRand(String rand) {
		this.rand = rand;
	}
	public Timestamp getTstamp() {
		return tstamp;
	}
	public void setTstamp(Timestamp tstamp) {
		this.tstamp = tstamp;
	}
	public Time getStart() {
		return start;
	}
	public void setStart(Time start) {
		this.start = start;
	}

	public Time getStop() {
		return stop;
	}

	public void setStop(Time stop) {
		this.stop = stop;
	}
	@Override
	public String toString() {
		return "Auc [imsi=" + imsi + ", k=" + k + ", op=" + op + ", opc=" + opc + ", amf=" + amf + ", sqn=" + sqn
				+ ", rand=" + rand + ", start=" + start + ", stop=" + stop + ", tstamp=" + tstamp + "]";
	}

}
