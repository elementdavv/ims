package com.sealtalk.common;

public enum  Tips {
	NULLUSER("用户名为空"),
	ERRORUSERORPWD("用户名或密码错误"),
	FALSECOMPAREPWD("密码不一致"),
	CHANGEPWDSUC("密码修改成功"),
	CHANGEPWDFAIL("密码修改失败"),
	SENDTEXTS("短信验证码已发送"),
	NULLTEXTS("短信验证码为空"),
	ERRORTEXTS("短信验证码不正确"),
	TRUETEXTS("短信验证通过"),
	UNKNOWERR("未知错误"),
	NOTFRIENDID("未选取好友"),
	FAILADDFRIEND("好友添加失败"),
	SUCADDFRIEND("好友添加成功"),
	HAVEFRIENDRELATION("已存在好友关系"),
	FAILDELFRIEND("好友删除失败"),
	SUCDELFRIEND("好友删除成功"),
	HAVEZEROFRIEND("没有好友"),
	NOHAVEFRIENDRELATION("不存在好友关系");
	
	private String name;
	
	private Tips(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
