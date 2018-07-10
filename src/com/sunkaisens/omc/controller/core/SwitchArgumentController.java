package com.sunkaisens.omc.controller.core;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sunkaisens.omc.service.core.SwitchArgumentService;
import com.sunkaisens.omc.vo.core.NumberTranslationResult;
import com.sunkaisens.omc.vo.core.PageBean;

import net.sf.json.JSONObject;

/**
 * 号码映射表
 *SwitchArgument Controller
 */
@Controller
@RequestMapping(value = "/switch")
public class SwitchArgumentController {
	
	//缓存所有的数据
	private List<String> originalNumList = new ArrayList<String>();
	private List<String> mappingNumList = new ArrayList<String>();
	private List<String> serviceOptionNumList = new ArrayList<String>();
	//注入SwitchArgumentService
	@Resource
	private SwitchArgumentService service;
	
    /**
     *  返回前台list界面
     */
	@RequestMapping("listUI")
	public ModelAndView listUI() throws Exception {
		ModelAndView mav = new ModelAndView("switch/list");
		return mav;
	}
	
	@RequestMapping("list")
	public @ResponseBody PageBean list() throws Exception {
		List<NumberTranslationResult> nts = service.readNumberTranslations();
		PageBean pageBean = new PageBean(1, 50, nts, nts.size());
		return pageBean;
	}
	
	/**
	 * 添加操作
	 */
	@RequestMapping(value="add",method=RequestMethod.POST,produces=MediaType.TEXT_HTML_VALUE+";charset=UTF-8")
	public @ResponseBody String add(NumberTranslationResult result) throws Exception {
		originalNumList.clear();
		mappingNumList.clear();
		serviceOptionNumList.clear();
		List<NumberTranslationResult> queryList = service.readNumberTranslations();
		for (NumberTranslationResult numberTranslationResult : queryList) {
			originalNumList.add(numberTranslationResult.getOriginalNumber());
			mappingNumList.add(numberTranslationResult.getMappingNumber());
		}
		//添加新增的数据
		originalNumList.add(result.getOriginalNumber());
		mappingNumList.add(result.getMappingNumber());
		//更新配置文件中的数据
		service.updateNumberTranslations(originalNumList,mappingNumList,serviceOptionNumList);
		JSONObject json=new JSONObject();
		json.element("msg", "添加成功");
		return json.toString();
	}
	
	/**
	 * 更新操作
	 */
	@RequestMapping(value="update",method=RequestMethod.POST,produces=MediaType.TEXT_HTML_VALUE+";charset=UTF-8")
	public @ResponseBody String update(NumberTranslationResult result) throws Exception {
		originalNumList.clear();
		mappingNumList.clear();
		serviceOptionNumList.clear();
		List<NumberTranslationResult> queryList = service.readNumberTranslations();
		for (NumberTranslationResult numberTranslationResult : queryList) {
			if(numberTranslationResult.getOriginalNumber().equals(result.getOriginalNumber())){
				numberTranslationResult.setOriginalNumber(result.getOriginalNumber());
				numberTranslationResult.setMappingNumber(result.getMappingNumber());
			}
			originalNumList.add(numberTranslationResult.getOriginalNumber());
			mappingNumList.add(numberTranslationResult.getMappingNumber());
		}
		service.updateNumberTranslations(originalNumList,mappingNumList,serviceOptionNumList);
		JSONObject json=new JSONObject();
		json.element("msg", "修改成功");
		return json.toString();
	}
	
	/**
	 * 删除操作
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST,produces=MediaType.TEXT_HTML_VALUE+";charset=UTF-8")
	public @ResponseBody String delete(String deleteOriginalNum,String deleteMappingNum,String deleteServiceOption) throws Exception {
		originalNumList.clear();
		mappingNumList.clear();
		serviceOptionNumList.clear();
		//读取配置文件中已有的数据
		List<NumberTranslationResult> queryList = service.readNumberTranslations();
		for (NumberTranslationResult numberTranslationResult : queryList) {
			if(numberTranslationResult.getOriginalNumber().equals(deleteOriginalNum)){
				continue;
			}
			originalNumList.add(numberTranslationResult.getOriginalNumber());
			mappingNumList.add(numberTranslationResult.getMappingNumber());
		}
		service.updateNumberTranslations(originalNumList,mappingNumList,serviceOptionNumList);
		JSONObject json=new JSONObject();
		json.element("msg", "删除成功");
		return json.toString();
	}
	
	/**
	 * 上移操作
	 */
	@RequestMapping(value="moveUp",method=RequestMethod.POST,produces=MediaType.TEXT_HTML_VALUE+";charset=UTF-8")
	public @ResponseBody String moveUp(String moveUpOriginalNum,String moveUpMappingNum,String moveUpServiceOption) throws Exception {
		originalNumList.clear();
		mappingNumList.clear();
		serviceOptionNumList.clear();
		//读取配置文件中已有的数据
		List<NumberTranslationResult> queryList = service.readNumberTranslations();
		for (int i=0;i<queryList.size();i++) {
			if(moveUpOriginalNum.equals(queryList.get(i).getOriginalNumber())){
				if(i==0){
					JSONObject json=new JSONObject();
					json.element("msg", "已经是第一个,不能上移!");
					return json.toString();
				}else{
					int beforeIndex = i-1;
					int currentIndex = i;
					queryList.add(beforeIndex, queryList.get(currentIndex));
					queryList.add(currentIndex+1, queryList.get(beforeIndex+1));
					queryList.remove(beforeIndex+1);
					queryList.remove(currentIndex+1);
				}
				break;
			}
		}
		for (NumberTranslationResult numberTranslationResult : queryList) {
			originalNumList.add(numberTranslationResult.getOriginalNumber());
			mappingNumList.add(numberTranslationResult.getMappingNumber());
		}
		service.updateNumberTranslations(originalNumList,mappingNumList,serviceOptionNumList);
		JSONObject json=new JSONObject();
		json.element("msg", "上移成功");
		return json.toString();
	}
	
	/**
	 * 下移操作
	 */
	@RequestMapping(value="moveDown",method=RequestMethod.POST,produces=MediaType.TEXT_HTML_VALUE+";charset=UTF-8")
	public @ResponseBody String moveDown(String moveDownOriginalNum,String moveDownMappingNum,String moveDownServiceOption) throws Exception {
		originalNumList.clear();
		mappingNumList.clear();
		serviceOptionNumList.clear();
		//读取配置文件中已有的数据
		List<NumberTranslationResult> queryList = service.readNumberTranslations();
		for (int i=0;i<queryList.size();i++) {
			if(moveDownOriginalNum.equals(queryList.get(i).getOriginalNumber())){
				if(i==(queryList.size()-1)){
					JSONObject json=new JSONObject();
					json.element("msg", "已经是最后一个,不能下移!");
					return json.toString();
				}else{
					int beforeIndex = i;
					int currentIndex = i+1;
					queryList.add(beforeIndex, queryList.get(currentIndex));
					queryList.add(currentIndex+1, queryList.get(beforeIndex+1));
					queryList.remove(beforeIndex+1);
					queryList.remove(currentIndex+1);
				}
				break;
			}
		}
		for (NumberTranslationResult numberTranslationResult : queryList) {
			originalNumList.add(numberTranslationResult.getOriginalNumber());
			mappingNumList.add(numberTranslationResult.getMappingNumber());
		}
		service.updateNumberTranslations(originalNumList,mappingNumList,serviceOptionNumList);
		JSONObject json=new JSONObject();
		json.element("msg", "下移成功");
		return json.toString();
	}
	
}	
