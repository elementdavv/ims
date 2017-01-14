package com.sealtalk.dao.group;

import java.util.ArrayList;
import java.util.List;

import com.sealtalk.model.TGroup;
import com.sealtalk.model.TGroupMember;

/**
 * 群组成员关系
 * @author hao_dy
 * @since jdk1.7
 * @date 2017/01/12
 */
public interface GroupMemberDao {

	/**
	 * 保存群组成员关系
	 * @param idsArr
	 */
	public void saveGroupMemeber(ArrayList<TGroupMember> idsArr);

	/**
	 * 获取群组成员
	 * @param groupId
	 * @return
	 */
	public List<TGroupMember> getTGroupMemberList(int groupId);

	/**
	 * 退出群组
	 * @param userIdsInt
	 * @param groupIdInt
	 */
	public void removeGroupMemeber(Integer[] userIdsInt, int groupIdInt);

}
