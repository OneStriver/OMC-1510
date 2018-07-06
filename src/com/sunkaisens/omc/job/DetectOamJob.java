package com.sunkaisens.omc.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.sunkaisens.omc.util.ProgressUtil;
import com.sunkaisens.omc.websocket.OamAlarmEndpoint;

/**
 * 定义一个QuartzJobBean,用于探测OAM进程是否启动
 */
public class DetectOamJob extends QuartzJobBean {
	protected final Logger log = LoggerFactory.getLogger(getClass());
	private static boolean isUp;
	
	/**
	 * 探测OAM进程是否启动
	 */
	@Override
	protected void executeInternal(JobExecutionContext ctx)
			throws JobExecutionException {
		boolean isRunning=ProgressUtil.isRunning("oam");
		if(!isRunning){
			if(isUp)
				OamAlarmEndpoint.broadcast("OAM进程不存在");
			isUp=false;
		}else{
			if(!isUp)
				OamAlarmEndpoint.broadcast("OAM已启动");
			isUp=true;
		}
	}
}