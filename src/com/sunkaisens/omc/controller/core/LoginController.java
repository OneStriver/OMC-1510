package com.sunkaisens.omc.controller.core;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.sunkaisens.omc.context.core.OmcServerContext;
import com.sunkaisens.omc.po.core.User;
import com.sunkaisens.omc.service.core.UserService;
import com.sunkaisens.omc.util.ZipUtils;
/**
 * 
 * 
 * 
 * 
 * 
 *Login Controller
 */
@Controller
@RequestMapping("/user")
public class LoginController {
	//注入UserService
	@Resource
	private UserService service;
	//注入LocaleResolver
	@Resource
	private LocaleResolver localreResolver;
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * login操作,根据条件返回不同视图
	 * @param session
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login")
	public ModelAndView login(HttpSession session, String username, String password)
			throws Exception {
		User user=service.getByNamePassword(username, password);
		ModelAndView modelAndView = new ModelAndView();
		session.setAttribute("theme", "blue");
		if(session.getAttribute("easyUILanguage")==null){
			session.setAttribute("easyUILanguage", "easyui-lang-zh_CN.js");
		}
		
		if(session.getAttribute("locale")==null){
			session.setAttribute("locale", "zh_CN");
		}
		
		if(user!=null){
			session.setAttribute("user", user);
			modelAndView.setViewName("redirect:/user/home.action");
		}else{
			modelAndView.setViewName("user/login");
			modelAndView.addObject("msg", "用户名或密码错误");
		}
		return modelAndView;
	}
/**
 * 
 * 
 * 
 * 
 * 
 * 对应注销操作，返回login视图
 * @param session
 * @return
 * @throws Exception
 */
	@RequestMapping("/logout")
	public String logout(HttpSession session) throws Exception {
		session.invalidate();
		return "user/login";
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 返回main视图
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/home")
	public String home(HttpSession session,Model model) throws Exception {
		model.addAttribute("project", OmcServerContext.getInstance().getProject());
		return "main";
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 返回时间至前台
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/showTime")
	public void showTime(HttpServletResponse response) throws Exception {
		response.getWriter().write(new Date().getTime()+"");
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 对应主题切换
	 * @param session
	 * @param theme
	 * @return
	 */
	@RequestMapping("/switchTheme")
	public String switchTheme(HttpSession session,String theme){
		session.setAttribute("theme", theme);
		return "main";
	}
	
	/**
	 * 对应语言选择
	 */
	@RequestMapping("/switchLanguage")
	public String switchLanguage(HttpServletRequest request,HttpServletResponse response){
		String language = request.getParameter("locale");
		String[] language_country = language.split("_");
		Locale locale = new Locale(language_country[0], language_country[1]);
		request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
		localreResolver.setLocale(request, response, locale);
		request.getSession().setAttribute("locale",language);
		return "/user/login";
	}
	/**
	 * 浏览器版本不正确时，查看是否需要下载最新浏览器
	 */
	@RequestMapping("/checkBrowserUI")
	public ModelAndView checkBrowserUI() {
		int check = OmcServerContext.getInstance().getCheckBrowserFlag();
		ModelAndView mav = null;
		if(check==1){
			mav = new ModelAndView("user/download");
			mav.addObject("downloadPath","user/download.action");
		}else{
			mav = new ModelAndView("user/login");
		}
		return mav;
	}
	
	/**
	 * 下载浏览器
	 */
	@RequestMapping("download")
	public void oneexport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<File> files = new ArrayList<File>();
        File Allfile = new File(request.getSession().getServletContext().getRealPath("/") + "browser/");
        if (Allfile.exists()) {
            File[] fileArr = Allfile.listFiles();
            for (File file2 : fileArr) {
                files.add(file2);
            }
        }
		String fileName = "Browser"+".zip";
        // 在服务器端创建打包下载的临时文件
        String outFilePath = request.getSession().getServletContext().getRealPath("/") + "browser/";

        File fileZip = new File(outFilePath + fileName);
        // 文件输出流
        FileOutputStream outStream = new FileOutputStream(fileZip);
        // 压缩流
        ZipOutputStream toClient = new ZipOutputStream(outStream);
        ZipUtils.zipFile(files, toClient);
        toClient.close();
        outStream.close();
        ZipUtils.downloadFile(fileZip, response, true);
	}

}
