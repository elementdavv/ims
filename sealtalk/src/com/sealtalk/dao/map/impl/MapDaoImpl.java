package com.sealtalk.dao.map.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;

import com.sealtalk.common.BaseDao;
import com.sealtalk.dao.map.MapDao;
import com.sealtalk.model.TMap;

public class MapDaoImpl extends BaseDao<TMap, Long> implements MapDao {

	@SuppressWarnings("unchecked")
	@Override
	public Object[] getLocation(int targetId) {
		try {

			String hql = "select MEM.logo, MAP.latitude, MAP.longitude from t_member MEM inner join t_map MAP on MEM.id=MAP.user_id where MEM.id=" + targetId;
			
			
			SQLQuery query = getSession().createSQLQuery(hql);
			
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
	public List<Object[]> getLocationForGroupId(int[] targetIdInt) {
		try {
			List<Object[]> ret = new ArrayList<Object[]>();
			String hql = "select MEM.id, MEM.logo, MAP.latitude, MAP.longitude from t_member MEM inner join t_map MAP on MEM.id=MAP.user_id where MEM.id in(" + targetIdInt + ")";
			
			SQLQuery query = getSession().createSQLQuery(hql);
			
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
	public void subLocation(TMap tm) {
		try {
			save(tm);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


}
