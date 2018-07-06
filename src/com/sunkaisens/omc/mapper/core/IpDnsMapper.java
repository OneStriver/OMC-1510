package com.sunkaisens.omc.mapper.core;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sunkaisens.omc.po.core.IpDns;
/**
 * 
 * 
 * 
 * 
 * 
 * 定义DNS接口
 *
 */
public interface IpDnsMapper {
	void insert(IpDns ipDns);
	
	IpDns selectByEthAndCardNum(@Param("eth") String eth,@Param("cardNum") Integer cardNum);

	void update(IpDns po);
	
	List<IpDns> exist(@Param("dns")String dns,@Param("eth") String eth,@Param("cardNum") Integer cardNum);
	
	void deleteByEthAndCardNum(@Param("eth") String eth,@Param("cardNum") Integer cardNum);
}
