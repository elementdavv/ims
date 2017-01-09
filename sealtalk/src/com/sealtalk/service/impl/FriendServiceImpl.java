package com.sealtalk.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import com.sealtalk.common.Tips;
import com.sealtalk.dao.FriendDao;
import com.sealtalk.dao.MemberDao;
import com.sealtalk.model.TFriend;
import com.sealtalk.model.TMember;
import com.sealtalk.service.FriendService;

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
			List<TMember> memberList = memberDao.getMultipleMember(mulMemberStr);
			
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
	
	private FriendDao friendDao;
	private MemberDao memberDao;
	
	public void setFriendDao(FriendDao fd) {
		this.friendDao = fd;
	}
	
	public void setMemberDao(MemberDao md) {
		this.memberDao = md;
	}
}
