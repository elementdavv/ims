package com.sealtalk.auth.service;

public interface AppSecretService {
	/**
	 * 获取生成的appid和secret
	 * @return
	 */
	public String getAppIDAndSecret();
	
	/**
	 * 设置appid和secret和url
	 * @param appId
	 * @param secret
	 * @param url
	 */
	public String setAppIDAndSecretAndUrl(String appId, String secret, String url);

	/**
	 * 获取临时令牌
	 * @param appId
	 * @return
	 */
	public String getTempToken(String appId);
}
