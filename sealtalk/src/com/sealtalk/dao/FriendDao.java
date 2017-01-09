package com.sealtalk.dao;

import com.sealtalk.common.IBaseDao;
import com.sealtalk.model.TFriend;
import com.sealtalk.model.TMember;

/**
 * 好友关系 
 * @author hao_dy
 * @since jdk1.7
 * @date 2017/01/09
 */
public interface FriendDao extends IBaseDao<TFriend, Long> {

	/**
	 * 获取一对好友关系
	 * @param accountId
	 * @param friendId
	 * @return
	 */
	public TFriend getFriendRelation(int accountId, int friendId);

	/**
	 * 增加好友关系 
	 * @param accountId
	 * @param friendId
	 */
	public void addFriend(int accountId, int friendId);
	
} 
