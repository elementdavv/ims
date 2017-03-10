package com.sealtalk.auth.model;

/**
 * appid,secret模型
 * @author hao_dy
 * @date 2017/03/08
 */
public class AppSecret {
	private String appId;
	private String secert;
	private String callBackUrl;
	private long time;
	
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
	public String getCallBackUrl() {
		return callBackUrl;
	}
	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
}
