package com.sunkaisens.omc.service.impl.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.google.gxp.com.google.common.io.ZipFiles;
import com.sunkaisens.omc.context.core.OmcServerContext;
import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.mapper.core.SoMapper;
import com.sunkaisens.omc.po.core.So;
import com.sunkaisens.omc.service.core.SoService;
import com.sunkaisens.omc.thread.cncpMsg.CncpProtoType;
import com.sunkaisens.omc.thread.cncpMsg.CncpTaskExecutor;
import com.sunkaisens.omc.thread.cncpMsg.SendCncpMsgFactory;
import com.sunkaisens.omc.thread.messageEncapsulation.SetUpMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.SetUpResponseMsg;
import com.sunkaisens.omc.util.CncpStatusTransUtil;
import com.sunkaisens.omc.util.FileUtil;

/**
 *SoService接口实现类
 */
@Service
public class SoServiceImpl implements SoService {
	@Resource
	private SoMapper mapper;
	@Resource
	private CncpTaskExecutor cncpTaskExecutor;
	@Resource
	private SendCncpMsgFactory sendCncpMsgFactory;
	
	private final String updateDir ="/lib/oam/common";
	private final String authDir ="/lib/oam/auth";
	
	@Override
	public List<So> findAll() {
		File updateDirFile=new File(updateDir);
		if(!updateDirFile.exists()) updateDirFile.mkdirs();
		File authDirFile=new File(authDir);
		if(!authDirFile.exists()) authDirFile.mkdirs();
		
		Collection<File> files=FileUtils.listFiles(updateDirFile, null, false);
		Collection<File> files2=FileUtils.listFiles(authDirFile, null, false);
		Collections.addAll(files,files2.toArray(new File[files2.size()]));
		
		List<So> sos=new ArrayList<So>();
		for(File file:files){
			String name=file.getName();
			So so=mapper.selectByName(name);
			if(so==null){
				so=new So(name, new Date());
				mapper.insert(so);
			}
			sos.add(so);
		}
		return sos;
	}
	
	@Override
	public void delete(String[] names) throws CustomException{
		for(String name:names){
			File file=new File(updateDir,name);
			File file2=new File(authDir,name);
			if(file.exists()) file.delete();
			else if(file2.exists()) file2.delete();
			mapper.deleteByName(name);
		}
		String data=StringUtils.join(names, "\n")+"\n";
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
				CncpProtoType.SUB_OAM, 0, CncpProtoType.OAM_SET_LIB_DELETE, data);
		SetUpResponseMsg respMsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
		if(respMsg.getResult()!=0){
			throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(respMsg.getResult()));
		}
	}
	
	@Override
	public void save(File file,String name,boolean isAuth) throws Exception {
		if (!name.toLowerCase().endsWith(".zip"))
			throw new CustomException("请以.zip命名！");
		if (!FileUtil.isZip(file))
			throw new CustomException("此文件不是ZIP格式！");
		File updateFolder=new File(updateDir);
		if(isAuth) updateFolder=new File(authDir);
		StringBuilder sb=new StringBuilder();
		try(ZipFile zipFile=new ZipFile(file)){
			Enumeration<? extends ZipEntry> entries=zipFile.entries();
			Date date=new Date();
			while(entries.hasMoreElements()){
				ZipEntry entry=entries.nextElement();
				if(entry.isDirectory()) throw new CustomException("ZIP包中只能有动态库，不能有文件夹");
				String fileName=entry.getName();
				sb.append(fileName).append("\n");
				File tmp1=new File(updateDir,fileName);
				File tmp2=new File(authDir,fileName);
				if(tmp1.exists()) tmp1.delete();
				if(tmp2.exists()) tmp2.delete();
				So so=mapper.selectByName(fileName);
				if(so!=null){
					mapper.deleteByName(fileName);
				}
				so=new So(fileName,date);
				mapper.insert(so);
			}
		}
		ZipFiles.unzipFile(file,updateFolder);
		
		SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
				CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
				CncpProtoType.SUB_OAM, 0, CncpProtoType.OAM_SET_LIB_UPDATE, sb.toString());
		SetUpResponseMsg respMsg = cncpTaskExecutor.invokeSetUpMsg(msg, false, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
	}
}