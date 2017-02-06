package com.sealtalk.dao.adm.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.sealtalk.common.BaseDao;
import com.sealtalk.dao.adm.MemberRoleDao;
import com.sealtalk.model.TMemberRole;

public class MemberRoleDaoImpl extends BaseDao<TMemberRole, Integer> implements MemberRoleDao {

	@Override
	public TMemberRole getRoleForId(int id) {
		try {
			
			Criteria ctr = getCriteria();
			ctr.add(Restrictions.eq("memberId", id));
			
			List<TMemberRole> list = ctr.list();
			
			if (list.size() > 0) {
				return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
