package com.sunkaisens.omc.util.hss;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import com.sunkaisens.omc.context.core.OmcServerContext;

public class FileReadUtil {
	private static File hosts = new File(OmcServerContext.getInstance().getMainBackUpAddress());
	
	//获取host文件中的配置
	public static String getHostList(){
		String deviceStatus = "";
		try {
			List<String> lines=FileUtils.readLines(hosts);
			for(String line:lines){
				if(StringUtils.isNotBlank(line)){
					String lineTrim = line.trim();
					String[] ip_host=lineTrim.split("=",2);
					if(ip_host[0].equals("deviceStatus")){
						deviceStatus = ip_host[1];
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return deviceStatus;
	}
}
