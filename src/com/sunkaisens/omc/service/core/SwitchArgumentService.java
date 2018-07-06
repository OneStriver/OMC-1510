package com.sunkaisens.omc.service.core;

import java.io.IOException;
import java.util.List;

import com.sunkaisens.omc.vo.core.NumberTranslation;
/**
 * 
 * 
 * 
 * 
 * 
 *定义SwitchArgumentService接口  
 *
 */
public interface SwitchArgumentService {

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * readNumberTranslations
	 * @return
	 * @throws Exception
	 */
	List<NumberTranslation> readNumberTranslations() throws Exception;

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * updateNumberTranslations
	 * @param ori
	 * @param des
	 * @throws Exception
	 */
	void updateNumberTranslations(String[] ori, String[] des) throws Exception;

}
