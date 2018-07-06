package com.sunkaisens.omc.service.impl.core;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunkaisens.omc.mapper.core.PrivilegeMapper;
import com.sunkaisens.omc.po.core.Privilege;
import com.sunkaisens.omc.service.core.PrivilegeService;
/**
 * 
 * 
 * 
 * 
 * 
 * PrivilegeService接口实现类
 *
 */
@Service
public class PrivilegeServiceImpl implements PrivilegeService {

	@Resource
	private PrivilegeMapper mapper;
	@Override
	public void save(Privilege privilege) {
		mapper.insert(privilege);
	}
	
	@Override
	public List<String> getAllPrivilegeUrl(){
		List<String> list=new ArrayList<String>();
		List<Privilege> priviles=mapper.selectAll();
		for(Privilege p:priviles){
			list.add(p.getUrl());
		}
		return list;
		
	}

	@Override
	public List<Privilege> getPrivilegeMene() {
		List<Privilege> mune=mapper.selectMenuPrivileges();
		return mune;
	}

	@Override
	public List<Privilege> getTree() {
		List<Privilege> ps=mapper.selectByParentId(null);
		tree(ps);
		return ps;
	}
	
	private void tree(List<Privilege> privileges){
		if(privileges==null||privileges.size()==0) return;
		for(Privilege p:privileges){
			List<Privilege> children=mapper.selectByParentId(p.getId());
			tree(children);
			p.setChildren(children);
		}
	}
}
