package com.sunkaisens.omc.websocket;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunkaisens.omc.util.ProgressUtil;
/**
 * 
 * 
 * WebSokect服务端点
 *
 */
@ServerEndpoint(value = "/ws/oam")
public class OamAlarmEndpoint {
	private static final Logger log = LoggerFactory.getLogger(OamAlarmEndpoint.class);
	private static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();
	/**
	 * 
	 * 
	 * 
	 * 打开连接时触发此方法
	 * @param session
	 */
	@OnOpen
	public void openConnection(final Session session) {
		sessionMap.put(session.getId(), session);
		log.debug("Session Id " + session.getId() + " open connection!");
		try {
			send(session.getId(), ProgressUtil.isRunning("oam")?"OAM已启动":"OAM不在线");
		} catch (Exception e1) {
			log.error(e1.getMessage());
			send(session.getId(), e1.getMessage());
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
		sessionMap.remove(session.getId());
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
		sessionMap.remove(session.getId());
		log.debug("Session Id " + session.getId() + " connection error!");
	}
    /**
     * 
     * 
     * 广播
     * @param message
     */
	public static void broadcast(String message) {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date=df.format(new Date());
		for (Session session : sessionMap.values()) {
			try {
				session.getBasicRemote().sendText(date+"<br/>"+message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
     /**
      * 
      * 发送消息
      * @param sessionId
      * @param message
      */
	public static void send(String sessionId, String message) {
		try {
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date=df.format(new Date());
			sessionMap.get(sessionId).getBasicRemote().sendText(date+"<br/>"+message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
