package com.sunkaisens.omc.controller.core;

import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.sunkaisens.omc.service.core.SwitchArgumentService;
import com.sunkaisens.omc.vo.core.NumberTranslation;
import net.sf.json.JSONObject;
/**
 * 
 * 
 * 
 * 
 * 
 *SwitchArgument Controller
 */
@Controller
@RequestMapping(value = "/switch")
public class SwitchArgumentController {
    //注入SwitchArgumentService
	@Resource
	private SwitchArgumentService service;
    /**
     * 
     * 
     * 
     * 
     * 
     * 
     *  返回前台list界面
     * @return
     * @throws IOException
     */
	@RequestMapping("listUI")
	public ModelAndView listUI() throws Exception {
		ModelAndView mav = new ModelAndView("switch/list");
		List<NumberTranslation> nts=service.readNumberTranslations();
//		List<NumberTranslation> nts = new ArrayList<NumberTranslation>();
//		for(int i=0;i<10;i++){
//			NumberTranslation numberTranslation = new NumberTranslation("*730"+i, "*72?");
//			nts.add(numberTranslation);
//		}
//		for(int i=0;i<10;i++){
//			NumberTranslation numberTranslation = new NumberTranslation("XX"+i, "XXXX");
//			nts.add(numberTranslation);
//		}
		mav.addObject("nts", nts);
		return mav;
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 更新操作
	 * @param ori
	 * @param des
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="update",produces=MediaType.TEXT_HTML_VALUE+";charset=UTF-8")
	public @ResponseBody String update(String[] ori,String[] des) throws Exception {
		service.updateNumberTranslations(ori,des);
		JSONObject json=new JSONObject();
		json.element("msg", "修改成功");
		return json.toString();
	}
}	
