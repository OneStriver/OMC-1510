package com.sunkaisens.omc.listener;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sunkaisens.omc.component.Installer;
import com.sunkaisens.omc.mapper.core.PrivilegeMapper;
import com.sunkaisens.omc.util.ServletContextUtil;
/**
 * 
 * 
 * 
 * 
 * 
 * 用于在web容器启动过程中对数据库里的字段进行初始化
 *
 */
public class InitDateServletContextListener implements ServletContextListener {
	//获取log对象
	protected final Logger log = LoggerFactory.getLogger(getClass());
	/**
	 * 在容器加载过程中初始化数据库
	 */
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext=sce.getServletContext();
		ServletContextUtil.setServletContext(servletContext);
		WebApplicationContext springContext=WebApplicationContextUtils.getWebApplicationContext(servletContext);
		Installer installer=(Installer)springContext.getBean("installer");
		installer.install();
		
		PrivilegeMapper privilegeMapper=(PrivilegeMapper)springContext.getBean("privilegeMapper");
		List<String> privileges=privilegeMapper.selectAllUrl();
		servletContext.setAttribute("allPrivileges", privileges);
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}
	
}
