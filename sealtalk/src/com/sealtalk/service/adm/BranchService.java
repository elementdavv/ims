package com.sealtalk.service.adm;

public interface BranchService {

	/**
	 * 获取部门数据
	 * @return
	 */
	public String getBranchTree();

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
