package com.sealtalk.auth.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;

import com.sealtalk.auth.dao.UserValidDao;
import com.sealtalk.auth.model.AppSecret;
import com.sealtalk.auth.model.UserValid;
import com.sealtalk.common.BaseDao;
import com.sealtalk.utils.LogUtils;

/**
 * 验证管理
 * 
 * @author hao_dy
 * @date 2017/03/08
 * @since jdk1.7
 */
public class UserValidDaoImpl extends BaseDao<UserValid, Integer> implements
		UserValidDao {

	@Override
	public void setUnAuthToken(UserValid uv) {
		try {
			saveOrUpdate(uv);
			logger.info("UnAuthToken is save!");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(LogUtils.getInstance().getErrorInfoFromException(e));
		}
	}

	@Override
	public UserValid getUserValidByUnAuthToken(String unAuthToken) {
		try {
			Criteria ctr = getCriteria();
			ctr.add(Restrictions.eq("unAuthToken", unAuthToken));

			List<UserValid> list = ctr.list();

			if (list.size() > 0) {
				return (UserValid) list.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public UserValid getUserValidByAuthToken(String authToken) {
		try {
			Criteria ctr = getCriteria();
			ctr.add(Restrictions.eq("authToken", authToken));

			List<UserValid> list = ctr.list();

			if (list.size() > 0) {
				return (UserValid) list.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public UserValid getUserValidByRealToken(String visitToken) {
		try {
			Criteria ctr = getCriteria();
			ctr.add(Restrictions.eq("visitToken", visitToken));

			List<UserValid> list = ctr.list();

			if (list.size() > 0) {
				return (UserValid) list.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<UserValid> getUserValidByAsId(int asId) {
		try {
			String sql = (new StringBuilder("from UserValid where asid=").append(asId)).toString();
			Query query = getSession().createQuery(sql);
			List list = query.list();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void delUserValid(int id) {
		try {
			String sql = (new StringBuilder("delete from UserValid where asid=")
					.append(id)).toString();
			System.out.println(sql);
			delete(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public int deleteRelationByIds(String userids) {
		try {
			String hql = (new StringBuilder("delete from UserValid where userId in (").append(userids).append(")")).toString();
			return delete(hql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

}
