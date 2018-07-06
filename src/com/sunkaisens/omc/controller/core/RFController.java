package com.sunkaisens.omc.controller.core;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sunkaisens.omc.context.core.OmcServerContext;
import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.po.core.Bts;
import com.sunkaisens.omc.po.core.Entity;
import com.sunkaisens.omc.po.core.Module;
import com.sunkaisens.omc.service.core.EntityService;
import com.sunkaisens.omc.service.core.ModuleService;
import com.sunkaisens.omc.service.core.RFService;
import com.sunkaisens.omc.vo.core.Bsc;
import com.sunkaisens.omc.vo.core.PageBean;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/rf")
public class RFController {
	@Resource
	private RFService service;
	@Resource
	private ModuleService moduleService;
	@Resource
	private EntityService entityService;
	
	private String bscDir;
	private String oamDir=OmcServerContext.getInstance().getFtpDir();

	//射频参数查看
	@RequestMapping("check")
	public ModelAndView check() throws IOException{
		Module module=moduleService.getByName("bsc");
		List<Entity> ens=entityService.find(module.getId());
		if(ens.size()>0){
			Entity e=ens.get(0);
			bscDir=oamDir+File.separator+module.getId()+"-"+e.getInstId();
		}
		Bsc bsc=service.readConf(bscDir);
		ModelAndView mav=new ModelAndView("rf/check");
		mav.addObject("bsc",bsc);
		return mav;
	}
	//射频参数查看 更新
	@RequestMapping("updateCheck")
	public @ResponseBody JSONObject updateCheck(Bsc bsc) throws IOException{
		JSONObject json=new JSONObject();
		service.updateCheck(bscDir,bsc);
		json.element("msg","修改完毕");
		return json;
	}
	
	//射频参数设置页面
	@RequestMapping(value="setList")
	public ModelAndView setList(){
		ModelAndView mav=new ModelAndView("rf/set");
		mav.addObject("pageBean", new PageBean());
		return mav;
	}
	//射频参数设置页面内容
	@RequestMapping("set")
	public @ResponseBody PageBean set(@RequestParam(defaultValue="1") Integer page,
			@RequestParam(value="rows",defaultValue="50") Integer pageSize){
		PageBean pageBean=service.getPageBean(page,pageSize,bscDir);
		return pageBean;
	}
	//射频参数设置页面 删除
	@RequestMapping(value="delete",produces="text/html;charset=UTF-8")
	public @ResponseBody String delete(String[] carrierIDs) throws CustomException, IOException{
		service.delete(carrierIDs[0],bscDir);
		JSONObject json=new JSONObject();
		json.element("msg", "删除完毕");
		return json.toString();
	}
	//射频参数设置页面  添加
	@RequestMapping(value="insert",produces="text/html;charset=UTF-8")
	public @ResponseBody String insert(Bts bts) throws CustomException, IOException{
		service.insert(bts,bscDir);
		JSONObject json=new JSONObject();
		json.element("msg", "添加完成");
		return json.toString();
	}
	//射频参数设置页面 更新
	@RequestMapping(value="update",produces="text/html;charset=UTF-8")
	public @ResponseBody String update(Bts bts) throws CustomException,IOException{
		service.update(bts,bscDir);
		JSONObject json=new JSONObject();
		json.element("msg", "保存完毕");
		return json.toString();
	}
	
}
