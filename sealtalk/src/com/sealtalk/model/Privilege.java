package com.sealtalk.model;

/**
 * 权限模型
 * @since jdk1.7
 * @author hao_dy
 * */
public class Privilege {
	private int id;
	private int parentId;			//default 0
	private int listOrder;			//deafult 0
	private char category;			//种类：1权限，2层级限制 default 0
	private char grouping;			//0非分组记录，1分组记录 deafult 0
	private String url;				//按url控制权限
	private String name;			//权限名称
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public char getCategory() {
		return category;
	}
	public void setCategory(char category) {
		this.category = category;
	}
	public char getGrouping() {
		return grouping;
	}
	public void setGrouping(char grouping) {
		this.grouping = grouping;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getListOrder() {
		return listOrder;
	}
	public void setListOrder(int listOrder) {
		this.listOrder = listOrder;
	}
	
}
