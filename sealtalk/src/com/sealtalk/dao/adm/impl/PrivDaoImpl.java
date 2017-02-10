package com.sealtalk.dao.adm.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;

import com.sealtalk.common.BaseDao;
import com.sealtalk.dao.adm.PrivDao;
import com.sealtalk.model.TFriend;
import com.sealtalk.model.TPriv;

public class PrivDaoImpl extends BaseDao<TPriv, Integer> implements PrivDao {

	@SuppressWarnings("unchecked")
	@Override
	public TPriv getPrivByUrl(String url) {
		try {
			
			Criteria ctr = getCriteria();
			ctr.add(Restrictions.eq("url", url));
			
			List<TPriv> list = ctr.list();
			
			if (list.size() > 0) {
				return list.get(0);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List getMemberByPrivId(int privId) {
		String sql = (new StringBuilder("select M.id mid,TRP.id trpid from t_member M left join t_member_role MR on M.id=MR.member_id left join t_role_priv TRP on TRP.role_id=MR.role_id and TRP.priv_id=").append(privId)).toString();
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		
		return list;
	}

}
