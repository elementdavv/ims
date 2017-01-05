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
	TRUETEXTS("短信验证通过");
	
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
