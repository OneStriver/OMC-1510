package com.sunkaisens.omc.interceptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sunkaisens.omc.mapper.core.LogMapper;
import com.sunkaisens.omc.mapper.core.PrivilegeMapper;
import com.sunkaisens.omc.po.core.Log;
import com.sunkaisens.omc.po.core.Privilege;
import com.sunkaisens.omc.po.core.User;

/**
 *用于权限检查的拦截器,对拥有不同权限的用户进行不同的处理
 */
public class CheckPrivilegeInterceptor implements HandlerInterceptor {
    //获取log对象
	Logger log=LoggerFactory.getLogger(CheckPrivilegeInterceptor.class);
	
	/**
	 * 对用户请求进行权限检查
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		String contextPath=request.getContextPath();
		String uri =request.getRequestURI();
		String privilegeUrl=uri.substring(contextPath.length()+1,uri.indexOf(".action"));
		if (user == null) {
			if (privilegeUrl.equals("user/login")||privilegeUrl.endsWith("user/switchLanguage")||privilegeUrl.endsWith("user/checkBrowserUI")||privilegeUrl.endsWith("user/download")) {
				return true;
			} else {
				System.err.println("privilegeUrl:"+privilegeUrl);
				if(privilegeUrl.equals("user/home")){
					request.getRequestDispatcher("/WEB-INF/jsp/user/login.jsp").forward(request, response);
				}else{
					request.getRequestDispatcher("/WEB-INF/jsp/user/disConnect.jsp").forward(request, response);
				}
			}
		}else if("nouser".equals(user.getName())){
			System.err.println("nouser>>>privilegeUrl:"+privilegeUrl);
			init(request);
			return true;
		}else {
			System.err.println("else>>>privilegeUrl:"+privilegeUrl);
			init(request);
			if (user.hasPrivilegeByUrl(privilegeUrl)) {
				return true;
			} else {
				request.getRequestDispatcher("/WEB-INF/jsp/noPrivilege.jsp").forward(request, response);
			}
		}
		return false;
	}
	
	/**
	 * 将合法请求封装对象
	 */
	private void init(HttpServletRequest request){
		String uri =request.getRequestURI();
		if(uri.endsWith("UI.action"))return;
		String contextPath=request.getContextPath();
		String privilegeUrl=uri.substring(contextPath.length()+1,uri.indexOf(".action"));
		ServletContext servletContext=request.getServletContext();
		WebApplicationContext springContext=WebApplicationContextUtils.getWebApplicationContext(servletContext);
		PrivilegeMapper privilegeMapper=springContext.getBean(PrivilegeMapper.class);
		Privilege privilege=privilegeMapper.selectByUrl(privilegeUrl);
		if(privilege!=null){
			User user=(User)request.getSession().getAttribute("user");
			Log log=new Log(privilege.getName(), user.getName());
			log.setSuccess(true);
			request.setAttribute("log", log);
		}
	}

	//进入Handler方法之后，返回modelAndView之前执行
	//应用场景从modelAndView出发：将公用的模型数据(比如菜单导航)在这里传到视图，也可以在这里统一指定视图
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	//执行Handler完成执行此方法
	//应用场景：统一异常处理，统一日志处理
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		Log log=(Log)request.getAttribute("log");
		if(log!=null){
			ServletContext servletContext=request.getServletContext();
			WebApplicationContext springContext=WebApplicationContextUtils.getWebApplicationContext(servletContext);
			LogMapper logMapper=springContext.getBean(LogMapper.class);
			logMapper.insert(log);
		}
	}
}
