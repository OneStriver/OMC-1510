package com.sunkaisens.omc.mapper.core;

import java.util.List;
import java.util.Map;
/**
 * 
 * 
 * 
 * 
 * 
 * 定义HssMeta接口
 *
 */
public interface HssMetaMapper {
	
	boolean isEnable(String name);
	
	void disableBusiness();
	
	void enableBusiness(Integer[] ids);
	
	void disableDeviceType();
	
	void enableDeviceType(Integer[] ids);
	
	void disableVoiceType();
	
	void enableVoiceType(Integer[] ids);
	
	void disableViewItem();
	
	void enableViewitem(Integer[] ids);
	
	List<Map<String,?>> getEnableDeviceType();
	
	List<Map<String,?>> getAllDeviceType();
	
	List<Map<String,?>> getEnableVoiceType();
	
	List<Map<String,?>> getAllVoiceType();
	
	List<Map<String,?>> getEnableViewItem();
	
	List<Map<String,?>> getAllViewItem();
	
	void insertTerminalType(int terminalId,String terminalName);
	
	void insertDeviceType(int id,String name);
	
	void insertVoiceType(int id,String name);
	
	void insertBusiness(String name,String i18n,int basic);
	
	void insertViewItem(int id,String name);
	
	List<Map<String,?>> getEnableBasicBusiness();
	
	List<Map<String,?>> getNonBasicBusiness();
	
	List<Map<String,?>> getAllBusiness();
	
	List<Map<String,?>> getConfigUserPriority();
	
	List<Map<String,Object>> getConfigGroupPriority();
	
	//获取所有终端类型
	List<Map<String,?>> getTerminalType();
	//每一个
	String selectTerminalById(int terminalId);
	
	Integer getDeviceTypeIdByName(String name);
	
	String getDeviceNameByDeviceTypeId(Integer deviceTypeId);
}
