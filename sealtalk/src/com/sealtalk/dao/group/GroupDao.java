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
	 * @param groupIdsArr
	 * @param groupname
	 */
	public String createGroup(int userid, String groupname);
	
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
	public TGroup getGroupForIdAndCode(String userid, String code);

	/**
	 * 删除群组
	 * @param groupId
	 */
	public void removeGroup(TGroup tg);

}
