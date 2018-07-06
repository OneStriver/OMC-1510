package com.sunkaisens.omc.service.impl.hssThread;

import com.sunkaisens.omc.mapper.hss.EPCSubscriptionDataMapper;
import com.sunkaisens.omc.vo.hss.HssBusinessVo;

public class EpcSubscriptionDataThread implements Runnable {

	private EPCSubscriptionDataMapper epcSubscriptionDataMapper;
	private int batchCount;
	private HssBusinessVo entry;
	
	public EpcSubscriptionDataThread() {
		
	}

	public EpcSubscriptionDataThread(EPCSubscriptionDataMapper epcSubscriptionDataMapper,int batchCount,HssBusinessVo entry) {
		this.epcSubscriptionDataMapper = epcSubscriptionDataMapper;
		this.batchCount = batchCount;
		this.entry = entry;
	}

	@Override
	public void run() {
		epcSubscriptionDataMapper.batchAdd(batchCount, entry.getImsi());
	}

}
