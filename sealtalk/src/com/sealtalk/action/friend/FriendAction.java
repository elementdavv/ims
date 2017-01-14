package com.sealtalk.action.friend;

import javax.servlet.ServletException;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.googlecode.sslplugin.annotation.Secured;
import com.sealtalk.common.BaseAction;
import com.sealtalk.common.Tips;
import com.sealtalk.service.friend.FriendService;

/**
 * 联系人管理 
 * @author hao_dy
 * @since jdk1.7
 * @date 2017/01/07
 */
@Secured
public class FriendAction extends BaseAction {

	private static final long serialVersionUID = -7261604465748499252L;
	private static final Logger logger = Logger.getLogger(FriendAction.class);
	
	/**
	 * 添加联系人关系
	 * @return
	 * @throws ServletException
	 */
	public String addFriend() throws ServletException {
		JSONObject jo = new JSONObject();
		String result = null;
		
		try{
			if (account == null || "".equals(account)) {
				jo.put("code", -1);
				jo.put("text", Tips.UNKNOWERR.getName());
				result = jo.toString();
			} else if (friend == null || "".equals(friend)) {
				jo.put("code", 0);
				jo.put("text", Tips.NOTFRIENDID.getName());
				result = jo.toString();
			} else {
				result = friendService.addFriend(account, friend);
			}
			
			logger.info(result);
			
			returnToClient(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "text";
	}
	
	/**
	 * 删除联系人关系
	 * @return
	 * @throws ServletException
	 */
	public String delFriend() throws ServletException {
		JSONObject jo = new JSONObject();
		String result = null;
		
		try {
			if (account == null || "".equals(account)) {
				jo.put("code", -1);
				jo.put("text", Tips.UNKNOWERR.getName());
				result = jo.toString();
			} else if (friend == null || "".equals(friend)) {
				jo.put("code", 0);
				jo.put("text", Tips.NOTFRIENDID.getName());
				result = jo.toString();
			} else {
				result = friendService.delFriend(account, friend);
			}
			logger.info(result);
			returnToClient(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "text";
	}
	
	/**
	 * 拉取联系人列表
	 * @return
	 * @throws ServletException
	 */
	public String getMemberFriends() throws ServletException {
		JSONObject jo = new JSONObject();
		String result = null;
		
		try {
			if (account == null || "".equals(account)) {
				jo.put("code", -1);
				jo.put("text", Tips.NULLUSER.getName());
			} else {
				result = friendService.getMemberFriends(account);
			}
			
			logger.info(result);
			returnToClient(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "text";
	}
	
	private FriendService friendService;
	
	public void setFriendService(FriendService fs) {
		this.friendService = fs;
	}
	
	private String account;
	private String friend;

	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getFriend() {
		return friend;
	}
	public void setFriend(String friend) {
		this.friend = friend;
	}
	
}
