package com.sunkaisens.omc.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunkaisens.omc.thread.observerProcess.ObserverImpl;
import com.sunkaisens.omc.thread.udpMsgEntity.Packet;

public class ProcessMessageHandler implements Runnable {
	// 获取log对象
	protected final Logger log = LoggerFactory.getLogger(getClass());
	private Packet packet;
	
	public ProcessMessageHandler(Packet packet) {
		this.packet = packet;
	}

	@Override
	public void run() {
		ObserverImpl.getInstance().receivedMsgAndSendObserver(packet);
	}

}
