package com.sunkaisens.omc.controller.core;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.sunkaisens.omc.service.core.BrowserDownload;

@Controller
@RequestMapping("/browser")
public class BrowserDownloadController {
	@Resource
	private BrowserDownload browserDownload;
	@RequestMapping("/downloadUI")
	public String down(){
		return "user/downalert";
	}
	@RequestMapping("/download")
	public void oneexport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String fileName="Firefox"+".zip";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-Disposition", "attachment;filename="+fileName);
		browserDownload.download(response.getOutputStream(),response);
	}
}
