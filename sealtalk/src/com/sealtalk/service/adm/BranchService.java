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
	public TMember getMemberByAccount(String account);
	public TBranch getBranchByName(String name);	

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
	public TBranchMember getBranchMemberByBranchMember(Integer branchId, Integer memberId);
	
	/*
	 * 取人员角色通过人员id
	 */
	public TMemberRole getMemberRoleByMemberId(Integer memberId);
	
	public Integer saveBranch(TBranch branch);
	public Integer saveMember(TMember member);
	public Integer saveBranchMember(TBranchMember branchMember);
	public Integer saveMemberRole(TMemberRole memberRole);
	
	public Integer delBranchMember(Integer branchMemberId);
	public void setMaster(Integer branchMemberId);
	public void reset(Integer memberId, String password);
	
	public void delMember(Integer memberid);
	public void delBranch(Integer branchId, Integer r, Integer organId);
	public void movMember(Integer memberId, Integer pId, Integer toId);
	public Integer movBranch(Integer branchId, Integer toId);


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
