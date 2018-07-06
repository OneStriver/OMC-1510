package com.sunkaisens.omc.service.core;

import java.util.List;

import org.xbill.DNS.Name;

import com.sunkaisens.omc.vo.core.DnsAxfr;
import com.sunkaisens.omc.vo.core.DnsDomain;
/**
 * 
 * 
 * 
 * 
 *  定义DnsServerService接口
 */
public interface DnsServerService {

	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回所有域
	 * @param ip
	 * @param port
	 * @return
	 * @throws Exception
	 */
	public List<DnsDomain> list(String ip,int port) throws Exception;

	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回所有域名
	 * @param ip
	 * @param port
	 * @return
	 * @throws Exception
	 */
	public List<DnsAxfr> listAxfr(String domain, String string) throws Exception;

	/**
	 * 
	 * 
	 * 
	 * 
	 * 修改域名
	 * @param hostIP
	 * @param zone
	 * @param sharedKeyName
	 * @param sharedKey
	 * @param name
	 * @param type
	 * @param ttl
	 * @param oldRecord
	 * @param record
	 * @throws Exception
	 */
	void doUpdate(String hostIP, Name zone, String sharedKeyName, String sharedKey, Name name, int type, long ttl,
			String oldRecord, String record) throws Exception;

	/**
	 * 
	 * 
	 * 
	 * 
	 * 删除域名
	 * @param hostIP
	 * @param zone
	 * @param sharedKeyName
	 * @param sharedKey
	 * @param name
	 * @param type
	 * @param oldRecord
	 * @throws Exception
	 */
	void doDelete(String hostIP, Name zone, String sharedKeyName, String sharedKey, Name name, int type,
			String oldRecord) throws Exception;

	/**
	 * 
	 * 
	 * 
	 * 
	 * 添加域名
	 * @param hostIP
	 * @param zone
	 * @param sharedKeyName
	 * @param sharedKey
	 * @param name
	 * @param type
	 * @param ttl
	 * @param record
	 * @throws Exception
	 */
	void doAdd(String hostIP, Name zone, String sharedKeyName, String sharedKey, Name name, int type, long ttl,
			String record) throws Exception;

	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回所有域名
	 * @param host
	 * @param port
	 * @return
	 * @throws Exception
	 */
	public List<DnsAxfr> listAllAxfr(String host, int port) throws Exception;
}
