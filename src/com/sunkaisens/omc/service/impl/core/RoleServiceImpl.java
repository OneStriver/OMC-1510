package com.sunkaisens.omc.service.impl.core;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.mapper.core.PrivilegeMapper;
import com.sunkaisens.omc.mapper.core.RoleMapper;
import com.sunkaisens.omc.po.core.Privilege;
import com.sunkaisens.omc.po.core.Role;
import com.sunkaisens.omc.service.core.RoleService;
/**
 * 
 * 
 * 
 * 
 * 
 * RoleService接口实现类
 *
 */
@Service
public class RoleServiceImpl implements RoleService {
	@Resource
	private RoleMapper mapper;
	@Resource
	private PrivilegeMapper privilegeMapper;
	@Override
	public List<Role> getAll() {
		return mapper.selectAll();
	}
	@Override
	public List<Role> getRolesByIds(Integer... ids) {
		List<Role> roles=new ArrayList<Role>();
		for(Integer id:ids){
			Role role=mapper.selectById(id);
			roles.add(role);
		}
		return roles;
	}
	@Override
	public void save(Role role, Integer[] privilegeIds) throws CustomException {
		if(mapper.selectByName(role.getName())!=null){
			throw new CustomException("角色为 "+role.getName()+"已存在");
		}
		mapper.insert(role);
		if(privilegeIds==null) return;
		List<Privilege> privileges=privilegeMapper.selectByIds(privilegeIds);
		role.getPrivileges().addAll(privileges);
		mapper.insertRolePrivileges(role);
	}
	@Override
	public Role findById(Integer id){
		Role role=mapper.selectById(id);
		return role;
	}
	@Override
	public void delete(Integer[] ids) {
		for(Integer roleId:ids){
			mapper.deleteRolePrivileges(roleId);
			mapper.deleteById(roleId);
		}
	}
	@Override
	public void update(Role role, Integer[] privilegeIds) throws CustomException {
		if(mapper.selectById(role.getId())==null){
			throw new CustomException("您要修改的角色已不存在");
		}
		mapper.update(role);
		mapper.deleteRolePrivileges(role.getId());
		if(privilegeIds==null) return;
		List<Privilege> privileges=privilegeMapper.selectByIds(privilegeIds);
		role.getPrivileges().addAll(privileges);
		mapper.insertRolePrivileges(role);
	}
}
