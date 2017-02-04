package com.sealtalk.service.friend.impl;

import java.util.List;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sealtalk.common.Tips;
import com.sealtalk.dao.friend.FriendDao;
import com.sealtalk.dao.member.MemberDao;
import com.sealtalk.model.TFriend;
import com.sealtalk.model.TMember;
import com.sealtalk.service.friend.FriendService;
import com.sealtalk.utils.JSONUtils;
import com.sealtalk.utils.RongCloudUtils;
import com.sealtalk.utils.StringUtils;

/**
 * 好友关系
 * @author hao_dy
 * @since jdk1.7
 */
public class FriendServiceImpl implements FriendService {

	private static final Logger logger = Logger.getLogger(FriendServiceImpl.class);
	
	@Override
	public String addFriend(String account, String friend) {
		
		JSONObject jo = new JSONObject();
		
		System.out.println("account: " + account);
		System.out.println("friend: " + friend);
		
		try {
			if (!StringUtils.getInstance().isBlank(account) && !StringUtils.getInstance().isBlank(friend)) {
				friend = StringUtils.getInstance().replaceChar(friend, "\"", "");
				friend = StringUtils.getInstance().replaceChar(friend, "[", "");
				friend = StringUtils.getInstance().replaceChar(friend, "]", "");
				
				String[] friendIds = null;
				
				if (friend.indexOf(",") != -1) {
					friendIds = friend.split(",");
				} else {
					friendIds = new String[1];
					friendIds[0] = friend;
				}
				
				//检查好友及用户是否存在
				String[] mulMemberStr = new String[friendIds.length + 1];
				System.arraycopy(friendIds, 0, mulMemberStr, 0, friendIds.length);
				mulMemberStr[friendIds.length] = account;
				List<TMember> memberList = memberDao.getMultipleMemberForAccounts(mulMemberStr);
				
				if (memberList == null || memberList.size() == 0) {
					jo.put("code", -1);
					jo.put("text", Tips.FAILADDFRIEND.getText());
				} else {
					int accountId = 0;
					Integer[] friendId = new Integer[memberList.size() - 1];
					String[] targetIds = new String[memberList.size() - 1];
					
					int j = 0;
					//取出账号id
					for (int i = 0; i < memberList.size(); i++) {
						TMember tm = memberList.get(i);
						if (account.equals(tm.getAccount())) {
							accountId = tm.getId();
						} else {
							friendId[j] = Integer.valueOf(tm.getId());
							targetIds[j] = tm.getId() + "";
							j++;
						}
					}
					
					//判断是否已存在好友关系
					List<TFriend> friendRelation = friendDao.getFriendRelationForFriendIds(accountId, friendId);
					
					for(int i = 0; i < friendId.length; i++) {
					//	TFriend tf = friendRelation.get(i);
					//	if (tf == null) {
							friendDao.addFriend(accountId, friendId[i]);
						//}
					}
					jo.put("code", 1);
					jo.put("text", Tips.SUCADDFRIEND.getText());
					/*if (friendRelation == null) {
						//增加好友关系
						friendDao.addFriend(accountId, friendId);
						jo.put("code", 1);
						jo.put("text", Tips.SUCADDFRIEND.getText());
					} else {
						jo.put("code", 0);
						jo.put("text", Tips.HAVEFRIENDRELATION.getText());
					}*/
					
					//String[] targetIds = {friendId+""};
					//通知融云
					RongCloudUtils.getInstance().sendSysMsg(accountId+"", targetIds, "", "");
				}
			}
		} catch (Exception e) {
			jo.put("code", -1);
			jo.put("text", Tips.FAILADDFRIEND.getText());
			e.printStackTrace();
		}
		
		return jo.toString(); 
	}
	
	@Override
	public String delFriend(String account, String friend) {
		JSONObject jo = new JSONObject();
		
		//检查好友及用户是否存在
		String[] mulMemberStr = {account, friend};
		List<TMember> memberList = memberDao.getMultipleMemberForAccounts(mulMemberStr);
		
		if (memberList == null || memberList.size() != 2) {
			jo.put("code", -1);
			jo.put("text", Tips.FAILDELFRIEND.getText());
		} else {
			int accountId = 0;
			int friendId = 0;
			
			//取出账号id
			for (int i = 0; i < memberList.size(); i++) {
				TMember tm = memberList.get(i);
				if (account.equals(tm.getAccount())) {
					accountId = tm.getId();
				}
				if (friend.equals(tm.getAccount())) {
					friendId = tm.getId();
				}
			}
			
			//判断是否已存在好友关系
			TFriend friendRelation = friendDao.getFriendRelation(accountId, friendId);
			
			if (friendRelation != null) {
				//删除好友关系
				friendDao.delFriend(accountId, friendId);
				jo.put("code", 1);
				jo.put("text", Tips.SUCDELFRIEND.getText());
			} else {
				jo.put("code", 0);
				jo.put("text", Tips.NOHAVEFRIENDRELATION.getText());
			}
		}
		
		return jo.toString();
	}
	
	@Override
	public String getMemberFriends(String account) {
		
		boolean status = true;
		String result = null;
		
		logger.info(account);
		
		try {
			int id = memberDao.getMemberIdForAccount(account);
			
			if (id == 0) {
				status = false;
			} else {
				List<TFriend> friendList = friendDao.getFriendRelationForId(id);
				
				if (friendList == null) {
					status = false;
				} else {
					int len = friendList.size();
					Integer[] accounts = new Integer[len];
					
					for(int i = 0; i < len; i++) {
						accounts[i] = friendList.get(i).getFriendId();
					}
					
					List<TMember> memberList = memberDao.getMultipleMemberForIds(accounts);
					
					if (memberList != null) {
						int memberLen = memberList.size();
						
						if (memberList == null) {
							status = false;
						} else {
							JSONArray ja = new JSONArray();
							
							for(int i = 0; i < memberLen; i++) {
								TMember tms = memberList.get(i);
								JSONObject text = JSONUtils.getInstance().modelToJSONObj(tms);
								if (i == 0) {
									text.put("code", 1);
									text.put("text", "ok");
								}
								ja.add(text);
							}
							
							result = ja.toString();
						}
					} else {
						status = false;
					}
				}
			}
			if (!status) {
				JSONObject jo = new JSONObject();
				jo.put("code", 0);
				jo.put("text", Tips.HAVEZEROFRIEND.getText());
				
				result = jo.toString();
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	
	private FriendDao friendDao;
	private MemberDao memberDao;
	
	public void setFriendDao(FriendDao fd) {
		this.friendDao = fd;
	}
	
	public void setMemberDao(MemberDao md) {
		this.memberDao = md;
	}

}
