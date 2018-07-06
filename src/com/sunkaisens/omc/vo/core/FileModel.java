package com.sunkaisens.omc.vo.core;

import java.util.Date;
/**
 * 
 * 
 * 
 * 
 * FileModel 实体类
 *
 */
public class FileModel {
	//文件名
	private String name;
	//文件路径
	private String path;
	//是否目录
	private Boolean isDirectory;
	//是否文件
	private Boolean isFile;
	//是否隐藏
	private Boolean isHidden;
	//最后修改时间
	private Date lastModified;
	//可读
	private Boolean canRead;
	//可写
	private Boolean canWrite;
	//可执行
	private Boolean canExecute;
	//文件大小
	private Long size;
	
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Boolean getIsDirectory() {
		return isDirectory;
	}
	public void setIsDirectory(Boolean isDirectory) {
		this.isDirectory = isDirectory;
	}
	public Boolean getIsHidden() {
		return isHidden;
	}
	public void setIsHidden(Boolean isHidden) {
		this.isHidden = isHidden;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	public Boolean getCanRead() {
		return canRead;
	}
	public void setCanRead(Boolean canRead) {
		this.canRead = canRead;
	}
	public Boolean getCanWrite() {
		return canWrite;
	}
	public void setCanWrite(Boolean canWrite) {
		this.canWrite = canWrite;
	}
	public Boolean getCanExecute() {
		return canExecute;
	}
	public void setCanExecute(Boolean canExecute) {
		this.canExecute = canExecute;
	}
	public Boolean getIsFile() {
		return isFile;
	}
	public void setIsFile(Boolean isFile) {
		this.isFile = isFile;
	}
}
