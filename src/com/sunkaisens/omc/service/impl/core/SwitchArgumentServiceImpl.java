package com.sunkaisens.omc.service.impl.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.sunkaisens.omc.context.core.OmcServerContext;
import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.po.core.Config;
import com.sunkaisens.omc.po.core.Entity;
import com.sunkaisens.omc.po.core.Module;
import com.sunkaisens.omc.service.core.ConfigService;
import com.sunkaisens.omc.service.core.EntityService;
import com.sunkaisens.omc.service.core.ModuleService;
import com.sunkaisens.omc.service.core.SwitchArgumentService;
import com.sunkaisens.omc.vo.core.NumberTranslationResult;

/**
 * SwitchArgumentService接口实现类
 */
@Service
public class SwitchArgumentServiceImpl implements SwitchArgumentService {
	
	@Resource
	private EntityService entityService;
	@Resource
	private ModuleService moduleService;
	@Resource
	private ConfigService configService;
	
	@Override
	public List<NumberTranslationResult> readNumberTranslations() throws Exception{
		Module module=moduleService.getByName("vcs");
		if(module == null){
			throw new CustomException("VCS网元未下发");
		}
		List<Entity> entities=entityService.find(module.getId());
		Config config=configService.findByModuleIdAndName(module.getId(),"NumberTranslation.txt");
		String oamDir=OmcServerContext.getInstance().getFtpDir();
		File f=new File(oamDir+File.separator+module.getId()+"-"+entities.get(0).getInstId()+File.separator+config.getName());
//		File f=new File("D:\\NumberTranslation.txt");
		List<String> lines=FileUtils.readLines(f);
		return lines2NumberTranslation(lines);
	}
	
	private List<NumberTranslationResult> lines2NumberTranslation(List<String> lines){
		List<NumberTranslationResult> nts=new ArrayList<NumberTranslationResult>();
		for(String line:lines){
			if(StringUtils.isNotBlank(line)){
				String[] kv=line.split(":");
				nts.add(new NumberTranslationResult(kv[0].trim(),kv.length>1?kv[1].trim():""));
			}
		}
		return nts;
	}

	@Override
	public void updateNumberTranslations(List<String> originalNum, List<String> mappingNum,List<String> serviceOption) throws Exception {
		Module module=moduleService.getByName("vcs");
		List<Entity> entities=entityService.find(module.getId());
		Config config=configService.findByModuleIdAndName(module.getId(), "NumberTranslation.txt");
		String oamDir=OmcServerContext.getInstance().getFtpDir();
		File f=new File(oamDir+File.separator+module.getId()+"-"+entities.get(0).getInstId()+File.separator+config.getName());
//		File f=new File("D:\\NumberTranslation.txt");
		List<String> lines=new ArrayList<>();
		for(int i=0;i<originalNum.size();i++){
			lines.add(originalNum.get(i)+":"+mappingNum.get(i));
		}
		FileUtils.writeLines(f, lines);
	  
	}
	
}
