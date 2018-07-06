package com.sunkaisens.omc.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 *判断进程是否在线的工具类
 *
 */
public abstract class ProgressUtil {
	private static final Logger log = LoggerFactory.getLogger(ProgressUtil.class);

	public static boolean isRunning(String name) {
		InputStream infoIs=null;
		InputStream errIs=null;
		try {
			Process process = null;
			if (isWindows()) {
				String cmd = new String("C:\\Windows\\System32\\tasklist.exe");
				process = Runtime.getRuntime().exec(cmd);
			} else {
				String[] cmd = new String[] { "/bin/sh", "-c",
						"ps -e | grep -n '[[:blank:]]" + name + "'$" };
				process = Runtime.getRuntime().exec(cmd);
			}
			infoIs=process.getInputStream();
			String info = Streams.asString(infoIs);
			errIs=process.getErrorStream();
			String errorInfo = Streams.asString(errIs);
			if (StringUtils.isNotEmpty(errorInfo)) {
				log.error(errorInfo);
			}
			process.waitFor();
			return info.contains(name);
		} catch (Exception e) {
			log.error(e.getMessage());
		}finally{
			try {
				if(infoIs!=null) infoIs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(errIs!=null) errIs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static boolean isWindows() {
		return System.getProperty("os.name").toLowerCase().contains("windows");
	}
}
