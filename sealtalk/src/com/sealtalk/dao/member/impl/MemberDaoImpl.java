package com.sealtalk.dao.member.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;

import com.sealtalk.common.BaseDao;
import com.sealtalk.dao.member.MemberDao;
import com.sealtalk.model.TMember;
import com.sealtalk.utils.PasswordGenerator;
import com.sealtalk.utils.TimeGenerator;

/**
 * @功能  成员数据管理层
 * @author hao_dy
 * @date 2017/01/04
 * @since jdk1.7
 */
public class MemberDaoImpl extends BaseDao<TMember, Integer> implements MemberDao {

	@Override
	public TMember getMemberByName(String name) {

		Criteria ctr = getCriteria();
		ctr.add(Restrictions.eq("fullname", name));
		
		List list = ctr.list();
		
		if (list.size() > 0) {
			return (TMember) list.get(0);
		}
		
		return null;
	}

	@Override
	public List getMemberPosition(Integer memberId) {

		String sql = "select position_id, branch_id, id from t_branch_member"
				+ " where member_id = " + memberId 
				+ " order by is_master desc";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		
		List list = query.list();
		
		return list;
	}

	@Override
	public List getMemberRole(Integer memberId) {

		String sql = "select role_id from t_member_role"
				+ " where member_id = " + memberId;
		SQLQuery query = this.getSession().createSQLQuery(sql);
		
		List list = query.list();
		
		return list;
	}


	@SuppressWarnings("unchecked")
	@Override
	public TMember searchSigleUser(String name, String password) {
		
		try {
			
			Criteria ctr = getCriteria();
			ctr.add(Restrictions.eq("account", name));
			ctr.add(Restrictions.eq("password", password));
			
			List list = ctr.list();
			
			if (list.size() > 0) {
				return (TMember) list.get(0);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean updateUserPwd(String account, String md5Pwd) {
		
		String hql = "update TMember set password='" + md5Pwd + "' where account='" + account + "'";
		
		boolean status = true;
		
		try {
			executeUpdate(hql);
		} catch (Exception e) {
			status = false;
			e.printStackTrace();
		}
		
		return status;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TMember getOneOfMember(String account) {
		try {
			
			Criteria ctr = getCriteria();
			ctr.add(Restrictions.eq("account", account));
			
			List<TMember> list = ctr.list();
			
			if (list.size() > 0) {
				return (TMember) list.get(0);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TMember> getMultipleMemberForAccounts(String[] mulMemberStr) {
		try {
			
			Criteria ctr = getCriteria();
			ctr.add(Restrictions.in("account", mulMemberStr));
			
			List<TMember> list = ctr.list();
			
			if (list.size() > 0) {
				return list;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TMember> getMultipleMemberForIds(Integer[] ids) {
		try {
			
			Criteria ctr = getCriteria();
			ctr.add(Restrictions.in("id", ids));
			
			List<TMember> list = ctr.list();
			
			if (list.size() > 0) {
				return list;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public int updateUserTokenForId(String userId, String token) {
		
		try {
			long now = TimeGenerator.getInstance().getUnixTime();
			String hql = "update TMember mem set mem.token='" + token + "',createtokendate=" + now + " where id=" + userId;
			
			int row = update(hql);
			
			return row;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

}
