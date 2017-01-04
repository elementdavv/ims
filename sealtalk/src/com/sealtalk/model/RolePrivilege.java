package com.sealtalk.model;

/**
 * 角色权限模型
 * @since jdk1.7
 * @author hao_dy 
 * */
public class RolePrivilege {
	private int id;
	private int roleId;			//deafult 0
	private int privId;			//default 0
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getPrivId() {
		return privId;
	}
	public void setPrivId(int privId) {
		this.privId = privId;
	}
	
}
