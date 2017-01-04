package com.sealtalk.model;

/**
 * 暂时不明
 * @since jdk1.7
 * @author hao_dy
 *
 */
public class Contact {
	private int id;
	private int memberId;				//default 0
	private int contactId;				//default 0
	private int contactTimes;			//deafult 0
	private String lastContactDate;
	
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
	public int getContactId() {
		return contactId;
	}
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	public int getContactTimes() {
		return contactTimes;
	}
	public void setContactTimes(int contactTimes) {
		this.contactTimes = contactTimes;
	}
	public String getLastContactDate() {
		return lastContactDate;
	}
	public void setLastContactDate(String lastContactDate) {
		this.lastContactDate = lastContactDate;
	}
	
}
