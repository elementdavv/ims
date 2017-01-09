package com.sealtalk.service;

/**
 * 好友关系管理 
 * @since jdk1.7
 * @author hao_dy
 *
 */
public interface FriendService {
	/**
	 * 添加好友关系 
	 * @param account
	 * @param friend
	 * @return
	 */
	public String addFriend(String account, String friend);

}
