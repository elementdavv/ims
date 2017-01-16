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
	
	/**
	 * 刷新群信息（名称）
	 * @param groupid
	 * @param groupname
	 * @return
	 */
	public String refreshGroup(String groupid, String groupname);
	
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

	/**
	 * 与融云同步用户群列表
	 * @param userid
	 * @return
	 */
	public String syncUserGroup(String userid);

	/**
	 * 转移群组
	 * @param userid
	 * @param groupid
	 * @return
	 */
	public String transferGroup(String userid, String groupid);

	/**
	 * 获取群成员
	 * @param groupid
	 * @return
	 */
	public String listGroupMembers(String groupid);

	/**
	 * 统一管理群成员
	 * @param groupid
	 * @param groupids
	 * @return
	 */
	public String manageGroupMem(String groupid, String groupids);
}
