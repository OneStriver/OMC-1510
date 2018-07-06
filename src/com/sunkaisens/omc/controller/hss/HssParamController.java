package com.sunkaisens.omc.controller.hss;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sunkaisens.omc.mapper.core.HssMetaMapper;
import com.sunkaisens.omc.service.hss.HssParamService;
/**
 * 
 * 
 * 
 * 
 * 
 *hssParam Controller
 */
@Controller
@RequestMapping("/hssParam")
public class HssParamController {
	//注入HssMetaMapper
	@Resource 
	private HssMetaMapper mapper;
	//注入HssParamService
	@Resource 
	private HssParamService serivce;
    /**
     * 
     * 
     * 
     * 
     * 
     * 
     * 返回前台list视图
     * @return
     */
	@RequestMapping("listUI")
	public ModelAndView param(){
		ModelAndView mav=new ModelAndView("hssParam/list");
		List<Map<String, ?>> devices=mapper.getAllDeviceType();
		List<Map<String, ?>> voices=mapper.getAllVoiceType();
		List<Map<String, ?>> allBusinesses=mapper.getAllBusiness();
		List<Map<String,?>> viewItems = mapper.getAllViewItem();
		mav.addObject("devices", devices);
		mav.addObject("voices", voices);
		mav.addObject("allBusinesses", allBusinesses);
		mav.addObject("viewItems", viewItems);
		return mav;
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * hssParam 更新操作
	 * @param device
	 * @param voice
	 * @param business
	 */
	@RequestMapping(value="update",produces="text/html;charset=UTF-8")
	public void update(Integer[] device,Integer[] voice,Integer[] business, Integer[] viewItem){
		serivce.update(device, voice, business,viewItem);
	}
}
