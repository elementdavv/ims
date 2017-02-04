package com.sealtalk.dao.friend.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.sealtalk.common.BaseDao;
import com.sealtalk.dao.friend.FriendDao;
import com.sealtalk.model.TFriend;
import com.sealtalk.utils.TimeGenerator;

/**
 * 好友关系数据管理层
 * @author hao_dy
 * @date 2017/01/04
 * @since jdk1.7
 */
public class FriendDaoImpl extends BaseDao<TFriend, Long> implements FriendDao {

	@SuppressWarnings("unchecked")
	@Override
	public TFriend getFriendRelation(int accountId, int friendId) {
		try {
			
			Criteria ctr = getCriteria();
			ctr.add(Restrictions.or(Restrictions.and(Restrictions.eq("memberId", accountId), Restrictions.eq("friendId", friendId)), Restrictions.and(Restrictions.eq("friendId", accountId), Restrictions.eq("memberId", friendId))));
			
			List<TFriend> list = ctr.list();
			
			if (list.size() > 0) {
				return list.get(0);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void addFriend(int accountId, int friendId) {
		try {
			int countFriend = count("from TFriend");
			
			TFriend friend = new TFriend();
			friend.setFriendId(friendId);
			friend.setMemberId(accountId);
			friend.setCreatedate(TimeGenerator.getInstance().formatNow("yyyyMMdd"));
			friend.setListorder(countFriend);
			save(friend);
			
			TFriend friend1 = new TFriend();
			friend1.setFriendId(accountId);
			friend1.setMemberId(friendId);
			friend1.setCreatedate(TimeGenerator.getInstance().formatNow("yyyyMMdd"));
			friend1.setListorder(countFriend + 1);
			
			save(friend1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delFriend(int accountId, int friendId) {
		try {
			String hql = "delete from TFriend where memberId=" + accountId + " and friendId=" + friendId + "" +
					" or memberId=" + friendId + " and friendId=" + accountId;
			delete(hql);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TFriend> getFriendRelationForId(int id) {
		try {
			Criteria ctr = getCriteria();
			ctr.add(Restrictions.eq("memberId", id));
			
			List<TFriend> list = ctr.list();
			
			if (list.size() > 0) {
				return list;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TFriend> getFriendRelationForFriendIds(int accountId,
			Integer[] friendId) {
		try {
			
			Criteria ctr = getCriteria();
			ctr.add(Restrictions.and(Restrictions.eq("memberId", accountId), Restrictions.in("friendId", friendId)));
			
			List<TFriend> list = ctr.list();
			
			if (list.size() > 0) {
				return list;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
