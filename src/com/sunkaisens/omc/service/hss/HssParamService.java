package com.sunkaisens.omc.service.hss;
/**
 * 
 * 
 * 
 * 
 * 
 * 定义HssParamService接口
 *
 */
public interface HssParamService {

	/**
	 * 
	 * 
	 * 
	 * 
	 * HssParam 更新操作
	 * @param device
	 * @param voice
	 * @param business
	 */
	void update(Integer[] device,Integer[] voice,Integer[] business,Integer[] viewItem);
}
