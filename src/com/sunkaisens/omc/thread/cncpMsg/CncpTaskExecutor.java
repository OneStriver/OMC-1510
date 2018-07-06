package com.sunkaisens.omc.thread.cncpMsg;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sunkaisens.omc.context.core.OMCNetContext;
import com.sunkaisens.omc.context.core.OmcServerContext;
import com.sunkaisens.omc.thread.messageEncapsulation.CncpBaseResponseMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.CncpBaseSendMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.OmcToNIMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.OmcToNIResponseMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.QueryMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.QueryResponseMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.SetUpMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.SetUpResponseMsg;
import com.sunkaisens.omc.thread.observerProcess.UDPNetComm;
import com.sunkaisens.omc.thread.timeOutHandle.TimerOutJob;
import com.sunkaisens.omc.thread.udpMsgEntity.Packet;
import com.sunkaisens.omc.util.CodecUtil;

/**
 * CNCP消息收发器, 提供工程内部所有CNCP消息的收发 注意: TransactionID 必须由此接口获得方能保证运行期间事务ID的一致性,
 * 任意服务层不可以随意定义事务ID
 */
@Component
public class CncpTaskExecutor {
	
	private static String JOB_GROUP_NAME = "TIMEOUT_JOBGROUP";  
	private static String TRIGGER_GROUP_NAME = "TIMEOUT_TRIGGERGROUP";
	private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();  
    //日志记录
	protected final Logger log = LoggerFactory.getLogger(getClass());
	//消息队列的处理
	private UDPNetComm udpNetComm = UDPNetComm.getInstance();
	private OMCNetContext omcNetContext = OMCNetContext.getInstance();
	//超时时长
	private int timeOut = OmcServerContext.getInstance().getTimeout();
	//获取线程池
	private static final ExecutorService pool = Executors.newCachedThreadPool();
    //消息收发器构造器
	public CncpTaskExecutor() {
		
	}
	//查询消息
	public QueryResponseMsg invokeQueryMsg(QueryMsg msg, boolean isBlock,String ip,Integer port) {
		if (msg == null){
			throw new IllegalArgumentException("消息不能为空！");
		}
		QueryTask task = new QueryTask(msg, isBlock,ip,port);
		QueryResponseMsg result = null;
		try {
			int transId = task.msg.getTransId();
			if (isBlock){
				queryTimeOutJob(timeOut,transId,task);
			}
			System.err.println("1510-OMC查询消息:"+transId);
			Future<QueryResponseMsg> poolResult = pool.submit(task);
			result = poolResult.get();
			if (result!=null && result.getResult() != CncpProtoType.TIMEOUT) {
				System.err.println("查询消息>>>超时处理");
				removeTimeOutJob(""+transId, JOB_GROUP_NAME, TRIGGER_GROUP_NAME);
			}
		} catch (Exception e) {
			log.error(">> task interrupted ", e);
		}
		return result;
	}
	//查询消息
	class QueryTask implements Callable<QueryResponseMsg> {
		final QueryMsg msg;
		final boolean isBlock;
		final String ip;
		final Integer port;
		QueryTask(QueryMsg msg,boolean isBlock,String ip,Integer port) {
			this.msg = msg;
			this.isBlock=isBlock;
			this.ip = ip;
			this.port = port;
		}
		@Override
		public QueryResponseMsg call() {
			try {
				Packet pack = new Packet();
				byte[] bts = new byte[msg.getMessageLength()];
				msg.writeToQueryMsg(bts, msg);
				pack.setMsg(bts);
				pack.setLen(bts.length);
				log.info(">>>>>>>>>>>>发送数据包>>>>>>>>>>>>>>");
				//发送消息
				udpNetComm.send(pack, ip, port);
				int sendMsgTransId = msg.getTransId();
				//接收相应消息
				//接收相应消息
				QueryResponseMsg convertObj=null;
				while (isBlock&&!Thread.interrupted()) {
					BlockingQueue<Packet> receivedMsgQueue = UDPNetComm.responseMsgQueue;
					Packet takePacket = receivedMsgQueue.poll();
					if(takePacket!=null){
						int responseTransId = Integer.parseInt(CodecUtil.getBCDString(takePacket.getMsg(), 6, 8), 16);
						if(sendMsgTransId==responseTransId){
							log.info("1510-OMC接收响应消息 Transaction Id:"+ responseTransId);
							convertObj = (QueryResponseMsg)udpNetComm.convertToProtoObj(takePacket);
							break;
						}
					}
				}
				return convertObj;
			} catch (Exception e) {
				log.error("线程【"+Thread.currentThread().getName()+"】InterruputedExecption", e);
			}
			return null;
		}
	}
	
