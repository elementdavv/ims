/**
 * 
 */
package com.sealtalk.dao.adm;

import java.util.List;

import com.sealtalk.common.IBaseDao;
import com.sealtalk.model.TBranch;

/**
 * @author alopex
 *
 */
public interface BranchDao extends IBaseDao<TBranch, Long> {
	
	/*
	 * 取部门关系树
	 */
	public List getBranchTree();

	/**
	 * 取部门关系树与成员信息
	 * @return
	 */
	public List getBrancTreeAndMember();

	/**
	 * 获取指定部门的成员
	 * @param branchId
	 * @return
	 */
	public List getBranchMember(String branchId);
	
}
