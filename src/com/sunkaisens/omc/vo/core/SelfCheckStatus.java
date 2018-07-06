package com.sunkaisens.omc.vo.core;

import java.util.List;
/**
 * 
 * 
 * 
 * 状态自检实体类
 *
 */
public class SelfCheckStatus {
	private List<SysService> service;
	private List<Mem> mem;
	private List<Disk> disk;
	private List<Interface> inter;
	public List<SysService> getService() {
		return service;
	}
	public void setService(List<SysService> service) {
		this.service = service;
	}
	public List<Mem> getMem() {
		return mem;
	}
	public void setMem(List<Mem> mem) {
		this.mem = mem;
	}
	public List<Disk> getDisk() {
		return disk;
	}
	public void setDisk(List<Disk> disk) {
		this.disk = disk;
	}
	public List<Interface> getInterface() {
		return inter;
	}
	public void setInterface(List<Interface> inter) {
		this.inter = inter;
	}
}

class Interface{
	private String inter;
	private String up;
	private String running;
	private String inet_addr;
	private String mask;
	private String bcast;
	private String inet6_addr;
	public String getInter() {
		return inter;
	}
	public void setInter(String inter) {
		this.inter = inter;
	}
	public String getUp() {
		return up;
	}
	public void setUp(String up) {
		this.up = up;
	}
	public String getRunning() {
		return running;
	}
	public void setRunning(String running) {
		this.running = running;
	}
	public String getInet_addr() {
		return inet_addr;
	}
	public void setInet_addr(String inet_addr) {
		this.inet_addr = inet_addr;
	}
	public String getMask() {
		return mask;
	}
	public void setMask(String mask) {
		this.mask = mask;
	}
	public String getBcast() {
		return bcast;
	}
	public void setBcast(String bcast) {
		this.bcast = bcast;
	}
	public String getInet6_addr() {
		return inet6_addr;
	}
	public void setInet6_addr(String inet6_addr) {
		this.inet6_addr = inet6_addr;
	}
}

class Disk{
	private String filesystem;
	private String type;
	private String size;
	private String used;
	private String avail;
	private String use;
	private String mountedOn;
	public String getFilesystem() {
		return filesystem;
	}
	public void setFilesystem(String filesystem) {
		this.filesystem = filesystem;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getUsed() {
		return used;
	}
	public void setUsed(String used) {
		this.used = used;
	}
	public String getAvail() {
		return avail;
	}
	public void setAvail(String avail) {
		this.avail = avail;
	}
	public String getUse() {
		return use;
	}
	public void setUse(String use) {
		this.use = use;
	}
	public String getMountedOn() {
		return mountedOn;
	}
	public void setMountedOn(String mountedOn) {
		this.mountedOn = mountedOn;
	}
}

class SysService{
	private String serv;
	private String status;
	public String getServ() {
		return serv;
	}
	public void setServ(String serv) {
		this.serv = serv;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
class Mem{
	private String name;
	private String total;
	private String used;
	private String avail;
	private String use;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getUsed() {
		return used;
	}
	public void setUsed(String used) {
		this.used = used;
	}
	public String getAvail() {
		return avail;
	}
	public void setAvail(String avail) {
		this.avail = avail;
	}
	public String getUse() {
		return use;
	}
	public void setUse(String use) {
		this.use = use;
	}
}