	//设置消息
	public SetUpResponseMsg invokeSetUpMsg(SetUpMsg msg, boolean isBlock,String ip,Integer port) {
		if (msg == null){
			throw new IllegalArgumentException("消息不能为空！");
		}
		SetUpTask setUpTask = new SetUpTask(msg, isBlock,ip,port);
		SetUpResponseMsg result = null;
		try {
			int transId = setUpTask.msg.getTransId();
			if (isBlock){
				setUpTimeOutJob(timeOut,transId,setUpTask);
			}
			System.err.println("1510-OMC设置消息:"+transId);
			Future<SetUpResponseMsg> poolResult = pool.submit(setUpTask);
			result = poolResult.get();
			if (result!=null && result.getResult() != CncpProtoType.TIMEOUT) {
				System.err.println("1510-OMC设置消息>>>超时处理");
				removeTimeOutJob(""+transId, JOB_GROUP_NAME, TRIGGER_GROUP_NAME);
			}
		} catch (Exception e) {
			log.error(">> task interrupted ", e);
		}
		return result;
	}
	//设置消息
	class SetUpTask implements Callable<SetUpResponseMsg> {
		final SetUpMsg msg;
		final boolean isBlock;
		final String ip;
		final Integer port;
		SetUpTask(SetUpMsg msg,boolean isBlock,String ip,Integer port) {
			this.msg = msg;
			this.isBlock=isBlock;
			this.ip = ip;
			this.port = port;
		}
		@Override
		public SetUpResponseMsg call() {
			try {
				Packet pack = new Packet();
				byte[] bts = new byte[msg.getMessageLength()];
				msg.writeToSetUpMsg(bts, msg);
				pack.setMsg(bts);
				pack.setLen(bts.length);
				log.info(">>>>>>>>>>>>发送数据包>>>>>>>>>>>>>>");
				//发送消息
				udpNetComm.send(pack, ip, port);
				int sendMsgTransId = msg.getTransId();
				//接收相应消息
				SetUpResponseMsg convertObj=null;
				while (isBlock&&!Thread.interrupted()) {
					BlockingQueue<Packet> receivedMsgQueue = UDPNetComm.responseMsgQueue;
					Packet takePacket = receivedMsgQueue.poll();
					if(takePacket!=null){
						int responseTransId = Integer.parseInt(CodecUtil.getBCDString(takePacket.getMsg(), 6, 8), 16);
						if(sendMsgTransId==responseTransId){
							log.info("1510-OMC接收响应消息 Transaction Id:"+ responseTransId);
							convertObj = (SetUpResponseMsg)udpNetComm.convertToProtoObj(takePacket);
							break;
						}
					}
				}
				return convertObj;
			} catch (Exception e) {
				log.error("线程【"+Thread.currentThread().getName()+"】InterruputedExecption", e);
			}
			return null;
		}
	}
	
	// OMC发送消息给北向接口
	public OmcToNIResponseMsg invokeOmcToNIMsg(OmcToNIMsg msg, boolean isBlock,String ip,Integer port) {
		if (msg == null){
			throw new IllegalArgumentException("消息不能为空！");
		}
		OmcToNITask niToOmcTask = new OmcToNITask(msg, isBlock,ip,port);
		OmcToNIResponseMsg result = null;
		try {
			int transId = niToOmcTask.msg.getTransId();
			if (isBlock){
				OmcToNITimeOutJob(timeOut,transId,niToOmcTask);
			}
			System.err.println("1510-OMC发送给北向接口的消息:"+transId);
			Future<OmcToNIResponseMsg> poolResult = pool.submit(niToOmcTask);
			result = poolResult.get();
			if (result!=null && result.getResult() != CncpProtoType.TIMEOUT) {
				System.err.println("1510-OMC发送给北向接口消息之后,北向接口响应的超时消息>>>超时处理");
				removeTimeOutJob(""+transId, JOB_GROUP_NAME, TRIGGER_GROUP_NAME);
			}
		} catch (Exception e) {
			log.error(">> task interrupted ", e);
		}
		return result;
	}
	// OMC发送消息给北向接口
	class OmcToNITask implements Callable<OmcToNIResponseMsg> {
		final OmcToNIMsg msg;
		final boolean isBlock;
		final String ip;
		final Integer port;
		OmcToNITask(OmcToNIMsg msg,boolean isBlock,String ip,Integer port) {
			this.msg = msg;
			this.isBlock=isBlock;
			this.ip = ip;
			this.port = port;
		}
		@Override
		public OmcToNIResponseMsg call() {
			try {
				Packet pack = new Packet();
				byte[] bts = new byte[msg.getMessageLength()];
				msg.writeToOmcToNIMsg(bts, msg);
				pack.setMsg(bts);
				pack.setLen(bts.length);
				log.info(">>>>>>>>>>>>发送数据包>>>>>>>>>>>>>>");
				//发送消息
				udpNetComm.send(pack, ip, port);
				int sendMsgTransId = msg.getTransId();
				//接收相应消息
				OmcToNIResponseMsg convertObj=null;
				System.err.println("Thread.interrupted()>>>>"+Thread.interrupted());
				while (isBlock&&!Thread.interrupted()) {
					BlockingQueue<Packet> receivedMsgQueue = UDPNetComm.responseMsgQueue;
					Packet takePacket = receivedMsgQueue.poll();
					if(takePacket!=null){
						int responseTransId = Integer.parseInt(CodecUtil.getBCDString(takePacket.getMsg(), 6, 8), 16);
						if(sendMsgTransId==responseTransId){
							log.info("1510-OMC发送消息给北向接口接收响应消息 Transaction Id:"+ responseTransId);
							convertObj = (OmcToNIResponseMsg)udpNetComm.convertToProtoObj(takePacket);
							break;
						}
					}
				}
				return convertObj;
			} catch (Exception e) {
				log.error("线程【"+Thread.currentThread().getName()+"】InterruputedExecption", e);
			}
			return null;
		}
	}
	
