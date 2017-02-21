package com.sealtalk.service.friend.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.sealtalk.common.Tips;
import com.sealtalk.dao.adm.BranchMemberDao;
import com.sealtalk.dao.adm.OrgDao;
import com.sealtalk.dao.friend.FriendDao;
import com.sealtalk.dao.member.MemberDao;
import com.sealtalk.model.TFriend;
import com.sealtalk.model.TMember;
import com.sealtalk.model.TOrgan;
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
					String msg = "建立好友关系，现在可以开始聊天";
					String extrMsg = msg;
					RongCloudUtils.getInstance().sendPrivateMsg(accountId+"", targetIds, msg, msg, "4", "0", "0", "0", "2");
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
				
				String[] targetIds = {friend};
				
				String msg = "解除好友关系";
				RongCloudUtils.getInstance().sendPrivateMsg(accountId+"", targetIds, msg, msg, "4", "0", "0", "0", "2");
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
		JSONObject jo = new JSONObject();
		
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
					
					//获取多个用户
					List<TMember> memberList = memberDao.getMultipleMemberForIds(accounts);
					
					if (memberList != null) {
						int memberLen = memberList.size();
						//获取用户职务id
						StringBuilder sb = new StringBuilder();
						StringBuilder so = new StringBuilder();
						ArrayList<Integer> organId = new ArrayList<Integer>();
						
						for(int i = 0; i< memberLen;i++) {
							TMember t = memberList.get(i);
							sb.append(t.getId()).append(",");
							if (!organId.contains(t.getOrganId())) {
								so.append(t.getOrganId());
							}
						}
						String sbStr = sb.toString();
						String soStr = sb.toString();
						
						if (sbStr != null) {
							sbStr = sbStr.substring(0, sbStr.length() - 1);
						}
						
						if (soStr != null) {
							soStr = soStr.substring(0, soStr.length() - 1);
						}
						
						List memberPosition = branchMemberDao.getBranchMemberByMemberIds(sbStr);
						List memberOrgan = orgDao.getInfos(soStr);
						
						if (memberList == null) {
							status = false;
						} else {
							JSONArray ja = new JSONArray();
							
							for(int i = 0; i < memberLen; i++) {
								TMember tms = memberList.get(i);
								JSONObject text = JSONUtils.getInstance().modelToJSONObj(tms);
								boolean f = false;
								boolean g = false;
								
								for(int j = 0; j < memberPosition.size(); j++) {
									if(memberPosition.get(j) != null) {
										Object[] o = (Object[]) memberPosition.get(j);
										if ((tms.getId()+"").equals(String.valueOf(o[0]))) {
											text.put("position", o[1]);
											text.put("branch", o[2]);
											f = true;
											break;
										}
									}
								}
								
								for(int k = 0; k < memberOrgan.size(); k++) {
									if(memberOrgan.get(k) != null) {
										Object[] organ = (Object[]) memberOrgan.get(k);
										if ((tms.getId()+"").equals(String.valueOf(organ[0]))) {
											text.put("organName", organ[1]);
											g = true;
											break;
										}
									}
								}
								
								if (!f) {
									text.put("position", "");
									text.put("branch", "");
								}
								
								if (!g) {
									text.put("organName", "");
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
				jo.put("code", 0);
				jo.put("text", Tips.HAVEZEROFRIEND.getText());
			}  else {
				jo.put("code", 1);
				jo.put("text", result);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jo.toString();
	}

	@Override
	public String getFriendsRelation(String userId, String friendId) {
		JSONObject j = new JSONObject();
		
		if (!StringUtils.getInstance().isBlank(userId) && !StringUtils.getInstance().isBlank(friendId)) {
			int userIdInt = StringUtils.getInstance().strToInt(userId);
			int friendIdInt = StringUtils.getInstance().strToInt(friendId);
			TFriend friendRelation = friendDao.getFriendRelation(userIdInt, friendIdInt);
			
			if (friendRelation != null) {
				j.put("code", 1);
				j.put("text", "true");
			} else {
				j.put("code", 0);
				j.put("text", "false");
			}
		} else {
			j.put("code", -1);
			j.put("text", Tips.WRONGPARAMS.getText());
		}
		
		return j.toString();
	}
	
	private FriendDao friendDao;
	private MemberDao memberDao;
	private BranchMemberDao branchMemberDao;
	private OrgDao orgDao;
	
	public void setOrgDao(OrgDao orgDao) {
		this.orgDao = orgDao;
	}

	public void setBranchMemberDao(BranchMemberDao branchMemberDao) {
		this.branchMemberDao = branchMemberDao;
	}

	public void setFriendDao(FriendDao fd) {
		this.friendDao = fd;
	}
	
	public void setMemberDao(MemberDao md) {
		this.memberDao = md;
	}

}
