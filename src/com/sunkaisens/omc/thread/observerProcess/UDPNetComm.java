package com.sunkaisens.omc.thread.observerProcess;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunkaisens.omc.thread.cncpMsg.CncpProtoType;
import com.sunkaisens.omc.thread.messageEncapsulation.CncpBaseMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.OmcToNIResponseMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.QueryResponseMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.SetUpResponseMsg;
import com.sunkaisens.omc.thread.udpMsgEntity.Packet;
import com.sunkaisens.omc.util.CodecUtil;
import com.sunkaisens.omc.util.HexDump;
import com.sunkaisens.omc.util.StackUtil;

/**
 * 接收消息的内容 
 */
public class UDPNetComm implements Runnable {
	
	private static Logger logger = LoggerFactory.getLogger(UDPNetComm.class);
	public static final int MAX_UDP_PACKET_LEN = 4096;
	private byte[] buf = new byte[MAX_UDP_PACKET_LEN];
	public static DatagramSocket socket;
	private String socketName;
	private String localIP;
	private int localPort;
	private Thread thread;
	//存储收到的消息
	public static BlockingQueue<Packet> receivedMsgQueue = new LinkedBlockingQueue<Packet>();
	//消息类型缓存
	private List<Integer> receivedMsgTypeList = new ArrayList<Integer>();
	//北向接口发送之后有响应的消息
	public static BlockingQueue<Packet> responseMsgQueue = new LinkedBlockingQueue<Packet>();
	//北向接口发送之后有响应的消息
	private List<Integer> responseMsgTypeList = new ArrayList<Integer>();
	
	// 创建对象(单例模式)
	private UDPNetComm() {
		//OMC发送之后响应的消息
		//OMC添加,修改,删除用户收到的响应消息的类型
		//receivedMsgTypeList.add(CncpProtoType.CNCP_OMC_NI_MSG);
		//receivedMsgTypeList.add(CncpProtoType.CNCP_SET_MSG);
		//receivedMsgTypeList.add(CncpProtoType.CNCP_GET_MSG);
		responseMsgTypeList.add(CncpProtoType.CNCP_OMC_NI_ACK_MSG);
		responseMsgTypeList.add(CncpProtoType.CNCP_SET_ACK_MSG);
		responseMsgTypeList.add(CncpProtoType.CNCP_GET_ACK_MSG);
		
		receivedMsgTypeList.add(CncpProtoType.CNCP_NI_OMC_MSG);
		//receivedMsgTypeList.add(CncpProtoType.CNCP_STATUS_MSG);
		//receivedMsgTypeList.add(CncpProtoType.CNCP_ALARM_MSG);
	}
	public static UDPNetComm getInstance() {
		return UDPNetCommInstance.instance;
	}
	private static class UDPNetCommInstance {
		private static UDPNetComm instance = new UDPNetComm();
	}

	public void initNetComm(String localIP, int localPort) {
		this.localIP = localIP;
		this.localPort = localPort;
		try {
			socket = new DatagramSocket(localPort);
		} catch (Exception exception) {
			logger.error(StackUtil.getTrace(exception));
		}
		initUDPNetComms();
	}
	
