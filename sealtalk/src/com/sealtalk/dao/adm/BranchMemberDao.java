package com.sealtalk.dao.adm;

import java.util.List;

import com.sealtalk.common.IBaseDao;
import com.sealtalk.model.TBranchMember;

public interface BranchMemberDao extends IBaseDao<TBranchMember, Integer> {

	public TBranchMember getBranchMemberByBranchPosition(Integer branchId, Integer positionId);
	public TBranchMember getBranchMemberByBranchMember(Integer branchId, Integer memberId);
	public List<TBranchMember> getBranchMemberByMember(Integer memberId);
	public List<TBranchMember> getBranchMemberByBranch(Integer branchId);
	public void selectMaster(Integer memberId);
	public List getBranchMemberByMemberIds(String memberIds);
	public int updatePositionByUseId(int userIdInt, int positionId);
	public int delRelationByIds(String userids);
}
