package com.sealtalk.dao.adm;

import com.sealtalk.common.IBaseDao;
import com.sealtalk.model.TBranchMember;

public interface BranchMemberDao extends IBaseDao<TBranchMember, Integer> {

	public TBranchMember getBranchMemberByBranchPosition(Integer branchId, Integer positionId);
	public void selectMaster(Integer memberId);
}
