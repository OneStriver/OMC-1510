package com.sunkaisens.omc.controller.core;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.service.core.EthService;
import com.sunkaisens.omc.vo.core.Eth;
import com.sunkaisens.omc.vo.core.PageBean;
import net.sf.json.JSONObject;

/**
 * ETH Controller(网卡管理)
 */
@Controller@RequestMapping("/eth")
public class EthController{
	@Resource
	private EthService service;
	
	/**
	 * 返回前台eth/list视图
	 */
	@RequestMapping("listUI")
	public String listUI(){
		return "eth/list";
	}
	
	/**
	 * 返回前台activelist视图
	 */
	@RequestMapping("listActivateUI")
	public ModelAndView listActivateUI(){
		ModelAndView mav=new ModelAndView("eth/activelist");
		PageBean pageBean=new PageBean();
		mav.addObject("pageBean",pageBean);
		return mav;
	}
	
	/**
	 * 向前台返回数据
	 */
	@RequestMapping("listActivate")
	public @ResponseBody PageBean listActivate(
			@RequestParam(defaultValue="0") Integer cardNum,Integer page,Integer rows) throws CustomException{
		
		return  service.listActivate(cardNum,page,rows);
	}
	
	/**
	 * 修改操作
	 */
	@RequestMapping(value="activateUpdate",produces="text/html;charset=UTF-8")
	public @ResponseBody String activateUpdate(Eth eth) throws CustomException{
		service.activateUpdate(eth);
		JSONObject json=new JSONObject();
		json.element("msg", "修改完毕");
		return json.toString();
	}
	
	/**
	 * 激活操作
	 */
	@RequestMapping(value="activate",produces="text/html;charset=UTF-8")
	public @ResponseBody String activate(Eth eth) throws CustomException{
		service.activateUpdate(eth);
		JSONObject json=new JSONObject();
		json.element("msg", "激活成功");
		return json.toString();
	}
	
	/**
	 * 去激活操作
	 */
	@RequestMapping(value="deactivate",produces="text/html;charset=UTF-8")
	public @ResponseBody String deactivate(
			@RequestParam(defaultValue="0")Integer cardNum,String name) throws CustomException{
		service.deactivate(new String[]{name},cardNum);
		JSONObject json=new JSONObject();
		json.element("msg", "去激活成功");
		return json.toString();
	}
	
	/**
	 * 返回eth/staticlist视图
	 */
	@RequestMapping("listStaticUI")
	public ModelAndView listStaticUI(){
		ModelAndView mav=new ModelAndView("eth/staticlist");
		PageBean pageBean=new PageBean();
		mav.addObject("pageBean",pageBean);
		return mav;
	}
	
	/**
	 * 往前台返回数据
	 */
	@RequestMapping("listStatic")
	public @ResponseBody PageBean listStatic(
			@RequestParam(defaultValue="0")Integer cardNum, Integer page, Integer rows) throws CustomException{
		
		return service.listStatic(cardNum,page,rows);
	}
	
	/**
	 * activate添加操作
	 */
	@RequestMapping(value="activateAdd",produces="text/html;charset=UTF-8")
	public @ResponseBody String activateAdd(Eth eth) throws CustomException{
		service.addVirtualEth(eth);
		JSONObject json=new JSONObject();
		json.element("msg", "添加完毕");
		return json.toString();
	}
	
	/**
	 * static添加操作
	 */
	@RequestMapping(value="staticAdd",produces="text/html;charset=UTF-8")
	public @ResponseBody String staticAdd(
			@RequestParam(defaultValue="0")Integer cardNum,Eth eth,String[] dns) throws CustomException{
		service.addConfEth(eth,dns);
		JSONObject json=new JSONObject();
		json.element("msg", "添加完毕");
		return json.toString();
	}
	
	/**
	 * 删除操作
	 */
	@RequestMapping("delete")
	public @ResponseBody String delete(
			Integer[] cardNums,String[] ids) throws CustomException{
		for(int i=0;i<ids.length;i++)
			service.delete(cardNums[i],ids[i]);
		JSONObject json=new JSONObject();
		json.element("msg", "删除完毕");
		return json.toString();
	}
	
	/**
	 * static 更新操作
	 */
	@RequestMapping(value="staticUpdate",produces="text/html;charset=UTF-8")
	public @ResponseBody String staticUpdate(Eth eth,String[] dns) throws CustomException{
		service.staticUpdate(eth,dns);
		JSONObject json=new JSONObject();
		json.element("msg", "更新完毕,请在板卡管理中重启板卡");
		return json.toString();
	}
	
}
