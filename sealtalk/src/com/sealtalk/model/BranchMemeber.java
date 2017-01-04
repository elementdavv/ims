package com.sealtalk.model;

/**
 * 部门模型
 * @since jdk1.7
 * @author hao_dy
 */
public class BranchMemeber {
	private int id;
	private int branchId;		//deafult 0
	private int memeberId;		//deafult 0
	private int positionId;		//default 0
	private int listOrder;		//default 0
	private char isMaster;		//default 0 0非主要职能，1主要智能
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	public int getMemeberId() {
		return memeberId;
	}
	public void setMemeberId(int memeberId) {
		this.memeberId = memeberId;
	}
	public int getPositionId() {
		return positionId;
	}
	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}
	public int getListOrder() {
		return listOrder;
	}
	public void setListOrder(int listOrder) {
		this.listOrder = listOrder;
	}
	public char getIsMaster() {
		return isMaster;
	}
	public void setIsMaster(char isMaster) {
		this.isMaster = isMaster;
	}

}
