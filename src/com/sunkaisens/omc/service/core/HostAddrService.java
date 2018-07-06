package com.sunkaisens.omc.service.core;

import java.util.List;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.vo.core.HostAddr;
/**
 * 
 * 
 * 
 * 
 * 定义HostAddrService接口 
 *
 */
public interface HostAddrService {

	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回host
	 * @return
	 * @throws CustomException
	 */
	List<HostAddr> list() throws CustomException;

	/**
	 * 
	 * 
	 * 
	 * 
	 * 修改host
	 * @param model
	 * @throws CustomException
	 */
	void update(HostAddr model)throws CustomException;

	/**
	 * 
	 * 
	 * 
	 * 
	 * 添加host
	 * @param model
	 * @throws CustomException
	 */
	void save(HostAddr model)throws CustomException;

	/**
	 * 
	 * 
	 * 
	 * 
	 * 删除host
	 * @param model
	 * @throws CustomException
	 */
	void delete(String[] ips, String[] hostNames)throws CustomException;

}
