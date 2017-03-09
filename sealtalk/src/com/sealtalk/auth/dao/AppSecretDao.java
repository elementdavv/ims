package com.sealtalk.auth.dao;

import com.sealtalk.auth.model.AppSecret;
import com.sealtalk.common.IBaseDao;

public interface AppSecretDao extends IBaseDao<AppSecret, Integer> {
	/**
	 * 设置app,secret,url
	 * @param as
	 */
	public void setAppIDAndSecretAndUrl(AppSecret as);
	
	/**
	 * 依据appId获取appSecret
	 * @param appId
	 * @return
	 */
	public AppSecret getAppSecretByAppId(String appId);
}
