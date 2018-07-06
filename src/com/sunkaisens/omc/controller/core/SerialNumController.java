package com.sunkaisens.omc.controller.core;


import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.service.core.SerialNumberService;
import com.sunkaisens.omc.vo.core.SerialNumber;
/**
 * 
 * 
 * 
 * 
 * SerialNumber Controller
 *
 */
@Controller
@RequestMapping(value="/serialNumber")
public class SerialNumController {
    //注入SerialNumberService
	@Resource
	private SerialNumberService service;
	/**
	 * @RequestMapping("listUI")
	public @ResponseBody String listUI() throws CustomException{
		List<SerialNumber> list=service.readSerial();
		JSONObject json=new JSONObject();
		json.element("list", list);
		return json.toString();
	}
	 * 
	 * 
	 * 
	 * 
	 * 读取板卡序列号,并返回list至前台
	 * @return 
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping("listUI")
	public/* @ResponseBody*/ ModelAndView listUI() throws CustomException{
		List<SerialNumber> list=service.readSerial();
//		List<SerialNumber> list = new ArrayList<SerialNumber>();
//		for(int i = 0; i< 4; i++){
//			 SerialNumber serialNumber = new SerialNumber();
//			 serialNumber.setName("主控板"+i);
//			 serialNumber.setSerial("0EXSSJAHGUQIJJWJ8997R"+i);
//			 list.add(serialNumber);
//		}
		ModelAndView mav=new ModelAndView("serialNumber/list");
		mav.addObject("list",list);
		return mav;
	
	}
	
	//返回JSON数据的方法
	@RequestMapping("listSerialNumber")
	public @ResponseBody String listSerialNumber() throws CustomException{
		List<SerialNumber> list=service.readSerial();
		JSONObject json=new JSONObject();
		json.element("list", list);
		return json.toString();
	}
	
	@RequestMapping(value="downLoad",produces="text/html;charset=UTF-8")
	public  void downLoad(String name,HttpServletResponse response) throws CustomException, IOException{
		List<SerialNumber> list=service.readSerial();
//		List<SerialNumber> list = new ArrayList<SerialNumber>();
//		for(int i = 0; i< 4; i++){
//			 SerialNumber serialNumber = new SerialNumber();
//			 serialNumber.setName("主控板"+i);
//			 serialNumber.setSerial("0EXSSJAHGUQIJJWJ8997R"+i);
//			 list.add(serialNumber);
//		}
		if(!name.equals("")||name!=null){
			for(SerialNumber serialNumber : list){
				if(name.equals(serialNumber.getName())){
					String fileName=name+"序列号.txt";
					response.reset();
					response.setContentType("application/x-msdownload");
					response.setHeader("Content-Disposition", "attachment;filename="+
						URLEncoder.encode(fileName,"UTF-8"));
					try {			
						OutputStream outputStream = response.getOutputStream();
						outputStream.write(serialNumber.getSerial().getBytes(), 0, serialNumber.getSerial().getBytes().length);
					    outputStream.flush();
						outputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
			}
		}
		
	}
}
