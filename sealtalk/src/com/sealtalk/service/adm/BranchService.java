package com.sealtalk.service.adm;

import java.util.List;

import com.sealtalk.model.TBranch;
import com.sealtalk.model.TBranchMember;
import com.sealtalk.model.TMember;
import com.sealtalk.model.TMemberRole;

public interface BranchService {


	/*
	 * 取部门树
	 * by alopex
	 */
	public String getOrganTree(Integer organId);
	public String getOrganOnlyTree(Integer organId);
	
	/*
	 * 取部门通过部门id
	 */
	public String getBranchById(Integer branchId);

	/*
	 * 取部门对象通过部门id
	 */
	public TBranch getBranchObjectById(Integer branchId);

	/*
	 * 取人员通过人员id
	 */
	public String getMemberById(Integer memberId);
	public List getMemberBranchById(Integer memberId);
	
	/*
	 * 取人员对象通过人员id
	 */
	public TMember getMemberObjectById(Integer memberId);
	/*
	 * 取字典
	 */
	public String getRole();
	public String getSex();
	public String getPosition();
	
	/*
	 * 取部门人员通过部门人员id
	 */
	public TBranchMember getBranchMemberById(Integer branchMemberId);

	public TBranchMember getBranchMemberByBranchPosition(Integer branchId, Integer positionId);	
	
	/*
	 * 取人员角色通过人员id
	 */
	public TMemberRole getMemberRoleByMemberId(Integer memberId);
	
	public String saveBranch(TBranch branch);
	public Integer saveMember(TMember member);
	public Integer saveBranchMember(TBranchMember branchMember);
	public Integer saveMemberRole(TMemberRole memberRole);
	
	public void delBranchMember(Integer branchMemberId);
	public void setMaster(Integer branchMemberId);
	public void reset(Integer memberId, String password);
	
}
