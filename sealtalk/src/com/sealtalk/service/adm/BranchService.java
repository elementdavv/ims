package com.sealtalk.service.adm;

public interface BranchService {


	/*
	 * 取部门树
	 * by alopex
	 */
	public String getOrganTree(Integer organId);
	
	/*
	 * 取部门通过部门id
	 */
	public String getBranchById(Integer branchId);

	/*
	 * 取人员通过人员id
	 */
	public String getMemberById(Integer memberId);
	
	/*
	 * 取字典
	 */
	public String getRole();
	public String getSex();
	public String getPosition();
	
	/**
	 * 获取部门+成员 数据
	 * @return
	 */
	public String getBranchTreeAndMember();

	/**
	 * 取部门下的成员
	 * @param branchId
	 * @return
	 */
	public String getBranchMember(String branchId);
	
}
