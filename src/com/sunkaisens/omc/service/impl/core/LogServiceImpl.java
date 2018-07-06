package com.sunkaisens.omc.service.impl.core;

import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunkaisens.omc.mapper.core.LogMapper;
import com.sunkaisens.omc.po.core.Log;
import com.sunkaisens.omc.service.core.LogService;
import com.sunkaisens.omc.vo.core.PageBean;
/**
 * 
 * 
 * 
 * 
 * 
 * 接口实现类
 *
 */
@Service
public class LogServiceImpl implements LogService {

	@Resource
	private LogMapper mapper;

	@Override
	public PageBean getPageBean(Integer page, Integer pageSize, String sort,
			String order,ResourceBundle bundle) {
		List<Log> logs=mapper.select((page-1)*pageSize, pageSize,sort,order);
		for(Log log : logs){
			try {
				log.setDescription(bundle.getString(log.getDescription()));
			} catch (Exception e) {
				System.err.println("异常情况不用国际化查询对应信息");
				log.setDescription(log.getDescription());
			}
		}
		int count=mapper.selectCount();
		PageBean p=new PageBean(page, pageSize, logs, count);
		return p;
	}

	@Override
	public void deleteByIds(Integer[] ids) {
		mapper.deleteByIds(ids);
	}

}
