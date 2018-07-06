package com.sunkaisens.omc.util;

import javax.servlet.ServletContext;
/**
 * 
 * 
 * WEB应用上下文工具类
 *
 */
public abstract class ServletContextUtil {

	private static ServletContext SERVLET_CONTEXT;
	public static ServletContext getServletContext(){
		return SERVLET_CONTEXT;
	}
	
	public static void setServletContext(ServletContext servletContext){
		SERVLET_CONTEXT=servletContext;
	}
}
