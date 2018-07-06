package com.sunkaisens.omc.service.core;

import java.io.File;
import java.util.List;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.po.core.So;
/**
 * 
 * 
 * 
 * 
 * 
 *  定义SoService接口
 *
 */
public interface SoService {

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * so列表
	 * @return
	 */
	List<So> findAll();

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 添加库文件
	 * @param f
	 * @param name
	 * @param auth
	 * @throws Exception
	 */
	void save(File f, String name,boolean auth) throws Exception;

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 删除库文件
	 * @param names
	 * @throws CustomException
	 */
	void delete(String[] names) throws CustomException;
}
