package com.sunkaisens.omc.controller.core;

import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sunkaisens.omc.service.core.LanguageBundle;
import com.sunkaisens.omc.service.core.LogService;
import com.sunkaisens.omc.vo.core.PageBean;
/**
 * 
 * 
 * 
 * 
 * 
 * 日志 Controller
 */
@Controller
@RequestMapping(value = "/log")
public class LogController {
	//注入LogService
	@Resource
	private LogService service;
    /**
     * 
     * 
     * 
     * 
     * 
     * 
     * 获取数据库数据列表
     * @param page
     * @param pageSize
     * @param sort
     * @param order
     * @return
     */
	@Resource
    private LanguageBundle languageBundle;
	@RequestMapping("list")
	public @ResponseBody PageBean list(HttpServletRequest request,HttpSession session,
			@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(value = "rows", defaultValue = "50") Integer pageSize,
			String sort, String order) {
		ResourceBundle bundle=languageBundle.getBundle(request, session);
		PageBean pageBean = service.getPageBean(page, pageSize, sort, order,bundle);
		return pageBean;
	}
    /**
     * 
     * 
     * 
     * 
     * 
     * 
     * 返回list视图
     * @return
     */
	@RequestMapping("listUI")
	public ModelAndView listUI() {
		ModelAndView mav = new ModelAndView("log/list");
		mav.addObject("pageBean", new PageBean());
		return mav;
	}
    /**
     * 
     * 
     * 
     * 
     * 
     * 删除操作
     * @param ids
     * @return
     */
	@RequestMapping(value = "delete")
	public @ResponseBody String delete(Integer[] ids) {
		service.deleteByIds(ids);
		JSONObject json = new JSONObject();
		json.element("msg", "删除完毕");
		return json.toString();
	}
}
