package com.sunkaisens.omc.service.core;

import java.util.List;
/**
 * 
 * 
 * 
 * 
 *  定义SelfCheckService接口
 *
 */
public interface SelfCheckService {
	/**
	 * 
	 * 
	 * 
	 * 
	 * 实时自检
	 * @return
	 * @throws Exception
	 */
	List<Object> rtStatus()throws Exception ;

	/**
	 * 
	 * 
	 * 
	 * 
	 * 开机自检
	 * @return
	 * @throws Exception
	 */
	List<Object> startupStatus()throws Exception;

}
