package com.sunkaisens.omc.service.impl.ims;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.mapper.cscf.UeMapper;
import com.sunkaisens.omc.po.cscf.Ue;
import com.sunkaisens.omc.service.hss.HssService;
import com.sunkaisens.omc.service.ims.ImsService;
import com.sunkaisens.omc.vo.core.PageBean;
/**
 * 
 * 
 * 
 * 
 * 
 * 
 * ImsService接口实现类
 *
 */
@Service
public class ImsServiceImpl implements ImsService {
	//注入UeMapper
	@Resource
	private UeMapper mapper;
	//注入HssService
	@Resource
	private HssService hssService;
	
	@Override
	public PageBean getPageBean(int pageNum, int pageSize, Ue ue) {
		List<Ue> ues=mapper.select((pageNum-1)*pageSize, pageSize,ue);
		Integer count=mapper.selectCount(ue);
		PageBean p=new PageBean(pageNum, pageSize, ues, count);
		return p;
	}

	@Override
	public void save(Ue ue,String domain) throws CustomException {
		if(null!=mapper.selectByName(ue.getUeName()))
			throw new CustomException("号码【"+ue.getUeName()+"】已存在");
		final String sipUriPatten = "sip:%s@%s";
		ue.setUeUri(String.format(sipUriPatten, ue.getUeName(), domain));
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
		mapper.insert(ue);
	}

	@Override
	public Ue getByName(String ueName) {
		Ue ue=mapper.selectByName(ueName);
		return ue;
	}

	@Override
	public void update(Ue ue ,String domain) throws CustomException {
		Ue u=mapper.selectByName(ue.getUeName());
		if(u==null) throw new CustomException("号码【"+ue.getUeName()+"】已不存在");
		u.setUePassword(ue.getUePassword());
		final String sipUriPatten = "sip:%s@%s";
		u.setUeUri(String.format(sipUriPatten, ue.getUeName(), domain));
		mapper.update(u);
	}

	@Override
	public void deleteUes(String[] ueNames) {
		for(String name:ueNames){
			mapper.deleteByName(name);
		}
	}

}
