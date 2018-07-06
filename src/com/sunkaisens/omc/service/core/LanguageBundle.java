package com.sunkaisens.omc.service.core;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/**
 * 
 * 
 * 
 * 
 *  定义LanguageBundle接口
 */
public interface LanguageBundle {
	/**
	 * 
	 * 
	 * 
	 * 
	 * 获取ResourceBundle
	 * @param request
	 * @param session
	 * @return
	 */
  public ResourceBundle getBundle(HttpServletRequest request ,HttpSession session);
}
