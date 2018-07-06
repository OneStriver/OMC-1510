package com.sunkaisens.omc.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.sunkaisens.omc.po.core.Log;
/**
 * 自定义异常处理机制
 */
public class CustomExceptionResolver implements HandlerExceptionResolver {

	protected final Logger log = LoggerFactory.getLogger(getClass());
	/**
	 * 对出现的异常进行日志记录以及将此异常返回前台提供前台显示
	 */
	@Override
	@ResponseBody
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		ex.printStackTrace();
		Log log=(Log)request.getAttribute("log");
		if(log!=null){
			log.setReason(ex.getMessage());
			log.setSuccess(false);
		}
		CustomException customException = null;
		if(ex instanceof CustomException){
			customException = (CustomException)ex;
		}else{
			customException = new CustomException("未知错误："+ex.getMessage());
		}
		String message = customException.getMessage();
		if(message==null){
			message="未知错误!";
		} 
		JSONObject json=new JSONObject();
		json.element("error", message);
		ModelAndView mav=new ModelAndView("error");
		mav.addObject("error",json.toString());
		return mav;
	}

}
