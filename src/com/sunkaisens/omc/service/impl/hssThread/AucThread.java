package com.sunkaisens.omc.service.impl.hssThread;

import com.sunkaisens.omc.mapper.hss.AucMapper;
import com.sunkaisens.omc.vo.hss.HssBusinessVo;

public class AucThread implements Runnable {

	private AucMapper aucMapper;
	private int batchCount;
	private HssBusinessVo entry;
	
	public AucThread() {
		
	}

	public AucThread(AucMapper aucMapper,int batchCount,HssBusinessVo entry) {
		this.aucMapper = aucMapper;
		this.batchCount = batchCount;
		this.entry = entry;
	}

	@Override
	public void run() {
		aucMapper.batchAdd(batchCount, entry.getImsi());
	}

}
