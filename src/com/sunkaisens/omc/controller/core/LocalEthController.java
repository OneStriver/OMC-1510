package com.sunkaisens.omc.controller.core;


import java.net.SocketException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sunkaisens.omc.service.core.DeviceService;
import com.sunkaisens.omc.vo.core.LocalEth;
/**
 * 
 * 
 * 
 * 
 * 
 * 
 *LocalEth  Controller
 */
@Controller
@RequestMapping("/localeth")
public class LocalEthController {

	@Resource
	DeviceService service;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 返回locaolist视图
	 * @return
	 * @throws SocketException
	 */
	@RequestMapping("list")
	public ModelAndView list() throws SocketException{
		ModelAndView model=new ModelAndView("eth/locallist");
		List<LocalEth> eths=service.list();
		model.addObject("eths", eths);
		return model;
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 更新操作
	 * @param jsonstr
	 * @return
	 */
	@RequestMapping("update")
	public @ResponseBody String update(@RequestBody String jsonstr){
		
		return "OK";
	}
	
}
