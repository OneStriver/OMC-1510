package com.sunkaisens.omc.service.impl.hssThread;

import com.sunkaisens.omc.mapper.hss.TerminalInfoMapper;
import com.sunkaisens.omc.vo.hss.HssBusinessVo;

public class TerminalInfoThread implements Runnable {

	private TerminalInfoMapper terminalInfoMapper;
	private int batchCount;
	private HssBusinessVo entry;
	
	public TerminalInfoThread() {
		
	}

	public TerminalInfoThread(TerminalInfoMapper terminalInfoMapper,int batchCount,HssBusinessVo entry) {
		this.terminalInfoMapper = terminalInfoMapper;
		this.batchCount = batchCount;
		this.entry = entry;
	}

	@Override
	public void run() {
		terminalInfoMapper.batchAddTerminal(batchCount, entry.getImsi(), entry.getTerminalId(), entry.getTerminalType(),
				entry.getPowerLevel(), entry.getMsprofile(), entry.getGwId(), entry.getDepartment(), entry.getUserType(),
				entry.getCreateTime(), entry.getServicePriority());
	}

}
