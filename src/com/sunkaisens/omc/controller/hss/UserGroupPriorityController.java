package com.sunkaisens.omc.controller.hss;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sunkaisens.omc.po.hss.UserGroupPriority;
import com.sunkaisens.omc.service.hss.UserGroupPriorityService;

import net.sf.json.JSONObject;

/**
 *优先级初始化设置
 *
 */
@Controller
@RequestMapping("/hss")
public class UserGroupPriorityController {
	
	@Resource
	private UserGroupPriorityService service;
	
	@RequestMapping("userGroupListUI")
	public ModelAndView listUI(Boolean isSave){
		ModelAndView mav=new ModelAndView("hss/userGroupList");
		List<UserGroupPriority> allUserGroup = service.readUserGroupPriority();
		mav.addObject("allUserGroup", allUserGroup);
		return mav;
	}
	
	@RequestMapping(value="userGroupUpdate",produces=MediaType.TEXT_HTML_VALUE+";charset=UTF-8")
	public @ResponseBody String updateUserGroup(@RequestParam("id")String[] ids,@RequestParam("level")String[] levels,@RequestParam("priority")String[] priorities,
			@RequestParam("enable")String[] enables,@RequestParam("userType")String[] userTypes){
		for(int i=0;i<ids.length;i++){
			UserGroupPriority userGroup = new UserGroupPriority();
			userGroup.setId(Integer.valueOf(ids[i]));
			userGroup.setLevel(Integer.valueOf(levels[i]));
			userGroup.setPriority(Integer.valueOf(priorities[i]));
			userGroup.setEnable(Integer.valueOf(enables[i]));
			userGroup.setUserType(Integer.valueOf(userTypes[i]));
			service.updateUserGroupInfo(userGroup);
		}
		JSONObject json=new JSONObject();
		json.element("msg", "修改完毕");
		return json.toString();
	}
	
}
