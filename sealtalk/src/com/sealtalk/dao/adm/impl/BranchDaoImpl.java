package com.sealtalk.dao.adm.impl;

import java.util.List;

import org.hibernate.SQLQuery;

import com.sealtalk.common.BaseDao;
import com.sealtalk.dao.adm.BranchDao;
import com.sealtalk.model.TBranch;

public class BranchDaoImpl extends BaseDao<TBranch, Long> implements BranchDao {

	@SuppressWarnings("unchecked")
	@Override
	public List getBranchTree() {
		
		String sql = "select id,parent_id,name from t_branch";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		
		List list = query.list();
		
		return list;
	}

}
