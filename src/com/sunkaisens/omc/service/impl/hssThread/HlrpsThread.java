package com.sunkaisens.omc.service.impl.hssThread;

import com.sunkaisens.omc.mapper.hss.HlrpsMapper;
import com.sunkaisens.omc.vo.hss.HssBusinessVo;

public class HlrpsThread implements Runnable {

	private HlrpsMapper hlrpsMapper;
	private int batchCount;
	private HssBusinessVo entry;
	
	public HlrpsThread() {
		
	}

	public HlrpsThread(HlrpsMapper hlrpsMapper,int batchCount,HssBusinessVo entry) {
		this.hlrpsMapper = hlrpsMapper;
		this.batchCount = batchCount;
		this.entry = entry;
	}

	@Override
	public void run() {
		hlrpsMapper.batchAdd(batchCount, entry.getImsi(), entry.getMdn(), entry.getEsn(),
				entry.getMsprofile(), entry.getDeviceType(), entry.getMsvocodec(),entry.getMsprofile_extra());
	}

}
