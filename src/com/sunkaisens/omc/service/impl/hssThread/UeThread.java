package com.sunkaisens.omc.service.impl.hssThread;

import java.util.Date;

import com.sunkaisens.omc.mapper.core.HssMetaMapper;
import com.sunkaisens.omc.mapper.cscf.UeMapper;
import com.sunkaisens.omc.po.cscf.Ue;
import com.sunkaisens.omc.vo.hss.HssBusinessVo;

public class UeThread implements Runnable {

	private UeMapper ueMapper;
	private int batchCount;
	private HssBusinessVo entry;
	
	public UeThread() {
		
	}

	public UeThread(UeMapper ueMapper,int batchCount,HssBusinessVo entry) {
		this.ueMapper = ueMapper;
		this.batchCount = batchCount;
		this.entry = entry;
	}

	@Override
	public void run() {
		//ueMapper.batchAdd(batchCount, entry.getMdn(), entry.getDomain(), entry.getUePassword());
		final String sipUriPatten = "sip:%s@%s";
		Ue ue = new Ue();
		// 以下是数据库不允许为空的字段, 而且没有给予默认值, 如不注入参数会引发异常
		ue.setUePcscfDomain("");
		ue.setUeScscfDomain("");
		ue.setUeHomeScscfDomain("");
		ue.setUeScscfAddr("0.0.0.0:0");
		ue.setUePcscfAddr("0.0.0.0:0");
		ue.setUeAddr("0.0.0.0:0");
		ue.setUeStatus(0);
		ue.setUeUpdateType(0);
		ue.setUeHomeScscfAddr("0.0.0.0:0");
		ue.setUeUpdateTstamp(new Date());
		for (int i = 0; i < batchCount; i++) {
			String countMdnStr = String.valueOf(Long.valueOf(entry.getMdn())+i);
			ue.setUeName(countMdnStr);
			if (entry.getUePassword() == null) {
				ue.setUePassword("");
			} else {
				ue.setUePassword(entry.getUePassword());
			}
			ue.setUeUri(String.format(sipUriPatten, countMdnStr, entry.getDomain()));
			
			if (ueMapper.selectByName(countMdnStr) == null) {
				ueMapper.insert(ue);
			} else {
				ueMapper.update(ue);
			}
		}
		
	}

}
