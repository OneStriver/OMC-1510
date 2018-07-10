package com.sunkaisens.omc.service.core;

import java.io.IOException;

import com.sunkaisens.omc.vo.core.Fxo;
import com.sunkaisens.omc.vo.core.Isup;
import com.sunkaisens.omc.vo.core.Sip;

/**
 *  定义OutService接口
 */
public interface OutService {

	/**
	 * 读取出局配置
	 */
	Isup readConf(String isupDir, String sgwDir,boolean isTup) throws IOException;

	/**
	 * 修改出局配置
	 */
	void update(String isupDir,String sgwDir,Isup isup,boolean isTup) throws IOException;

	Sip readSipConf(String sipDir)throws Exception;

	void updateSip(String sipDir, Sip sip) throws Exception;

	Fxo readFxoConf(String fxoDir)throws IOException;

	void updateFxo(String fxoDir, Fxo fxo) throws IOException;

}
