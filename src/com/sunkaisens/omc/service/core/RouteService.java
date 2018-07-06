package com.sunkaisens.omc.service.core;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.vo.core.PageBean;
import com.sunkaisens.omc.vo.core.Route;
/**
 * 
 * 
 * 
 * 
 * 
 *  定义RouteService接口
 *
 */
public interface RouteService{

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 添加路由
	 * @param route
	 * @throws CustomException
	 */
	void add(Route route) throws CustomException;

	/**
	 * 
	 * 
	 * 
	 * 
	 * 删除路由
	 * @param cardNums
	 * @param destinations
	 * @param gateways
	 * @param genmasks
	 * @throws CustomException
	 */
	void delete(Integer[] cardNums, String[] destinations,String[] gateways,String[] genmasks)throws CustomException;

	/**
	 * 
	 * 
	 * 
	 * 
	 * 修改路由
	 * @param route
	 * @throws CustomException
	 */
	void update(Route route)throws CustomException;

	/**
	 * 
	 * 
	 * 
	 * 
	 * 路由列表
	 * @param cardNum
	 * @return
	 * @throws CustomException
	 */
	PageBean list(Integer cardNum, Integer page, Integer rows)throws CustomException;
}
