package com.sunkaisens.omc.job;

import java.sql.Timestamp;
import java.util.Calendar;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.sunkaisens.omc.mapper.core.AlarmMapper;
import com.sunkaisens.omc.mapper.core.LogMapper;

/**
 * 定义一个QuartzJobBean,用于后台自动清楚日志及告警信息
 */
public class DeleteLogAndAlarmJob extends QuartzJobBean {
	//获取日志对象
	protected final Logger log = LoggerFactory.getLogger(getClass());
	//注入LogMapper
	private LogMapper logMapper;
	//注入AlarmMapper
	private AlarmMapper alarmMapper;
	
	/**
	 * 定时清除告警记录
	 */
	@Override
	protected void executeInternal(JobExecutionContext ctx)
			throws JobExecutionException {
		Calendar now=Calendar.getInstance();
		now.add(Calendar.DAY_OF_MONTH, -7);
		Timestamp lastWeek=new Timestamp(now.getTimeInMillis());
		alarmMapper.deleteBefore(lastWeek);
		logMapper.deleteBefore(lastWeek);
	}
	
	public void setLogMapper(LogMapper logMapper) {
		this.logMapper = logMapper;
	}
	public void setAlarmMapper(AlarmMapper alarmMapper) {
		this.alarmMapper = alarmMapper;
	}
	
	
}