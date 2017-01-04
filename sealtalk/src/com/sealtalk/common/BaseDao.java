package com.sealtalk.common;

import java.sql.SQLException;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class BaseDao extends HibernateDaoSupport {
	/**
	 * @Title: bulkExecute
	 * @Description: TODO(指定IDS参数执行update、delete)
	 * @param
	 * @param queryString
	 * @param
	 * @param ids
	 *            设定文件
	 * @return void 返回类型
	 * @throws
	 */
	@SuppressWarnings({ "unchecked" })
	public void bulkExecute(final String queryString, final Object[] ids)
	{
		
		this.getHibernateTemplate().execute(new HibernateCallback()
		{
			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				Query query = session.createQuery(queryString);
				query.setParameterList("ids", ids);
				return query.executeUpdate();
			}
		});
	}
	
	/**
	 * @Title: execute
	 * @Description: TODO(通用update delete)
	 * @param
	 * @param hql
	 *            设定文件
	 * @return void 返回类型
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public void execute(final String hql)
	{
		getHibernateTemplate().execute(new HibernateCallback()
		{
			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				Query q = session.createQuery(hql);
				q.executeUpdate();
				return null;
			}
		});
	}
}
