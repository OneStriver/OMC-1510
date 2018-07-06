package com.sunkaisens.omc.service.impl.hss;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunkaisens.omc.mapper.core.HssMetaMapper;
import com.sunkaisens.omc.service.hss.HssParamService;
/**
 * 
 * 
 * 
 * 
 * 
 * HssParamService 接口实现类
 *
 */
@Service
public class HssParamServiceImpl implements HssParamService {

	@Resource private HssMetaMapper mapper;
	@Override
	public void update(Integer[] device, Integer[] voice, Integer[] business,Integer[] viewItem) {
		mapper.disableDeviceType();
		if(device!=null) mapper.enableDeviceType(device);
		mapper.disableVoiceType();
		if(voice!=null) mapper.enableVoiceType(voice);
		mapper.disableBusiness();
		if(business!=null) mapper.enableBusiness(business);
		mapper.disableViewItem();
		if(viewItem!=null) mapper.enableViewitem(viewItem);
	}

}
