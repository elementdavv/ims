package com.sealtalk.auth.dao;

import com.sealtalk.auth.model.UserValid;
import com.sealtalk.common.IBaseDao;

public interface UserValidDao extends IBaseDao<UserValid, Integer> {

	public void setUnAuthToken(UserValid uv);

	public UserValid getUserValidByUnAuthToken(String unAuthToken);

	public UserValid getUserValidByAuthToken(String authToken);

	public UserValid getUserValidByRealToken(String visitToken);
}
