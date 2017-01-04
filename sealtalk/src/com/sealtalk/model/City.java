package com.sealtalk.model;

/**
 * 市模型
 * @since jdk1.7
 * @author hao_dy
 */
public class City {
	private int id;
	private int provinceId;		//市id		default 0;
	private int listOrder;	
	private String name;		//市名称	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
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
