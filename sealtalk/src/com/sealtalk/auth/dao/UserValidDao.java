package com.sealtalk.auth.dao;

import java.util.List;

import com.sealtalk.auth.model.UserValid;
import com.sealtalk.common.IBaseDao;

public interface UserValidDao extends IBaseDao<UserValid, Integer> {

	public void setUnAuthToken(UserValid uv);

	public UserValid getUserValidByUnAuthToken(String unAuthToken);

	public UserValid getUserValidByAuthToken(String authToken);

	public UserValid getUserValidByRealToken(String visitToken);

	public List<UserValid> getUserValidByAsId(int asId);

	public void delUserValid(int id);

	public int deleteRelationByIds(String userids);
}
