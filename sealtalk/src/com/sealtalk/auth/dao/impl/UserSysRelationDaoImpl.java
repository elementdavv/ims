package com.sealtalk.auth.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.sealtalk.auth.dao.UserSysRelationDao;
import com.sealtalk.auth.model.AppSecret;
import com.sealtalk.auth.model.UserSysRelation;
import com.sealtalk.common.BaseDao;

/**
 * 验证管理
 * 
 * @author hao_dy
 * @date 2017/03/08
 * @since jdk1.7
 */
public class UserSysRelationDaoImpl extends BaseDao<UserSysRelation, Integer> implements UserSysRelationDao {
	private static final Logger logger = Logger
			.getLogger(UserSysRelationDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public UserSysRelation getRelation(Integer appId, Integer userId) {
		try {
			Criteria ctr = getCriteria();
			ctr.add(Restrictions.and(Restrictions.eq("appId", appId), Restrictions.eq("userId", userId)));

			List<UserSysRelation> list = ctr.list();

			if (list.size() > 0) {
				return (UserSysRelation) list.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<UserSysRelation> getAllRelation(int id) {
		try {
			String hql = (new StringBuilder("from UserSysRelation where appId=").append(id)).toString();
			List<UserSysRelation> list = this.getSession().createQuery(hql).list();
			
			if (list != null) {
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
