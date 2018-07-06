package com.sunkaisens.omc.service.core;

import java.util.List;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.po.core.User;
/**
 * 
 * 
 * 
 * 
 * 
 *  定义UserService接口
 *
 */
public interface UserService {
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 新建用户
	 * @param user
	 */
	void save(User user);

	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回用户
	 * @param id
	 * @return
	 */
	User getById(Integer id);

	/**
	 * 
	 * 
	 * 
	 * 
	 * 用户名 密码 验证
	 * @param name
	 * @param password
	 * @return
	 */
	User getByNamePassword(String name, String password);

	/**
	 * 
	 * 
	 * 
	 * 
	 * 返回所有用户
	 * @return
	 */
	List<User> getAll();
	/**
	 * 
	 * 
	 * 
	 * 
	 * 修改用户
	 * @param user
	 */
	void update(User user);

	/**
	 * 
	 * 
	 * 
	 * 
	 * 用户数量
	 * @return
	 */
	int getCount();

	/**
	 * 
	 * 
	 * 
	 * 
	 * 删除用户
	 * @param ids
	 */
	void delete(Integer[] ids);

	/**
	 * 
	 * 
	 * 
	 * 
	 * 自我修改密码
	 * @param user
	 * @param pwd
	 * @param newPwd
	 * @param newPwd2
	 * @throws CustomException
	 */
	void updateSelf(User user, String pwd, String newPwd, String newPwd2) throws CustomException;
}
