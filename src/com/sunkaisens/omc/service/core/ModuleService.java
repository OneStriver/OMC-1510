package com.sunkaisens.omc.service.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.po.core.Module;
import com.sunkaisens.omc.vo.core.PageBean;
/**
 * 
 * 
 * 
 * 
 * 网元Service
 * @author shenchao
 *
 */
public interface ModuleService {

	/**
	 * 
	 * 
	 * 
	 * 
	 * 分页
	 * @param page
	 * @param pageSize
	 * @return
	 */
	PageBean getPageBean(int pageNum, Integer pageSize);

	/**
	 * 
	 * 
	 * 
	 * 
	 * 上传网元
	 * @param name
	 * @param f
	 * @param moduleId
	 * @throws CustomException
	 * @throws IOException
	 */
	void saveUploadModule(String name,File f,Integer moduleId) throws CustomException, IOException;

	/**
	 * 
	 * 
	 * 
	 * 
	 * 更新
	 * @param module
	 */
	void update(Module module);

	/**
	 * 
	 * 
	 * 
	 * 
	 * 删除
	 * @param ids
	 * @throws IOException
	 * @throws CustomException
	 */
	void delete(Integer[] ids) throws IOException, CustomException;

	/**
	 * 
	 * 
	 * 
	 * 
	 * 所有网元
	 * @return
	 */
	List<Module> findAll();

	/**
	 * 
	 * 
	 * 
	 * 
	 * 批量上传
	 * @param is
	 * @throws Exception
	 */
	void saveUploadAllModule(InputStream is) throws Exception;
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回byname
	 * @param name
	 * @return
	 */
	Module getByName(String name);

	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回by id
	 * @param parseInt
	 * @return
	 */
	Module findById(Integer parseInt);
}
