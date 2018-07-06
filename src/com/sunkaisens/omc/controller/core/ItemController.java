package com.sunkaisens.omc.controller.core;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.po.core.Item;
import com.sunkaisens.omc.service.core.ItemService;
import com.sunkaisens.omc.vo.core.PageBean;
/**
 * 
 * 
 * 
 * 
 * 
 * 
 *配置项Controller
 */
@Controller@RequestMapping("/item")
public class ItemController {
    //注入 ItemService
	@Resource
	private ItemService service;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 返回list视图
	 * @return
	 */
	@RequestMapping("/listUI")
	public ModelAndView listUI(){
		PageBean pageBean=new PageBean();
		ModelAndView mav=new ModelAndView("item/list");
		mav.addObject("pageBean",pageBean);
		return mav;
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 获取数据库数据列表
	 * @param page
	 * @param pageSize
	 * @param configId
	 * @param sort
	 * @param order
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public @ResponseBody PageBean list(@RequestParam(defaultValue="1") Integer page,
			@RequestParam(value="rows",defaultValue="50") Integer pageSize,
			Integer configId,String sort,String order) throws Exception{
		PageBean pageBean=service.getPageBean(page,pageSize,configId,sort,order);
		return pageBean;
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 更新操作
	 * @param item
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping(value="update",produces="text/html;charset=UTF-8")
	public @ResponseBody String update(Item item) throws CustomException{
		Item i=service.findById(item.getId());
		if(i==null)
			throw new CustomException("修改的配置文件不存在！");
		service.update(item);
		JSONObject json=new JSONObject();
		json.element("msg","修改完毕");
		return json.toString();
	}
	/**
	 * 
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
		service.delete(ids);
		JSONObject json=new JSONObject();
		json.element("msg","删除完毕");
		return json.toString();
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 保存操作
	 * @param item
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping(value="save",produces="text/html;charset=UTF-8")
	public @ResponseBody String save(Item item) throws CustomException{
		service.save(item);
		JSONObject json=new JSONObject();
		json.element("msg","添加完毕");
		return json.toString();
	}
}
