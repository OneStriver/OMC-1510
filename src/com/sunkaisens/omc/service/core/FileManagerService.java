package com.sunkaisens.omc.service.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipOutputStream;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.vo.core.FileModel;
/**
 * 
 * 
 * 
 * 
 * 定义FileManagerService接口 
 *
 */
public interface FileManagerService {
	/**
	 * 
	 * 
	 * 
	 * 
	 * 获取文件列表
	 * @param dir
	 * @return
	 * @throws CustomException
	 */
	List<FileModel> listFileModel(String dir) throws CustomException;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 删除文件
	 * @param fileModel
	 * @throws CustomException
	 */
	void deleteFile(FileModel fileModel) throws CustomException;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 读取文件
	 * @param fileModel
	 * @return
	 * @throws Exception
	 */
	String readFile(FileModel fileModel) throws Exception;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 下载单个文件
	 * @param fileModel
	 * @return
	 * @throws IOException
	 */
	InputStream download(FileModel fileModel) throws IOException;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 全部下载
	 * @param zipOutputStream
	 * @param path
	 * @throws IOException
	 */
	void downloadAll(ZipOutputStream zipOutputStream, String path) throws IOException;

	
}
