package com.sunkaisens.omc.context.core;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sunkaisens.omc.thread.ProcessMessageHandler;
import com.sunkaisens.omc.thread.handler.CncpAlarmPacketHandler;
import com.sunkaisens.omc.thread.handler.CncpStatusPacketHandler;
import com.sunkaisens.omc.thread.handler.NI1510TerminalInfoUpdatePacketHandler;
import com.sunkaisens.omc.thread.observerProcess.ObserverImpl;
import com.sunkaisens.omc.thread.observerProcess.UDPNetComm;
import com.sunkaisens.omc.thread.udpMsgEntity.Packet;

//初始化监听接口UDP消息
public class OMCNetContext {
	private static OMCNetContext context = new OMCNetContext("OMCNetContextHandler");
	private UDPNetComm udpNetComm;
	private ObserverImpl observerServer = ObserverImpl.getInstance();
	//获取线程池
	private static final ExecutorService threadPool = Executors.newCachedThreadPool();
	private Timer timer = new Timer(true);

	public synchronized static OMCNetContext getInstance() {
		return context;
	}

	public OMCNetContext(String name) {
		initialize();
	}

	public void initialize() {
		// 注册处理北向接口消息的线程
		observerServer.registerObserver(NI1510TerminalInfoUpdatePacketHandler.getInstance());
		// 告警上报处理
		observerServer.registerObserver(CncpAlarmPacketHandler.getInstance());
		// 网元状态上报处理
		observerServer.registerObserver(CncpStatusPacketHandler.getInstance());
		//初始化Socket连接
		udpNetComm = UDPNetComm.getInstance();
		// 初始化socket监听的IP和Port
		udpNetComm.initNetComm(OmcServerContext.getInstance().getOmcIp(),Integer.valueOf(OmcServerContext.getInstance().getOmcPort()));
		//启动一个线程不停的获取queue中的消息并进行处理
		timer.schedule(new TimerTask() {
			@Override
			public synchronized void run() {
				Packet packet = UDPNetComm.receivedMsgQueue.poll();
				if(packet != null){
					System.err.println("The Packet Is Not Null");
					threadPool.submit(new ProcessMessageHandler(packet));
				}else{
					System.err.println("The Packet Is Null");
				}
			}
		}, 0, 1000);
	}

}
