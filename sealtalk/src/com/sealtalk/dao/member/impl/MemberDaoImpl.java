package com.sealtalk.dao.member.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
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
	public boolean updateUserPwdForAccount(String account, String md5Pwd) {
		
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
	
	@Override
	public boolean updateUserPwdForPhone(String phone, String md5Pwd) {
		
		String hql = "update TMember set password='" + md5Pwd + "' where mobile='" + phone + "'";
		
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
	public Object[] getOneOfMember(int id) {
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
				"where M.id=" + id;
			
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

	@Override
	public int updateUserLogo(int userId, String picName) {
		
		try {
			String hql = "update TMember mem set mem.logo='" + picName + "' where id=" + userId;
			int ret = update(hql);
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isUsedPic(int userId, String picName) {
		try {
			Criteria ctr = getCriteria();
			ctr.add(Restrictions.and(Restrictions.eq("id", userId), Restrictions.eq("logo", picName)));
			
			List<TMember> list = ctr.list();
			
			if (list.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int updateMemeberInfo(String account, String fullname, String sex,
			String email, String phone, String sign) {

		try {
			String hql = "update TMember T set t.fullname='" + fullname + "',sex='" + sex + "',email='" + email + "',telephone='" + phone + "',sign='" + sign + "' where account='" + account +"'";
			int ret = update(hql);
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
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
	public TMember getOneMember(String account) {
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
	public List<TMember> getLimitMemberIds(int limit) {
		String sql = (new StringBuilder("select new TMember(t.id) from TMember t")).toString();
		
		try {
			Query query = getSession().createQuery(sql);
			query.setFirstResult(0);
			query.setMaxResults(limit);
			
			List<TMember> list = query.list();
			
			if (list.size() > 0) {
				return list;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}


}
