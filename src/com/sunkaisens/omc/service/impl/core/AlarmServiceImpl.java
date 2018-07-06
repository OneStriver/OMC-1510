package com.sunkaisens.omc.service.impl.core;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunkaisens.omc.mapper.core.AlarmMapper;
import com.sunkaisens.omc.po.core.Alarm;
import com.sunkaisens.omc.service.core.AlarmService;
import com.sunkaisens.omc.vo.core.PageBean;
/**
 * 
 * 
 * 
 * 
 * AlarmService接口实现类
 *
 */
@Service
public class AlarmServiceImpl implements AlarmService {

	@Resource
	private AlarmMapper mapper;
	@Override
	public PageBean getPageBean(Integer page, Integer pageSize, String sort,
			String order) {
		List<Alarm> logs=mapper.select((page-1)*pageSize, pageSize,sort,order);
		int count=mapper.selectCount();
		PageBean p=new PageBean(page, pageSize, logs, count);
		return p;
	}

	@Override
	public void deleteByIds(Integer[] ids) {
		mapper.deleteByIds(ids);
	}

}
