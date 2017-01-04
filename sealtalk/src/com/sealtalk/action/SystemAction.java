package com.sealtalk.action;

import com.sealtalk.common.BaseAction;
import com.sealtalk.common.Constants;
import com.sealtalk.model.SessionUser;

public class SystemAction extends BaseAction{
	
	private static final long serialVersionUID = 2646630297165564585L;
	
	private final String LOGIN_ERROR_MESSAGE = "loginErrorMsg";
	
	public String login() throws Exception
	{
		if (getSessionUser() == null) {
			return "loginPage";
		} else {
			return "loginSuccess";
		}
	}
	
	public String afterLogin() throws Exception
	{
		String name = request.getParameter("um_name");
		String password = request.getParameter("um_pwd");
		
		System.out.println("当前登录账号: " + name);
		
		SessionUser su = new SessionUser();
		// 1.设置登录用户基本信息
		su.setUserName(name);	
		//2.相关权限信息
		setSessionUser(su);
		return "loginSuccess";
	}
	
	public String logOut() throws Exception
	{
		request.getSession().removeAttribute(Constants.ATTRIBUTE_NAME_OF_SESSIONUSER);
		request.getSession().invalidate();
		return "loginPage";
	}
	
}
