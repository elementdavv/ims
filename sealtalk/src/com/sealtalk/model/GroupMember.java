package com.sealtalk.model;

/**
 * 组织成员模型
 * @since jdk1.7
 * @author hao_dy
 * */
public class GroupMember {
	private int id;	
	private int groupId;			//deafult 0
	private int memberId;			//default 0
	private int listOrder;			//default 0
	private char isCreater;			//deafult '0' 0非创建者，1创建者
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public int getListOrder() {
		return listOrder;
	}
	public void setListOrder(int listOrder) {
		this.listOrder = listOrder;
	}
	public char getIsCreater() {
		return isCreater;
	}
	public void setIsCreater(char isCreater) {
		this.isCreater = isCreater;
	}
	
}
