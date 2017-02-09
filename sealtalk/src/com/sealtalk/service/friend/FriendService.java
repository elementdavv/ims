package com.sealtalk.service.friend;

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

	/**
	 * 删除好友关系
	 * @param account
	 * @param friend
	 * @return
	 */
	public String delFriend(String account, String friend);

	/**
	 * 获取联系人列表
	 * @param account
	 * @return
	 */
	public String getMemberFriends(String account);

	/**
	 * 获取好友关系
	 * @param account
	 * @param friend
	 * @return
	 */
	public String getFriendsRelation(String userId, String friendId);

}
