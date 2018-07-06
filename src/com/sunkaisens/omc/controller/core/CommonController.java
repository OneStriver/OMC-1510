package com.sunkaisens.omc.controller.core;


import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.po.core.Common;
import com.sunkaisens.omc.po.core.Relevance;
import com.sunkaisens.omc.service.core.CommonService;
import com.sunkaisens.omc.vo.core.PageBean;
/**
 * 
 * 
 * 
 * 
 * Common Controller
 *
 */
@Controller
@RequestMapping(value="/common")
public class CommonController {
	//注入CommonService 实现类的实例
	@Resource
	private CommonService service;
	/**
	 * 
	 * 
	 * 
	 * 从数据库获取数据列表
	 * @param requestPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("list")
	public @ResponseBody PageBean list(@RequestParam(defaultValue="1") Integer page,
			@RequestParam(value="rows",defaultValue="50") Integer pageSize){
		PageBean pageBean=service.getPageBean(page,pageSize);
		return pageBean;
	}
	/**
	 * 
	 * 
	 * 
	 * 将数据返回前台
	 * @return
	 */
	@RequestMapping(value="listjsonarr",produces="text/html;charset=UTF-8")
	public @ResponseBody String listJsonArr(){
		List<Common> commons=service.findAll();
//		Common c=new Common("　");
//		commons.add(0, c);
		return JSONArray.fromObject(commons).toString();
	}
	/**
	 * 
	 * 
	 * 
	 * 返回前台视图
	 * @return
	 */
	@RequestMapping("listUI")
	public ModelAndView listUI(){
		ModelAndView mav=new ModelAndView("common/list");
		mav.addObject("pageBean", new PageBean());
		return mav;
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 保存操作
	 * @param common
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping(value="save")
	public @ResponseBody String save(Common common) throws CustomException{
		service.save(common);
		JSONObject json=new JSONObject();
		json.element("msg", "保存完毕");
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
		service.deleteByIds(ids);
		JSONObject json=new JSONObject();
		json.element("msg", "删除完毕");
		return json.toString();
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 更新操作
	 * @param common
	 * @return
	 */
	@RequestMapping(value="update")
	public @ResponseBody String update(Common common){
		service.update(common);
		JSONObject json=new JSONObject();
		json.element("msg", "修改完毕");
		return json.toString();
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 返回前台show视图
	 * @param id
	 * @return
	 */
	@RequestMapping(value="show")
	public ModelAndView show(Integer id){
		List<Relevance> relevances=service.getRelevanceById(id);
		ModelAndView mav=new ModelAndView("common/show");
		mav.addObject("items", relevances);
		return mav;
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 返回前台page视图
	 * @return
	 */
	@RequestMapping(value="page")
	public ModelAndView page(){
		List<Common> commons=service.findAll();
		ModelAndView mav=new ModelAndView("common/page");
		mav.addObject("commons", commons);
		return mav;
	}
    /**
     * 
     * 
     * 
     * 
     * 返回前台often视图
     * @param id
     * @return
     * @throws Exception
     */
	@RequestMapping(value="component")
	public ModelAndView component(Integer id) throws Exception{
		List<Relevance> relevances =service.generateComponent(id);
		ModelAndView mav=new ModelAndView("common/often");
		mav.addObject("items", relevances);
		return mav;
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 修改操作
	 * @param jsonarr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="modify")
	public @ResponseBody String modify(String jsonarr) throws Exception{
		JSONArray arr=JSONArray.fromObject(jsonarr);
		service.modifyConf(arr);
		JSONObject json=new JSONObject();
		json.element("msg", "修改完毕");
		return json.toString();
	}

}
