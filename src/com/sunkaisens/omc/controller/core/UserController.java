package com.sunkaisens.omc.controller.core;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.po.core.Role;
import com.sunkaisens.omc.po.core.User;
import com.sunkaisens.omc.service.core.RoleService;
import com.sunkaisens.omc.service.core.UserService;
import com.sunkaisens.omc.vo.core.PageBean;
/**
 * 
 * 
 * 
 * 
 * 
 * 
 * 用户 Controller
 */
@Controller
@RequestMapping("/user")
public class UserController {
   //注入UserService
	@Resource
	private UserService service;
	//注入RoleService
	@Resource
	private RoleService roleService;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 从数据库查询用户数据列表
	 * @return
	 */
	@RequestMapping(value="list",produces="text/html;charset=UTF-8")
	public @ResponseBody String list(){
		List<User> users=service.getAll();
		JSONArray arr=JSONArray.fromObject(users);
		String json=arr.toString();
		return json;
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 返回前台list视图
	 * @return
	 */
	@RequestMapping(value="listUI")
	public ModelAndView listUI(){
		ModelAndView mav=new ModelAndView("user/list");
		mav.addObject("pageBean", new PageBean());
		return mav;
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 更新操作
	 * @param id
	 * @param name
	 * @param password
	 * @param roleId
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping("update")
	public @ResponseBody String update(Integer id,String name,
				String password,Integer[] roleId) throws CustomException{
		User user=service.getById(id);
		if(user.getName().equals("nouser")&&!name.equals("nouser"))
			throw new CustomException("超级管理员不允许更改登录名");
		Integer[] roleIds=roleId;
		List<Role> roles=roleService.getRolesByIds(roleIds);
		user.setName(name);
		user.setPassword(password);
		user.getRoles().clear();
		user.getRoles().addAll(roles);
		service.update(user);
		JSONObject json=new JSONObject();
		json.element("msg", "保存完毕");
		return json.toString();
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 保存操作
	 * @param name
	 * @param password
	 * @param roleId
	 * @return
	 */
	@RequestMapping("save")
	public @ResponseBody String save(String name,
			String password,Integer[] roleId){
		User user=new User();
		Integer[] roleIds=roleId;
		List<Role> roles=roleService.getRolesByIds(roleIds);
		user.setName(name);
		user.setPassword(password);
		user.getRoles().clear();
		user.getRoles().addAll(roles);
		service.save(user);
		JSONObject json=new JSONObject();
		json.element("msg", "保存完毕");
		return json.toString();
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 删除操作
	 * @param ids
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping("delete")
	public @ResponseBody String delete(Integer[] ids) throws CustomException{
		if(ArrayUtils.contains(ids, 1))
			throw new CustomException("超级管理员不允许被删除");
		service.delete(ids);
		JSONObject json=new JSONObject();
		json.element("msg", "删除完毕");
		return json.toString();
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 修改操作
	 * @param session
	 * @param pwd
	 * @param newPwd
	 * @param newPwd2
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping(value="modifySelf",produces="text/html;charset=UTF-8")
	public @ResponseBody String modifySelf(HttpSession session,
			String pwd,String newPwd,String newPwd2) throws CustomException{
		User user=(User)session.getAttribute("user");
		service.updateSelf(user,pwd,newPwd,newPwd2);
		JSONObject json=new JSONObject();
		json.element("msg", "修改完毕");
		return json.toString();
	}
	
	@RequestMapping(value="stackInfo")
	public String stackInfo(HttpSession session) throws CustomException{
		User user=(User)session.getAttribute("user");
		if(!"nouser".equals(user.getName())){
			throw new CustomException("除管理员外，无权使用此功能！");
		}
		return "allstacktraces";
	}
}
