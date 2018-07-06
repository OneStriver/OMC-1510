package com.sunkaisens.omc.service.core;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.sf.json.JSONArray;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.po.core.Entity;
import com.sunkaisens.omc.po.core.Module;
import com.sunkaisens.omc.vo.core.PageBean;
/**
 * 
 * 
 * 
 * 
 *  定义EntityService接口
 *
 */
public interface EntityService {
	/**
	 * 
	 * 
	 * 
	 * 
	 * 下发网元
	 * @param moduleId
	 * @param json
	 * @param cardNum
	 * @param name
	 * @throws IOException
	 * @throws CustomException
	 */
	void save(Integer moduleId,Integer instId,String json, Integer cardNum, String name) throws IOException, CustomException;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 删除网元
	 * @param id
	 * @throws Exception
	 */
	void deleteById(Integer id) throws Exception;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回所有网元实体
	 * @return
	 */
	List<Entity> findAll();
	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回网元实体by id
	 * @param id
	 * @return
	 */
	Entity getById(Integer id);
	/**
	 * 
	 * 
	 * 
	 * 
	 * 实体分页
	 * @param requestPage
	 * @param pageSize
	 * @param sort
	 * @param order
	 * @return
	 */
	PageBean getPageBean(Integer requestPage, Integer pageSize,String sort,String order);
	/**
	 * 
	 * 
	 * 
	 * 
	 * 删除实体
	 * @param ids
	 * @throws IOException
	 * @throws CustomException
	 */
	void deleteByIds(Integer[] ids) throws IOException, CustomException;
	/**
	 * 
	 * 
	 * 
	 * 
	 * getComponentModule
	 * @param moduleId
	 * @return
	 */
	Module getComponentModule(Integer moduleId);
	/**
	 * 
	 * 
	 * 
	 * 
	 * viewComponentModule
	 * @param entityId
	 * @return
	 * @throws CustomException
	 * @throws IOException
	 */
	Module viewComponentModule(Integer entityId) throws CustomException, IOException;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 更新配置
	 * @param json
	 * @param id
	 * @throws CustomException
	 * @throws IOException
	 */
	void update(String json, Integer id) throws CustomException, IOException;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 读取网元实体配置
	 * @param entityId
	 * @return
	 * @throws IOException
	 */
	JSONArray readConfArr(Integer entityId) throws IOException;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 网元启动
	 * @param id
	 * @throws CustomException
	 */
	void start(Integer id) throws CustomException;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 网元重启
	 * @param id
	 * @throws CustomException
	 */
	void restart(Integer id) throws CustomException;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 网元停止
	 * @param id
	 * @throws CustomException
	 */
	void stop(Integer id) throws CustomException;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回网元实体ByModuleAndInstId
	 * @param moduleId
	 * @param instId
	 * @return
	 */
	Entity getByModuleAndInstId(Integer moduleId, Integer instId);
	/**
	 * 
	 * 
	 * 
	 * 
	 * 修改实体信息
	 * @param entity
	 */
	void update(Entity entity);
	/**
	 * 
	 * 
	 * 
	 * 
	 * 修改实体信息
	 * @param entity
	 */
	void updateEntity(Integer id, Integer instId, Integer moduleId, File f) throws Exception;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回此网元所有实体
	 * @param moduleId
	 * @return
	 */
	List<Entity> find(Integer moduleId);
	/**
	 * 
	 * 
	 * 
	 * 
	 * 设置启动类型
	 * @param id
	 * @param b
	 * @throws CustomException
	 */
	void setStartup(Integer id, boolean b) throws CustomException;
}
