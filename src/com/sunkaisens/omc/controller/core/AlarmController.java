package com.sunkaisens.omc.controller.core;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sunkaisens.omc.service.core.AlarmService;
import com.sunkaisens.omc.vo.core.PageBean;
/**
 * Alarm处理的Controller
 */
@Controller
@RequestMapping(value = "/alarm")
public class AlarmController {

	@Resource
	private AlarmService service;
	
	/**
	 * 获取alarm消息列表
	 */
	@RequestMapping("list")
	public @ResponseBody PageBean list(
			@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(value = "rows", defaultValue = "50") Integer pageSize,
			String sort, String order) {
		PageBean pageBean = service.getPageBean(page, pageSize, sort, order);
		return pageBean;
	}
	
    /**
     * 返回alarm/list视图
     */
	@RequestMapping("listUI")
	public ModelAndView listUI() {
		ModelAndView mav = new ModelAndView("alarm/list");
		mav.addObject("pageBean", new PageBean());
		return mav;
	}
	
    /**
     * 删除alarm消息
     */
	@RequestMapping(value = "delete")
	public @ResponseBody String delete(Integer[] ids) {
		service.deleteByIds(ids);
		JSONObject json = new JSONObject();
		json.element("msg", "删除完毕");
		return json.toString();
	}
	
}
