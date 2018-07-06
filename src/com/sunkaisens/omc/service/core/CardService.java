package com.sunkaisens.omc.service.core;

import java.util.List;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.po.core.Card;
import com.sunkaisens.omc.vo.core.PageBean;
/**
 * 
 * 
 * 
 * 
 *  定义CardService接口
 *
 */
public interface CardService {
	/**
	 * 
	 * 
	 * 
	 * 
	 * 添加板卡
	 * @param card
	 */
	int save(Card card);
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 修改板卡信息
	 * @param card
	 */
	void update(Card card);
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 删除板卡BY ID
	 * @param id
	 */
	int deleteById(Integer id);
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回所有板卡列表
	 * @return
	 * @throws CustomException 
	 */
	List<Card> findAll();
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回板卡ＢＹ　ＩＤ
	 * @param id
	 * @return
	 */
	Card getById(Integer id);
	
	/**、
	 * 
	 * 
	 * 
	 * 
	 * 分页显示
	 * @param requestPage
	 * @param pageSize
	 * @return
	 */
	PageBean getPageBean(Integer requestPage, Integer pageSize);
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 删除板卡BY ids
	 * @param ids
	 */
	List<Integer> deleteByIds(Integer[] ids);
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 重启所有板卡
	 */
	void rebootAll();
}
