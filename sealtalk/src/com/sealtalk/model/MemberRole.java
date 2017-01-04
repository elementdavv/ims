package com.sealtalk.model;

/**
 * 成员角色
 * @since jdk1.7
 * @author hao_dy
 *
 */
public class MemberRole {
	private int id;
	private int memberId;					//成员id  	default 0
	private int roleId;						//角色id		default 0
	private int listOrder;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getListOrder() {
		return listOrder;
	}
	public void setListOrder(int listOrder) {
		this.listOrder = listOrder;
	}
	
}
