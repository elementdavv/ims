package com.sealtalk.service.group.impl;

import io.rong.models.GroupInfo;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.sealtalk.common.Tips;
import com.sealtalk.dao.fun.DontDistrubDao;
import com.sealtalk.dao.group.GroupDao;
import com.sealtalk.dao.group.GroupMemberDao;
import com.sealtalk.dao.member.MemberDao;
import com.sealtalk.model.TDontDistrub;
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
			
				groupIds = StringUtils.getInstance().replaceChar(groupIds, "\"", "");
				//保存群组成员关系
				groupIds = groupIds.substring(1, groupIds.length() - 1);

				ArrayList<String> tempArrIds = new ArrayList<String>();
				
				String[] groupIdsArr = groupIds.split(",");
				
				for(int i = 0; i < groupIdsArr.length; i++) {
					if (!StringUtils.getInstance().isBlank(groupIdsArr[i])) {
						tempArrIds.add(groupIdsArr[i]);
					}
				}  
				
				int idsLen = groupIdsArr.length;
				/*
				if (tempArrIds.contains(userId)) {
					idsLen = groupIdsArr.length;
				} else {
					tempArrIds.add(userId);
					idsLen = groupIdsArr.length + 1;
				}*/
				
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
					
					int groupId = groupDao.createGroup(userIdInt, code, groupNameStr, memberList.size());
					
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
						
						String createCGcode = RongCloudUtils.getInstance().createGroup(groupIdsArr, groupId + "", groupNameStr);
						RongCloudUtils.getInstance().sendSysMsg(userId, groupIdsArr, "你创建了群组", "");
						
						jo.put("code", createCGcode);
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
				
				int volumeUse = tg.getVolumeuse();
				int volume = tg.getVolume();
				int memberVolume = 0;
			
				String groupName = tg.getName();
				
				String[] groupIdsArr = StringUtils.getInstance().stringSplit(groupIds, ",");

				memberVolume = groupIdsArr.length;
					
				if (volumeUse >= volume ||
						(volume + memberVolume) > volume) {
					jo.put("code", 0);
					jo.put("text", Tips.GROUPMOREVOLUME.getText());
				} else {
					//保存数据库
					ArrayList<TGroupMember> tgmList = new ArrayList<TGroupMember>();
					
					for(int i = 0; i < groupIdsArr.length; i++) {
						
						int id = StringUtils.getInstance().strToInt(groupIdsArr[i]);
						
						tgmList.add(new TGroupMember(groupIdInt, id, "0", 0));
					}
					
					groupMemberDao.saveGroupMemeber(tgmList);
					groupDao.updateGroupMemberNum(groupIdInt, memberVolume);
					
					//通知融云
					RongCloudUtils.getInstance().joinGroup(groupIdsArr, groupId, groupName);
					RongCloudUtils.getInstance().sendSysMsg(groupId, groupIdsArr, "加入群组", "");
					jo.put("code", 1);
					jo.put("text", Tips.OK.getText());
				}
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
				groupDao.updateGroupMemberNum(groupIdInt, userIds.length);
				
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
	public String dissLovedGroup(String userId, String groupId) {
		JSONObject jo = new JSONObject();
		
		try {
			int userIdInt = StringUtils.getInstance().strToInt(userId);
			int groupIdInt = StringUtils.getInstance().strToInt(groupId);
			
			if (userIdInt == -1) {
				jo.put("code", -1);
				jo.put("text", Tips.NULLUSER.getText());
			} else if (groupIdInt == -1) {
				jo.put("code", -1);
				jo.put("text", Tips.NOSECGROUP.getText());
			} else {
				int count = groupMemberDao.getGroupMemberCountForGoupId(groupId);
				int delNum = groupMemberDao.removeGroupMember(groupIdInt);
				
				if (count == delNum) {
					int delGroupNum = groupDao.removeGroupForGroupId(groupId);
					
					if (delGroupNum > 0) {
						jo.put("code", 1);
						jo.put("text", Tips.OK.getText());
						RongCloudUtils.getInstance().dissLoveGroup(userId, groupId);
					} else {
						jo.put("code", 0);
						jo.put("text", Tips.FAIL.getText());
					}
				} else {
					jo.put("code", 0);
					jo.put("text", Tips.NOTCLEARALLMEMBER.getText());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jo.toString();
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
				List<TGroupMember> groupMembers = groupMemberDao.getGroupMemberForUserId(userIdInt);
				
				if (groupMembers != null) {
					ArrayList<Integer> temp = new ArrayList<Integer>();
					
					Integer[] groups = new Integer[groupMembers.size()];
					
					for(int i = 0; i < groupMembers.size(); i++) {
						int id = groupMembers.get(i).getGroupId();
						if (temp.contains(id)) continue ;
						groups[i] = id;
					}
					
					List<TGroup> groupList = groupDao.getGroupList(groups);
					List<TDontDistrub> dontDistrubList = dontDistrubDao.getDistrubListForUserId(userIdInt);
					
					int lenDistrub = 0;
					
					if (dontDistrubList != null) {
						lenDistrub = dontDistrubList.size();
					} 
					
					if (groupList != null) {
						JSONArray ja = new JSONArray();
						
						for(int i = 0; i < groupList.size(); i++) {
							TGroup tp = groupList.get(i);
							JSONObject t = JSONUtils.getInstance().modelToJSONObj(tp);
							
							for(int j = 0; j < lenDistrub; j++) {
								TDontDistrub tdd = dontDistrubList.get(j);
								if (tp.getId() == tdd.getId()) {
									t.put("dontdistrub", tdd.getIsOpen());
								} else {
									t.put("dontdistrub", 0);
								}
							}
							
							if (dontDistrubList == null) {
								t.put("dontdistrub", 0);
							}
							ja.add(t);
						}
						
						jo.put("code", 1);
						jo.put("text", ja.toString());
					} else {
						jo.put("code", 0);
						jo.put("text", Tips.FAIL.getText());
					}
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
	
	@Override
	public String syncUserGroup(String userId) {
		JSONObject jo = new JSONObject();
		
		try {
			int userIdInt = StringUtils.getInstance().strToInt(userId);
			
			if (userIdInt == -1) {
				jo.put("code", -1);
				jo.put("text", Tips.NULLUSER.getText());
			} else {
				List<TGroupMember> groupMembers = groupMemberDao.getGroupMemberForUserId(userIdInt);
				ArrayList<Integer> temp = new ArrayList<Integer>();
				
				Integer[] groups = new Integer[groupMembers.size()];
				
				for(int i = 0; i < groupMembers.size(); i++) {
					int id = groupMembers.get(i).getGroupId();
					if (temp.contains(id)) continue ;
					groups[i] = id;
				}
				
				List<TGroup> groupList = groupDao.getGroupList(groups);
				
				if (groupList != null) {
					GroupInfo[] gi = new GroupInfo[groupList.size()];
					
					for(int i = 0; i < groupList.size(); i++) {
						TGroup t = groupList.get(i);
						
						gi[i] = new GroupInfo(t.getId() + "", t.getName());
					}
					String code = RongCloudUtils.getInstance().syncGroup(gi, userId);
					jo.put("code", code);
					jo.put("text", code.equals("200") ? Tips.OK.getText() : Tips.FAIL.getText());
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
	

	@Override
	public String transferGroup(String userId, String groupId) {
		JSONObject jo = new JSONObject();
		
		try {
			int userIdInt = StringUtils.getInstance().strToInt(userId);
			int groupIdInt = StringUtils.getInstance().strToInt(groupId);
			boolean b = true;
			
			if (userIdInt == -1) {
				jo.put("code", -1);
				jo.put("text", Tips.NULLUSER.getText());
			} else if (groupIdInt == -1) {
				jo.put("code", -1);
				jo.put("text", Tips.NOSECGROUP.getText());
			} else {
				TGroupMember tgm = groupMemberDao.getGroupMemberCreator(groupId);
				
				if (tgm != null) {
					//更新群组成员关系表
					int resut1 = groupMemberDao.transferGroup(userIdInt, groupIdInt, tgm.getId());
					
					if (resut1 > 0) {
						//更新群组表
						int result = groupDao.transferGroup(userIdInt, groupIdInt);
						if (result > 0) {
							jo.put("code", 1);
							jo.put("text", Tips.OK.getText());
						} else {
							b = false;
						}
					} else {
						b = false;
					}
				} else {
					b = false;
				}
				
			}
			
			if (!b) {
				jo.put("code", 0);
				jo.put("text", Tips.FAIL.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jo.toString();	
	}
	
	@Override
	public String listGroupMembers(String groupId) {
		JSONObject jo = new JSONObject();
		
		try {
			int groupIdInt = StringUtils.getInstance().strToInt(groupId);
			
			if (groupIdInt == -1) {
				jo.put("code", -1);
				jo.put("text", Tips.NOSECGROUP.getText());
			} else {
				List<TGroupMember> groupMember = groupMemberDao.listGroupMembers(groupIdInt);
				int[] ids = new int[groupMember.size()];
				
				for(int i = 0; i < groupMember.size(); i++) {
					ids[i] = groupMember.get(i).getMemberId();
				}
				jo.put("code", 1);
				jo.put("text", ids);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jo.toString();
	}
	
	@Override
	public String refreshGroup(String groupId, String groupName) {
		JSONObject jo = new JSONObject();
		
		try {
			int groupIdInt = StringUtils.getInstance().strToInt(groupId);
			
			if (groupIdInt == -1) {
				jo.put("code", -1);
				jo.put("text", Tips.NOSECGROUP.getText());
			} else if (StringUtils.getInstance().isBlank(groupName)) {
				jo.put("code", -1);
				jo.put("text", Tips.NULLGROUPNAME.getText());
			} else {
				int result = groupDao.changeGroupName(groupIdInt, groupName);
				
				if (result > 0) {
					RongCloudUtils.getInstance().refreshGroup(groupId, groupName);
					jo.put("code", 1);
					jo.put("text", Tips.OK.getText());
				} else {
					jo.put("code", 1);
					jo.put("text", Tips.FAIL.getText());
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jo.toString();
	}
	
	@Override
	public String manageGroupMem(String groupId, String groupIds) {
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
				
				int volume = tg.getVolume();
				int memberVolume = 0;
			
				groupIds = StringUtils.getInstance().replaceChar(groupIds, "\"", "");
				groupIds = StringUtils.getInstance().replaceChar(groupIds, "[", "");
				groupIds = StringUtils.getInstance().replaceChar(groupIds, "]", "");
				String[] groupIdsArr = StringUtils.getInstance().stringSplit(groupIds, ",");
				ArrayList<Integer> grouIdsListInt = (ArrayList<Integer>) StringUtils.getInstance().stringArrToListInt(groupIdsArr);

				memberVolume = groupIdsArr.length;
					
				if (memberVolume >= volume) {
					jo.put("code", 0);
					jo.put("text", Tips.GROUPMOREVOLUME.getText());
				} else {
					//获取群组成员
					List<TGroupMember> groupMember = groupMemberDao.listGroupMembers(groupIdInt);
					List<Integer> dbGroupMemIds = new ArrayList<Integer>();
					//要增加的id
					List<Integer> needAddIds = new ArrayList<Integer>();		
					//要删除的id
					List<Integer> needDelIds = new ArrayList<Integer>();
					
					//所有现有群组id
					for(int i = 0; i < groupMember.size(); i++) {
						dbGroupMemIds.add(groupMember.get(i).getMemberId());
					}
					
					for (int i = 0; i < grouIdsListInt.size(); i++) {
						if (!dbGroupMemIds.contains(grouIdsListInt.get(i))) {
							needAddIds.add(grouIdsListInt.get(i));
						}
					}
					
					for (int i = 0; i < dbGroupMemIds.size(); i++) {
						if (!grouIdsListInt.contains(dbGroupMemIds.get(i))) {
							needDelIds.add(dbGroupMemIds.get(i));
						}
					}
					
					String needDelStr = needDelIds.toString();
					
					if (!StringUtils.getInstance().isBlank(needDelStr)) {
						needDelStr = needDelStr.substring(1, needDelStr.length() -1);
					}
					
					//删除多余数据
					if (needDelIds.size() > 0) {
						groupMemberDao.delGroupMemberForMemberIdsAndGroupId(groupIdInt, needDelStr);
					}
					//保存新增数据 
					
					ArrayList<TGroupMember> tgmList = new ArrayList<TGroupMember>();
					
					for(int i = 0; i < needAddIds.size(); i++) {
						tgmList.add(new TGroupMember(groupIdInt, needAddIds.get(i), "0", 0));
					}
					
					if (tgmList.size() > 0) {
						groupMemberDao.saveGroupMemeber(tgmList);
					}
					
					groupDao.updateGroupMemberNum(groupIdInt, needAddIds.size() - needDelIds.size());
					
					//通知融云
					
					jo.put("code", 1);
					jo.put("text", Tips.OK.getText());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jo.toString();
	}
	
	@Override
	public String groupInfo(String groupId) {
		JSONObject jo = new JSONObject();
		
		try {
			if (StringUtils.getInstance().isBlank(groupId)) {
				jo.put("code", -1);
				jo.put("text", Tips.NOSECGROUP.getText());
			} else {
				int id = StringUtils.getInstance().strToInt(groupId);
				
				TGroup t = groupDao.groupInfo(id);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jo.toString();
	}


	private MemberDao memberDao;
	private GroupDao groupDao;
	private GroupMemberDao groupMemberDao;
	private DontDistrubDao dontDistrubDao;
	
	public DontDistrubDao getDontDistrubDao() {
		return dontDistrubDao;
	}

	public void setDontDistrubDao(DontDistrubDao DontDistrubDao) {
		this.dontDistrubDao = DontDistrubDao;
	}

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
