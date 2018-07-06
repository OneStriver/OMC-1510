package com.sunkaisens.omc.controller.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sunkaisens.omc.context.core.OmcServerContext;

@Controller
@RequestMapping("/link")
public class LinkController {
	
	
	@RequestMapping("physics")
	public ModelAndView physics() throws IOException{
		ModelAndView mav=new ModelAndView("link/physics");
		return mav;
	}
	
	@RequestMapping(value="ping",produces="text/html;charset=UTF-8")
	public ModelAndView ping() throws IOException{
		ModelAndView mav=new ModelAndView("link/physics");
		String line = null;
		StringBuffer lineStr = new StringBuffer();		
		String pingIp = OmcServerContext.getInstance().getPingIp();
		try {
			Process pro = Runtime.getRuntime().exec("ping "+pingIp+"  -n 5");
			BufferedReader buf = new BufferedReader(new InputStreamReader(pro.getInputStream(),"GBK"));
			while((line=buf.readLine())!=null){
				System.err.println(line);
				lineStr.append(line+"<br>");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		mav.addObject("line",lineStr);
		System.out.println(lineStr);
		return mav;
	}
}
