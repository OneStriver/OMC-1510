package com.sunkaisens.omc.service.impl.core;

import java.io.File;
import java.io.FileFilter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sunkaisens.omc.context.core.OmcServerContext;
import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.mapper.core.EntityMapper;
import com.sunkaisens.omc.po.core.Entity;
import com.sunkaisens.omc.service.core.OnekeyService;
import com.sunkaisens.omc.thread.cncpMsg.CncpProtoType;
import com.sunkaisens.omc.thread.cncpMsg.CncpTaskExecutor;
import com.sunkaisens.omc.thread.cncpMsg.SendCncpMsgFactory;
import com.sunkaisens.omc.thread.messageEncapsulation.SetUpMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.SetUpResponseMsg;
import com.sunkaisens.omc.util.CncpStatusTransUtil;
import com.sunkaisens.omc.util.FileUtil;
import com.sunkaisens.omc.util.ServletContextUtil;
import com.sunkaisens.omc.util.ZipCompressUtil;
/**
 * OnekeyService接口实现类
 */
@Service
public class OnekeyServiceImpl implements OnekeyService {
	protected final Logger log = LoggerFactory.getLogger(getClass());
	@Resource
	private EntityMapper entityMapper;
	@Resource
	private CncpTaskExecutor cncpTaskExecutor;
	@Resource
	private SendCncpMsgFactory sendCncpMsgFactory;
	@Override
	public void download(ServletOutputStream outputStream,HttpServletResponse response) throws Exception {
		try(
			InputStream is=getClass().getResourceAsStream("/db.properties");
			ZipOutputStream zos=new ZipOutputStream(outputStream,Charset.forName("UTF-8"));
		){
			Properties p=new Properties();
			p.load(is);
			String dbUser=p.getProperty("jdbc.username");
			String dbPwd=p.getProperty("jdbc.password");
			String command="mysqldump --skip-comments -u"+dbUser+" -p"+dbPwd+" myomc";
			Process process=Runtime.getRuntime().exec(command);
			String database=Streams.asString(process.getInputStream(),"UTF-8");
			database="drop database if exists myomc;\r\n"+
					"create database myomc char set utf8;\r\n"+
					"use myomc;\r\n"+database;
			int result=process.waitFor();
			if(result!=0){
				throw new CustomException(Streams.asString(process.getErrorStream()));
			}
			
			File liboam=new File("/lib/oam/common");
			if(liboam.exists()&&liboam.isDirectory()){
				ZipCompressUtil.zip(null,zos,liboam);
			}
			
			ServletContext context=ServletContextUtil.getServletContext();
			String repository=context.getRealPath("/WEB-INF/repository");
			ZipCompressUtil.zip(null, zos, new File(repository));
			
			String oamDir=OmcServerContext.getInstance().getFtpDir();
			File[] exceptFiles=new File(oamDir).listFiles(new FileFilter() {
				public boolean accept(File pathname) {
					return pathname.getName().toLowerCase().startsWith("oam");
				}
			});
		
			ZipCompressUtil.zip(null,zos, new File(oamDir),exceptFiles);
			
			zos.putNextEntry(new ZipEntry("database.sql"));
			IOUtils.write(database, zos,Charset.forName("UTF-8"));
			zos.closeEntry();
		}
	}
	
	@Override
	public void restore(File zip) throws Exception {
		if(!FileUtil.isZip(zip)) throw new CustomException("该文件不是ZIP格式");
		try(
			ZipFile zipFile=new ZipFile(zip);
			InputStream dbis=getClass().getResourceAsStream("/db.properties");
		){
			ZipEntry entry=zipFile.getEntry("database.sql");
			if(entry==null){
				throw new CustomException("缺少database.sql文件");
			}
			//查询出所有的网元实体发消息
			List<Entity> es=entityMapper.selectAll(null,null);
			for (Entity entity : es) {
				Integer moduleId = entity.getModule().getId();
				Integer instId = entity.getInstId();
				SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
						CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
						moduleId, instId, CncpProtoType.CNCP_SET_NE_PLAN_DELETE, "");
				SetUpResponseMsg resMsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
				if (resMsg.getResult() == CncpProtoType.SUCCESS||resMsg.getResult()==CncpProtoType.ERR_NE_NOTEXIST) {
					entityMapper.deleteById(entity.getId());
				}else{
					throw new CustomException(CncpStatusTransUtil.transLocaleStatusMessage(resMsg.getResult()));
				}
			}
			
			InputStream is=zipFile.getInputStream(entry);
			Reader reader=new InputStreamReader(is,"UTF-8");
			Properties ps=new Properties();
			ps.load(dbis);
			try(
				Connection connection=DriverManager.getConnection(ps.getProperty("jdbc.mysql.url"), 
						ps.getProperty("jdbc.username"),  ps.getProperty("jdbc.password"));
			){
				ScriptRunner runner=new ScriptRunner(connection);
				runner.runScript(reader);
			}
			//将上传的资源包解压到repository中
			String oamDir=OmcServerContext.getInstance().getFtpDir();
			ServletContext context=ServletContextUtil.getServletContext();
			String repository=context.getRealPath("/WEB-INF/repository");
			File[] desDirs=new File[]{new File(oamDir),new File(repository),new File("/lib/oam/common")};
			ZipCompressUtil.unzip(zip, desDirs);
			
			String ftpdir = OmcServerContext.getInstance().getFtpDir();
			StringBuilder err=new StringBuilder();
			boolean isErr=false;
			entityMapper.deleteById(-1);
			es=entityMapper.selectAll(null,null);
			for (Entity entity : es) {
				Integer moduleId = entity.getModule().getId();
				Integer instId = entity.getInstId();
				File moduleDir = new File(ftpdir,moduleId + "-" + instId);
				File z = new File(moduleDir + ".zip");
				ZipCompressUtil.zip(z, moduleDir,null);
				
				String content = "file:" + moduleId + "-" + instId + ".zip";
				content += "\n" + "boot:"+(entity.getType()==1?true:false)+"\n";
				SetUpMsg msg = sendCncpMsgFactory.createSendSetCncpMsg(
						CncpProtoType.OMC_HEADER, CncpProtoType.OAM_HEADER, CncpProtoType.CNCP_SET_MSG, 
						CncpProtoType.SUB_OAM, entity.getCard().getCardNum(), CncpProtoType.CNCP_SET_NE_PLAN_CREATE, content);
				SetUpResponseMsg resMsg = cncpTaskExecutor.invokeSetUpMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
				if (resMsg.getResult() != CncpProtoType.SUCCESS) {
					isErr=true;
					err.append(moduleId + "-" + instId+ ".zip ==> ");
					err.append(CncpStatusTransUtil.transLocaleStatusMessage(resMsg.getResult()));
					err.append("<br/>");
				}
			}
			if(isErr){
				throw new CustomException(err.toString());
			}
		}
	}

	@Override
	public String getHostName() throws Exception {
		String command="hostname";
		Process process=Runtime.getRuntime().exec(command);
		String hostname=Streams.asString(process.getInputStream(),"UTF-8");
		return hostname;
	}
}
