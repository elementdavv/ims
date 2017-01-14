package com.sealtalk.action.member;

import javax.servlet.ServletException;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.googlecode.sslplugin.annotation.Secured;
import com.sealtalk.common.BaseAction;
import com.sealtalk.common.Tips;
import com.sealtalk.service.member.MemberService;

/**
 * 成员action
 * @author hao_dy
 * @since jdk1.7
 * @date 2017/01/07
 */
@Secured
public class MemberAction extends BaseAction {

	private static final long serialVersionUID = -9024506148523628104L;
	private static final Logger logger = Logger.getLogger(MemberAction.class);
	
	/**
	 * 获取单个成员信息
	 * @return
	 * @throws ServletException
	 */
	public String getOneOfMember() throws ServletException {
		String result = null;
		
		try {
			if (account == null || "".equals(account)) {
				JSONObject jo = new JSONObject();
				jo.put("code", 0);
				jo.put("text", Tips.NULLUSER);
			} else {
				result = memberService.getOneOfMember(account);
			}
			
			logger.info(result);
			returnToClient(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "text";
	}
	
	private MemberService memberService;
	
	public void setMemberService(MemberService ms) {
		this.memberService = ms;
	}
	
	private String account;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
}
