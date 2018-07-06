package com.sunkaisens.omc.service.impl.hssThread;

import com.sunkaisens.omc.mapper.hss.MsserviceMapper;
import com.sunkaisens.omc.vo.hss.HssBusinessVo;

public class MsserviceThread implements Runnable {

	private MsserviceMapper msserviceMapper;
	private int batchCount;
	private HssBusinessVo entry;
	
	public MsserviceThread() {
		
	}

	public MsserviceThread(MsserviceMapper msserviceMapper,int batchCount,HssBusinessVo entry) {
		this.msserviceMapper = msserviceMapper;
		this.batchCount = batchCount;
		this.entry = entry;
	}

	@Override
	public void run() {
		String WireTapAddr = entry.getMonitorIP()+":"+entry.getMonitorPort();
		msserviceMapper.batchAdd(batchCount, entry.getImsi(),WireTapAddr);
	}

}
