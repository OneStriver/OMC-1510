package com.sunkaisens.omc.controller.core;



import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.service.core.CardService;
import com.sunkaisens.omc.service.core.RouteService;
import com.sunkaisens.omc.vo.core.PageBean;
import com.sunkaisens.omc.vo.core.Route;

import net.sf.json.JSONObject;
/**
 * 
 * 
 * 
 * 
 * Route Controller
 *
 */
@Controller@RequestMapping("/route")
public class RouteController {
	//注入CardService
	@Resource
	private CardService cardService;
	//注入RouteService
	@Resource
	private RouteService service;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 获取数据库Route数据
	 * @param cardNum
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping("/list")
	public @ResponseBody PageBean list(
			@RequestParam(defaultValue="0")Integer cardNum, Integer page, Integer rows) throws CustomException{
//		List<Route> list = new ArrayList<>();
//		for(int i=0;i<15;i++){
//			Route route = new Route("127.0.0.1", "192.168.1.125", "255.255.255.0", "1", "2", "1", "yes", "12");
//			list.add(route);
//		}
//		PageBean pageBean = new PageBean(1, 1, list, list.size());
//		service.list(cardNum,page,rows)
		return service.list(cardNum,page,rows);
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 返回前台list视图
	 * @return
	 */
	@RequestMapping("listUI")
	public ModelAndView listUI(){
		ModelAndView mav=new ModelAndView("route/list");
		PageBean pageBean=new PageBean();
		mav.addObject("pageBean",pageBean);
		return mav;
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 添加保存操作
	 * @param route
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping(value="save",produces="text/html;charset=UTF-8")
	public @ResponseBody String save(Route route) throws CustomException{
		service.add(route);
		JSONObject json=new JSONObject();
		json.element("msg", "添加完毕");
		return json.toString();
	}
	/**
	 * 添加默认路由
	 * @param route
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping(value="saveDefault",produces="text/html;charset=UTF-8")
	public @ResponseBody String saveDefault(Route route) throws CustomException{
		service.add(route);
		JSONObject json=new JSONObject();
		json.element("msg", "添加完毕");
		return json.toString();
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 删除操作
	 * @param cardNums
	 * @param destinations
	 * @param gateways
	 * @param genmasks
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping("delete")
	public @ResponseBody String delete(Integer[] cardNums,String[] destinations,
			String[] gateways,String[] genmasks)throws CustomException{
		service.delete(cardNums,destinations,gateways,genmasks);
		JSONObject json=new JSONObject();
		json.element("msg", "删除完毕");
		return json.toString();
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 更新操作
	 * @param route
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping("update")
	public @ResponseBody String update(Route route)throws CustomException{
		service.update(route);
		JSONObject json=new JSONObject();
		json.element("msg", "删除完毕");
		return json.toString();
	}
}
