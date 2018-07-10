package com.sunkaisens.omc.controller.core;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sunkaisens.omc.po.core.Card;
import com.sunkaisens.omc.service.core.CardService;
import com.sunkaisens.omc.vo.core.PageBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 处理板卡的Controller(板卡管理)
 */
@Controller
@RequestMapping(value="/card")
public class CardController {
	private static int OAM_ERROR_SUCCESS = 0;
	private static int OAM_ERROR_HOST_NEXIST = 0x14;
	private static int OAM_ERROR_EXIST = 0x17;
	private static int OAM_ERROR_IN_USE = 0x18;
	@Resource
	private CardService cardService;
	
	/**
	 * 从数据库获取板卡列表信息
	 */
	@RequestMapping({"list"})
	public @ResponseBody PageBean list(@RequestParam(defaultValue="1") Integer page,
			@RequestParam(value="rows",defaultValue="50") Integer pageSize){
		PageBean pageBean=cardService.getPageBean(page,pageSize);
		return pageBean;
	}
	
	/**
	 * 将数据返回前台
	 */
	@RequestMapping(value="listjsonarr",produces="text/html;charset=UTF-8")
	public @ResponseBody String listJsonArr(){
		List<Card> cards=cardService.findAll();
		return JSONArray.fromObject(cards).toString();
	}
	
	/**
	 * 返回card/list视图
	 */
	@RequestMapping("listUI")
	public ModelAndView listUI(){
		ModelAndView mav=new ModelAndView("card/list");
		mav.addObject("pageBean", new PageBean());
		return mav;
	}
	
	/**
	 * 保存操作
	 */
	@RequestMapping(value="save")
	public @ResponseBody String save(Card card){
		int result = cardService.save(card);
		JSONObject json=new JSONObject();
		if(result == OAM_ERROR_SUCCESS){
			json.element("msg", "操作成功");
		}
		if(result == OAM_ERROR_EXIST){
			json.element("msg", "板卡已经存在");
		}
		return json.toString();
	}
	
	/**
	 * 删除操作
	 */
	@RequestMapping(value="delete")
	public @ResponseBody String delete(Integer[] ids){
		List<Integer> list = cardService.deleteByIds(ids);
		JSONObject json=new JSONObject();
		if(list.contains(OAM_ERROR_SUCCESS)){
			json.element("msg", "操作成功");
		}
		if(list.contains(OAM_ERROR_EXIST) || list.contains(OAM_ERROR_HOST_NEXIST) || list.contains(OAM_ERROR_IN_USE)){
			json.element("msg", "板卡已经存在");
		}
		return json.toString();
	}
	
	/**
	 * 更新操作
	 */
	@RequestMapping(value="update")
	public @ResponseBody String update(Card card){
		cardService.update(card);
		JSONObject json=new JSONObject();
		json.element("msg", "修改完毕");
		return json.toString();
	}
	
	/**
	 * 重启操作
	 */
	@RequestMapping(value="reboot")
	public @ResponseBody String reboot(){
		cardService.rebootAll();
		JSONObject json=new JSONObject();
		json.element("msg", "设备已重启，请稍后刷新浏览器");
		return json.toString();
	}

}
