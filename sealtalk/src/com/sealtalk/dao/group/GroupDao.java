package com.sealtalk.dao.group;

import java.util.ArrayList;
import java.util.List;

import com.sealtalk.model.TGroup;
import com.sealtalk.model.TGroupMember;

/**
 * 群组dao
 * @author hao_dy
 * @since jdk1.7
 * @date 2017/01/12
 */
public interface GroupDao {

	/**
	 * 创建群组
	 * @param userid
	 * @param tempIds
	 * @param groupname
	 */
	public int createGroup(int userid, String code, String groupname);
	
	/**
	 * 获取群组数量
	 * @return
	 */
	public int countGroup();

	/**
	 * 查询群组
	 * @param userid
	 * @param code
	 * @return
	 */
	public TGroup getGroupForIdAndCode(int userid, String code);

	/**
	 * 删除群组
	 * @param groupId
	 */
	public void removeGroup(TGroup tg);

	/**
	 * 按id查找群组
	 * @param groupId
	 * @return
	 */
	public TGroup getGroupForId(int groupId);
	
	/**
	 * 获取群列表
	 * @param userIdInt
	 */
	public List<TGroup> getGroupList(int userIdInt);

}
