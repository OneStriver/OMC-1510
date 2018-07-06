package com.sunkaisens.omc.controller.core;

import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunkaisens.omc.po.core.Privilege;
import com.sunkaisens.omc.po.core.User;
import com.sunkaisens.omc.service.core.LanguageBundle;
import com.sunkaisens.omc.service.core.PrivilegeService;
/**
 * 
 * 
 * 
 * 
 * 权限 Controller
 *
 */
@Controller
@RequestMapping(value="/privilege",produces="text/html;charset=UTF-8")
public class PrivilegeController {
	//注入PrivilegeService
	@Resource
	private PrivilegeService service;
	//注入LanguageBundle
	@Resource
	private LanguageBundle languageBundle;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 获取菜单项并将数据返回至前台
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("menu")
	public @ResponseBody String menu(HttpServletRequest request ,HttpSession session){
		List<Privilege> menu=service.getPrivilegeMene();
		User user=(User)session.getAttribute("user");
		if(!user.getName().equals("nouser")){
			Iterator<Privilege> iterator=menu.iterator();
			while(iterator.hasNext()){
				Privilege p=iterator.next();
				String name=p.getName();
				if(!user.hasPrivilegeByName(name)){
					iterator.remove();
				}
				List<Privilege> children=p.getChildren();
				Iterator<Privilege> i=children.iterator();
				while(i.hasNext()){
					if(!user.hasPrivilegeByName(i.next().getName())){
						i.remove();
					}
				}
			}
		}

		ResourceBundle resourceBundle = languageBundle.getBundle(request,session);
		for(Privilege privilege : menu){
			privilege.setName(resourceBundle.getString(privilege.getName()));
			for(Privilege child : privilege.getChildren()){
				child.setName(resourceBundle.getString(child.getName()));
			}
		}
		JSONArray arr=JSONArray.fromObject(menu);
		String json=arr.toString().replace("name", "text");
		return json;
	}
	
	/**
	 * 查询菜单树
	 */
	@RequestMapping("tree")
	public @ResponseBody String tree(HttpServletRequest request ,HttpSession session ){
		ResourceBundle resourceBundle = languageBundle.getBundle(request,session);
		List<Privilege> tree=service.getTree();
		for(Privilege privilege : tree){
			privilege.setName(resourceBundle.getString(privilege.getName()));
			for(Privilege child : privilege.getChildren()){
				child.setName(resourceBundle.getString(child.getName()));
				for(Privilege child1 : child.getChildren()){
				   child1.setName(resourceBundle.getString(child1.getName()));
				}
			}
		}
		String arr=JSONArray.fromObject(tree).toString().replace("name", "text");
		return arr;
	}
}
