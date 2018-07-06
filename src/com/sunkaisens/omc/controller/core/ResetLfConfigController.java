package com.sunkaisens.omc.controller.core;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.service.core.ResetLfService;

import net.sf.json.JSONObject;
/**
 * 
 * 
 * 
 * 
 * ResetLfConfig  Controller
 *
 */
@Controller
@RequestMapping(value="/resetLf")
public class ResetLfConfigController {
	//注入ResetLfService
	@Resource
	private ResetLfService service;
	/**
	 * 
	 * 
	 * 返回前台resetLf/list视图
	 * @return
	 */
	@RequestMapping("listUI")
	public ModelAndView listUI(){
		ModelAndView mav=new ModelAndView("resetLf/list");
		return mav;
	}
	/**
	 * 
	 * 
	 * 重启操作
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping(value="reset")
	public @ResponseBody String reset() throws CustomException{
		service.reset();
		JSONObject json=new JSONObject();
		json.element("msg", "系统将在下次重启后还原");
		return json.toString();
	}
}
