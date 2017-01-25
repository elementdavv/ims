/**
 * 
 */
package com.sealtalk.dao.adm;

import java.util.List;

import com.sealtalk.common.IBaseDao;
import com.sealtalk.model.TBranch;
import com.sealtalk.model.TBranchMember;
import com.sealtalk.model.TMember;

/**
 * @author alopex
 *
 */
public interface BranchDao extends IBaseDao<TBranch, Integer> {
	
	/*
	 * 取部门关系树
	 * by alopex
	 */
	public List getOrgan(Integer organId);
	public List getBranch(Integer organId);
	public List getMember(Integer organId);

	/*
	 * 取字典
	 */
	public List getRole();
	public List getSex();
	public List getPosition();

	/*
	 * 取人员所在部门
	 */
	public List getBranchMember(Integer memberId);

	/*
	 * 取部门通过部门名称
	 */
	public TBranch getOneOfBranch(String name);
	
	/*
	 * 取子部门
	 */
	public List getChildren(Integer branchId);
	
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
