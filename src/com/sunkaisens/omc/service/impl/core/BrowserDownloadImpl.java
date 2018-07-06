package com.sunkaisens.omc.service.impl.core;

import java.io.File;
import java.nio.charset.Charset;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.sunkaisens.omc.service.core.BrowserDownload;
import com.sunkaisens.omc.util.ServletContextUtil;
import com.sunkaisens.omc.util.ZipCompressUtil;
@Service
public class BrowserDownloadImpl implements BrowserDownload
{
	@Override
	public void download(ServletOutputStream outputStream,
			HttpServletResponse response) throws Exception {
		ZipOutputStream zos=new ZipOutputStream(outputStream,Charset.forName("UTF-8"));
		ServletContext context=ServletContextUtil.getServletContext();
		String browser=context.getRealPath("/browser");
		ZipCompressUtil.zip(null, zos, new File(browser));
		zos.close();
		
	}

}
