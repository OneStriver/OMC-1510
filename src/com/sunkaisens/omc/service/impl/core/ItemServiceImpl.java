package com.sunkaisens.omc.service.impl.core;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.mapper.core.ConfigMapper;
import com.sunkaisens.omc.mapper.core.ItemMapper;
import com.sunkaisens.omc.mapper.core.OptionsMapper;
import com.sunkaisens.omc.mapper.core.RelevanceMapper;
import com.sunkaisens.omc.po.core.Item;
import com.sunkaisens.omc.po.core.Options;
import com.sunkaisens.omc.po.core.Relevance;
import com.sunkaisens.omc.service.core.ItemService;
import com.sunkaisens.omc.vo.core.PageBean;
/**
 * 
 * 
 * 
 * 
 * 
 * ItemService接口实现类 
 *
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Resource
	private ConfigMapper configMapper;
	@Resource
	private ItemMapper mapper;
	@Resource
	private OptionsMapper optionsMapper;
	@Resource
	private RelevanceMapper relevanceMapper;
	@Override
	public PageBean getPageBean(Integer page, Integer pageSize,
			Integer configId,String sort,String order) {
		List<Item> items=null;
		if(configId==null||configId==0){
			configId=null;
		}
		items=mapper.select((page-1)*pageSize, pageSize,configId,sort,order);
		int count=mapper.selectCount(configId);
		PageBean p=new PageBean(page, pageSize, items, count);
		return p;
	}

	@Override
	public void update(Item item) {
		Relevance r=item.getRelevance();
		if(r!=null&&r.getId()!=null){
			r=relevanceMapper.selectById(r.getId());
			item.setMultiline(r.getMultiline());
		}
		item.setValue(item.getValue().replaceAll("[\r\n]+", "\n"));
		mapper.update(item);
		optionsMapper.deleteByItemId(item.getId());
		for(Options options:item.getOptiones()){
			if(StringUtils.isBlank(options.getText())&&
					StringUtils.isBlank(options.getVal())){
				continue;
			}
			options.setItemId(item.getId());
			optionsMapper.insert(options);
		}
	}

	@Override
	public Item findById(Integer id) {
		return mapper.selectById(id);
	}

	@Override
	public void delete(Integer[] ids) {
		if(ids==null) return;
		for(Integer id:ids){
			optionsMapper.deleteByItemId(id);
			mapper.deleteById(id);
		}
	}

	@Override
	public void save(Item item) throws CustomException {
		if(configMapper.selectById(item.getConfig().getId()).getSole()){
			throw new CustomException("此配置文件不能添加配置项");
		}
		
		Relevance r=item.getRelevance();
		if(r!=null&&r.getId()!=null){
			r=relevanceMapper.selectById(r.getId());
			item.setMultiline(r.getMultiline());
		}
		item.setValue(item.getValue().replaceAll("[\r\n]+", "\n"));
		mapper.insert(item);
		for(Options options:item.getOptiones()){
			if(StringUtils.isBlank(options.getText())&&
					StringUtils.isBlank(options.getVal())){
				continue;
			}
			options.setItemId(item.getId());
			optionsMapper.insert(options);
		}
	}

	@Override
	public List<Item> findByConfigId(Integer configId) {
		List<Item> items=mapper.selectByConfigId(configId);
		return items;
	}
}
