package com.sunkaisens.omc.controller.core;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.po.core.Role;
import com.sunkaisens.omc.service.core.RoleService;
import com.sunkaisens.omc.vo.core.PageBean;
/**
 * 
 * 
 * 
 * 角色 Controller
 *
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    //注入RoleService
	@Resource
	private RoleService service;
	/**
	 * 
	 * 
	 * 
	 * 从数据库获取角色列表，并返回前台
	 * @return
	 */
	@RequestMapping(value="list",produces="text/html;charset=UTF-8")
	public @ResponseBody String list(){
		List<Role> roles=service.getAll();
		JSONArray arr=JSONArray.fromObject(roles);
		String json=arr.toString();
		return json;
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回前台list视图
	 * @return
	 */
	@RequestMapping(value="listUI")
	public ModelAndView listUI(){
		ModelAndView mav=new ModelAndView("role/list");
		mav.addObject("pageBean", new PageBean());
		return mav;
	}
	/**
	 * 
	 * 
	 * 
	 * 返回前台add视图
	 * @return
	 */
	@RequestMapping(value="addUI")
	public ModelAndView addUI(){
		ModelAndView mav=new ModelAndView("role/add");
		return mav;
	}
	/**
	 * 
	 * 
	 * 
	 *添加操作
	 * @param role
	 * @param privilegeIds
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping(value="add")
	public @ResponseBody String add(Role role,Integer[] privilegeIds) throws CustomException{
		service.save(role,privilegeIds);
		JSONObject json=new JSONObject();
		json.element("msg", "添加完毕！");
		return json.toString();
	}
	/**
	 * 
	 * 
	 * 
	 * 返回前台update视图
	 * @param id
	 * @return
	 */
	@RequestMapping(value="updateUI")
	public ModelAndView updateUI(Integer id){
		Role role=service.findById(id);
		ModelAndView mav=new ModelAndView("role/update");
		mav.addObject("role", role);
		return mav;
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 更新操作
	 * @param role
	 * @param privilegeIds
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping(value="update")
	public @ResponseBody String update(Role role,Integer[] privilegeIds) throws CustomException{
		service.update(role,privilegeIds);
		JSONObject json=new JSONObject();
		json.element("msg", "修改完毕！");
		return json.toString();
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 删除操作
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="delete")
	public @ResponseBody String delete(Integer[] ids){
		service.delete(ids);
		JSONObject json=new JSONObject();
		json.element("msg", "删除完毕");
		return json.toString();
	}
}
