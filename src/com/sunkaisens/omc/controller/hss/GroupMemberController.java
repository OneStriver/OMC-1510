package com.sunkaisens.omc.controller.hss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.mapper.core.HssMetaMapper;
import com.sunkaisens.omc.mapper.hss.HssGroupMapper;
import com.sunkaisens.omc.po.hss.GroupMember;
import com.sunkaisens.omc.service.hss.GroupMemberService;
import com.sunkaisens.omc.service.hss.HssGroupService;
import com.sunkaisens.omc.vo.core.ComboboxVo;
import com.sunkaisens.omc.vo.core.PageBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 组成员 Controller
 */
@Controller
@RequestMapping({"/groupMember","meetingMember"})
public class GroupMemberController {
    //注入GroupMemberService
	@Resource
	private GroupMemberService service;
	//注入HssGroupService
	@Resource
	private HssGroupService groupService;
	@Resource
	private HssGroupMapper hssGroupMapper; 
	@Resource
	private HssMetaMapper hssMetaMapper;
	
	protected  Logger logger = LoggerFactory.getLogger(getClass());
	
	@ModelAttribute("groupNums")
	public Map<String,String> getDeviceTypes() {
		Map<String,String> groupNums=new HashMap<>();
		List<Map<String, ?>> list=hssGroupMapper.getGroupNumber();
		for(Map<String,?> map:list){
			groupNums.put(map.get("groupNum").toString(), map.get("groupNum").toString());
		}
		return groupNums;
	}
	
	@RequestMapping("listNum")
	public @ResponseBody JSONArray getList() {
		List<ComboboxVo> groupMembers = new ArrayList<ComboboxVo>();
		List<Map<String, ?>> list=hssGroupMapper.getGroupNumber();
		for(Map<String,?> map:list){
			ComboboxVo comboboxVo = new ComboboxVo();
			comboboxVo.setTextField(map.get("groupNum").toString());
			comboboxVo.setValueField(map.get("groupNum").toString());
			groupMembers.add(comboboxVo);
		}
		JSONArray jsonArray = JSONArray.fromObject(groupMembers);
		return jsonArray;
	}
	
	/**
	 * 从数据库获取组成员数据列表
	 */
	@RequestMapping("list")
	public @ResponseBody PageBean list(@RequestParam(defaultValue="1") Integer page,
			@RequestParam(value="rows",defaultValue="50") Integer pageSize,GroupMember member) {
		if(member.getGroupId().endsWith(",")){
			member.setGroupId(member.getGroupId().split(",")[0]);
		}
		PageBean pageBean=service.getGroupMemberList(page,pageSize,member);
		return pageBean;
	}
	
	/**
	 * 返回前台list界面
	 */
	@RequestMapping("listUI")
	public ModelAndView listUI(Boolean isSave,String groupId) {
		ModelAndView mav=new ModelAndView("hssMember/list");
		List<Map<String, Object>> groupPriority=hssMetaMapper.getConfigGroupPriority();//配置组优先级
		mav.addObject("pageBean", new PageBean());
		mav.addObject("isSave", isSave);
		mav.addObject("groupPriority", groupPriority);
		mav.addObject("groupId",groupId);
		return mav;
	}
	
	/**
	 * 返回前台list2视图
	 */
	@RequestMapping("listUI2")
	public ModelAndView listUI2(Boolean isSave,String meetingGroupId) {
		ModelAndView mav=new ModelAndView("hssMember/list2");
		mav.addObject("pageBean", new PageBean());
		mav.addObject("isSave", isSave);
		mav.addObject("groupId", meetingGroupId);
		return mav;
	}
	
	/**
	 * 添加组成员操作 
	 */
	@RequestMapping(value="add",produces="text/html;charset=UTF-8")
	public @ResponseBody String add(GroupMember member) throws CustomException {
		service.save(member);
		JSONObject json=new JSONObject();
		json.element("msg", "添加完毕");
		return json.toString();
	}
	
	/**
	 * 修改组成员操作
	 */
	@RequestMapping(value="update",produces="text/html;charset=UTF-8")
	public @ResponseBody String update(GroupMember member) throws CustomException {
		service.update(member);
		JSONObject json=new JSONObject();
		json.element("msg", "修改完毕");
		return json.toString();
	}
	
	/**
	 * 删除组成员操作
	 */
	@RequestMapping("delete")
	public @ResponseBody String delete(String[] mdn,String[] groupId) throws CustomException {
		service.delete(mdn,groupId);
		JSONObject json=new JSONObject();
		json.element("msg", "删除完毕");
		return json.toString();
	}
	
	/**
	 * 查询该号码属于哪一个组 
	 */
	@RequestMapping("selectBelongGroup")
	public @ResponseBody String selectBelongGroup(String mdn) throws CustomException {
		List<GroupMember> groupList = service.selectBelongGroup(mdn);		
		List<String> groupStr =  new ArrayList<String>();
		for (GroupMember groupMember : groupList) {
			groupStr.add(groupMember.getGroupId());
		}
		JSONObject json=new JSONObject();
		json.element("groupStr", groupStr);
		return json.toString();
	}
	
}
