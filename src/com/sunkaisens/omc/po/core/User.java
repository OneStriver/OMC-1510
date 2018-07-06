package com.sunkaisens.omc.po.core;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sunkaisens.omc.util.ServletContextUtil;

/**
 * 
 * 
 * 
 * 
 * 
 *User实体类
 *
 */
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
    /**
     * 
     * 
     * 
     * 无参数构造器
     */
	public User(){}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 有参数构造器
	 * @param name
	 * @param password
	 */
	public User(String name,String password){
		this.name=name;
		this.password=password;
	}
	//用户id
	private Integer id;

	//用户名
	private String name;

	//用户密码
	private String password;

	//用户拥有的角色
	private Set<Role> roles=new HashSet<Role>();

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/**
	 * 判断本用户是否有指定名称的权限
	 * 
	 * @param privilegeName
	 * @return
	 */
	public boolean hasPrivilegeByName(String privilegeName) {
		// 其他用户要是有权限才返回true
		for (Role role : roles) {
			for (Privilege privilege : role.getPrivileges()) {
				if (privilege.getName().equals(privilegeName)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断本用户是否有指定URL的权限
	 * 
	 * @param privilegeUrl
	 * @return
	 */
	public boolean hasPrivilegeByUrl(String privilegeUrl) {
		
		if("nouser".equals(name)) return true;
		// 如果以UI后缀结尾，就去掉UI后缀，以得到对应的权限（例如：addUI与add是同一个权限）
		if (privilegeUrl!=null&&privilegeUrl.endsWith("UI")) {
			privilegeUrl = privilegeUrl.substring(0, privilegeUrl.length() - 2);
		}
		
		@SuppressWarnings("unchecked")
		List<String> allPrivileges=(List<String>)ServletContextUtil.getServletContext().getAttribute("allPrivileges");
		
		if(!allPrivileges.contains(privilegeUrl)){
			return true;
		}
		// 如果是要控制的功能，则有权限才能使用
		for (Role role : roles) {
			for (Privilege privilege : role.getPrivileges()) {
				if (privilegeUrl!=null&&privilegeUrl.equals(privilege.getUrl())) {
					return true;
				}
			}
		}
		return false;
	}
    /**
     * 
     * 
     * 
     * 
     * 重写toString方法
     */
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password
				+ ", roles=" + roles + "]";
	}
}
