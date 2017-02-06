package com.sealtalk.dao.adm.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.sealtalk.common.BaseDao;
import com.sealtalk.dao.adm.BranchMemberDao;
import com.sealtalk.model.TBranchMember;

public class BranchMemberDaoImpl extends BaseDao<TBranchMember, Integer> implements BranchMemberDao {

	@Override
	public TBranchMember getBranchMemberByBranchPosition(Integer branchId, Integer positionId) {
		
		Criteria c = getCriteria();
		c.add(Restrictions.eq("branchId", branchId));
		c.add(Restrictions.eq("positionId", positionId));
		
		List list = c.list();
		if (list.isEmpty()) {
			return null;
		}
		return (TBranchMember)list.get(0); 
	}

	@Override
	public List<TBranchMember> getBranchMemberByMember(Integer memberId) {

		Criteria c = getCriteria();
		c.add(Restrictions.eq("memberId", memberId));
		
		List list = c.list();
		return list; 
	}

	@Override
	public List<TBranchMember> getBranchMemberByBranch(Integer branchId) {

		Criteria c = getCriteria();
		c.add(Restrictions.eq("branchId", branchId));
		
		List list = c.list();
		return list; 
	}

	@Override
	public void selectMaster(Integer memberId) {
		
		Criteria c = getCriteria();
		c.add(Restrictions.eq("memberId", memberId));
		c.add(Restrictions.eq("isMaster", "0"));
		c.addOrder(Order.asc("listorder"));
		
		List list = c.list();
		if (list.isEmpty()) return;
		
		TBranchMember bm = (TBranchMember)list.get(0);
		bm.setIsMaster("1");
		update(bm);
	}

	@Override
	public TBranchMember getBranchMemberByBranchMember(Integer branchId, Integer memberId) {

		Criteria c = getCriteria();
		c.add(Restrictions.eq("branchId", branchId));
		c.add(Restrictions.eq("memberId", memberId));
		
		List list = c.list();
		if (list.isEmpty()) {
			return null;
		}
		return (TBranchMember)list.get(0); 
	}

}
