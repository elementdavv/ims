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
	 * @param groupname
	 * @return
	 */
	public String createGroup(String userid, String groupids, String groupname);
	public String joinGroup();
	public String leftGroup();
	public String disslovedGroup();
	public String refreshGroup();
	public String listGroupMemebers();
}
