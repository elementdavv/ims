package com.sealtalk.common;

import net.sf.json.JSONObject;

public enum  AuthTips {
	WORNGMAKEAS("AppId或secret生成错误", "00001"),
	WORNGAPPID("AppId或secret生成错误", "00002"),
	WORNGPARAM("参数错误", "00003"),
	
	OK("OK", "10000"),
	ERROR("error", "3000"),
	FAIL("fail", "20000");
	
	private String name;
	private String code;
	
	private AuthTips(String name, String code) {
		this.name = name;
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getText() {
		JSONObject jo = new JSONObject();
		
		jo.put("context", getName());
		jo.put("code", getCode());
		
		return jo.toString();
	}
	
}
