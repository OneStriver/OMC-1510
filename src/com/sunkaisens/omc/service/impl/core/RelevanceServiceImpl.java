package com.sunkaisens.omc.service.impl.core;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.mapper.core.ItemMapper;
import com.sunkaisens.omc.mapper.core.OptionsMapper;
import com.sunkaisens.omc.mapper.core.RelevanceMapper;
import com.sunkaisens.omc.po.core.Item;
import com.sunkaisens.omc.po.core.Options;
import com.sunkaisens.omc.po.core.Relevance;
import com.sunkaisens.omc.service.core.RelevanceService;
import com.sunkaisens.omc.vo.core.PageBean;
/**
 * 
 * 
 * 
 * 
 * 
 *RelevanceService接口实现类
 *
 */
@Service
public class RelevanceServiceImpl implements RelevanceService {

	@Resource
	private RelevanceMapper relevanceMapper;
	@Resource
	private ItemMapper itemMapper;
	@Resource
	private OptionsMapper optionsMapper;
	@Override
	public void save(Relevance relevance) throws CustomException {
		if(null!=relevanceMapper.selectByNameAndCommonId(relevance.getName(), 
				relevance.getCommon().getId())){
			throw new CustomException("该配置页已存在配置项【"+relevance.getName()+"】");
		}
		relevanceMapper.insert(relevance);
		if(relevance.getValue()!=null){
			relevance.setValue(relevance.getValue().replaceAll("[\r\n]+", "\n"));
		}
		for(Options options:relevance.getOptiones()){
			if(StringUtils.isBlank(options.getText())&&
					StringUtils.isBlank(options.getVal())){
				continue;
			}
			options.setItemId(relevance.getId());
			optionsMapper.insert(options);
		}
	}

	@Override
	public void update(Relevance relevance) throws Exception {
		if(null==relevanceMapper.selectByNameAndCommonId(relevance.getName(), 
				relevance.getCommon().getId())){
			throw new CustomException("此配置参数【"+relevance.getName()+"】已不存在");
		}
		if(relevance.getValue()!=null)
			relevance.setValue(relevance.getValue().replaceAll("[\r\n]+", "\n"));
		relevanceMapper.update(relevance);
		optionsMapper.deleteByItemId(relevance.getId());
		for(Options options:relevance.getOptiones()){
			if(StringUtils.isBlank(options.getText())&&
					StringUtils.isBlank(options.getVal())){
				continue;
			}
			options.setItemId(relevance.getId());
			optionsMapper.insert(options);
		}
		/*不需要同步到关联项
		List<Item> items=itemMapper.getItemByRelevanceId(relevance.getId());
		for(Item item:items){
			Integer id=item.getId();
			BeanUtils.copyProperties(item, relevance);
			item.setId(id);
			optionsMapper.deleteByItemId(item.getId());
			for(Options options:relevance.getOptiones()){
				if(StringUtils.isBlank(options.getText())&&
						StringUtils.isBlank(options.getVal())){
					continue;
				}
				options.setItemId(item.getId());
				optionsMapper.insert(options);
			}
			itemMapper.update(item);
		}*/
	}

	@Override
	public void deleteById(Integer id) {
		deleteByIds(new Integer[]{id});
	}

	@Override
	public List<Relevance> findAll() {
		return relevanceMapper.selectAll(null,null,null,null);
	}

	@Override
	public Relevance getById(Integer id) {
		return relevanceMapper.selectById(id);
	}

	@Override
	public PageBean getPageBean(Integer pageNum, Integer pageSize,String sort,String order) {
		int count=relevanceMapper.selectCount();
		List<Relevance> relevances=relevanceMapper.selectAll((pageNum-1)*pageSize, pageSize,null,null);
		PageBean pageBean=new PageBean(pageNum, pageSize, relevances, count);
		return pageBean;
	}

	@Override
	public void deleteByIds(Integer[] ids) {
		if(ids==null) return;
		for(Integer id:ids){
			List<Item> items=itemMapper.getItemByRelevanceId(id);
			for(Item item:items){
				item.setRelevance(null);
				itemMapper.update(item);
			}
			optionsMapper.deleteByItemId(id);
			relevanceMapper.deleteById(id);
		}
	}

	@Override
	public List<Item> getItemById(Integer id) {
		return itemMapper.getItemByRelevanceId(id);
	}
}
