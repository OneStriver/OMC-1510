package com.sunkaisens.omc.controller.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import org.springframework.core.convert.converter.Converter;

/**
 * 
 * <p>Title: CustomLongConverter</p>
 * <p>Description:时间字符串长整型转换器(单位秒) </p>
 */
public class CustomLongConverter implements Converter<String,Long>{

	@Override
	public Long convert(String source) {
//		System.out.println("Long转换器："+source);
		try {
			Pattern p=Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
			if(!p.matcher(source).matches())
				return new Long(source);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//转成直接返回
			return simpleDateFormat.parse(source).getTime()/1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//如果参数绑定失败返回null
		return null;
	}

}
