package com.sealtalk.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.sealtalk.common.BaseDao;
import com.sealtalk.dao.MemberDao;
import com.sealtalk.model.TMember;
import com.sealtalk.utils.PasswordGenerator;

/**
 * @功能  成员数据管理层
 * @author hao_dy
 * @date 2017/01/04
 * @since jdk1.7
 */
public class MemberDaoImpl extends BaseDao<TMember, Long> implements MemberDao {

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
	public List<TMember> getMultipleMember(String[] mulMemberStr) {
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

}
