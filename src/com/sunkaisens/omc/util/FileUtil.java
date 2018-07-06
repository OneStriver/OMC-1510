package com.sunkaisens.omc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.lang.ArrayUtils;
/**
 * 
 * 
 * 
 * 针对文件的工具类
 *
 */
public abstract class FileUtil {

	public static String readFile(File file) throws Exception {
		FileInputStream is = null;
		try {
			is = new FileInputStream(file);
			StringBuilder sb = new StringBuilder();
			byte[] b = new byte[1024];
			int len = 0;
			while ((len = is.read(b)) != -1) {
				sb.append(new String(b, 0, len));
			}
			return sb.toString();
		} finally {
			if (is != null)
				is.close();
		}
	}
	
	/**
	 * 判断文件是否是ZIP文件
	 * @param file
	 * @return
	 */
	public static boolean isZip(File file){
		FileInputStream is=null;
		try {
			is = new FileInputStream(file);
			byte[] b=new byte[4];
			int len=is.read(b);
			if(len<4) return false;
			String str=ByteUtil.bytes2Hex(b);
			if(str.equalsIgnoreCase("504b0304"))
				return true;
			else 
				return false;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(is!=null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return false;
	}
	
	public static boolean isZip(byte[] bytes){
		String str=ByteUtil.bytes2Hex(ArrayUtils.subarray(bytes, 0, 4));
		if(str.equalsIgnoreCase("504b0304"))
			return true;
		else 
			return false;
	}
	
	
}
