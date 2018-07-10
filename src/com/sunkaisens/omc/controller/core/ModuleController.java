package com.sunkaisens.omc.controller.core;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.po.core.Module;
import com.sunkaisens.omc.service.core.ModuleService;
import com.sunkaisens.omc.vo.core.PageBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *  资源包管理
 *  Module Controller
 */
@Controller@RequestMapping("/module")
public class ModuleController {

	@Resource
	//注入ModuleService
	private ModuleService service;
	
	/**
	 * 返回list视图
	 */
	@RequestMapping("/listUI")
	public ModelAndView listUI(){
		PageBean pageBean=new PageBean();
		ModelAndView mav=new ModelAndView("module/list");
		mav.addObject("pageBean",pageBean);
		return mav;
	}
	
	/**
	 * 返回数据库数据列表
	 */
	@RequestMapping("list")
	public @ResponseBody PageBean list(@RequestParam(defaultValue="1") Integer page,
			@RequestParam(value="rows",defaultValue="50") Integer pageSize) throws Exception{
		PageBean pageBean=service.getPageBean(page,pageSize);
		return pageBean;
	}
	
	/**
	 * 将数据返回前台
	 */
	@RequestMapping(value="listjsonarr",produces="text/html;charset=UTF-8")
	public @ResponseBody String listjsonarr() throws Exception{
		List<Module> list=service.findAll();
		JSONArray arr=JSONArray.fromObject(list);
		return arr.toString();
	}
	
	/**
	 * 上传操作
	 */
	@RequestMapping(value="upload",produces="text/html;charset=UTF-8")
	public @ResponseBody String upload(HttpServletRequest request,Integer moduleId) throws Exception, CustomException{
		MultipartHttpServletRequest req=(MultipartHttpServletRequest)request;
		List<MultipartFile> files=req.getFiles("file");
		for(int i=0;i<files.size();i++){
			String name = files.get(i).getOriginalFilename();
			File f=Files.createTempFile("xxxxx", ".tmp").toFile();
			//转换成临时文件保存
			files.get(i).transferTo(f);
			readZipFile(f);
			service.saveUploadModule(name,f,moduleId==null?null:moduleId+i);
		}
		JSONObject json=new JSONObject();
		json.element("msg","上传完毕");
		return json.toString();
	}
	
	public void readZipFile(File file) throws Exception { 
		try(ZipFile zf = new ZipFile(file);
			InputStream in = new BufferedInputStream(new FileInputStream(file)); 
			ZipInputStream zin = new ZipInputStream(in);) {
			ZipEntry ze; 
			while ((ze = zin.getNextEntry()) != null) { 
				if (ze.isDirectory()) {
					System.err.println("文件名:" + ze.getName());
				} else { 
					String fileName = ze.getName();
					System.err.println("文件名:" + fileName + ",大小:"+ ze.getSize() + "Bytes."); 
				}
				if(ze.getName().toLowerCase().endsWith(".zip")){
					throw new CustomException("单个网元ZIP包中不能包含ZIP包！OR 不能上传批量的网元ZIP包！");
				}
			}
		}catch (Exception e) {
			throw new CustomException("ZIP包文件格式不对！OR 请检查包的冗余性！");
		}
	}
	
	/**
	 * 批量上传操作
	 */
	@RequestMapping(value="uploadAll",produces="text/html;charset=UTF-8")
	public @ResponseBody String uploadAll(MultipartFile file) throws Exception, CustomException{
		ZipInputStream zipInputStream = new ZipInputStream(file.getInputStream());
		ZipEntry zipEntry;
		while ((zipEntry = zipInputStream.getNextEntry()) != null) {
			String zipName = zipEntry.getName();
			if(zipName.toLowerCase().endsWith(".zip")==false){
				throw new CustomException("批量上传ZIP包中只能是单个网元ZIP包！");
			}
		}
		try(InputStream is=file.getInputStream()){
			service.saveUploadAllModule(is);
		}
		JSONObject json=new JSONObject();
		json.element("msg","上传完毕");
		return json.toString();
	}
	
	/**
	 * 更新操作
	 */
	@RequestMapping(value="update")
	public @ResponseBody String update(Module module){
		service.update(module);
		JSONObject json=new JSONObject();
		json.element("msg","修改完毕");
		return json.toString();
	}
	
	/**
	 * 删除操作 
	 */
	@RequestMapping("delete")
	public @ResponseBody String delete(Integer[] ids) throws IOException, CustomException{
		service.delete(ids);
		JSONObject json=new JSONObject();
		json.element("msg","删除完毕");
		return json.toString();
	}
	
	@RequestMapping(value="comein")
	public ModelAndView comein(String name,HttpSession session) throws CustomException {
		String path=session.getServletContext().getRealPath("/WEB-INF/repository");
		File file=new File(path,name);
		if(!file.exists()) throw new CustomException("网元【"+name+"】已被删除");
		ModelAndView mav = new ModelAndView("fileManager/files");
		mav.addObject("title",name);
		mav.addObject("dir",file.getPath().replace("\\", "/"));
		return mav;
	}
}
