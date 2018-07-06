package com.sunkaisens.omc.service.core;

import java.io.IOException;

import com.sunkaisens.omc.vo.core.Fxo;
import com.sunkaisens.omc.vo.core.Isup;
import com.sunkaisens.omc.vo.core.Sip;
/**
 * 
 * 
 * 
 * 
 *  定义OutService接口
 *
 */
public interface OutService {

	/**
	 * 
	 * 
	 * 
	 * 
	 * 读取出局配置
	 * @param isupDir
	 * @param sgwDir
	 * @param isTup
	 * @return
	 * @throws IOException
	 */
	Isup readConf(String isupDir, String sgwDir,boolean isTup) throws IOException;

	/**
	 * 
	 * 
	 * 
	 * 
	 * 修改出局配置
	 * @param isupDir
	 * @param sgwDir
	 * @param isup
	 * @param isTup
	 * @throws IOException
	 */
	void update(String isupDir,String sgwDir,Isup isup,boolean isTup) throws IOException;

	Sip readSipConf(String sipDir)throws IOException;

	void updateSip(String sipDir, Sip sip) throws IOException;

	Fxo readFxoConf(String fxoDir)throws IOException;

	void updateFxo(String fxoDir, Fxo fxo) throws IOException;

}
