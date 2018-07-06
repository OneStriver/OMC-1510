package com.sunkaisens.omc.controller.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sunkaisens.omc.context.core.OmcServerContext;
import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.po.core.Entity;
import com.sunkaisens.omc.po.core.Module;
import com.sunkaisens.omc.service.core.EntityService;
import com.sunkaisens.omc.service.core.FileManagerService;
import com.sunkaisens.omc.util.ServletUtil;
import com.sunkaisens.omc.vo.core.PageBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 实体 Controller
 */
@Controller
@RequestMapping(value="/entity")
public class EntityController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private EntityService service;
	@Resource
	private FileManagerService fileManagerService;
	
    /**
     * 从数据库获取数据列表
     */
	@RequestMapping("list")
	public @ResponseBody PageBean list(@RequestParam(defaultValue="1") Integer page,
			@RequestParam(value="rows",defaultValue="50") Integer pageSize,
			String sort,String order){
		System.err.println("page:"+page+";"+"pageSize:"+pageSize);
		PageBean pageBean=service.getPageBean(page,pageSize,sort,order);
		return pageBean;
	}
	
	/**
	 * 返回前台list界面
	 */
	@RequestMapping("listUI")
	public ModelAndView listUI(){
		ModelAndView mav=new ModelAndView("entity/list");
		mav.addObject("pageBean", new PageBean());
		return mav;
	}
	
	/**
	 * 保存操作
	 */
	@RequestMapping(value="save")
	public @ResponseBody String save(Integer moduleId,Integer instId,String json,
			@RequestParam(defaultValue="0")Integer cardNum,String name) throws IOException, CustomException{
		service.save(moduleId,instId,json,cardNum,name);
		JSONObject j=new JSONObject();
		j.element("msg", "保存完毕");
		return j.toString();
	}
	
	/**
	 * 更新操作
	 */
	@RequestMapping(value="update")
	public @ResponseBody String update(String json,Integer id) throws IOException, CustomException{
		service.update(json,id);
		JSONObject j=new JSONObject();
		j.element("msg", "修改完毕");
		return j.toString();
	}
	
	/**
	 * 升级Entity操作
	 */
	@RequestMapping(value="updateEntity",produces="text/html;charset=UTF-8")
	public @ResponseBody String updateEntity(Integer id,Integer instId,
			Integer moduleId,MultipartFile file)throws Exception{
		File f=Files.createTempFile("xxxxx", ".tmp").toFile();
		file.transferTo(f);
		service.updateEntity(id,instId,moduleId,f);
		JSONObject json=new JSONObject();
		json.element("msg","升级完毕");
		return json.toString();
	}
	
	/**
	 * 删除操作
	 */
	@RequestMapping(value="delete")
	public @ResponseBody String delete(Integer[] ids) throws Exception{
		service.deleteByIds(ids);
		JSONObject json=new JSONObject();
		json.element("msg", "删除完毕");
		return json.toString();
	}
	
	/**
	 * 返回add视图
	 */
	@RequestMapping(value="addUI")
	public ModelAndView addUI(Entity entity){
		System.err.println(entity);
		Module module=service.getComponentModule(entity.getModule().getId());
		ModelAndView mav=new ModelAndView("entity/add");
		mav.addObject("module", module);
		mav.addObject("entity",entity);
		return mav;
	}
	
	/**
	 * 返回update视图
	 */
	@RequestMapping(value="updateUI")
	public ModelAndView updateUI(Integer id) throws Exception{
		Module module=service.viewComponentModule(id);
		Entity entity=service.getById(id);
		ModelAndView mav=new ModelAndView("entity/update");
		mav.addObject("module", module);
		mav.addObject("entity",entity);
		return mav;
	}
	
    /**
     * 向前台返回数据
     */
	@RequestMapping(value="view")
	public @ResponseBody String view(Integer id) throws Exception{
		JSONArray arr=service.readConfArr(id);
		return arr.toString();
	}
	
	/**
	 * 启动操作
	 */
	@RequestMapping(value="start")
	public @ResponseBody String start(Integer id) throws Exception{
		service.start(id);
		JSONObject json=new JSONObject();
		json.element("msg", "启动完毕");
		return json.toString();
	}
	
	/**
	 * 重启操作
	 */
	@RequestMapping(value="restart")
	public @ResponseBody String restart(Integer id) throws Exception{
		service.restart(id);
		JSONObject json=new JSONObject();
		json.element("msg", "启动完毕");
		return json.toString();
	}
	
	/**
	 * 停止进程
	 */
	@RequestMapping(value="stop")
	public @ResponseBody String stop(Integer id) throws Exception{
		service.stop(id);
		JSONObject json=new JSONObject();
		json.element("msg", "进程已停止");
		return json.toString();
	}
	
	@RequestMapping(value="comein")
	public ModelAndView comein(Integer id,Integer instId) throws CustomException {
		Entity entity=service.getById(id);
		Module module=entity.getModule();
		Integer moduleId=module.getId();
		String dir=OmcServerContext.getInstance().getFtpDir();
		File file=new File(dir,moduleId+"-"+instId);
		if(!file.exists()) throw new CustomException("网元【"+module.getName()+">>"+entity.getName()+"】已被删除");
		ModelAndView mav = new ModelAndView("fileManager/files");
		mav.addObject("title",entity.getName());
		mav.addObject("dir",file.getPath().replace("\\", "/"));
		return mav;
	}
	
	@RequestMapping(value="listLog")
	public ModelAndView listLog(Integer id,Integer instId) throws CustomException {
		Entity entity=service.getById(id);
		Module module=entity.getModule();
		Integer moduleId=module.getId();
		String dir=OmcServerContext.getInstance().getFtpDir()
			+File.separator+moduleId+"-"+instId+File.separator+module.getLog();
		logger.debug("日志目录："+dir);
		File file=new File(dir);
		if(!file.exists()||!file.isDirectory()) 
			throw new CustomException("网元【"+module.getName()+">>"+entity.getName()+"】日志已被删除");
		ModelAndView mav = new ModelAndView("entity/logs");
		mav.addObject("title",entity.getName());
		mav.addObject("dir",file.getPath().replace("\\", "/"));
		return mav;
	}
	
	/**
	 * 日志下载操作
	 */
	@RequestMapping(value="downloadAllLog")
	public void downloadAllLog(HttpServletResponse response) throws CustomException, IOException {
		String oamDir=OmcServerContext.getInstance().getFtpDir();
		List<Entity> entities=service.findAll();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ServletUtil.setFileDownloadHeader(response,sdf.format(new Date())+"_log.zip");
		ServletOutputStream sos=response.getOutputStream();
		try(ZipOutputStream zos=new ZipOutputStream(sos);){
			for(Entity e:entities){
				Module module=e.getModule();
				fileManagerService.downloadAll(zos,oamDir+File.separator+module.getId()+"-"+e.getInstId()+File.separator+module.getLog());
			}
		}
	}
	
	/**
	 * 日志下载操作
	 */
	@RequestMapping(value="logdownload")
	public void logdownload(HttpServletResponse response,Integer[] ids) throws CustomException, IOException {
		String oamDir=OmcServerContext.getInstance().getFtpDir();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ServletUtil.setFileDownloadHeader(response,sdf.format(new Date())+"_log.zip");
		ServletOutputStream sos=response.getOutputStream();
		try(ZipOutputStream zos=new ZipOutputStream(sos);){
			for(Integer id:ids){
				Entity e=service.getById(id);
				Module module=e.getModule();
				fileManagerService.downloadAll(zos,oamDir+File.separator+module.getId()+"-"+e.getInstId()+File.separator+module.getLog());
			}
		}
	}
	
	/**
	 * 开机自动启动设置操作
	 */
	@RequestMapping(value="startup")
	public @ResponseBody String startup(Integer id) throws IOException, CustomException{
		service.setStartup(id,true);
		JSONObject j=new JSONObject();
		j.element("msg", "开机自启动设置成功");
		return j.toString();
	}
	
	/**
	 * 关闭开机自启操作
	 */
	@RequestMapping(value="shutdown")
	public @ResponseBody String shutdown(Integer id) throws IOException, CustomException{
		service.setStartup(id,false);
		JSONObject j=new JSONObject();
		j.element("msg", "禁止开机自启设置成功");
		return j.toString();
	}
}
