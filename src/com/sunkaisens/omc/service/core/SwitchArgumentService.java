package com.sunkaisens.omc.service.core;

import java.util.List;

import com.sunkaisens.omc.vo.core.NumberTranslationResult;

/**
 *定义SwitchArgumentService接口  
 */
public interface SwitchArgumentService {

	/**
	 * readNumberTranslations
	 */
	List<NumberTranslationResult> readNumberTranslations() throws Exception;

	/**
	 * updateNumberTranslations
	 */
	void updateNumberTranslations(List<String> originalNum, List<String> mappingNum,List<String> serviceOption) throws Exception;

}
