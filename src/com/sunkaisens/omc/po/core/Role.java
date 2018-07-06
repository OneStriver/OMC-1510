package com.sunkaisens.omc.po.core;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
/**
 * 
 * 
 * 
 * 
 * 
 * Role实体类
 *
 */
public class Role implements Serializable{
	private static final long serialVersionUID = 1L;

	//角色id
	private Integer id;
	
	//角色名称
	private String name;
	
	//角色描述
	private String description;
	
	//拥有该角色的用户
	private Set<User> users=new HashSet<User>();
//	
	//该角色拥有的权限
	private Set<Privilege> privileges=new HashSet<Privilege>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}
    /**
     * 
     * 
     * 
     * 
     * 重写hashCode方法
     */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
    /**
     * 
     * 
     * 
     * 
     * 重写equals方法
     */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
    /**
     * 
     * 
     * 
     * 
     * 
     * 重写toString方法
     */
	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", description="
				+ description + ", privileges="
				+ privileges + "]";
	}
	
	
}
