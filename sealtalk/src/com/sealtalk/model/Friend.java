package com.sealtalk.model;

/**
 * 好友关系模型
 * @since jdk1.7
 * @author hao_dy
 *
 */
public class Friend {
	private int id;
	private int memberId;				//default 0
	private int friendId;				//default 0
	private int listOrder;				//default 0
	private String createDate;			//创建时间
	
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
	public int getFriendId() {
		return friendId;
	}
	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}
	public int getListOrder() {
		return listOrder;
	}
	public void setListOrder(int listOrder) {
		this.listOrder = listOrder;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
}
