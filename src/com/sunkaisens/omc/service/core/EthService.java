package com.sunkaisens.omc.service.core;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.vo.core.Eth;
import com.sunkaisens.omc.vo.core.PageBean;
/**
 * 
 * 
 * 
 * 
 * 定义EthService接口 
 *
 */
public interface EthService {

	/**
	 * 
	 * 
	 * 
	 * 
	 * 活动网卡列表
	 * @param cardNum
	 * @param rows 
	 * @param page 
	 * @return
	 * @throws CustomException
	 */
	public PageBean listActivate(Integer cardNum, Integer page, Integer rows) throws CustomException;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 活动网卡修改
	 * @param cardNum
	 * @return
	 * @throws CustomException
	 */
	public void activateUpdate(Eth eth) throws CustomException;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 添加虚拟网卡
	 * @param cardNum
	 * @return
	 * @throws CustomException
	 */
	public void addVirtualEth(Eth eth) throws CustomException;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 添加网卡文件
	 * @param cardNum
	 * @return
	 * @throws CustomException
	 */
	public void addConfEth(Eth eth, String[] dns) throws CustomException;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 静态网卡列表
	 * @param cardNum
	 * @return
	 * @throws CustomException
	 */
	public PageBean listStatic(Integer cardNum, Integer page, Integer rows) throws CustomException;

	/**
	 * 
	 * 
	 * 
	 * 
	 * 去激活
	 * @param names
	 * @param cardNum
	 * @throws CustomException
	 */
	public void deactivate(String[] names, Integer cardNum) throws CustomException;

	/**
	 * 
	 * 
	 * 
	 * 
	 * 删除网卡
	 * @param cardNum
	 * @param eth
	 * @throws CustomException
	 */
	public void delete(Integer cardNum,String eth) throws CustomException;

	/**
	 * 
	 * 
	 * 
	 * 
	 * 修改静态网卡
	 * @param eth
	 * @param dns
	 * @throws CustomException
	 */
	public void staticUpdate(Eth eth, String[] dns) throws CustomException;

}
