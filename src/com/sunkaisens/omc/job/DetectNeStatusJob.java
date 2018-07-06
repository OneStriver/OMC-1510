package com.sunkaisens.omc.job;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.sunkaisens.omc.context.core.OmcServerContext;
import com.sunkaisens.omc.mapper.core.EntityMapper;
import com.sunkaisens.omc.po.core.Entity;
import com.sunkaisens.omc.thread.cncpMsg.CncpProtoType;
import com.sunkaisens.omc.thread.cncpMsg.CncpTaskExecutor;
import com.sunkaisens.omc.thread.cncpMsg.SendCncpMsgFactory;
import com.sunkaisens.omc.thread.messageEncapsulation.QueryMsg;
import com.sunkaisens.omc.thread.messageEncapsulation.QueryResponseMsg;

/**
 * 定义一个QuartzJobBean,用于探测,更新网元的状态
 */
public class DetectNeStatusJob extends QuartzJobBean {
	protected final Logger log = LoggerFactory.getLogger(getClass());
	private EntityMapper mapper;
	private SendCncpMsgFactory sendCncpMsgFactory;
	private CncpTaskExecutor cncpTaskExecutor;
	
	/**
	 * 探测网元状态
	 */
	@Override
	protected void executeInternal(JobExecutionContext ctx)
			throws JobExecutionException {
		List<Entity> entities=mapper.selectAll(null,null);
		for(Entity entity:entities){
			QueryMsg msg = sendCncpMsgFactory.createSendQueryCncpMsg(
					CncpProtoType.OMC_HEADER,CncpProtoType.OAM_HEADER,CncpProtoType.CNCP_GET_MSG,
					entity.getModule().getId(), entity.getInstId(),(char)CncpProtoType.OAM_NE_PING, "");
			QueryResponseMsg resmsg = cncpTaskExecutor.invokeQueryMsg(msg, true, OmcServerContext.getInstance().getOamIp(), OmcServerContext.getInstance().getOamPort());
			int result=resmsg.getResult()==0?1:0;
			entity.setStatus(result);
			mapper.update(entity);
		}
	}
	public void setMapper(EntityMapper mapper) {
		this.mapper = mapper;
	}
	public void setSimpleCncpMsgFactory(SendCncpMsgFactory sendCncpMsgFactory) {
		this.sendCncpMsgFactory = sendCncpMsgFactory;
	}
	public void setCncpTaskExecutor(CncpTaskExecutor cncpTaskExecutor) {
		this.cncpTaskExecutor = cncpTaskExecutor;
	}
}