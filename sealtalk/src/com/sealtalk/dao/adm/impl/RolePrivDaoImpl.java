package com.sealtalk.dao.adm.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.sealtalk.common.BaseDao;
import com.sealtalk.dao.adm.RolePrivDao;
import com.sealtalk.model.TPriv;
import com.sealtalk.model.TRolePriv;

public class RolePrivDaoImpl extends BaseDao<TRolePriv, Integer> implements RolePrivDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<TRolePriv> getRolePrivsByPrivs(Integer[] privIds) {
		try {
			
			Criteria ctr = getCriteria();
			ctr.add(Restrictions.in("privId", privIds));
			
			List<TRolePriv> list = ctr.list();
			
			if (list.size() > 0) {
				return list;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
