package com.sealtalk.auth.model;

/**
 * oauth2登陆数据模型
 * @author hao_dy
 * @date 2017/03/08
 */
public class AppSecret {
	private int id;
	private int userId;						//用户id
	private String appId;					//appid		用来获取未授权临时令牌
	private String secert;					//secret	用来获取访问令牌
	private String unAuthToken;
	private String authToken;				//已授权临时令牌
	private String visitToken;				//访问令牌
	private String callBackUrl;				//第三方系统回调地址
	private long appTime;					//appid,secret生成时间
	private long unAuthTokenTime;			//未授权临时令牌生成时间
	private long authTokenTime;				//已授权临时令牌生成时间
	private long visitTokenTime;			//访问令牌生成时间
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getSecert() {
		return secert;
	}
	public void setSecert(String secert) {
		this.secert = secert;
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
	public String getVisitToken() {
		return visitToken;
	}
	public void setVisitToken(String visitToken) {
		this.visitToken = visitToken;
	}
	public String getCallBackUrl() {
		return callBackUrl;
	}
	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}
	public long getAppTime() {
		return appTime;
	}
	public void setAppTime(long appTime) {
		this.appTime = appTime;
	}
	public long getUnAuthTokenTime() {
		return unAuthTokenTime;
	}
	public void setUnAuthTokenTime(long unAuthTokenTime) {
		this.unAuthTokenTime = unAuthTokenTime;
	}
	public long getAuthTokenTime() {
		return authTokenTime;
	}
	public void setAuthTokenTime(long authTokenTime) {
		this.authTokenTime = authTokenTime;
	}
	public long getVisitTokenTime() {
		return visitTokenTime;
	}
	public void setVisitTokenTime(long visitTokenTime) {
		this.visitTokenTime = visitTokenTime;
	}
	
}
