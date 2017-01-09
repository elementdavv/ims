package com.sealtalk.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.sealtalk.common.BaseDao;
import com.sealtalk.dao.FriendDao;
import com.sealtalk.model.TFriend;
import com.sealtalk.model.TMember;
import com.sealtalk.utils.TimeGenerator;

/**
 * @功能  成员数据管理层
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
			ctr.add(Restrictions.eq("memberId", accountId));
			ctr.add(Restrictions.eq("friendId", friendId));
			
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
			TFriend friend = new TFriend();
			
			friend.setFriendId(friendId);
			friend.setMemberId(accountId);
			friend.setCreatedate(TimeGenerator.getInstance().formatNow("yyyyMMdd"));
			friend.setListorder(0);
			
			save(friend);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
