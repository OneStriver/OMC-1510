package com.sunkaisens.omc.controller.ims;


import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.po.cscf.Ue;
import com.sunkaisens.omc.service.ims.ImsService;
import com.sunkaisens.omc.vo.core.PageBean;

/**
 * Ims Controller
 */
@Controller
@RequestMapping("/ims")
public class ImsController {
	
	@Resource
	private ImsService service;
	
	/**
	 * 从数据库查询ims数据列表
	 */
	@RequestMapping("list")
	public @ResponseBody PageBean list(@RequestParam(defaultValue="1") Integer page,
			@RequestParam(value="rows",defaultValue="50") Integer pageSize,Ue ue){
		PageBean pageBean=service.getPageBean(page,pageSize,ue);
		return pageBean;
	}
	
	/**
	 * 返回前台list视图
	 */
	@RequestMapping("listUI")
	public ModelAndView listUI(){
		ModelAndView mav=new ModelAndView("ims/list");
		mav.addObject("pageBean", new PageBean());
		return mav;
	}
	
	/**
	 * 保存ims信息操作
	 */
	@RequestMapping(value="save",produces="text/html;charset=UTF-8")
	public @ResponseBody String save(Ue ue,String domain) throws CustomException{
		service.save(ue,domain);
		JSONObject json=new JSONObject();
		json.element("msg", "添加完毕");
		return json.toString();
	}
	
	/**
	 * 修改ims操作
	 */
	@RequestMapping(value="update",produces="text/html;charset=UTF-8")
	public @ResponseBody String update(Ue ue,String domain) throws CustomException{
		service.update(ue,domain);
		JSONObject json=new JSONObject();
		json.element("msg", "修改完毕");
		return json.toString();
	}
	
	/**
	 * 删除ims操作
	 */
	@RequestMapping("delete")
	public @ResponseBody String delete(String[] ids) throws CustomException{
		service.deleteUes(ids);
		JSONObject json=new JSONObject();
		json.element("msg", "删除完毕");
		return json.toString();
	}
	
}
