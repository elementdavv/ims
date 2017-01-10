package com.sealtalk.service.friend.impl;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sealtalk.common.Tips;
import com.sealtalk.dao.friend.FriendDao;
import com.sealtalk.dao.member.MemberDao;
import com.sealtalk.model.TFriend;
import com.sealtalk.model.TMember;
import com.sealtalk.service.friend.FriendService;
import com.sealtalk.utils.JSONUtils;

/**
 * 好友关系
 * @author hao_dy
 * @since jdk1.7
 */
public class FriendServiceImpl implements FriendService {

	@Override
	public String addFriend(String account, String friend) {
		
		JSONObject jo = new JSONObject();
		
		try {
			//检查好友及用户是否存在
			String[] mulMemberStr = {account, friend};
			List<TMember> memberList = memberDao.getMultipleMemberForAccounts(mulMemberStr);
			
			if (memberList == null || memberList.size() != 2) {
				jo.put("code", -1);
				jo.put("text", Tips.FAILADDFRIEND.getName());
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
				
				if (friendRelation == null) {
					//增加好友关系
					friendDao.addFriend(accountId, friendId);
					jo.put("code", 1);
					jo.put("text", Tips.SUCADDFRIEND.getName());
				} else {
					jo.put("code", 0);
					jo.put("text", Tips.HAVEFRIENDRELATION.getName());
				}
			}
			
		} catch (Exception e) {
			jo.put("code", -1);
			jo.put("text", Tips.FAILADDFRIEND.getName());
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
			jo.put("text", Tips.FAILDELFRIEND.getName());
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
				jo.put("text", Tips.SUCDELFRIEND.getName());
			} else {
				jo.put("code", 0);
				jo.put("text", Tips.NOHAVEFRIENDRELATION.getName());
			}
		}
		
		return jo.toString();
	}
	
	@Override
	public String getMemberFriends(String account) {
		
		boolean status = true;
		String result = null;
		
		try {
			TMember tm = memberDao.getOneOfMember(account);
			if (tm == null) {
				status = false;
			} else {
				int id = tm.getId();
				
				List<TFriend> friendList = friendDao.getFriendRelationForId(id);
				
				int len = friendList.size();
				
				if (friendList == null) {
					status = false;
				} else {
					Integer[] accounts = new Integer[len];
					
					for(int i = 0; i < len; i++) {
						accounts[i] = friendList.get(i).getFriendId();
					}
					
					List<TMember> memberList = memberDao.getMultipleMemberForIds(accounts);
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
				}
			}
			if (!status) {
				JSONObject jo = new JSONObject();
				jo.put("code", 0);
				jo.put("text", Tips.HAVEZEROFRIEND.getName());
				
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
