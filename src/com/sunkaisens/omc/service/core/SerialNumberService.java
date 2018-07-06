package com.sunkaisens.omc.service.core;

import java.util.List;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.vo.core.SerialNumber;
/**
 * 
 * 
 * 
 * 
 *  定义SerialNumberService接口
 *
 */
public interface SerialNumberService {

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 读取板卡序列号
	 * @return
	 * @throws CustomException
	 */
	List<SerialNumber> readSerial() throws CustomException;

}
