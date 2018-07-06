package com.sunkaisens.omc.controller.core;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sunkaisens.omc.service.core.SelfCheckService;

/**
 * 
 * 
 * 
 * 自检 SelfCheck   Controller
 *
 *
 */
@Controller@RequestMapping("/selfCheck")
public class SelfCheckController{
	//注入SelfCheckService
	@Resource
	private SelfCheckService service;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 调用实时自检，并返回selfCheck/rt视图
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("rtCheck")
	public ModelAndView rtCheck() throws Exception{
		ModelAndView mav=new ModelAndView("selfCheck/rt");
		List<Object> arr=service.rtStatus();
		mav.addObject("status", arr);
		return mav;
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 调用开机自检，并返回selfCheck/startup视图
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("startupCheck")
	public ModelAndView start2upCheck() throws Exception{
		ModelAndView mav=new ModelAndView("selfCheck/startup");
		List<Object> arr=service.startupStatus();
		mav.addObject("status", arr);
		return mav;
	}
}
