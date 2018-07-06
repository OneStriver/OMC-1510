package com.sunkaisens.omc.controller.core;


import java.util.ArrayList;




import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.mapper.core.IpDnsMapper;
import com.sunkaisens.omc.po.core.IpDns;
import com.sunkaisens.omc.service.core.CardService;
import com.sunkaisens.omc.service.core.HostAddrService;
import com.sunkaisens.omc.vo.core.HostAddr;
import com.sunkaisens.omc.vo.core.PageBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 
 * 
 * 
 * 
 * 
 * HostAddress Controller
 */
@Controller@RequestMapping("/hostaddr")
public class HostAddrController{
	@Resource
	private HostAddrService service;
	@Resource 
	private CardService cardService;
	@Resource
	private IpDnsMapper ipDnsMapper;
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 向前台返回经过处理后的数据
	 * @param eth
	 * @param cardNum
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="listAllAxfr",produces="text/html;charset=UTF-8")
	public @ResponseBody String listAllAxfr(String eth,Integer cardNum) throws Exception{
//		List<HostAddr> list = new ArrayList<HostAddr>();
//		for(int i=0;i<20;i++){
//			HostAddr host = new HostAddr("192.168.1.196", "localhost"+i);
//			list.add(host);
//		}
//		JSONArray arr=JSONArray.fromObject(list);
//		return arr.toString();
		
		List<HostAddr> list=service.list();
		JSONArray arr=JSONArray.fromObject(list);
		if(StringUtils.isEmpty(eth)){
			return arr.toString();
		}else{
			IpDns ipDns=ipDnsMapper.selectByEthAndCardNum(eth, cardNum);
			if(ipDns==null){
				return arr.toString();
			}else{
				String dnsStr=ipDns.getDnsStr();
				if(StringUtils.isEmpty(dnsStr)){
					return arr.toString();
				}else{
					String[] dns=dnsStr.split(",");
					for(String d:dns){
						for(Object o:arr){
							JSONObject json=(JSONObject)o;
							if(json.getString("host").equals(d)){
								json.element("selected", true);
							}
						}
					}
					return arr.toString();
				}
			}
		}
		
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 从数据库获取数据列表
	 * @param page
	 * @param pageSize
	 * @param cardNum
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping("/list")
	public @ResponseBody PageBean list(@RequestParam(defaultValue="1") Integer page,
			@RequestParam(value="rows",defaultValue="50") Integer pageSize,
			@RequestParam(defaultValue="0")Integer cardNum) throws CustomException{
		List<HostAddr> list=service.list();
//		List<HostAddr> list=new ArrayList<HostAddr>();
//		for(int i=0;i<20;i++){
//			HostAddr e=new HostAddr("192.168.1."+i, "local,host");
//			list.add(e);
//		}
		List<HostAddr> listPart = new ArrayList<HostAddr>();
		int size = list.size();
		int startItem = (page-1)*pageSize;
		for(int i = 0 ; i<size;i++){
			if(startItem<=i&&i<startItem+pageSize){
				listPart.add(list.get(i));
			}
		}
		PageBean pageBean=new PageBean(1, 50, listPart, list.size());
		return pageBean;
	}
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
	@RequestMapping("/listUI")
	public ModelAndView listUI(){
		ModelAndView mav=new ModelAndView("hostaddr/list");
		PageBean pageBean=new PageBean();
		mav.addObject("pageBean", pageBean);
		return mav;
	}
    /**
     * 
     * 
     * 
     * 
     * 
     * 更新操作
     * @param hostAddr
     * @return
     * @throws CustomException
     */
	@RequestMapping(value="/update",produces="text/html;charset=UTF-8")
	public @ResponseBody String update(HostAddr hostAddr) throws CustomException{
		service.update(hostAddr);
		JSONObject json=new JSONObject();
		json.element("msg", "修改完毕");
		return json.toString();
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 保存操作
	 * @param hostAddr
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping(value="/save",produces="text/html;charset=UTF-8")
	public @ResponseBody String save(HostAddr hostAddr)throws CustomException{
		service.save(hostAddr);
		JSONObject json=new JSONObject();
		json.element("msg", "添加完毕");
		return json.toString();
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 删除操作
	 * @param ids
	 * @param hostNames
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping("/delete")
	public @ResponseBody String delete(String[] ids,String[] hostNames)throws CustomException{
		service.delete(ids,hostNames);
		JSONObject json=new JSONObject();
		json.element("msg", "删除完毕");
		return json.toString();
	}
}
