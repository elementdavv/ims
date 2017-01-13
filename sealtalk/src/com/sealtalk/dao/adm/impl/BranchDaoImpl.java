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
		SQLQuery query = this.getSession().createSQLQuery(sql);
		
		List list = query.list();
		
		return list;
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
		SQLQuery query = this.getSession().createSQLQuery(sql);
		
		List list = query.list();
		
		return list;
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
		SQLQuery query = this.getSession().createSQLQuery(sql);
		
		List list = query.list();
		
		return list;
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
		
		String sql = "select id, name from t_role";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		
		List list = query.list();
		
		return list;
	}

	/*
	 * (non-Javadoc)
	 * @see com.sealtalk.dao.adm.BranchDao#getMemberById(java.lang.Integer)
	 * by alopex
	 */
	@Override
	public List getSex() {
		
		String sql = "select id, name from t_sex";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		
		List list = query.list();
		
		return list;
	}

	/*
	 * (non-Javadoc)
	 * @see com.sealtalk.dao.adm.BranchDao#getMemberById(java.lang.Integer)
	 * by alopex
	 */
	@Override
	public List getPosition() {
		
		String sql = "select id, name from t_position";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		
		List list = query.list();
		
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getBrancTreeAndMember() {
		String sql = "select BM.id,BM.branch_id,BM.member_id,BM.position_id,BM.is_master," +
				"BC.parent_id,BC.name,M.account,M.fullname,M.logo,M.telephone,M.email," +
				"M.address,M.token,M.sex,M.birthday,M.workno,M.mobile,M.groupmax,M.groupuse," +
				"M.intro,BC.id,BM.id from t_branch_member BM right join t_branch BC on BM.branch_id=BC.id left join t_member M on BM.member_id=M.id";
		
		try {
			SQLQuery query = this.getSession().createSQLQuery(sql);
			
			List list = query.list();
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getBranchMember(String branchId) {
		String sql = "select M.id,M.account,M.fullname,M.logo,M.telephone,M.email," +
			"M.address,M.token,M.sex,M.birthday,M.workno,M.mobile,M.groupmax,M.groupuse," +
			"M.intro from t_branch_member BM inner join t_member M on BM.member_id=M.id where BM.branch_id=" + branchId;	
		
		try {
			SQLQuery query = this.getSession().createSQLQuery(sql);
			
			List list = query.list();
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
