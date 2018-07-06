package com.sunkaisens.omc.service.impl.core;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.mapper.core.UserMapper;
import com.sunkaisens.omc.po.core.User;
import com.sunkaisens.omc.service.core.UserService;
/**
 * 
 * 
 * 
 * 
 * 
 * UserService接口实现类
 *
 */
@Service
public class UserServiceImpl implements UserService {
	@Resource
	private UserMapper userMapper;
	
	@Override
	public void save(User user) {
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		userMapper.insert(user);
		if(user.getRoles().size()>0)
			userMapper.insertUserRoles(user);
	}
	
	@Override
	public User getById(Integer id){
		return userMapper.selectById(id);
	}
	
	@Override
	public User getByNamePassword(String name,String password){
		return userMapper.selectByNamePassword(name, DigestUtils.md5Hex(password));
	}

	@Override
	public List<User> getAll() {
		return userMapper.selectAll();
	}

	@Override
	public void update(User user) {
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		userMapper.update(user);
		userMapper.deleteUserRoles(user.getId());
		userMapper.insertUserRoles(user);
	}

	@Override
	public int getCount() {
		return userMapper.selectCount();
	}

	@Override
	public void delete(Integer[] ids) {
		for(Integer id:ids){
			userMapper.deleteUserRoles(id);
			userMapper.deleteById(id);
		}
	}

	@Override
	public void updateSelf(User user, String pwd, String newPwd, String newPwd2) throws CustomException {
		if(!newPwd.equals(newPwd2)) throw new CustomException("两次密码不一致!");
		user=userMapper.selectById(user.getId());
		if(!user.getPassword().equals(DigestUtils.md5Hex(pwd))) throw new CustomException("原密码错误!");
		if(user.getPassword().equals(DigestUtils.md5Hex(newPwd))) throw new CustomException("新密码与旧密码不能相同!");
		user.setPassword(DigestUtils.md5Hex(newPwd));
		userMapper.update(user);
	}
	
}
