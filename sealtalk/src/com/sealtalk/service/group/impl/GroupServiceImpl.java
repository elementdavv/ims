package com.sealtalk.service.group.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.sealtalk.common.Tips;
import com.sealtalk.dao.group.GroupDao;
import com.sealtalk.dao.group.GroupMemberDao;
import com.sealtalk.dao.member.MemberDao;
import com.sealtalk.model.TGroup;
import com.sealtalk.model.TGroupMember;
import com.sealtalk.model.TMember;
import com.sealtalk.service.group.GroupService;
import com.sealtalk.utils.JSONUtils;
import com.sealtalk.utils.RongCloudUtils;
import com.sealtalk.utils.StringUtils;
import com.sealtalk.utils.TimeGenerator;

public class GroupServiceImpl implements GroupService {

	private static final Logger logger = Logger.getLogger(GroupServiceImpl.class);
	
	@Override
	public String createGroup(String userId, String groupIds){
		JSONObject jo = new JSONObject();
		String result = null;
		
		boolean status = true;
		
		try {
			if (StringUtils.getInstance().isBlank(userId) || !StringUtils.getInstance().isNumeric(userId)) {
				jo.put("code", -1);
				jo.put("text", Tips.NULLUSER.getText());
			} else if(StringUtils.getInstance().isBlank(groupIds) || 
					!(groupIds.startsWith("[") && groupIds.endsWith("]"))) {
				jo.put("code", -1);
				jo.put("text", Tips.NULLGROUPMEMBER.getText());
			} else {
				int userIdInt = StringUtils.getInstance().strToInt(userId);
			
				//保存群组成员关系
				groupIds = groupIds.substring(1, groupIds.length() - 1);

				ArrayList<String> tempArrIds = new ArrayList<String>();
				
				String[] groupIdsArr = groupIds.split(",");
				
				for(int i = 0; i < groupIdsArr.length; i++) {
					if (!StringUtils.getInstance().isBlank(groupIdsArr[i])) {
						tempArrIds.add(groupIdsArr[i]);
					}
				}  
				
				int idsLen = 0;
				
				if (tempArrIds.contains(userId)) {
					idsLen = groupIdsArr.length;
				} else {
					tempArrIds.add(userId);
					idsLen = groupIdsArr.length + 1;
				}
				
				Integer [] tempIds = new Integer[idsLen];
			
				for(int i = 0; i < tempArrIds.size(); i++) {
					tempIds[i] = StringUtils.getInstance().strToInt(tempArrIds.get(i));	
				}
				
				if (status) {
					//生成群组名称
					List<TMember> memberList = memberDao.getMultipleMemberForIds(tempIds);
					
					StringBuilder groupName = new StringBuilder();
					String groupNameStr = null;
					
					if (memberList != null) {
						int len = 4;
						
						if (memberList.size() <= 4) {
							len = memberList.size();
						}
						
						for(int i = 0; i < len; i++) {
							groupName.append(memberList.get(i).getFullname()).append(",");
						}
						
						groupNameStr = groupName.toString();
						
						if (!StringUtils.getInstance().isBlank(groupNameStr)) {
							groupNameStr = groupNameStr.substring(0, groupNameStr.length() - 1);
						} else {
							groupNameStr = "";
						}
					}
					
					//创建群组
					String code = "G" + userId + "_" + TimeGenerator.getInstance().getUnixTime();
				
					int groupId = groupDao.createGroup(userIdInt, code, groupNameStr);
					
					ArrayList<TGroupMember> tgmList = new ArrayList<TGroupMember>();
					
					for(int i = 0; i < tempIds.length; i++) {
						String flag = "0";
						flag = (tempIds[i] == userIdInt) ? "1" : "0";
						
						tgmList.add(new TGroupMember(groupId, tempIds[i], flag, 0));
					}
					
					groupMemberDao.saveGroupMemeber(tgmList);
					
					//查询成员关系是否正确
					List<TGroupMember> tgmMember = groupMemberDao.getTGroupMemberList(groupId);
					List<String> tgmIds = new ArrayList<String>();
					List<String> delIds = new ArrayList<String>();
					List<String> notDelIds = new ArrayList<String>();
					
					String[] delIdsArray = null;
					String[] sendRCIds = null;
					
					TGroup tg = groupDao.getGroupForId(groupId);
					
					if (tgmMember != null) {
						for(int i = 0; i < tgmMember.size(); i++) {
							tgmIds.add(tgmMember.get(i).getId() + "");
						}
				
						//验证成员是否全部正常保存,去除发送到融云端的未保存成功的成员
						if (tgmMember.size() < groupIdsArr.length) {
							for(int i = 0; i < groupIdsArr.length; i++) {
								if (!tgmIds.contains(groupIdsArr[i])) {
									delIds.add(groupIdsArr[i]);
								} else {
									notDelIds.add(groupIdsArr[i]);
								}
							}
							delIdsArray = new String[delIds.size()];
							sendRCIds = new String[notDelIds.size()];
							delIds.toArray(delIdsArray);
							notDelIds.toArray(sendRCIds);
							
							groupIdsArr = sendRCIds;
						} 
						
						RongCloudUtils.getInstance().createGroup(groupIdsArr, groupId + "", groupNameStr);
						jo.put("code", 200);
						jo.put("text", JSONUtils.getInstance().modelToJSONObj(tg));
					} else {
						groupDao.removeGroup(tg);
						jo.put("code", 0);
						jo.put("text", Tips.NULLGROUPMEMBER.getText());
					}
					
				} 
			}
			
			if (!status) {
				jo.put("code", 0);
				jo.put("text", "fail");
			}
			result = jo.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return result;
	}
	
	@Override
	public String joinGroup(String groupIds, String groupId) {
		JSONObject jo = new JSONObject();
		
		try {
			int groupIdInt = StringUtils.getInstance().strToInt(groupId);
			
			if (groupIdInt == -1) {
				jo.put("code", -1);
				jo.put("text", Tips.NOSECGROUP.getText());
			} else if (StringUtils.getInstance().isBlank(groupIds) ||
				!(groupIds.startsWith("[") && groupIds.endsWith("]"))) {
					jo.put("code", -1);
					jo.put("text", Tips.NULLGROUPMEMBER.getText());
			} else {
				TGroup tg = groupDao.getGroupForId(groupIdInt);
				
				String groupName = tg.getName();
				
				String[] groupIdsArr = StringUtils.getInstance().stringSplit(groupIds, ",");
				
				//保存数据库
				ArrayList<TGroupMember> tgmList = new ArrayList<TGroupMember>();
				
				for(int i = 0; i < groupIdsArr.length; i++) {
					
					int id = StringUtils.getInstance().strToInt(groupIdsArr[i]);
					
					tgmList.add(new TGroupMember(groupIdInt, id, "0", 0));
				}
				
				groupMemberDao.saveGroupMemeber(tgmList);
				
				//通知融云
				RongCloudUtils.getInstance().joinGroup(groupIdsArr, groupId, groupName);
				
				jo.put("code", 1);
				jo.put("text", Tips.OK.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jo.toString();
	}

	@Override
	public String leftGroup(String userId, String groupId) {
		JSONObject jo = new JSONObject();
		
		try {
			int groupIdInt = StringUtils.getInstance().strToInt(groupId);
			
			if (StringUtils.getInstance().isBlank(userId)) {
				jo.put("code", -1);
				jo.put("text", Tips.NULLUSER.getText());
			} else if (groupIdInt == -1) {
				jo.put("code", -1);
				jo.put("text", Tips.NOSECGROUP.getText());
			} else {
				String[] userIds = StringUtils.getInstance().stringSplit(userId, ",");
				
				Integer[] userIdsInt = StringUtils.getInstance().stringArrToIntArr(userIds);
				
				groupMemberDao.removeGroupMemeber(userIdsInt, groupIdInt);
				
				//通知融云
				RongCloudUtils.getInstance().leftGroup(userIds, groupId);
				
				jo.put("code", 1);
				jo.put("text", Tips.OK.getText());
			}
		} catch (Exception e) {
			jo.put("code", 0);
			jo.put("text", Tips.FAIL.getText());
			e.printStackTrace();
		}
		
		return jo.toString();
	}
	
	@Override
	public String disslovedGroup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String refreshGroup() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String listGroupMemebers() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getGroupList(String userId) {
		JSONObject jo = new JSONObject();
		
		try {
			int userIdInt = StringUtils.getInstance().strToInt(userId);
			
			if (userIdInt == -1) {
				jo.put("code", -1);
				jo.put("text", Tips.NULLUSER.getText());
			} else {
				List<TGroup> groupList = groupDao.getGroupList(userIdInt);
				
				if (groupList != null) {
					JSONArray ja = new JSONArray();
					
					for(int i = 0; i < groupList.size(); i++) {
						JSONObject t = JSONUtils.getInstance().modelToJSONObj(groupList.get(i));
						ja.add(ja);
					}
					
					jo.put("code", 1);
					jo.put("text", ja.toString());
				} else {
					jo.put("code", 0);
					jo.put("text", Tips.FAIL.getText());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jo.toString();	
	}

	private MemberDao memberDao;
	private GroupDao groupDao;
	private GroupMemberDao groupMemberDao;
	
	public MemberDao getMemberDao() {
		return memberDao;
	}

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public GroupMemberDao getGroupMemberDao() {
		return groupMemberDao;
	}

	public void setGroupMemberDao(GroupMemberDao groupMemberDao) {
		this.groupMemberDao = groupMemberDao;
	}

	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

}
