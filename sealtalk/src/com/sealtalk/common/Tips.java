package com.sealtalk.common;

import net.sf.json.JSONObject;

public enum  Tips {
	NULLUSER("用户名为空", "00001"),
	NULLID("ID为空", "00002"),
	ERRORUSERORPWD("用户名或密码错误", "00003"),
	FALSECOMPAREPWD("密码不一致", "00004"),
	CHANGEPWDSUC("密码修改成功", "00005"),
	CHANGEPWDFAIL("密码修改失败", "00006"),
	SENDTEXTS("短信验证码已发送", "00007"),
	NULLTEXTS("短信验证码为空", "00008"),
	ERRORTEXTS("短信验证码不正确", "00009"),
	TRUETEXTS("短信验证通过", "00010"),
	UNKNOWERR("未知错误", "00011"),
	NOTFRIENDID("未选取好友", "00012"),
	FAILADDFRIEND("好友添加失败", "00013"),
	SUCADDFRIEND("好友添加成功", "00014"),
	HAVEFRIENDRELATION("已存在好友关系", "00015"),
	FAILDELFRIEND("好友删除失败", "00016"),
	SUCDELFRIEND("好友删除成功", "00017"),
	HAVEZEROFRIEND("没有好友", "00018"),
	NOHAVEFRIENDRELATION("不存在好友关系", "00019"),
	NOSENDPERSON("消息发送或接收主体不存在", "00020"),
	NULLGROUPMEMBER("没有组员", "00021"),
	NULLGROUPNAME("没有群组名称", "00022"),
	NOSECGROUP("未选取群组", "00023"),
	NOTCLEARALLMEMBER("有成员未删除，重试或手动删除", "00024"),
	GROUPMOREVOLUME("成员超出上限", "00025"),
	OK("OK", "00026"),
	FAIL("fail", "00027");
	
	private String name;
	private String code;
	
	private Tips(String name, String code) {
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
		//jo.put("errorcode", getCode());
		
		return jo.toString();
	}
	
}
