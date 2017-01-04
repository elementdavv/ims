package com.sealtalk.action;

import org.apache.log4j.Logger;

import com.sealtalk.common.BaseAction;
import com.sealtalk.common.Constants;
import com.sealtalk.model.SessionUser;
import com.sealtalk.model.TMember;
import com.sealtalk.service.MemberService;

public class SystemAction extends BaseAction{
	
	private static final long serialVersionUID = 2646630297165564585L;
	private static final Logger logger = Logger.getLogger(SystemAction.class);
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
		String name = request.getParameter("username");
		String password = request.getParameter("userpwd");
		
		
		//System.out.println("action name : " + name);
		//System.out.println("action password : " + password);
		
		if (name == null || "".equals(name)) {
			request.setAttribute(LOGIN_ERROR_MESSAGE, "用户名为空!");
			return "loginPage";
		}
		
		TMember member = memberService.searchSigleUser(name, password);
		
		if(member == null) {
			request.setAttribute(LOGIN_ERROR_MESSAGE, "用户名或密码错误!");
			return "loginPage";
		}
		
		logger.info("That logining account is " + name);
		
		SessionUser su = new SessionUser();
		
		if (member != null) {
			su.setAccount(member.getAccount());
			su.setFullname(member.getFullname());
		}
		
		//2.相关权限信息
		//code...
		
		setSessionUser(su);
		return "loginSuccess";
	}
	
	public String logOut() throws Exception
	{
		request.getSession().removeAttribute(Constants.ATTRIBUTE_NAME_OF_SESSIONUSER);
		request.getSession().invalidate();
		return "loginPage";
	}
	
	private MemberService memberService;
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
}
