package com.sunkaisens.omc.service.core;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public interface BrowserDownload {
	/**
	 * 下载浏览器
	 * @param outputStream
	 * @param response
	 * @throws Exception
	 */
	void download(ServletOutputStream outputStream, HttpServletResponse response) throws Exception;
}
