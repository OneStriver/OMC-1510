package com.sunkaisens.omc.mapper.core;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.sunkaisens.omc.po.core.Relevance;

/**
 * 
 * 
 * 
 * 
 * 
 * 定义Relevance接口
 *
 */
public interface RelevanceMapper {
	Integer insert(Relevance relevance);
	
	Relevance selectById(Integer id);
	
	//List<Relevance> selectAll(@Param("sort") String sort,@Param("order") String order);
	List<Relevance> selectAll(@Param("jumpNum") Integer jumpNum,
			@Param("limit") Integer limit,@Param("sort") String sort,@Param("order") String order);
	void update(Relevance relevance);

	void deleteById(Integer id);

	List<Relevance> getItemByCommonId(Integer id);

	List<Relevance> selectRelevanceByCommonId(Integer id);
	
	Relevance selectByNameAndCommonId(@Param("name")String name,@Param("commonId")Integer commonId);
    Integer selectCount();
}