	//逻辑处理之后的回复消息
	public void invokeCncpResponseMsg(CncpBaseResponseMsg msg,String ip,Integer port) {
		if (msg == null){
			throw new IllegalArgumentException("消息不能为空！");
		}
		CncpBaseResponseTask baseResponseTask = new CncpBaseResponseTask(msg, ip, port);
		try {
			pool.submit(baseResponseTask);
		} catch (Exception e) {
			log.error(">>> Task Interrupted ", e);
		}
	}
	//逻辑处理之后的回复消息
	class CncpBaseResponseTask implements Runnable {
		final CncpBaseResponseMsg msg;
		final String ip;
		final Integer port;
		CncpBaseResponseTask(CncpBaseResponseMsg msg,String ip,Integer port) {
			this.msg = msg;
			this.ip = ip;
			this.port = port;
		}
		@Override
		public void run() {
			try {
				Packet pack = new Packet();
				byte[] bts = new byte[20];
				msg.writeCncpBaseResponseMsg(bts, msg);
				pack.setMsg(bts);
				pack.setLen(bts.length);
				log.info(">>>>>>>>>>>>发送数据包>>>>>>>>>>>>>>");
				//发送消息
				udpNetComm.send(pack, ip, port);
			} catch (Exception e) {
				log.error("线程【"+Thread.currentThread().getName()+"】InterruputedExecption", e);
			}
		}
	}
	
	//回复带有数据的CNCP消息
	public void invokeCncpBaseSendMsg(CncpBaseSendMsg msg,String ip,Integer port) {
		if (msg == null){
			throw new IllegalArgumentException("消息不能为空！");
		}
		CncpBaseSendTask baseSendTask = new CncpBaseSendTask(msg, ip, port);
		try {
			pool.submit(baseSendTask);
		} catch (Exception e) {
			log.error(">> task interrupted ", e);
		}
	}
	//回复带有数据的CNCP消息
	class CncpBaseSendTask implements Runnable {
		final CncpBaseSendMsg msg;
		final String ip;
		final Integer port;
		CncpBaseSendTask(CncpBaseSendMsg msg,String ip,Integer port) {
			this.msg = msg;
			this.ip = ip;
			this.port = port;
		}
		@Override
		public void run() {
			try {
				Packet pack = new Packet();
				byte[] bts = new byte[20];
				msg.writeToCncpBaseSendMsg(bts, msg);
				pack.setMsg(bts);
				pack.setLen(bts.length);
				log.info(">>>>>>>>>>>>发送数据包>>>>>>>>>>>>>>");
				//发送消息
				udpNetComm.send(pack, ip, port);
			} catch (Exception e) {
				log.error("线程【"+Thread.currentThread().getName()+"】InterruputedExecption", e);
			}
		}
	}
	