	@Override
	public void run() {
		while(true){
			try {
				DatagramPacket revPacket = new DatagramPacket(this.buf, this.buf.length);
				socket.receive(revPacket);
				this.buf = revPacket.getData();
				Packet pack = new Packet();
				pack.setLen(revPacket.getLength());
				byte[] msgBuf = new byte[pack.getLen()];
				System.arraycopy(this.buf, 0, msgBuf, 0, revPacket.getLength());
				pack.setMsg(msgBuf);
				pack.setIpAddress(revPacket.getAddress().getHostAddress());
				pack.setPort(revPacket.getPort());
				int msgType = Integer.parseInt(CodecUtil.getBCDString(pack.getMsg(), 4, 6), 16);
				if(responseMsgTypeList.contains(msgType)){
					pack.setMsgType(msgType);
					logger.info("1510-OMC发送消息之后接收响应的消息的IP和Port:" + pack.getIpAddress() + ":" + pack.getPort() + "\n"+HexDump.dump(pack.getMsg(), pack.getLen(), 0));
					responseMsgQueue.put(pack);
					System.err.println(">>>responseMsgQueue的大小:"+responseMsgQueue.size());
				}
				if(receivedMsgTypeList.contains(msgType)){
					pack.setMsgType(msgType);
					logger.info("1510-OMC接受上报的消息的IP和Port:" + pack.getIpAddress() + ":" + pack.getPort() + "\n"+HexDump.dump(pack.getMsg(), pack.getLen(), 0));
					receivedMsgQueue.put(pack);
					System.err.println(">>>receivedMsgQueue的大小:"+receivedMsgQueue.size());
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	private void initUDPNetComms() {
		createThread();
	}

	private void createThread() {
		thread = new Thread(this, "UDPNetCommThread");
		thread.setDaemon(true);
		thread.start();
	}

	/**
	 * 发送UDP消息 
	 */
	public synchronized void send(Packet pack, String remoteIP, int remotePort) {
		try {
			socket.send(new DatagramPacket(pack.getMsg(), pack.getLen(), InetAddress.getByName(remoteIP), remotePort));
			logger.info("1510-OMC发送UDP消息,转换成码流>>>Send Packet To HexDump\n" + HexDump.dump(pack.getMsg(), pack.getLen(), 0));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public String getSocketName() {
		return socketName;
	}

	public void setSocketName(String socketName) {
		this.socketName = socketName;
	}

	public String getLocalIP() {
		return localIP;
	}

	public void setLocalIP(String localIP) {
		this.localIP = localIP;
	}

	public int getLocalPort() {
		return localPort;
	}

	public void setLocalPort(int localPort) {
		this.localPort = localPort;
	}

	/**
	 * 从字节数组里面提取数据并封装成需要的对象
	 */
	public CncpBaseMsg convertToProtoObj(Packet takePacket) {
		byte[] takePackMsgBytes = takePacket.getMsg();
		int type = Integer.parseInt(CodecUtil.getBCDString(takePackMsgBytes, 4, 6), 16);
		CncpBaseMsg resp = null;
		switch (type) {
		case CncpProtoType.CNCP_SET_ACK_MSG:
			//SrcType
			char setSrcType = CodecUtil.getChar(takePackMsgBytes,0);
			//destType
			char setDestType = CodecUtil.getChar(takePackMsgBytes,1);
			//messageLength
			int setMessageLength = Integer.parseInt(CodecUtil.getBCDString(takePackMsgBytes,2,4),16);
			//messageType
			int setMessageType = Integer.parseInt(CodecUtil.getBCDString(takePackMsgBytes,4,6),16);
			//transId
			int setTransId = Integer.parseInt(CodecUtil.getBCDString(takePackMsgBytes,6,8),16);
			//neType
			int setNeType = Integer.parseInt(CodecUtil.getBCDString(takePackMsgBytes,8,12),16);
			//instId
			int setInstId = Integer.parseInt(CodecUtil.getBCDString(takePackMsgBytes,12,16),16);
			//subType
			char setSubType = CodecUtil.getChar(takePackMsgBytes,16);
			//holdBit
			char setHoldBit = CodecUtil.getChar(takePackMsgBytes,17);
			//result
			char setResult = CodecUtil.getChar(takePackMsgBytes,18);
			//resultHoldBit
			char setResultHoldBit = CodecUtil.getChar(takePackMsgBytes,19);
			resp = new SetUpResponseMsg(setSrcType, setDestType, setMessageLength, setMessageType, setTransId, setNeType, setInstId, setSubType, setHoldBit, setResult, setResultHoldBit);
			break;
		case CncpProtoType.CNCP_GET_ACK_MSG:
			//SrcType
			char getSrcType = CodecUtil.getChar(takePackMsgBytes,0);
			//destType
			char getDestType = CodecUtil.getChar(takePackMsgBytes,1);
			//messageLength
			int getMessageLength = Integer.parseInt(CodecUtil.getBCDString(takePackMsgBytes,2,4),16);
			//messageType
			int getMessageType = Integer.parseInt(CodecUtil.getBCDString(takePackMsgBytes,4,6),16);
			//transId
			int getTransId = Integer.parseInt(CodecUtil.getBCDString(takePackMsgBytes,6,8),16);
			//neType
			int getNeType = Integer.parseInt(CodecUtil.getBCDString(takePackMsgBytes,8,12),16);
			//instId
			int getInstId = Integer.parseInt(CodecUtil.getBCDString(takePackMsgBytes,12,16),16);
			//subType
			char getSubType = CodecUtil.getChar(takePackMsgBytes,16);
			//holdBit
			char getHoldBit = CodecUtil.getChar(takePackMsgBytes,17);
			//result
			char getResult = CodecUtil.getChar(takePackMsgBytes,18);
			//resultHoldBit
			char getResultHoldBit = CodecUtil.getChar(takePackMsgBytes,19);
			//dataLength
			int getDataLength = Integer.parseInt(CodecUtil.getBCDString(takePackMsgBytes,20,22),16);
			//data
			String getData = CodecUtil.getString(takePackMsgBytes,22);
			resp = new QueryResponseMsg(getSrcType, getDestType, getMessageLength, getMessageType, getTransId, getNeType, getInstId, getSubType, getHoldBit, getResult, getResultHoldBit, getDataLength, getData);
			break;
		case CncpProtoType.CNCP_OMC_NI_ACK_MSG:
			//SrcType
			char niSrcType = CodecUtil.getChar(takePackMsgBytes,0);
			//destType
			char niDestType = CodecUtil.getChar(takePackMsgBytes,1);
			//messageLength
			int niMessageLength = Integer.parseInt(CodecUtil.getBCDString(takePackMsgBytes,2,4),16);
			//messageType
			int niMessageType = Integer.parseInt(CodecUtil.getBCDString(takePackMsgBytes,4,6),16);
			//transId
			int niTransId = Integer.parseInt(CodecUtil.getBCDString(takePackMsgBytes,6,8),16);
			//neType
			int niNeType = Integer.parseInt(CodecUtil.getBCDString(takePackMsgBytes,8,12),16);
			//instId
			int niInstId = Integer.parseInt(CodecUtil.getBCDString(takePackMsgBytes,12,16),16);
			//subType
			char niSubType = CodecUtil.getChar(takePackMsgBytes,16);
			//holdBit
			char niHoldBit = CodecUtil.getChar(takePackMsgBytes,17);
			//result
			char niResult = CodecUtil.getChar(takePackMsgBytes,18);
			//resultHoldBit
			char niResultHoldBit = CodecUtil.getChar(takePackMsgBytes,19);
			resp = new OmcToNIResponseMsg(niSrcType, niDestType, niMessageLength, niMessageType, niTransId, niNeType, niInstId, niSubType, niHoldBit, niResult, niResultHoldBit);
			break;
		}
		return resp;
	}

}
