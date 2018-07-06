package com.sunkaisens.omc.controller.core;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.po.core.Module;
import com.sunkaisens.omc.service.core.FileManagerService;
import com.sunkaisens.omc.service.core.ModuleService;
import com.sunkaisens.omc.util.ServletUtil;
import com.sunkaisens.omc.vo.core.FileModel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
/**
 * 
 * 
 * 
 * 
 *fileManager Controller
 */
@Controller
@RequestMapping(value = "/fileManager")
public class FileManagerController {

	@Resource
	private FileManagerService service;
	@Resource
	private ModuleService moduleService;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 从数据库获取数据列表
	 * @param dir
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping("list")
	public @ResponseBody String list(String dir) throws CustomException {
		List<FileModel> fileModels=service.listFileModel(dir);
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new DateJsonProcessor());
		JSONArray arr=JSONArray.fromObject(fileModels,jsonConfig);
		return arr.toString();
	}
   /**
    * 
    * 
    * 
    * 
    * 
    * 删除操作
    * @param fileModel
    * @return
    * @throws CustomException
    */
	@RequestMapping(value = "delete")
	public @ResponseBody String delete(FileModel fileModel) throws CustomException {
		service.deleteFile(fileModel);
		JSONObject json = new JSONObject();
		json.element("msg", "删除完毕");
		return json.toString();
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * openFile操作
	 * @param fileModel
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "openFile",produces="text/html;charset=UTF-8")
	public @ResponseBody String openFile(FileModel fileModel) throws Exception {
		String content=service.readFile(fileModel);
		return "<pre>"+content+"</pre>";
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 下载操作
	 * @param fileModel
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("download")
	public void download(FileModel fileModel,HttpServletResponse response) throws Exception {
		try(
			InputStream is=service.download(fileModel);
		){
			ServletUtil.setFileDownloadHeader(response, fileModel.getName());
			IOUtils.copy(is, response.getOutputStream());
		}
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 全部下载
	 * @param fileModel
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("downloadAll")
	public void downloadAll(FileModel fileModel,HttpServletResponse response) throws Exception {
		String id=new File(fileModel.getPath()).getParentFile().getName();
		Module module=moduleService.findById(Integer.parseInt(id.split("-")[0]));
		ServletUtil.setFileDownloadHeader(response, module.getName()+".zip");
		ServletOutputStream sos=response.getOutputStream();
		try(ZipOutputStream zos=new ZipOutputStream(sos);){
			service.downloadAll(zos,fileModel.getPath());
		}
	}
}
