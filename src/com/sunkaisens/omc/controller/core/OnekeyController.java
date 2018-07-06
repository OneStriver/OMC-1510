package com.sunkaisens.omc.controller.core;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sunkaisens.omc.service.core.OnekeyService;
/**
 * 
 * 
 * 
 * 
 *一键导入导出操作 Controller
 *
 */
@Controller
@RequestMapping("/onekey")
public class OnekeyController {
	//注入OnekeyService
	@Resource
	private OnekeyService service;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回view视图
	 * @return
	 */
	@RequestMapping("view")
	public String view() {
		return "onekey/view";
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回download视图
	 * @return
	 */
	@RequestMapping("exportUI")
	public String oneexportUI(){
		return "onekey/download";
	}
    /**
     * 
     * 
     * 
     * 
     * 导出操作
     * @param request
     * @param response
     * @throws Exception
     */
	@RequestMapping("export")
	public void oneexport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		String hostName = service.getHostName();
		String fileName="BackUp "+hostName+date+".zip";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/x-msdownload");
//		response.setContentLength(1050929775);
		response.setHeader("Content-Disposition", "attachment;filename="+
			fileName);
		service.download(response.getOutputStream(),response);
//		JSONObject json=new JSONObject();
//		json.element("msg", "导出完毕");
//		return json.toString();
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 导入操作
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="import",produces="text/html;charset=UTF-8")
	public @ResponseBody String oneimport(MultipartFile file)throws Exception{
		File f=Files.createTempFile("xxxxx", ".zip").toFile();
		file.transferTo(f);
		service.restore(f);
		JSONObject json=new JSONObject();
		json.element("msg","导入完毕");
		return json.toString();
	}

}
