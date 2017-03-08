package com.sealtalk.auth.action;

import javax.servlet.ServletException;

import com.sealtalk.auth.service.AppSecretService;
import com.sealtalk.common.BaseAction;

/**
 * appid, secret action
 * @author hao_dy
 * @date 2017/03/08
 */
public class AppSecretAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 获取appid和secret
	 * @return
	 * @throws ServletException
	 */
	public String getAppIDAndSecret() throws ServletException {
		String result = appSecretService.getAppIDAndSecret();
		returnToClient(result);
		return "text";
	}
	
	/**
	 * 设置auth2登陆基本信息
	 * @return
	 * @throws ServletException
	 */
	public String setAppIDAndSecretAndUrl() throws ServletException {
		String result = appSecretService.setAppIDAndSecretAndUrl(appId, secret, url);
		returnToClient(result);
		return "text";
	}
	private String appId;
	private String secret;
	private String url;
	
	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	private AppSecretService appSecretService;

	public void setAppSecretService(AppSecretService appSecretService) {
		this.appSecretService = appSecretService;
	}
	
}
