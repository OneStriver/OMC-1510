package com.sunkaisens.omc.controller.core;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sunkaisens.omc.context.core.OmcServerContext;
import com.sunkaisens.omc.po.core.Entity;
import com.sunkaisens.omc.po.core.Module;
import com.sunkaisens.omc.service.core.EntityService;
import com.sunkaisens.omc.service.core.ModuleService;
import com.sunkaisens.omc.service.core.OutService;
import com.sunkaisens.omc.vo.core.Fxo;
import com.sunkaisens.omc.vo.core.Isup;
import com.sunkaisens.omc.vo.core.Sip;

import net.sf.json.JSONObject;
/**
 * 
 * 
 * 
 * Out Controller
 */
@Controller
@RequestMapping("/out")
public class OutController {
	//
    //注入OutService
	@Resource
	private OutService service;
	//
	//注入EntityService
	@Resource
	private EntityService entityService;
	//
	//注入ModuleService
	@Resource
	private ModuleService moduleService;
	
	private String isupDir;
	private String tupDir;
	private String sgwDir;
	private String sipDir;
	private String fxoDir;
	private String isupDir2;
	private String tupDir2;
	private String sgwDir2;
	private String oamDir=OmcServerContext.getInstance().getFtpDir();
	
	@RequestMapping("isup")
	public ModelAndView isup() throws IOException{
		Module module=moduleService.getByName("isup");
		List<Entity> ens=entityService.find(module.getId());
		if(ens.size()>0){
			Entity e=ens.get(0);
			isupDir=oamDir+File.separator+module.getId()+"-"+e.getInstId();
		}
		module=moduleService.getByName("sgw");
		ens=entityService.find(module.getId());
		if(ens.size()>0){
			Entity e=ens.get(0);
			sgwDir=oamDir+File.separator+module.getId()+"-"+e.getInstId();
		}
		ModelAndView mav=new ModelAndView("out/isup");
		mav.addObject("index", "");
		Isup isup=service.readConf(isupDir,sgwDir,false);
		mav.addObject(isup);
		return mav;
	}
	
	@RequestMapping("isup2")
	public ModelAndView isup2() throws IOException{
		Module module=moduleService.getByName("isup");
		List<Entity> ens=entityService.find(module.getId());
		if(ens.size()>1){
			Entity e=ens.get(1);
			isupDir2=oamDir+File.separator+module.getId()+"-"+e.getInstId();
		}
		module=moduleService.getByName("sgw");
		ens=entityService.find(module.getId());
		if(ens.size()>1){
			Entity e=ens.get(1);
			sgwDir2=oamDir+File.separator+module.getId()+"-"+e.getInstId();
		}
		ModelAndView mav=new ModelAndView("out/isup");
		mav.addObject("index", "2");
		Isup isup=service.readConf(isupDir2,sgwDir2,false);
		mav.addObject(isup);
		return mav;
	}
	
	//===========================TUP======================
	@RequestMapping("tup")
	public ModelAndView tup() throws IOException{
		Module module=moduleService.getByName("tup");
		List<Entity> ens=entityService.find(module.getId());
		if(ens.size()>0){
			Entity e=ens.get(0);
			tupDir=oamDir+File.separator+module.getId()+"-"+e.getInstId();
		}
		module=moduleService.getByName("sgw");
		ens=entityService.find(module.getId());
		if(ens.size()>0){
			Entity e=ens.get(0);
			sgwDir=oamDir+File.separator+module.getId()+"-"+e.getInstId();
		}
		ModelAndView mav=new ModelAndView("out/tup");
		mav.addObject("index", "");
		Isup isup=service.readConf(tupDir,sgwDir,true);
		mav.addObject(isup);
		return mav;
	}
	
	@RequestMapping("tup2")
	public ModelAndView tup2() throws IOException{
		Module module=moduleService.getByName("tup");
		List<Entity> ens=entityService.find(module.getId());
		if(ens.size()>1){
			Entity e=ens.get(1);
			tupDir2=oamDir+File.separator+module.getId()+"-"+e.getInstId();
		}
		module=moduleService.getByName("sgw");
		ens=entityService.find(module.getId());
		if(ens.size()>1){
			Entity e=ens.get(1);
			sgwDir2=oamDir+File.separator+module.getId()+"-"+e.getInstId();
		}
		ModelAndView mav=new ModelAndView("out/tup");
		mav.addObject("index", "2");
		Isup isup=service.readConf(tupDir2,sgwDir2,true);
		mav.addObject(isup);
		return mav;
	}
	
	//===========================SIP出局======================
	@RequestMapping("sip")
	public ModelAndView sip() throws IOException{
		Module module=moduleService.getByName("sipt");
		List<Entity> ens=entityService.find(module.getId());
		if(ens.size()>0){
			Entity e=ens.get(0);
			sipDir=oamDir+File.separator+module.getId()+"-"+e.getInstId();
		}
		ModelAndView mav=new ModelAndView("out/sip");
		Sip sip=service.readSipConf(sipDir);
		mav.addObject(sip);
		return mav;
	}
	
	@RequestMapping("sip/update")
	public @ResponseBody JSONObject updateSip(Sip sip) throws IOException{
		JSONObject json=new JSONObject();
		service.updateSip(sipDir,sip);

		json.element("msg","修改完毕");
		return json;
	}
	
	//===========================FXO出局======================
	@RequestMapping("fxo")
	public ModelAndView fxo() throws IOException{
		Module module=moduleService.getByName("fxo");
		List<Entity> ens=entityService.find(module.getId());
		if(ens.size()>0){
			Entity e=ens.get(0);
			fxoDir=oamDir+File.separator+module.getId()+"-"+e.getInstId();
		}
		ModelAndView mav=new ModelAndView("out/fxo");
		Fxo fxo=service.readFxoConf(fxoDir);
		mav.addObject(fxo);
		return mav;
	}
	
	@RequestMapping("fxo/update")
	public @ResponseBody JSONObject updateFxo(Fxo fxo) throws IOException{
		JSONObject json=new JSONObject();
		service.updateFxo(fxoDir,fxo);

		json.element("msg","修改完毕");
		return json;
	}
	
	/**
	 * 
	 * 
	 * 
	 * 修改操作
	 * @param index
	 * @param isup
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("isup/update")
	public @ResponseBody JSONObject updateIsup(String index,Isup isup) throws IOException{
		JSONObject json=new JSONObject();
		if("2".equals(index)){
			service.update(isupDir2,sgwDir2,isup,false);
		}else{
			service.update(isupDir,sgwDir,isup,false);
		}
		json.element("msg","修改完毕");
		return json;
	}
	/**
	 * 
	 * 
	 * 
	 * 修改操作
	 * @param index
	 * @param isup
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("tup/update")
	public @ResponseBody JSONObject updateTup(String index,Isup isup) throws IOException{
		JSONObject json=new JSONObject();
		if("2".equals(index)){
			service.update(tupDir2,sgwDir2,isup,true);
		}else{
			service.update(tupDir,sgwDir,isup,true);
		}
		json.element("msg","修改完毕");
		return json;
	}
	
}
