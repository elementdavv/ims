package com.sealtalk.auth.action;

import java.io.IOException;

import javax.servlet.ServletException;

import org.json.JSONException;
import org.json.JSONObject;

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
	
	/**
	 * 场景一取临时令牌
	 * @return
	 * @throws ServletException
	 */
	public String getTempTokenSceneOne() throws ServletException,IOException, JSONException {
		JSONObject result = appSecretService.getTempTokenSceneOne(appId);
		if (result.getString("code").equals("500")) {
			returnToClient(result.toString());
			return "text";
		} else {
			setUnAuthToken(result.getString("text"));
			return "redirectLogin";
			//response.sendRedirect("apage.jsp");
		}
	}
	
	/**
	 * 场景二取临时令牌
	 * @return
	 * @throws ServletException
	 */
	public String getTempTokenSceneTwo() throws ServletException,IOException, JSONException {
		JSONObject result = appSecretService.getTempTokenSceneOne(appId);
		returnToClient(result.toString());
		return "text";
	}
	
	/**
	 * 获取未授权临时令牌并跳转登陆
	 * @return
	 * @throws ServletException
	 */
	public String redirectLogin() throws ServletException {
		return "login";
	}
	
	/**
	 * 场景一登陆并授权，返回授权临时令牌
	 * @return
	 * @throws ServletException
	 * @throws JSONException 
	 * @throws IOException 
	 */
	public String reqAuthorizeOne() throws ServletException, JSONException, IOException {
		JSONObject result = appSecretService.reqAuthorizeOne(unAuthToken, userName, userPwd);
		String code = result.getString("code");
		
		if (code.equals("500")) {
			returnToClient(result.toString());
			return "text";
		} else {
			String url = result.getString("url") + "?authToken=" + result.getString("text");
			response.sendRedirect(url);
		}
		return null;
	}
	
	/**
	 * 场景二授权
	 * @return
	 * @throws ServletException
	 * @throws JSONException 
	 * @throws IOException 
	 */
	public String reqAuthorizeTwo() throws ServletException, IOException {
		String result = appSecretService.reqAuthorizeTwo(getSessionUser(), unAuthToken);
		returnToClient(result);
		return "text";
	}
	
	
	/**
	 * 获取访问令牌
	 * @return
	 * @throws ServletException
	 */
	public String getRealToken() throws ServletException {
		String result = appSecretService.getRealToken(secret, authToken);
		returnToClient(result.toString());
		return "text";
	}
	
	/**
	 * 获取授权用户数据
	 * @return
	 * @throws ServletException
	 */
	public String getAuthResource() throws ServletException {
		String result = appSecretService.getAuthResource(visitiToken);
		returnToClient(result.toString());
		return "text";
	}
	
	public String visitiToken;
	public String authToken;
	public String unAuthToken;
	private String appId;
	private String secret;
	private String url;
	private String userName;
	private String userPwd;
	
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	public void setSecret(String secret) {
		this.secret = secret;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUnAuthToken() {
		return unAuthToken;
	}
	
	public void setUnAuthToken(String unAuthToken) {
		this.unAuthToken = unAuthToken;
	}
	
	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getVisitiToken() {
		return visitiToken;
	}

	public void setVisitiToken(String visitiToken) {
		this.visitiToken = visitiToken;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	private AppSecretService appSecretService;

	public void setAppSecretService(AppSecretService appSecretService) {
		this.appSecretService = appSecretService;
	}
	
}
