package com.sealtalk.model;

/**
 * 省会模型
 * @since jdk1.7
 * @author hao_dy
 * */
public class Province {
	private int id;
	private int listOrder;			//default 0
	private String name;			//省会名称 
	
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
