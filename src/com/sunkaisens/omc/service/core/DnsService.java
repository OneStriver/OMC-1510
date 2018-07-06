package com.sunkaisens.omc.service.core;

import java.util.List;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.vo.core.Dns;
/**
 * 
 * 
 * 
 * 
 * 定义DnsService接口
 *
 */
public interface DnsService{
    /**
     * 
     * 
     * 
     * 
     * 获取DNS列表
     * @param cardNum
     * @return
     * @throws CustomException
     */
	List<Dns> query(Integer cardNum)throws CustomException;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 更新DNS
	 * @param model
	 * @throws CustomException
	 */
	void update(Dns model) throws CustomException;

	
}
