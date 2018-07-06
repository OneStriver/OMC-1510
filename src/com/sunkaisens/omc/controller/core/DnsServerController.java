package com.sunkaisens.omc.controller.core;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.xbill.DNS.Name;
import org.xbill.DNS.Type;

import com.sunkaisens.omc.context.core.OmcServerContext;
import com.sunkaisens.omc.service.core.DnsServerService;
import com.sunkaisens.omc.vo.core.DnsAxfr;
import com.sunkaisens.omc.vo.core.DnsDomain;
import com.sunkaisens.omc.vo.core.PageBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 
 * 
 * 
 * 
 *DnsServer Controller
 */
@Controller@RequestMapping("/dnsServer")
public class DnsServerController  {
	@Resource
	private DnsServerService service;
	// DnsServerIp
	private String host=OmcServerContext.getInstance().getDnsServerIp();
	//DnsServerPort
	private int port=OmcServerContext.getInstance().getDnsServerPort();
	/**
	 * 
	 * 
	 * 
	 * 获取数据库数据列表
	 * @param cardNum
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public @ResponseBody PageBean list(@RequestParam(defaultValue="1") Integer page,
			@RequestParam(value="rows",defaultValue="50") Integer pageSize,Integer cardNum) throws Exception{
		
		List<DnsDomain> domains=service.list(host, port);
		List<DnsDomain> listPart = new ArrayList<DnsDomain>();
		int size = domains.size();
		int startItem = (page-1)*pageSize;
		for(int i = 0 ; i<size;i++){
			if(startItem<=i&&i<startItem+pageSize){
				listPart.add(domains.get(i));
			}
		}
		PageBean pageBean=new PageBean(1, 50, listPart, domains.size());
		return pageBean;
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回前台list界面
	 * @return
	 */
	@RequestMapping("listUI")
	public ModelAndView listUI(){
		ModelAndView mav=new ModelAndView("dnsServer/list");
		PageBean pageBean=new PageBean();
		mav.addObject("pageBean", pageBean);
		return mav;
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回前台listAxfr界面
	 * @param domain
	 * @return
	 */
	@RequestMapping("listAxfrUI")
	public ModelAndView listAxfrUI(String domain){
		ModelAndView mav=new ModelAndView("dnsServer/listAxfr");
		mav.addObject("domain", domain);
		return mav;
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 获取数据库数据列表
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listAxfr")
	public @ResponseBody PageBean listAxfr(String domain) throws Exception{
		List<DnsAxfr> list=service.listAxfr(domain,host);
		PageBean pageBean=new PageBean(1, list.size(), list, list.size());
		return pageBean;
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 往前台返回数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="listAllAxfr",produces="text/html;charset=UTF-8")
	public @ResponseBody String listAllAxfr() throws Exception{
		List<DnsAxfr> list=service.listAllAxfr(host,port);
		return JSONArray.fromObject(list).toString();
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 更新操作
	 * @param axfr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("update")
	public @ResponseBody JSONObject update(DnsAxfr axfr) throws Exception{
		service.doUpdate(host, Name.fromString(axfr.getZone(), Name.root), null, null, 
				Name.fromString(axfr.getName(), Name.root), 
				Type.value(axfr.getType()), Long.parseLong(axfr.getTtl()), 
				axfr.getOldData(), axfr.getData());
		JSONObject json=new JSONObject();
		json.element("msg","修改完毕");
		return json;
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 删除操作
	 * @param name
	 * @param zone
	 * @param type
	 * @param data
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="delete")
	public @ResponseBody String delete(String[] name,String[] zone,String[] type,String[] data) throws Exception{
		for(int i=0;i<name.length;i++){
			DnsAxfr axfr=new DnsAxfr(name[i], null, null, type[i], data[i]);
			axfr.setZone(zone[i]);
			service.doDelete(host, Name.fromString(axfr.getZone(), Name.root), 
					null, null, Name.fromString(axfr.getName(), Name.root), Type.value(axfr.getType()), axfr.getData());
		}
		JSONObject json=new JSONObject();
		json.element("msg","删除成功");
		return json.toString();
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 添加操作
	 * @param axfr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("add")
	public @ResponseBody JSONObject add(DnsAxfr axfr) throws Exception{
		service.doAdd(host, Name.fromString(axfr.getZone(), Name.root), null, null
				, Name.fromString(axfr.getName(), Name.root), Type.value("A"), 
				Long.parseLong(axfr.getTtl()), axfr.getData());
		JSONObject json=new JSONObject();
		json.element("msg","添加完毕");
		return json;
	}
}
