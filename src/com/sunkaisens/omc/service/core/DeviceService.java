package com.sunkaisens.omc.service.core;

import java.net.SocketException;
import java.util.List;

import com.sunkaisens.omc.vo.core.LocalEth;
/**
 * 
 * 
 * 
 * 
 *  定义DeviceService接口
 *
 */
public interface DeviceService {
    /**
     * 
     * 
     * 
     * 
     * 获取LocalEth列表
     * @return
     * @throws SocketException
     */
	public List<LocalEth> list() throws SocketException;
}
