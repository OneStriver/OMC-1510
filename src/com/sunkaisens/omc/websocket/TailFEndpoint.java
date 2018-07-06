package com.sunkaisens.omc.websocket;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * 
 * WebSokect服务端点
 *
 */
@ServerEndpoint(value = "/ws/tailF/{path}")
public class TailFEndpoint {
	private static final Logger log = LoggerFactory.getLogger(TailFEndpoint.class);

	
	private class TailFThread extends Thread{
		private Session session;
		private String path;
		private boolean stop;
		public TailFThread(Session session,String path){
			this.session=session;
			this.path=path;
		}
		public void run() {
			try(
				BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(path),"UTF-8"));
					){
				String line=IOUtils.toString(reader).replace("\n", "<br/>");
				session.getBasicRemote().sendText(line+"<br/>");
				while(true&&!stop){
					line=reader.readLine();
					if(line==null) {
						Thread.sleep(3000);
					}else{
						session.getBasicRemote().sendText(line+"<br/>");
						log.debug("websocket:"+line);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		public void setStop(boolean stop){
			this.stop=stop;
		}
	}
	
	private TailFThread thread;
	/**
	 * 
	 * 
	 * 
	 * 打开连接时触发此方法
	 * @param session
	 */
	@OnOpen
	public void openConnection(final Session session,@PathParam("path")String path) {
		log.debug("Session Id " + session.getId() + " open connection!");
		try {
			thread=new TailFThread(session,"/"+path.replace("@@", "/"));
			thread.start();
		} catch (Exception e1) {
			log.error(e1.getMessage());
		}
	}
	 /**
     * 
     * 
     * 
     * 关闭连接时触发此方法
     * @param session
     */
	@OnClose
	public void onClose(Session session) {
		thread.setStop(true);
		log.debug("Session Id " + session.getId() + " connection closed!");
	}
	/**
     * 
     * 
     * 
     * 连接出错时触发此方法
     * @param session
     * @param t
     */
	@OnError
	public void error(Session session, Throwable t) {
		thread.setStop(true);
		log.debug("Session Id " + session.getId() + " connection error!");
	}
}
