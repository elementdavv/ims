package com.sealtalk.dao.group.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import com.sealtalk.common.BaseDao;
import com.sealtalk.dao.group.GroupMemberDao;
import com.sealtalk.model.TGroup;
import com.sealtalk.model.TGroupMember;

/**
 * @功能  成员数据管理层
 * @author hao_dy
 * @date 2017/01/04
 * @since jdk1.7
 */
public class GroupMemberDaoImpl extends BaseDao<TGroupMember, Long> implements GroupMemberDao {

	@Override
	public void saveGroupMemeber(ArrayList<TGroupMember> gmList) {
		try {
			save(gmList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TGroupMember> getTGroupMemberList(int groupId) {
		try {
			
			Criteria ctr = getCriteria();
			ctr.add(Restrictions.eq("groupId", groupId));
			
			List list = ctr.list();
			
			if (list.size() > 0) {
				return list;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return null;
	}

	@Override
	public void removeGroupMemeber(Integer[] userIds, int groupId) {
		try {
			String sql = "delete from t_group_member where group_id=" + groupId + " and userIds in (" + userIds + ")";
			
			System.out.println("removeGroupMember sql: " + sql);
			
			this.getSession().createSQLQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
			throw new HibernateException(e);
		}
	}

}
