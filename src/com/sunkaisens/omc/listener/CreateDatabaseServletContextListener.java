package com.sunkaisens.omc.listener;

import java.io.CharArrayWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.IOUtils;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sunkaisens.omc.context.core.OmcServerContext;
/**
 * 用于在web容器加载时自动初始化数据库
 */
public class CreateDatabaseServletContextListener implements ServletContextListener {
	//获取log对象
	protected final Logger log = LoggerFactory.getLogger(getClass());
	/**
	 * 
	 * 在容器监听器中加载执行SQL文件
	 */
	public void contextInitialized(ServletContextEvent sce) {
		Properties ps=new Properties();
		try(
			//myomc库
			InputStream is1=getClass().getResourceAsStream("/sql/myomc.sql");
			Reader reader1=new InputStreamReader(is1);
			//hss库
			InputStream is2=getClass().getResourceAsStream("/sql/hss.sql");
			Reader reader2=new InputStreamReader(is2);
			//hss冗余库
			InputStream is6=getClass().getResourceAsStream("/sql/hss_procedure.sql");
			Reader reader6=new InputStreamReader(is6);
			//sbc库
			InputStream is5=getClass().getResourceAsStream("/sql/sbc/sbc.sql");
			Reader reader5=new InputStreamReader(is5);
			
			//hss做分库
			InputStream is4=getClass().getResourceAsStream("/sql/fenku/hss-fenku.sql");
			Reader reader4=new InputStreamReader(is4);
			InputStream is7=getClass().getResourceAsStream("/sql/fenku/hss_procedure.sql");
			Reader reader7=new InputStreamReader(is7);
			InputStream is8=getClass().getResourceAsStream("/sql/fenku/hss_trigger.sql");
			Reader reader8=new InputStreamReader(is8);
			InputStream is9=getClass().getResourceAsStream("/sql/fenku/hss.sql");
			Reader reader9=new InputStreamReader(is9);
			
			//暂时不用
			InputStream is3=getClass().getResourceAsStream("/sql/cscf.sql");
			Reader reader3=new InputStreamReader(is3);
			//暂时不用
			InputStream is10=getClass().getResourceAsStream("/sql/cscf_procedure.sql");
			Reader reader10=new InputStreamReader(is10);
			
			InputStream dbis=getClass().getResourceAsStream("/db.properties");
			
			//hssDaoConf为null
			InputStream hssDaoConf=getClass().getResourceAsStream("/applicationContext-dao-hss.xml");

			//hss分库
			CharArrayWriter writer=new CharArrayWriter();
			CharArrayWriter triggerWriter=new CharArrayWriter();
			CharArrayWriter writer4=new CharArrayWriter();
			CharArrayWriter writer7=new CharArrayWriter();
			//
			CharArrayWriter writer2=new CharArrayWriter();
			CharArrayWriter writer6=new CharArrayWriter();
			
		){
			ps.load(dbis);
			Class.forName(ps.getProperty("jdbc.driver"));
			try(
				Connection connection=DriverManager.getConnection(ps.getProperty("jdbc.mysql.url"), 
				ps.getProperty("jdbc.username"),  ps.getProperty("jdbc.password"));
			){
				String hssUrl=ps.getProperty("jdbc.hss.url");
				String hssName=hssUrl.substring(hssUrl.lastIndexOf("/")+1,hssUrl.lastIndexOf("?"));
				ScriptRunner runner=new ScriptRunner(connection);
				if(!log.isDebugEnabled()) 
				runner.setLogWriter(null);
				
				runner.runScript(reader3);
				runner.setDelimiter("$");
				runner.runScript(reader10);
				runner.setDelimiter(";");
				runner.runScript(reader1);
				
				//SBC库中的表
				try(
					Statement st=connection.createStatement();
					ResultSet rs=st.executeQuery("select count(*) from config where name='net'");
				){
					if(OmcServerContext.getInstance().getProject().startsWith("DaS")
							&&rs.first()&&rs.getInt(1)==0){
						runner.runScript(new StringReader("use myomc;"));
						runner.runScript(reader5);
					}
				}
				IOUtils.copy(reader9, writer2);
				
				if(hssDaoConf!=null){
					//使用分库
					IOUtils.copy(reader9, writer);
					IOUtils.copy(reader8, triggerWriter);
					for(int i=1,len=getHssSourceCount();i<=len;i++){
						String create="create database if not exists "+hssName+i+";";
						runner.runScript(new StringReader(create));
						
						//创建每个HSS中的表.replaceAll("hss",hssName+i)
						runner.runScript(new StringReader("use "+hssName+i+";"));
//						runner.setDelimiter("$");
						runner.runScript(new StringReader(writer.toString()));
//						runner.setDelimiter(";");
						
						//创建每个HSS中表的触发器
						runner.runScript(new StringReader("use "+hssName+i+";"));
//						runner.setDelimiter("$");
						runner.runScript(new StringReader(triggerWriter.toString().replace("hss",hssName+i)));
//						runner.setDelimiter(";");
					}
					//
					IOUtils.copy(reader4, writer4);
					runner.runScript(new StringReader(writer4.toString().replace("hss",hssName)));
					
					runner.setDelimiter("$");
					IOUtils.copy(reader7, writer7);
					runner.runScript(new StringReader(writer7.toString().replace("hss",hssName)));
					runner.setDelimiter(";");
					
				}else{
					//没有使用分库	applicationContext-dao
					runner.runScript(new StringReader("create database if not exists "+hssName+";"));
					runner.runScript(new StringReader("use "+hssName+";"));
					runner.runScript(new StringReader(writer2.toString()));
					runner.setDelimiter("$");
					IOUtils.copy(reader6, writer6);
					runner.runScript(new StringReader(writer6.toString().replace("hss",hssName)));
					runner.setDelimiter(";");
				}
				
				//HSS_Reduancy库(冗余)
				runner.runScript(new StringReader("create database if not exists HSS_Reduancy;"));
				runner.runScript(new StringReader("use HSS_Reduancy;"));
				runner.runScript(new StringReader(writer2.toString()));
				
				//关闭runner连接
				runner.closeConnection();
			}catch(Exception e){
				e.printStackTrace();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private int getHssSourceCount() throws Exception{
		try (//
			InputStream is=getClass().getResourceAsStream("/applicationContext-dao-hss.xml");
		){
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = db.parse(is);
			XPath xpath = XPathFactory.newInstance().newXPath();
			Node node = (Node)xpath.evaluate("//bean[@id='sqlSessionFactories']/property[@name='hssDataSourceCount']", 
					doc,XPathConstants.NODE);
			String value=node.getAttributes().getNamedItem("value").getTextContent();
			return Integer.parseInt(value);
		}
	}
	
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
	
}
