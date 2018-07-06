package com.sunkaisens.omc.controller.core;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.po.core.Config;
import com.sunkaisens.omc.service.core.ConfigService;
import com.sunkaisens.omc.vo.core.PageBean;
/**
 * 
 * 
 * 
 * 
 * 
 *配置Controller
 */
@Controller@RequestMapping("/config")
public class ConfigController {

	@Resource
	private ConfigService service;
	/**
	 * 
	 * 
	 * 
	 * 返回前台list视图
	 * @return
	 */
	@RequestMapping("/listUI")
	public ModelAndView listUI(){
		PageBean pageBean=new PageBean();
		ModelAndView mav=new ModelAndView("config/list");
		mav.addObject("pageBean",pageBean);
		return mav;
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 获取数据库数据列表
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public @ResponseBody PageBean list(@RequestParam(defaultValue="1") Integer page,
			@RequestParam(value="rows",defaultValue="50") Integer pageSize) throws Exception{
		PageBean pageBean=service.getPageBean(page,pageSize);
		return pageBean;
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回数据至前台
	 * @param isAll
	 * @return
	 */
	@RequestMapping(value="listjsonarr",produces="text/html;charset=UTF-8")
	public @ResponseBody String listjsonarr(Boolean isAll){
		List<Config> configs=service.findAll();
		for(Config c:configs){
			c.setName(c.getName()+"【"+c.getModule().getName()+"】");
		}
		if(isAll!=null&&isAll){
			Config all=new Config();
			all.setName("全部配置文件");
			configs.add(0, all);
		}
		return JSONArray.fromObject(configs).toString();
	}
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 更新操作
	 * @param id
	 * @param name
	 * @param description
	 * @param sole
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping(value="update")
	public @ResponseBody String update(Integer id,String name,
			String description,Boolean sole) throws CustomException{
		Config c=service.findById(id);
		if(c==null)
			throw new CustomException("修改的配置文件不存在！");
		c.setDescription(description);
		c.setName(name);
		c.setSole(sole);
		service.update(c);
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
	 * 删除操作
	 * @param ids
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping(value="delete",produces="text/html;charset=UTF-8")
	public @ResponseBody String delete(Integer[] ids) throws CustomException{
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
	 * 文件上传处理
	 * @param moduleId
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="upload",produces="text/html;charset=UTF-8")
	public @ResponseBody String upload(Integer moduleId,MultipartFile file,String path) throws Exception{
		String content=new String(file.getBytes());
		String name=file.getOriginalFilename();
		if(path==null){
			path="";
		}
		if(StringUtils.isBlank(path)){
			path="";
		}else{
			while(path.endsWith("/")){
				path=path.substring(0, path.length()-1);
			}
			while(path.startsWith("/")){
				path=path.substring(1);
			}
		}
		service.saveUpload(moduleId,StringUtils.isBlank(path)?name:path+"/"+name,content);
		JSONObject json=new JSONObject();
		json.element("msg","上传完毕");
		return json.toString();
	}
	
}
