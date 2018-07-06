package com.sunkaisens.omc.service.core;

import java.io.File;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
/**
 * 
 * 
 * 
 * 
 * 定义OnekeyService接口 
 *
 */
public interface OnekeyService {
	/**
	 * 
	 * 
	 * 
	 * 
	 * 一键备份
	 * @param outputStream
	 * @param response
	 * @throws Exception
	 */
	void download(ServletOutputStream outputStream, HttpServletResponse response) throws Exception;

	/**
	 * 
	 * 
	 * 
	 * 
	 * 一键还原
	 * @param outputStream
	 * @param response
	 * @throws Exception
	 */
	void restore(File zip) throws Exception;
	
	public String getHostName()throws Exception;
}
