package com.sealtalk.auth.dao;

import java.util.List;

import com.sealtalk.auth.model.UserSysRelation;
import com.sealtalk.common.IBaseDao;

public interface UserSysRelationDao extends IBaseDao<UserSysRelation, Integer> {
	
	/**
	 * 获取用户应用关系 
	 * @param appId
	 * @param userId
	 * @return
	 */
	public UserSysRelation getRelation(Integer appId, Integer userId);

	/**
	 * 获取所有用户应用关系
	 * @param appRecordId 
	 * @return
	 */
	public List<UserSysRelation> getAllRelation(int appRecordId);

}
