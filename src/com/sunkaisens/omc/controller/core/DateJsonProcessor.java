package com.sunkaisens.omc.controller.core;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
/**
 * 
 * 
 * 
 * 日期格式化处理
 *
 */
public class DateJsonProcessor implements JsonValueProcessor {

	@Override
	public Object processArrayValue(Object arg0, JsonConfig arg1) {
		return arg0;
	}
    /**
     * 对日期进行规定的格式化处理
     */
	@Override
	public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
		if(arg1 instanceof Date){
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return df.format(arg1);
		}
		return arg1;
	}

}
