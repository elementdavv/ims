package com.sealtalk.model;

/**
 * 地区模型
 * @since jdk1.7
 * @author hao_dy
 *
 */
public class District {
	private int id;
	private int cityId;				//default 0
	private int listOrder;			//deafult 0
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
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
