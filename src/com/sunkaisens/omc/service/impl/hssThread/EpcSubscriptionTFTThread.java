package com.sunkaisens.omc.service.impl.hssThread;

import com.sunkaisens.omc.mapper.hss.EPCSubscriptionTFTMapper;
import com.sunkaisens.omc.vo.hss.HssBusinessVo;

public class EpcSubscriptionTFTThread implements Runnable {

	private EPCSubscriptionTFTMapper epcSubscriptionTFTMapper;
	private int batchCount;
	private HssBusinessVo entry;
	
	public EpcSubscriptionTFTThread() {
		
	}

	public EpcSubscriptionTFTThread(EPCSubscriptionTFTMapper epcSubscriptionTFTMapper,int batchCount,HssBusinessVo entry) {
		this.epcSubscriptionTFTMapper = epcSubscriptionTFTMapper;
		this.batchCount = batchCount;
		this.entry = entry;
	}

	@Override
	public void run() {
		epcSubscriptionTFTMapper.batchAdd(batchCount, entry.getImsi());
	}

}