	public static void queryTimeOutJob(int outTime,int transId,QueryTask task) throws SchedulerException{
		System.err.println("queryTimeOutJob>>>>>>>>>>>>>>");
        //创建任务
        JobDetail jobDetail = JobBuilder.newJob(TimerOutJob.class).withIdentity(""+transId, JOB_GROUP_NAME).build();
        QueryMsg queryMsg = task.msg;
        CncpBaseResponseMsg cncpBaseResponseMsg = new CncpBaseResponseMsg(queryMsg.getSrcType(), queryMsg.getDestType(),
				queryMsg.getMessageLength(), queryMsg.getMessageType(), transId, queryMsg.getNeType(),
				queryMsg.getInstId(), queryMsg.getSubType(), queryMsg.getHoldBit(), (char) CncpProtoType.TIMEOUT,
				(char) 0);
		jobDetail.getJobDataMap().put(TimerOutJob.USERDATA, cncpBaseResponseMsg);
        //创建触发器
        Trigger trigger = (SimpleTrigger)TriggerBuilder.newTrigger().withIdentity(""+transId, TRIGGER_GROUP_NAME)
        				.startAt(DateBuilder.futureDate(outTime, DateBuilder.IntervalUnit.SECOND)).build();
        Scheduler scheduler = schedulerFactory.getScheduler();
        //将任务及其触发器放入调度器
        scheduler.scheduleJob(jobDetail, trigger);
        //调度器开始调度任务
        scheduler.start();
    }
	
	
    public static void setUpTimeOutJob(int outTime,int transId,SetUpTask task) throws SchedulerException{
    	System.err.println("setUpTimeOutJob>>>>>>>>>>>>>>");
        //创建任务
        JobDetail jobDetail = JobBuilder.newJob(TimerOutJob.class).withIdentity(""+transId, JOB_GROUP_NAME).build();
        SetUpMsg setUpMsg = task.msg;
		CncpBaseResponseMsg cncpBaseResponseMsg = new CncpBaseResponseMsg(setUpMsg.getSrcType(), setUpMsg.getDestType(),
				setUpMsg.getMessageLength(), setUpMsg.getMessageType(), transId, setUpMsg.getNeType(),
				setUpMsg.getInstId(), setUpMsg.getSubType(), setUpMsg.getHoldBit(), (char) CncpProtoType.TIMEOUT,
				(char) 0);
		jobDetail.getJobDataMap().put(TimerOutJob.USERDATA, cncpBaseResponseMsg);
        //创建触发器
        Trigger trigger = (SimpleTrigger)TriggerBuilder.newTrigger().withIdentity(""+transId, TRIGGER_GROUP_NAME)
        				.startAt(DateBuilder.futureDate(outTime, DateBuilder.IntervalUnit.SECOND)).build();
        Scheduler scheduler = schedulerFactory.getScheduler();
        //将任务及其触发器放入调度器
        scheduler.scheduleJob(jobDetail, trigger);
        //调度器开始调度任务
        scheduler.start();
    }

    public static void OmcToNITimeOutJob(int outTime,int transId,OmcToNITask task) throws SchedulerException{
    	System.err.println("nIToOmcTimeOutJob>>>>>>>>>>>>>>");
        //创建任务
        JobDetail jobDetail = JobBuilder.newJob(TimerOutJob.class).withIdentity(""+transId, JOB_GROUP_NAME).build();
        OmcToNIMsg nIToOmcMsg = task.msg;
		CncpBaseResponseMsg cncpBaseResponseMsg = new CncpBaseResponseMsg(nIToOmcMsg.getSrcType(), nIToOmcMsg.getDestType(),
				nIToOmcMsg.getMessageLength(), nIToOmcMsg.getMessageType(), transId, nIToOmcMsg.getNeType(),
				nIToOmcMsg.getInstId(), nIToOmcMsg.getSubType(), nIToOmcMsg.getHoldBit(), (char) CncpProtoType.TIMEOUT,
				(char) 0);
		jobDetail.getJobDataMap().put(TimerOutJob.USERDATA, cncpBaseResponseMsg);
        //创建触发器
        Trigger trigger = (SimpleTrigger)TriggerBuilder.newTrigger().withIdentity(""+transId, TRIGGER_GROUP_NAME)
        				.startAt(DateBuilder.futureDate(outTime, DateBuilder.IntervalUnit.SECOND)).build();
        Scheduler scheduler = schedulerFactory.getScheduler();
        //将任务及其触发器放入调度器
        scheduler.scheduleJob(jobDetail, trigger);
        //调度器开始调度任务
        scheduler.start();
    }
    
    /** 
     * 移除一个任务(使用默认的任务组名，触发器名，触发器组名) 
     */  
    public static void removeTimeOutJob(String transId, String jobGroupName,String triggerGroupName) {  
    	System.err.println("removeTimeOutJob>>>>>>>>>>>>>>");
        try {  
            Scheduler sched = schedulerFactory.getScheduler();  
            TriggerKey triggerKey = TriggerKey.triggerKey(transId, triggerGroupName);
            sched.pauseTrigger(triggerKey);// 停止触发器  
            sched.unscheduleJob(triggerKey);// 移除触发器  
            sched.deleteJob(JobKey.jobKey(transId, jobGroupName));// 删除任务  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }   
    
}
