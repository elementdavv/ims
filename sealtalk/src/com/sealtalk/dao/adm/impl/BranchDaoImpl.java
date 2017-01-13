package com.sealtalk.dao.adm.impl;

import java.util.List;

import org.hibernate.SQLQuery;

import com.sealtalk.common.BaseDao;
import com.sealtalk.dao.adm.BranchDao;
import com.sealtalk.model.TBranch;
import com.sealtalk.model.TMember;

public class BranchDaoImpl extends BaseDao<TBranch, Integer> implements BranchDao {

	/*
	 * (non-Javadoc)
	 * @see com.sealtalk.dao.adm.BranchDao#getOrgan(java.lang.Integer)
	 * by alopex
	 */
	@Override
	public List getOrgan(Integer organId) {
		
		String sql = "select id, name from t_organ"
				+ " where id = " + organId;
		return runSql(sql);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sealtalk.dao.adm.BranchDao#getBranch(java.lang.Integer)
	 * by alopex
	 */
	@Override
	public List getBranch(Integer organId) {
		
		String sql = "select id, parent_id, name from t_branch"
				+ " where organ_id = " + organId;
		return runSql(sql);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sealtalk.dao.adm.BranchDao#getMember(java.lang.Integer)
	 * by alopex
	 */
	@Override
	public List getMember(Integer organId) {
		
		String sql = "select m.id, bm.branch_id as parent_id, m.fullname as name"
				+ " from t_branch_member bm left join t_member m on bm.member_id = m.id"
				+ " where m.organ_id = " + organId;
		return runSql(sql);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sealtalk.dao.adm.BranchDao#getBranchById(java.lang.Integer)
	 * by alopex
	 */
	@Override
	public TBranch getBranchById(Integer branchId) {
		
		return this.get(branchId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.sealtalk.dao.adm.BranchDao#getMemberById(java.lang.Integer)
	 * by alopex
	 */
	@Override
	public List getRole() {
		
		String sql = "select id, name from t_role order by listorder desc";
		return runSql(sql);
	}

	/*
	 * (non-Javadoc)
	 * @see com.sealtalk.dao.adm.BranchDao#getMemberById(java.lang.Integer)
	 * by alopex
	 */
	@Override
	public List getSex() {
		
		String sql = "select id, name from t_sex order by listorder desc";
		return runSql(sql);
	}

	/*
	 * (non-Javadoc)
	 * @see com.sealtalk.dao.adm.BranchDao#getMemberById(java.lang.Integer)
	 * by alopex
	 */
	@Override
	public List getPosition() {
		
		String sql = "select id, name from t_position order by listorder desc";
		return runSql(sql);
	}

	@Override
	public List getBranchMember(Integer memberId) {
		
		String sql = "select bm.id,b.name bn,p.name pn,bm.is_master"
				+ " from t_branch_member bm"
				+ " left join t_branch b on bm.branch_id = b.id"
				+ " left join t_position p on bm.position_id = p.id"
				+ " where bm.member_id = " + memberId + ""
						+ " order by bm.is_master asc, bm.listorder desc";
		return runSql(sql);
	}

}
