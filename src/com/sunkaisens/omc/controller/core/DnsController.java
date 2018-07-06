package com.sunkaisens.omc.controller.core;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.service.core.DnsService;
import com.sunkaisens.omc.vo.core.Dns;
import com.sunkaisens.omc.vo.core.PageBean;
/**
 * 
 * 
 * 
 * DNS Controller
 *
 */
@Controller@RequestMapping("/dns")
public class DnsController  {
	@Resource
	private DnsService service;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 从数据库获取数据列表
	 * @param cardNum
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping("/list")
	public @ResponseBody PageBean list(@RequestParam(defaultValue="1") Integer page,
			@RequestParam(value="rows",defaultValue="50") Integer pageSize,Integer cardNum) throws CustomException{
		List<Dns> dns=service.query(cardNum);
		List<Dns> listPart = new ArrayList<Dns>();
		int size = dns.size();
		int startItem = (page-1)*pageSize;
		for(int i = 0 ; i<size;i++){
			if(startItem<=i&&i<startItem+pageSize){
				listPart.add(dns.get(i));
			}
		}
		PageBean pageBean=new PageBean(1, 50, listPart, dns.size());
		return pageBean;
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回前台list视图
	 * @return
	 */
	@RequestMapping("listUI")
	public ModelAndView listUI(){
		ModelAndView mav=new ModelAndView("dns/list");
		PageBean pageBean=new PageBean();
		mav.addObject("pageBean", pageBean);
		return mav;
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 更新操作
	 * @param dns
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping("update")
	public @ResponseBody String update(Dns dns) throws CustomException{
		service.update(dns);
		JSONObject json=new JSONObject();
		json.element("msg","修改完毕");
		return json.toString();
	}
}
