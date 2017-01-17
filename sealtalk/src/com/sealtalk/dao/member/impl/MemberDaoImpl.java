package com.sealtalk.dao.member.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;

import com.sealtalk.common.BaseDao;
import com.sealtalk.dao.member.MemberDao;
import com.sealtalk.model.TMember;
import com.sealtalk.utils.TimeGenerator;

/**
 * @功能  成员数据管理层
 * @author hao_dy
 * @date 2017/01/04
 * @since jdk1.7
 */
public class MemberDaoImpl extends BaseDao<TMember, Long> implements MemberDao {

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
	public Object[] getOneOfMember(String account) {
		try {
			String hql = "select " +
				"M.id MID," + 
				"M.account," +
				"M.fullname," +
				"M.logo," +
				"M.telephone," +
				"M.email," +
				"M.address," +
				"M.token," +
				"M.sex," +
				"M.birthday," +
				"M.workno," +
				"M.mobile," +
				"M.groupmax," +
				"M.groupuse," +
				"M.intro," +
				"B.name BNAME," +
				"P.name PNAME," +
				"O.name ONAME " +
				"from t_member M left join t_branch_member BM on M.id=BM.member_id " +
				"left join t_branch B on BM.branch_id=B.id " +
				"left join t_position P on BM.position_id=P.id " +
				"inner join t_organ O on M.organ_id=O.id " +
				"where account='" + account + "'";
			
			SQLQuery query = this.getSession().createSQLQuery(hql);
			
			List list = query.list();
			
			if (list.size() > 0) {
				return (Object[]) list.get(0);
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

	@SuppressWarnings("unchecked")
	@Override
	public TMember getMemberForId(int id) {
		try {
			
			Criteria ctr = getCriteria();
			ctr.add(Restrictions.eq("id", id));
			
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
	public int getMemberIdForAccount(String account) {
		try {
			Criteria ctr = getCriteria();
			ctr.add(Restrictions.eq("account", account));
			
			List<TMember> list = ctr.list();
			
			if (list.size() > 0) {
				return list.get(0).getId();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List searchUser(String account) {
		try {
			String hql = "select " +
				"M.id MID," + 
				"M.account," +
				"M.fullname," +
				"M.logo," +
				"M.telephone," +
				"M.email," +
				"M.address," +
				"M.token," +
				"M.sex," +
				"M.birthday," +
				"M.workno," +
				"M.mobile," +
				"M.groupmax," +
				"M.groupuse," +
				"M.intro," +
				"B.name BNAME," +
				"P.name PNAME," +
				"O.name ONAME " +
				"from t_member M left join t_branch_member BM on M.id=BM.member_id " +
				"left join t_branch B on BM.branch_id=B.id " +
				"left join t_position P on BM.position_id=P.id " +
				"inner join t_organ O on M.organ_id=O.id " +
				"where account like '%" + account + "%' or fullname like '%" + account + "%' or pinyin like '%" + account + "%'";
			
			SQLQuery query = this.getSession().createSQLQuery(hql);
			
			List list = query.list();
			
			if (list.size() > 0) {
				return list;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean valideOldPwd(String account, String oldPwd) {
		try {
			Criteria ctr = getCriteria();
			ctr.add(Restrictions.and(Restrictions.eq("account", account), Restrictions.eq("password", oldPwd)));
			
			List<TMember> list = ctr.list();
			
			if (list.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
