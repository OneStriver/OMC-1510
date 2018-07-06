package com.sunkaisens.omc.controller.hss;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.mapper.core.HssMetaMapper;
import com.sunkaisens.omc.po.hss.HssGroup;
import com.sunkaisens.omc.po.hss.UserGroupPriority;
import com.sunkaisens.omc.service.hss.HssGroupService;
import com.sunkaisens.omc.vo.core.PageBean;

import net.sf.json.JSONObject;
/**
 * HssGroup Controller
 */
@Controller
@RequestMapping({"/hssGroup","meetingGroup"})
public class HssGroupController {
    //注入HssGroupService
	@Resource
	private HssGroupService service;
	//注入HssMetaMapper
	@Resource
	private HssMetaMapper hssMetaMapper;
	
	private List<UserGroupPriority> userGroupList = new ArrayList<>();
	
	@RequestMapping("list")
	public @ResponseBody PageBean list(@RequestParam(defaultValue="1") Integer page,
			@RequestParam(value="rows",defaultValue="50") Integer pageSize,HssGroup group){
		PageBean pageBean=service.getPageBean(page,pageSize,group);
		return pageBean;
	}
	/**
	 * 返回前台list视图
	 */
	@RequestMapping("listUI")
	public ModelAndView listUI(Boolean isSave){
		userGroupList.clear();
		ModelAndView mav=new ModelAndView("hssGroup/list");
		List<Map<String, Object>> groupPriority=hssMetaMapper.getConfigGroupPriority();//配置组优先级
		mav.addObject("pageBean", new PageBean());
		mav.addObject("isSave", isSave);
		mav.addObject("groupPriority", groupPriority);
		return mav;
	}
	
	/**
	 * 返回前台list2视图
	 */
	@RequestMapping("listUI2")
	public ModelAndView listUI2(Boolean isSave){
		ModelAndView mav=new ModelAndView("hssGroup/list2");
		mav.addObject("pageBean", new PageBean());
		mav.addObject("isSave", isSave);
		return mav;
	}
	/**
	 * 添加一个hssGroup
	 */
	@RequestMapping(value="add",produces="text/html;charset=UTF-8")
	public @ResponseBody String add(HssGroup group) throws CustomException{
		service.save(group);
		JSONObject json=new JSONObject();
		json.element("msg", "添加完毕");
		return json.toString();
	}
	/**
	 * 更新hssGroup操作
	 */
	@RequestMapping(value="update",produces="text/html;charset=UTF-8")
	public @ResponseBody String update(HssGroup group) throws CustomException{
		service.update(group);
		JSONObject json=new JSONObject();
		json.element("msg", "修改完毕");
		return json.toString();
	}
	/**
	 * 删除 hssGroup操作
	 */
	@RequestMapping("delete")
	public @ResponseBody String delete(String[] ids) throws CustomException{
		service.delete(ids);
		JSONObject json=new JSONObject();
		json.element("msg", "删除完毕");
		return json.toString();
	}
	
}
