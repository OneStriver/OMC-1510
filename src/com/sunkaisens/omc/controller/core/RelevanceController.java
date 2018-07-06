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
import com.sunkaisens.omc.po.core.Item;
import com.sunkaisens.omc.po.core.Relevance;
import com.sunkaisens.omc.service.core.RelevanceService;
import com.sunkaisens.omc.vo.core.PageBean;
/**
 * 
 * 
 * 
 * 
 * 
 * Relevance Controller
 *
 */
@Controller
@RequestMapping(value="/relevance")
public class RelevanceController {
	//注入RelevanceService
	@Resource
	private RelevanceService service;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 从数据库返回数据集合列表
	 * @param requestPage
	 * @param pageSize
	 * @param sort
	 * @param order
	 * @return
	 */
	@RequestMapping("list")
	public @ResponseBody PageBean list(@RequestParam(defaultValue="1") Integer page,
			@RequestParam(value="rows",defaultValue="50") Integer pageSize,
			String sort,String order){
		PageBean pageBean=service.getPageBean(page,pageSize,sort,order);
		return pageBean;
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 查询数据转为json格式并往前台传送数据
	 * @return
	 */
	@RequestMapping(value="listjsonarr",produces="text/html;charset=UTF-8")
	public @ResponseBody String listJsonArr(){
		List<Relevance> relevances=service.findAll();
		Relevance r=new Relevance();
		r.setName("　");
		relevances.add(0, r);
		return JSONArray.fromObject(relevances).toString();
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 返回前台list视图
	 * @return
	 */
	@RequestMapping("listUI")
	public ModelAndView listUI(){
		ModelAndView mav=new ModelAndView("relevance/list");
		mav.addObject("pageBean", new PageBean());
		return mav;
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 保存操作
	 * @param relevance
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping(value="save",produces="text/html;charset=UTF-8")
	public @ResponseBody String save(Relevance relevance) throws CustomException{
		if(relevance.getCommon().getId()==0){
			relevance.getCommon().setId(null);
		}
		service.save(relevance);
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
	 * 
	 * 更新操作
	 * @param relevance
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="update",produces="text/html;charset=UTF-8")
	public @ResponseBody String update(Relevance relevance) throws Exception{
		if(relevance.getCommon().getId()==0){
			relevance.getCommon().setId(null);
		}
		service.update(relevance);
		JSONObject json=new JSONObject();
		json.element("msg", "修改完毕");
		return json.toString();
	}
	/**
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
		List<Item> items=service.getItemById(id);
		ModelAndView mav=new ModelAndView("relevance/show");
		mav.addObject("items", items);
		return mav;
	}

}
