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
	public String joinGroup();
	public String leftGroup();
	public String disslovedGroup();
	public String refreshGroup();
	public String listGroupMemebers();
	
	/**
	 * 获取群组列表
	 * @param userid
	 * @return
	 */
	public String getGroupList(String userid);
}
