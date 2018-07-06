package com.sunkaisens.omc.service.impl.hssThread;

import com.sunkaisens.omc.mapper.hss.HlrMapper;
import com.sunkaisens.omc.vo.hss.HssBusinessVo;

public class HlrThread implements Runnable {

	private HlrMapper hlrMapper;
	private int batchCount;
	private HssBusinessVo entry;
	
	public HlrThread() {
		
	}

	public HlrThread(HlrMapper hlrMapper,int batchCount,HssBusinessVo entry) {
		this.hlrMapper = hlrMapper;
		this.batchCount = batchCount;
		this.entry = entry;
	}

	@Override
	public void run() {
		hlrMapper.batchAdd(batchCount, entry.getImsi(), entry.getMdn(), entry.getEsn(),
				entry.getMsprofile(), entry.getDeviceType(), entry.getMsvocodec(),entry.getMsprofile_extra());
	}

}
