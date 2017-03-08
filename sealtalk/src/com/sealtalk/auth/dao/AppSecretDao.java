package com.sealtalk.auth.dao;

import com.sealtalk.auth.model.AppSecret;
import com.sealtalk.common.IBaseDao;

public interface AppSecretDao extends IBaseDao<AppSecret, Integer> {
	public void setAppIDAndSecretAndUrl(AppSecret as);
}
