package com.sunkaisens.omc.mapper.core;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sunkaisens.omc.po.core.User;

/**
 * 
 * 
 * 
 * 
 * 
 * 定义User接口
 *
 */
public interface UserMapper {
	Integer insert(User user);
	
	void deleteById(Integer id);
	
	void deleteUserRoles(Integer userId);
	
	void update(User user);
	
	User selectById(Integer id);
	
	User selectByNamePassword(@Param("name") String name,
			@Param("password") String password);
	
	void insertUserRoles(User user);
	
	List<User> selectAll();

	int selectCount();
}
