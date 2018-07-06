package com.sunkaisens.omc.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class ZipCompressUtil {
	private static final Logger log = LoggerFactory.getLogger(ZipCompressUtil.class);
	
    public ZipCompressUtil() {}
    
    /*
    * inputFileName 输入一个文件夹
    * zipFileName 输出一个压缩文件夹
    */
    public static void zip(File zipFile, File inputFile,File exceptDir,String... exceptNames) throws IOException {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
        zip(out, inputFile, 
        		zipFile.getName().substring(0,zipFile.getName().lastIndexOf(".")),
        		exceptDir,exceptNames
        		);
        out.close();
    }
    
    private static void zip(ZipOutputStream out, File f, String base,File exceptDir,String... exceptNames)throws IOException{
    	if(f.getName().startsWith(".")||f.getName().contains(".dbg"))
    		return;
        if (f.isDirectory()) {
        	log.info("处理打包文件夹："+f.getName());
        	if(exceptDir!=null&&f.getName().equals(exceptDir.getName())){
        		return;
        	}
        	File[] fl = f.listFiles();
        	out.putNextEntry(new ZipEntry(base + "/"));
        	base = base.length() == 0 ? "" : base + "/";
        	for (int i = 0; i < fl.length; i++) {
        		zip(out, fl[i], base + fl[i].getName(),exceptDir,exceptNames);
        	}
        	log.info("结束打包文件夹："+f.getName());
        }else {
        	if((exceptNames!=null&&exceptNames.length>0)&&Arrays.asList(exceptNames).contains(f.getName()))
        		return;
        	log.info("打包前>>>>>>>>文件名称："+f.getAbsolutePath());
        	out.putNextEntry(new ZipEntry(base));
        	FileInputStream in = new FileInputStream(f);
        	byte[] b=new byte[4096];
        	int len=0;
        	while ( (len = in.read(b)) != -1) {
        		out.write(b,0,len);
        	}
        	in.close();
        	log.info("打包后>>>>>>>>文件名称："+f.getAbsolutePath());
        }
    }
    
    public static void zip(String baseDir,ZipOutputStream zos,File input,File... exceptFiles) throws IOException{
    	if(Arrays.asList(exceptFiles).contains(input)||input.getName().startsWith("."))
			return;
    	if(input.isDirectory()&&input.list().length>0){
    		File[] fs = input.listFiles();
    		for(File f:fs){
    			if(Arrays.asList(exceptFiles).contains(f))
    				continue;
    			String entryName=input.getName();
    			if(StringUtils.isNotEmpty(baseDir))
        			entryName=baseDir+"/"+entryName;
    			zip(entryName,zos,f);
    		}
    	}else{
    		String entryName=input.getName();
    		log.debug("打包前>>>>>>>>文件名称："+entryName);
    		if(StringUtils.isNotEmpty(baseDir))
    			entryName=baseDir+"/"+entryName;
    		if(input.isDirectory())
    			entryName+="/";
    		ZipEntry e=new ZipEntry(entryName);
    		zos.putNextEntry(e);
    		if(!input.isDirectory()){
    			try(FileInputStream fis=new FileInputStream(input);){
        			IOUtils.copy(fis, zos);
        		}
    		}
    		zos.closeEntry();
    		log.debug("打包后>>>>>>>>文件名称："+entryName);
    	}
    }
    
    public static void zip(String baseDir,File out,File input,File... exceptFiles) throws IOException{
    	try(ZipOutputStream zos=new ZipOutputStream(new FileOutputStream(out))){
    		zip(baseDir,zos,input,exceptFiles);
    	}
    	
    }
    
    public static void zip(String baseDir,ZipOutputStream zos,File[] inputs,File... exceptFiles) throws IOException{
    	for(File input:inputs){
    		zip(baseDir, zos, input, exceptFiles);
    	}
    }
    
    public static void zip(String baseDir,File out,File[] input,File... exceptFiles) throws IOException{
    	try(ZipOutputStream zos=new ZipOutputStream(new FileOutputStream(out))){
    		zip(baseDir,zos,input,exceptFiles);
    	}
    }
    
    public static void unzip(File src,File[] desDirs)throws IOException{
    	try(ZipFile zipFile=new ZipFile(src)){
    		Enumeration<? extends ZipEntry> entrys=zipFile.entries();
    		while(entrys.hasMoreElements()){
    			ZipEntry entry=entrys.nextElement();
    			for(File desDir:desDirs){
    				if(entry.getName().startsWith(desDir.getName())){
    					if(entry.isDirectory()){
    						File desFile=new File(desDir.getParentFile(),entry.getName());
    						desFile.mkdirs();
    					}else{
    						try(InputStream is=zipFile.getInputStream(entry)){
                				File desFile=new File(desDir.getParentFile(),entry.getName());
                				if(!desFile.getParentFile().exists()) desFile.getParentFile().mkdirs();
                				if(!desFile.exists()) desFile.createNewFile();
                    			try(OutputStream output=new FileOutputStream(desFile)){
                    				IOUtils.copy(is, output);
                    			}
                			}
    					}
    				}
    			}
    		}
    	}
    }
}