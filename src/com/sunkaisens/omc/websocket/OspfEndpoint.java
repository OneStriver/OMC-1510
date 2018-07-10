package com.sunkaisens.omc.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunkaisens.omc.context.core.OmcServerContext;
import com.sunkaisens.omc.util.TelnetUtil;

/**
 * WebSokect服务端点
 */
@ServerEndpoint(value = "/ws/ospf")
public class OspfEndpoint {
	private static final Logger log = LoggerFactory.getLogger(OspfEndpoint.class);
	private static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();
	private TelnetUtil telnet;
//	private String ospfPassword;
	
	/**
	 * 无参数构造器，并对属性值进行初始化
	 */
	public OspfEndpoint() {
		
		String ip, user, hostPassword, port,charset;
		ip = OmcServerContext.getInstance().getOspfIp();
		user = OmcServerContext.getInstance().getOspfUser();
//		ospfPassword=OmcServerContext.getInstance().getOspfPassword();
		hostPassword = OmcServerContext.getInstance().getHostPassword();
		port = OmcServerContext.getInstance().getOspfPort();
		charset = OmcServerContext.getInstance().getOspfCharset();
		telnet = new TelnetUtil(ip, port, user, hostPassword);
		telnet.setCharset(charset);
	}
	
	/**
	 * 打开连接时触发此方法
	 */
	@OnOpen
	public void openConnection(final Session session) {
		System.out.println(this);
		session.setMaxTextMessageBufferSize(4096);
		sessionMap.put(session.getId(), session);
		log.debug("Session Id " + session.getId() + " open connection!");
		try {
			telnet.connect();
			telnet.write("vtysh");
//			telnet.write("telnet 0 2601");
//			telnet.readUntil("Password:");
//			telnet.write(ospfPassword);
//			telnet.readUntil("Router>");
//			telnet.write("en");
//			telnet.readUntil("Password:");
//			telnet.write(ospfPassword);
			send(session.getId(), "Connect Success.");
		} catch (Exception e1) {
			log.error(e1.getMessage());
			send(session.getId(), e1.getMessage());
		}
		Thread reader = new Thread() {
			@Override
			public void run() {
				try {
					char[] cbuf=new char[1024];
					int len=0;
					while (!interrupted() && ((len=telnet.readChar(cbuf,0,cbuf.length))!=-1)) {
						send(session.getId(), new String(cbuf,0,len/*,"UTF-8"*/));
					}
				} catch (Exception e) {
					log.error(e.getLocalizedMessage()+":"+e.getCause().getLocalizedMessage());
				}
			}
		};
		reader.setDaemon(true);
		reader.start();
	}
	
	 /**
     * 关闭连接时触发此方法
     */
	@OnClose
	public void onClose(Session session) {
		sessionMap.remove(session.getId());
		telnet.disconnect();
		telnet=null;
		log.debug("Session Id " + session.getId() + " connection closed!");
	}

	@OnMessage
	public void onMessage(Session session, String message) {
		log.debug("输入命令："+message);
		telnet.write(message);
	}
	
	 /**
     * 连接出错时触发此方法
     */
	@OnError
	public void error(Session session, Throwable t) {
		sessionMap.remove(session.getId());
		telnet.disconnect();
		log.debug("Session Id " + session.getId() + " connection error!");
	}

	public void broadcast(String message) {
		for (Session session : sessionMap.values()) {
			try {
				session.getBasicRemote().sendText(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void send(String sessionId, String message) {
		try {
			log.debug(message);
			sessionMap.get(sessionId).getBasicRemote().sendText(message.replace("\n", "<br/>"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
