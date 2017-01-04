package com.sealtalk.model;

/**
 * 性别模型
 * @since jdk1.7
 * @author hao_dy
 *
 */
public class Sex {
	private int id;
	private int listOrder;
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getListOrder() {
		return listOrder;
	}
	public void setListOrder(int listOrder) {
		this.listOrder = listOrder;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
