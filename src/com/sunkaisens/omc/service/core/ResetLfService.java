package com.sunkaisens.omc.service.core;

import com.sunkaisens.omc.exception.CustomException;
/**
 * 
 * 
 * 
 * 
 * 
 *  定义ResetLFService接口
 *
 */
public interface ResetLfService {

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 恢复出厂设置
	 * @throws CustomException
	 */
	void reset() throws CustomException;

}
