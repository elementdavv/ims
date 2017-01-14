package com.sealtalk.service.group;

/**
 * 群组管理
 * @author hao_dy
 * @since jdk1.7
 * @date 2017/01/12
 */
public interface GroupService {
	/**
	 * 创建群组
	 * @param userid
	 * @param groupids
	 * @return
	 */
	public String createGroup(String userid, String groupids);
	
	/**
	 * 加入群组
	 * @param groupids
	 * @param groupid
	 * @return
	 */
	public String joinGroup(String groupids, String groupid);
	
	/**
	 * 退出群
	 * @param groupids
	 * @param groupid
	 * @return
	 */
	public String leftGroup(String groupids, String groupid);
	public String refreshGroup();
	public String listGroupMemebers();
	
	/**
	 * 获取群组列表
	 * @param userid
	 * @return
	 */
	public String getGroupList(String userid);

	/**
	 * 解散群组
	 * @param userid
	 * @param groupid
	 * @return
	 */
	public String dissLovedGroup(String userid, String groupid);
}
