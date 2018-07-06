package com.sunkaisens.omc.mapper.core;

import java.util.List;

import com.sunkaisens.omc.po.core.Card;
/**
 * 
 * 
 * 
 * 
 * 
 * 定义Card接口
 *
 */
public interface CardMapper {
	Integer insert(Card card);
	
	Card selectById(Integer id);
	
	List<Card> selectAll();
	
	void update(Card card);

	Integer deleteById(Integer id);

	Card selectByNum(Integer cardNum);
}
