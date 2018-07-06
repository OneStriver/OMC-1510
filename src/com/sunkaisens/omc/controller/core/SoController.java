package com.sunkaisens.omc.controller.core;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sunkaisens.omc.po.core.So;
import com.sunkaisens.omc.service.core.SoService;

import com.sunkaisens.omc.vo.core.PageBean;
/**
 * 
 * 
 * 
 * So Controller
 *
 */
@Controller
@RequestMapping(value="/so")
public class SoController {
	//注入SoService Bean
	@Resource
	private SoService service;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 查询So列表,并将结果返回前台
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("list")
	public @ResponseBody PageBean list(@RequestParam(defaultValue="1") Integer page,
			@RequestParam(value="rows",defaultValue="50") Integer pageSize){
		List<So> sos=service.findAll();
		List<So> listPart = new ArrayList<So>();
		int size = sos.size();
		int startItem = (page-1)*pageSize;
		for(int i = 0 ; i<size;i++){
			if(startItem<=i&&i<startItem+pageSize){
				listPart.add(sos.get(i));
			}
		}
		PageBean pageBean=new PageBean(1, 50, listPart, sos.size());
		return pageBean;
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回前台so/list视图
	 * @return
	 */
	@RequestMapping("listUI")
	public ModelAndView listUI(){
		ModelAndView mav=new ModelAndView("so/list");
		mav.addObject("pageBean", new PageBean());
		return mav;
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 执行上传操作
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="upload",produces="text/html;charset=UTF-8")
	public @ResponseBody String upload(MultipartFile file) throws Exception{
		String name = file.getOriginalFilename();
		File f=Files.createTempFile("xxxxx", "fffff").toFile();
		file.transferTo(f);
		service.save(f, name , false);
		JSONObject json=new JSONObject();
		json.element("msg","上传完毕");
		return json.toString();
	}
	
	@RequestMapping(value="authUpload",produces="text/html;charset=UTF-8")
	public @ResponseBody String authUpload(MultipartFile file) throws Exception{
		String name = file.getOriginalFilename();
		File f=Files.createTempFile("xxxxx", "fffff").toFile();
		file.transferTo(f);
		service.save(f, name , true);
		JSONObject json=new JSONObject();
		json.element("msg","上传完毕");
		return json.toString();
	}
	
	
	
	
	/**
	 * 执行删除操作
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delete")
	public @ResponseBody String delete(String[] ids)throws Exception{
		service.delete(ids);
		JSONObject json=new JSONObject();
		json.element("msg","删除完毕");
		return json.toString();
	}
	
}
