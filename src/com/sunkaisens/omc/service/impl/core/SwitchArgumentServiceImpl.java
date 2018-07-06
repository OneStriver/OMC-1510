package com.sunkaisens.omc.service.impl.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
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
import com.sunkaisens.omc.util.CncpStatusTransUtil;
import com.sunkaisens.omc.vo.core.NumberTranslation;
/**
 * 
 * 
 * 
 * 
 * 
 * SwitchArgumentService接口实现类
 *
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
	public List<NumberTranslation> readNumberTranslations() throws Exception{
		Module module=moduleService.getByName("vcs");
		if(module == null){
			throw new CustomException("VCS管理网元未下发");
		}
		List<Entity> entities=entityService.find(module.getId());
		Config config=configService.findByModuleIdAndName(module.getId(),"NumberTranslation.txt");
		String oamDir=OmcServerContext.getInstance().getFtpDir();
		File f=new File(oamDir+File.separator+module.getId()+"-"+entities.get(0).getInstId()+File.separator+config.getName());
		List<String> lines=FileUtils.readLines(f);
		return lines2NumberTranslation(lines);
	}
	
	private List<NumberTranslation> lines2NumberTranslation(List<String> lines){
		List<NumberTranslation> nts=new ArrayList<NumberTranslation>();
		for(String line:lines){
			if(StringUtils.isNotBlank(line)){
				String[] kv=line.split(":",2);
				nts.add(new NumberTranslation(kv[0].trim(),kv.length>1?kv[1].trim():""));
			}
		}
		return nts;
	}

	@Override
	public void updateNumberTranslations(String[] ori, String[] des) throws Exception {
		Module module=moduleService.getByName("vcs");
		List<Entity> entities=entityService.find(module.getId());
		Config config=configService.findByModuleIdAndName(module.getId(), "NumberTranslation.txt");
		String oamDir=OmcServerContext.getInstance().getFtpDir();
		File f=new File(oamDir+File.separator+module.getId()+"-"+entities.get(0).getInstId()+File.separator+config.getName());
		List<String> lines=new ArrayList<>();
		for(int i=0;i<ori.length;i++){
			lines.add(ori[i]+":"+des[i]);
		}
		lines.sort(new Comparator<String>() {
			public int compare(String o1, String o2) {
				o1=o1.split(":")[0];
				o2=o2.split(":")[0];
				if(!o1.contains("?")&&!o1.contains("X")){
					return -1;
				}else if(o1.contains("?")){
					if(!o2.contains("?")){
						return -1;
					}else{
						return o1.length()-o2.length();
					}
				}else if(o1.contains("X")){
					if(o2.contains("X")){
						return o1.length()-o2.length();
					}else{
						return 1;
					}
				}
				return 0;
			}
		});
		FileUtils.writeLines(f, lines);
	  
	}
}
