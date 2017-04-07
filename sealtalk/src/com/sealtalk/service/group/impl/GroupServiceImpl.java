package com.sealtalk.service.group.impl;

import io.rong.models.GagGroupUser;
import io.rong.models.GroupInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.sealtalk.common.FunctionName;
import com.sealtalk.common.Tips;
import com.sealtalk.dao.adm.MemberRoleDao;
import com.sealtalk.dao.adm.PrivDao;
import com.sealtalk.dao.adm.RolePrivDao;
import com.sealtalk.dao.fun.DontDistrubDao;
import com.sealtalk.dao.fun.FunctionDao;
import com.sealtalk.dao.group.GroupDao;
import com.sealtalk.dao.group.GroupMemberDao;
import com.sealtalk.dao.member.MemberDao;
import com.sealtalk.model.TDontDistrub;
import com.sealtalk.model.TFunction;
import com.sealtalk.model.TGroup;
import com.sealtalk.model.TGroupMember;
import com.sealtalk.model.TMember;
import com.sealtalk.model.TMemberRole;
import com.sealtalk.model.TPriv;
import com.sealtalk.model.TRolePriv;
import com.sealtalk.service.group.GroupService;
import com.sealtalk.utils.JSONUtils;
import com.sealtalk.utils.PropertiesUtils;
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
				groupIds = StringUtils.getInstance().replaceChar(groupIds, "[", "");
				groupIds = StringUtils.getInstance().replaceChar(groupIds, "]", "");

				ArrayList<String> tempArrIds = new ArrayList<String>();
				
				String[] groupIdsArrSplit = groupIds.split(",");
				
				//去重
				String[] groupIdsArr = StringUtils.getInstance().clearRepeat(groupIdsArrSplit);
				
				for(int i = 0; i < groupIdsArr.length; i++) {
					if (!StringUtils.getInstance().isBlank(groupIdsArr[i])) {
						String id = groupIdsArr[i].trim();
						groupIdsArr[i] = id;
						tempArrIds.add(id);
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
					
					//保存群组成员关系
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
						
						String[] sendGroupIds = {groupId+""};
						String createCGcode = RongCloudUtils.getInstance().createGroup(groupIdsArr, groupId + "", groupNameStr);
						RongCloudUtils.getInstance().sendGroupMsg(userId, sendGroupIds, "请在聊天中注意人身财产安全", "请在聊天中注意人身财产安全", 1, 1, 2);
						
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
				
				groupIds = StringUtils.getInstance().replaceChar(groupIds, "\"", "");
				groupIds = StringUtils.getInstance().replaceChar(groupIds, "[", "");
				groupIds = StringUtils.getInstance().replaceChar(groupIds, "]", "");
				
				String[] groupIdsArr = StringUtils.getInstance().stringSplit(groupIds, ",");
				
				//去除重复添加
				List<TGroupMember> tgm = groupMemberDao.getTGroupMemberList(groupIdInt);
				
				ArrayList<String> groupMemberIds = new ArrayList<String>();
 				
				if (tgm != null) {
					for (int i = 0; i < tgm.size(); i++) {
						groupMemberIds.add(tgm.get(i).getMemberId()+"");
					}
				}
				
				ArrayList<Integer> finalIds = new ArrayList<Integer>();
				
				for(int i = 0; i < groupIdsArr.length; i++) {
					if (!groupMemberIds.contains(groupIdsArr[i])) {
						finalIds.add(Integer.parseInt(groupIdsArr[i].trim()));
					}
				}

				memberVolume = finalIds.size();
					
				if (volumeUse >= volume ||
						(volumeUse + memberVolume) > volume) {
					jo.put("code", 0);
					jo.put("text", Tips.GROUPMOREVOLUME.getText());
				} else {
					Integer[] idsInt = new Integer[finalIds.size()];
					//保存数据库
					ArrayList<TGroupMember> tgmList = new ArrayList<TGroupMember>();
					
					for(int i = 0; i < finalIds.size(); i++) {
						int id = finalIds.get(i);
						
						tgmList.add(new TGroupMember(groupIdInt, id, "0", 0));
						idsInt[i] = id;
					}
					
					groupMemberDao.saveGroupMemeber(tgmList);
					groupDao.updateGroupMemberNum(groupIdInt, memberVolume);
					
					//通知融云
					RongCloudUtils.getInstance().joinGroup(groupIdsArr, groupId, groupName);
					
					//小灰条显示
					String[] groupIdA = {groupId};
					
					List<TMember> memList = memberDao.getMultipleMemberForIds(idsInt);
					
					if (memList != null) {
					
						for (int i = 0; i < memList.size(); i++) {
							TMember tm = memList.get(i);
							String msg = tm.getFullname() + "加入群组";
							String extrMsg = msg;
							RongCloudUtils.getInstance().sendGroupMsg(tm.getId()+"", groupIdA, msg, extrMsg, 1, 1, 2);
						}
					}
						
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
				userId = StringUtils.getInstance().replaceChar(userId, "\"", "");
				userId = StringUtils.getInstance().replaceChar(userId, "[", "");
				userId = StringUtils.getInstance().replaceChar(userId, "]", "");
				String[] userIds = StringUtils.getInstance().stringSplit(userId, ",");
				
				groupMemberDao.removeGroupMemeber(userId, groupIdInt);
				groupDao.updateGroupMemberNum(groupIdInt, -userIds.length);
				
				//通知融云
				RongCloudUtils.getInstance().leftGroup(userIds, groupId);
				
				//小灰条显示
				Integer[] idsInt = new Integer[userIds.length];
				
				for(int i = 0; i < userIds.length;i++) {
					idsInt[i] = Integer.parseInt(userIds[i].trim());
				}
				
				String[] groupIds = {groupId};
				
				List<TMember> memList = memberDao.getMultipleMemberForIds(idsInt);
				
				for (int i = 0; i < memList.size(); i++) {
					TMember tm = memList.get(i);
					String msg = tm.getFullname() + "离开群组";
					String extrMsg = msg;
					RongCloudUtils.getInstance().sendGroupMsg(tm.getId()+"", groupIds, msg, extrMsg, 1, 1, 2);
				}
				
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
			
			if (StringUtils.getInstance().isBlank(userId)) {
				jo.put("code", -1);
				jo.put("text", Tips.NULLUSER.getText());
			} else {
				int userIdInt = StringUtils.getInstance().strToInt(userId);
                List<TGroupMember> groupMembers = groupMemberDao.getGroupMemberForUserId(userIdInt);
                
                if (groupMembers != null) {
                    ArrayList<Integer> temp = new ArrayList<Integer>();
                    StringBuilder sb = new StringBuilder();
                    
                    for(int i = 0; i < groupMembers.size(); i++) {
                    	TGroupMember t = groupMembers.get(i);
                        int id = t.getGroupId().intValue();
                        
                        if(!temp.contains(id)) {
                            sb.append(id).append(",");
                            temp.add(id);
                        }
                    }

                    String groups = sb.toString();
                    
                    if(groups != null) {
                        groups = groups.substring(0, groups.length() - 1);
                    }
                    
                    List<Object[]> groupList = groupDao.getGroupListWithCreaterInfo(groups);
                    List<TDontDistrub> dontDistrubList = dontDistrubDao.getDistrubListForUserId(userIdInt);
                    
                    int lenDistrub = 0;
                    
                    if(dontDistrubList != null) {
                        lenDistrub = dontDistrubList.size();
                    }
                    if(groupList != null) {
                        JSONArray ja = new JSONArray();
                        for(int i = 0; i < groupList.size(); i++)
                        {
                            Object t[] = (Object[])groupList.get(i);
                            JSONObject jo1 = new JSONObject();
                            jo1.put("mid", t[0]);
                            jo1.put("account", t[1]);
                            jo1.put("fullname", t[2]);
                            jo1.put("logo", t[3]);
                            jo1.put("telephone", t[4]);
                            jo1.put("email", t[5]);
                            jo1.put("address", t[6]);
                            jo1.put("token", t[7]);
                            jo1.put("sex", t[8]);
                            jo1.put("birthday", t[9]);
                            jo1.put("workno", t[10]);
                            jo1.put("mobile", t[11]);
                            jo1.put("groupmax", t[12]);
                            jo1.put("groupuse", t[13]);
                            jo1.put("intro", t[14]);
                            jo1.put("GID", t[15]);
                            jo1.put("code", t[16]);
                            jo1.put("name", t[17]);
                            jo1.put("createdate", t[18]);
                            jo1.put("volume", t[19]);
                            jo1.put("volumeuse", t[20]);
                            jo1.put("space", t[21]);
                            jo1.put("spaceuse", t[22]);
                            jo1.put("annexlong", t[23]);
                            jo1.put("notice", t[24]);
                            for(int j = 0; j < lenDistrub; j++)
                            {
                                TDontDistrub tdd = (TDontDistrub)dontDistrubList.get(j);
                                if(t[15].equals(Integer.valueOf(tdd.getId())))
                                    jo1.put("dontdistrub", tdd.getIsOpen());
                                else
                                    jo1.put("dontdistrub", Integer.valueOf(0));
                            }

                            if(dontDistrubList == null)
                                jo1.put("dontdistrub", Integer.valueOf(0));
                            ja.add(jo1);
                        }

                        jo.put("code", Integer.valueOf(1));
                        jo.put("text", ja.toString());
                    } else
                    {
                        jo.put("code", Integer.valueOf(0));
                        jo.put("text", Tips.FAIL.getText());
                    }
                } else
                {
                    jo.put("code", Integer.valueOf(0));
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
				TGroupMember tgm = groupMemberDao.getGroupMemberCreator(groupIdInt);
				
				if (tgm != null) {
					//更新群组成员关系表
					int resut1 = groupMemberDao.transferGroup(userIdInt, groupIdInt, tgm.getId());
					
					if (resut1 > 0) {
						//更新群组表
						int result = groupDao.transferGroup(userIdInt, groupIdInt);
						if (result > 0) {
							//小灰条显示
							String[] groupIds = {groupId};
							TMember tm = memberDao.getMemberForId(userIdInt);
							String msg = "管理员已变更为" + tm.getFullname();
							String extrMsg = msg;
							RongCloudUtils.getInstance().sendGroupMsg(tm.getId()+"", groupIds, msg, extrMsg, 1, 1, 2);
							
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
				//int id = StringUtils.getInstance().strToInt(groupId);
				
				//TGroup t = groupDao.groupInfo(id);
				
				 int id = StringUtils.getInstance().strToInt(groupId);
	                Object t[] = groupDao.groupInfo(id);
	                JSONObject jo1 = new JSONObject();
	                jo1.put("mid", t[0]);
	                jo1.put("account", t[1]);
	                jo1.put("fullname", t[2]);
	                jo1.put("logo", t[3]);
	                jo1.put("telephone", t[4]);
	                jo1.put("email", t[5]);
	                jo1.put("address", t[6]);
	                jo1.put("token", t[7]);
	                jo1.put("sex", t[8]);
	                jo1.put("birthday", t[9]);
	                jo1.put("workno", t[10]);
	                jo1.put("mobile", t[11]);
	                jo1.put("groupmax", t[12]);
	                jo1.put("groupuse", t[13]);
	                jo1.put("intro", t[14]);
	                jo1.put("GID", t[15]);
	                jo1.put("code", t[16]);
	                jo1.put("name", t[17]);
	                jo1.put("createdate", t[18]);
	                jo1.put("volume", t[19]);
	                jo1.put("volumeuse", t[20]);
	                jo1.put("space", t[21]);
	                jo1.put("spaceuse", t[22]);
	                jo1.put("annexlong", t[23]);
	                jo1.put("notice", t[24]);
	                jo.put("code", Integer.valueOf(1));
	                jo.put("text", jo1.toString());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jo.toString();
	}
	
	@Override
	public String groupListWithAction(String userId) {
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
                    StringBuilder sb = new StringBuilder();
                    
                    for(int i = 0; i < groupMembers.size(); i++) {
                    	TGroupMember t = groupMembers.get(i);
                        int id = t.getGroupId().intValue();
                        
                        if(!temp.contains(id)) {
                            sb.append(id).append(",");
                            temp.add(id);
                        }
                    }

                    String groups = sb.toString();
                    
                    if(groups != null) {
                        groups = groups.substring(0, groups.length() - 1);
                    }
                    
                    List<Object[]> groupList = groupDao.getGroupListWithCreaterInfo(groups);
                    List<TDontDistrub> dontDistrubList = dontDistrubDao.getDistrubListForUserId(userIdInt);
                    
                    int lenDistrub = 0;
                    
                    if(dontDistrubList != null) {
                        lenDistrub = dontDistrubList.size();
                    }
                    if(groupList != null) {
                        JSONArray ja = new JSONArray();
                        JSONArray ja1 = new JSONArray();
                        
                        for(int i = 0; i < groupList.size(); i++)
                        {
                            Object t[] = (Object[])groupList.get(i);
                            JSONObject jo1 = new JSONObject();
                            System.out.println(i + "-" + t[0]);
                            if (t[0] == null) {
                            	System.out.println(i + "-" + t[0]);
                            	continue;
                            }
                            jo1.put("MID", t[0]);
                            jo1.put("account", t[1]);
                            jo1.put("fullname", t[2]);
                            jo1.put("logo", t[3]);
                            jo1.put("telephone", t[4]);
                            jo1.put("email", t[5]);
                            jo1.put("address", t[6]);
                            jo1.put("token", t[7]);
                            jo1.put("sex", t[8]);
                            jo1.put("birthday", t[9]);
                            jo1.put("workno", t[10]);
                            jo1.put("mobile", t[11]);
                            jo1.put("groupmax", t[12]);
                            jo1.put("groupuse", t[13]);
                            jo1.put("intro", t[14]);
                            jo1.put("GID", t[15]);
                            jo1.put("code", t[16]);
                            jo1.put("name", t[17]);
                            jo1.put("createdate", t[18]);
                            jo1.put("volume", t[19]);
                            jo1.put("volumeuse", t[20]);
                            jo1.put("space", t[21]);
                            jo1.put("spaceuse", t[22]);
                            jo1.put("annexlong", t[23]);
                            jo1.put("notice", t[24]);
                            for(int j = 0; j < lenDistrub; j++)
                            {
                                TDontDistrub tdd = (TDontDistrub)dontDistrubList.get(j);
                                if(t[15].equals(Integer.valueOf(tdd.getId())))
                                    jo1.put("dontdistrub", tdd.getIsOpen());
                                else
                                    jo1.put("dontdistrub", Integer.valueOf(0));
                            }
                            if(dontDistrubList == null)
                                jo1.put("dontdistrub", Integer.valueOf(0));
                            if (jo1.getString("MID").equals(userId)) {
                            	ja.add(jo1);		//我创建的
                            } else {
                            	ja1.add(jo1);		//我加入的
                            }
                        }
                        JSONObject type = new JSONObject();
                        type.put("ICreate", ja.toString());
                        type.put("IJoin", ja1.toString());

                        jo.put("code", Integer.valueOf(1));
                        jo.put("text", type.toString());
                    } else
                    {
                        jo.put("code", Integer.valueOf(0));
                        jo.put("text", Tips.FAIL.getText());
                    }
                } else
                {
                    jo.put("code", Integer.valueOf(0));
                    jo.put("text", Tips.FAIL.getText());
                }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jo.toString();	
	}

	@Override
	public String listGroupMemebersData(String groupId) {
		JSONObject jo = new JSONObject();
		
		try {
			int groupIdInt = StringUtils.getInstance().strToInt(groupId);
			
			if (groupIdInt == -1) {
				jo.put("code", -1);
				jo.put("text", Tips.NOSECGROUP.getText());
			} else {
				List<TGroupMember> groupMember = groupMemberDao.listGroupMembers(groupIdInt);
				Integer[] ids = null;
				JSONArray ja = new JSONArray();
				
				if (groupMember != null && groupMember.size() > 0) {
					 ids = new Integer[groupMember.size()];
					for(int i = 0; i < groupMember.size(); i++) {
						int memberId = groupMember.get(i).getMemberId();
						ids[i] = memberId;
					}
					
					List<TMember> memberList = memberDao.getMultipleMemberForIds(ids);
					
					if (memberList != null && memberList.size() > 0) {
						for(int i = 0; i < memberList.size(); i++) {
							TMember t = memberList.get(i);
							JSONObject memberObj = JSONUtils.getInstance().modelToJSONObj(t);
							memberObj.remove("password");
							ja.add(memberObj);
						}
					}
					
					//获取管理群组的权限 
					String[] groupManager = {"qzcjq", "qzjsq", "qzxgqcjz"};
					List<TPriv> tps = privDao.getPrivByUrl(groupManager);
					Map<Integer, String> memGroupPriv = new HashMap<Integer, String>();
					
					if(tps != null && tps.size() == 3) {
						Integer[] privIds = new Integer[tps.size()];
						for(int i = 0; i < tps.size(); i++) {
							privIds[i]  = Integer.valueOf(tps.get(i).getId());
						}
						//获取权限对应的角色
						List<TRolePriv> rolePrivList = rolePrivDao.getRolePrivsByPrivs(privIds);
						
						//所有的符合条件的角色id
						List<Integer> roleIds = new ArrayList<Integer>();
						
						if(rolePrivList != null) {
							
							for(int i = 0; i < rolePrivList.size(); i++) {
								TRolePriv tp = rolePrivList.get(i);
								if (!roleIds.contains(tp.getRoleId())) {
									roleIds.add(tp.getRoleId());
								}
							}
							
							//获取成员的角色
							List<TMemberRole> roleList = memberRoleDaoImpl.getRolesForIds(ids);
							
							if (roleList != null) {
								for(int i = 0; i < roleList.size(); i++) {
									TMemberRole tr = roleList.get(i);
									String status = "false";
									
									if(roleIds.contains(tr.getRoleId())) {
										status = "true";
									}
									memGroupPriv.put(tr.getMemberId(), status);
								}
							}
						}
						
					
						for(int i = 0; i < ja.size(); i++) {
							JSONObject mem = ja.getJSONObject(i);
							int id = mem.getInt("id");
							boolean status = false;
							
							for(Map.Entry<Integer, String> m: memGroupPriv.entrySet()) {
								if (m.getKey() == id) {
									mem.put("qzqx", m.getValue());
									status = true;
									break;
								} 
							}
							if (!status) {
								mem.put("qzqx", "false");
							}
						}
					}
					
					
					/*废弃代码
					if(tps != null && tps.size() == 3) {
						int[] privIds = new int[tps.size()];
						for(int i = 0; i < tps.size(); i++) {
							privIds[i]  = Integer.valueOf(tps.get(i).getId());
						}
						
						List memPriv = privDao.getMemberByPrivId(privIds);
						for(int i = 0; i < ja.size(); i++) {
							JSONObject mem = ja.getJSONObject(i);
							if (memPriv == null) {
								mem.put("qzqx", "false");
								continue ;
							}
							boolean f = false;
							
							for (int j = 0; j < memPriv.size(); j++) {
								Object[] priv = (Object[]) memPriv.get(j);
								if (mem.getString("id").equals(String.valueOf(priv[0])) && priv[1] != null) {
									mem.put("qzqx", true);
									f = true;
									break;
								}
							}
							if (f == false) {
								mem.put("qzqx", "false");
							}
						}
					}*/
				}
				
				jo.put("code", 1);
				jo.put("text", ja.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jo.toString();
	}
	

	@Override
	public String changeGroupName(String groupId, String groupName) {
		JSONObject jo = new JSONObject();
		
		try {
			int grouIdInt = StringUtils.getInstance().strToInt(groupId);
			int ret = groupDao.changeGroupName(grouIdInt, groupName);
			
			if (ret > 0) {
				jo.put("code", 1);
				jo.put("text", Tips.OK.getText());
			} else {
				jo.put("code", 0);
				jo.put("text", Tips.FAIL.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return jo.toString();
	}
	

	@Override
	public String shutUpGroup(String userId, String groupId) {
		JSONObject jo = new JSONObject();
		
		if (StringUtils.getInstance().isBlank(groupId)) {
			jo.put("code", -1);
			jo.put("text", Tips.WRONGPARAMS.getText());
		} else {
			try {
				int groupIdInt = StringUtils.getInstance().strToInt(groupId);
				ArrayList<String> userList = new ArrayList<String>();
				
				if (!StringUtils.getInstance().isBlank(userId)) {
					userList.add(userId);
				} else {
					List<TGroupMember> groupMemList = groupMemberDao.getTGroupMemberList(groupIdInt);
					if (groupMemList != null) {
						for(int i = 0; i < groupMemList.size(); i++) {
							TGroupMember tm = groupMemList.get(i);
							if (!tm.getIsCreator().equals("1")) {		//去除群主
								userList.add(tm.getMemberId()+"");
							}
						}
					}
				}
				
				System.out.println("---------shutUpGroup-------------: " + userList.toString());
				
				String shutUpTime = PropertiesUtils.getStringByKey("group.shutuptime");
				ArrayList<String> codeList = new ArrayList<String>();
				
				for(int i = 0; i < userList.size(); i++) {
					String id = userList.get(i);
					String code = RongCloudUtils.getInstance().shutUpGroup(id, groupId, shutUpTime);
					codeList.add(code);
				}
				
				//服务器主动记录状态	
				String name =(new StringBuilder(groupId).append("_").append(FunctionName.SHUTUP.getName())).toString();
				TFunction tf = functionDao.getFunctionStatus(name);
				if (tf != null) {
					functionDao.updateFunctionStatus(name, "1");
				} else {
					tf = new TFunction();
					tf.setIsOpen("1");
					tf.setListorder(0);
					tf.setName((new StringBuilder(groupId).append("_").append(FunctionName.SHUTUP.getName())).toString());
					functionDao.setFunctionStatus(tf);
				}
				
				TFunction tf1 = functionDao.getFunctionStatus(name);
				if (tf1.getIsOpen().equals("1")) {
				//if (codeList.size() == userList.size()) {
					jo.put("code", 1);
					jo.put("text", Tips.OK.getText());
				} else {
					jo.put("code", 0);
					jo.put("text", Tips.FAIL.getText());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return jo.toString();
	}
	
	@Override
	public String unShutUpGroup(String userId, String groupId) {
		JSONObject jo = new JSONObject();
		
		try {
			if (StringUtils.getInstance().isBlank(groupId)) {
				jo.put("code", -1);
				jo.put("text", Tips.WRONGPARAMS.getText());
			} else {
				int groupIdInt = StringUtils.getInstance().strToInt(groupId);
				ArrayList<String> userList = new ArrayList<String>();
				
				if (StringUtils.getInstance().isBlank(userId)) {
					List<TGroupMember> groupMemList = groupMemberDao.getTGroupMemberList(groupIdInt);
					
					if (groupMemList != null) {
						for(int i = 0; i < groupMemList.size(); i++) {
							userList.add(groupMemList.get(i).getMemberId()+"");
						}
					}
				} else {
					userList.add(userId);
				}
				
				System.out.println("---------shutUpGroup-------------: " + userList.toString());
				
				ArrayList<String> codeList = new ArrayList<String>();
				
				String[] userIds = (String[]) userList.toArray(new String[userList.size()]);
				
				for(int i = 0; i < userList.size(); i++) {
					String code = RongCloudUtils.getInstance().unShutUpGroup(userIds, groupId);
					codeList.add(code);
				}
				
				//删除禁言状态
				String name =(new StringBuilder(groupId).append("_").append(FunctionName.SHUTUP.getName())).toString();
				TFunction tf = functionDao.getFunctionStatus(name);
				if (tf != null) {
					functionDao.updateFunctionStatus(name, "0");
				}
				
				if (codeList.size() == userList.size()) {
					jo.put("code", 1);
					jo.put("text", Tips.OK.getText());
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
	public String getShutUpGroupStatus(String groupId) {
		JSONObject result = new JSONObject();
		
		try {
			if (StringUtils.getInstance().isBlank(groupId)) {
				result.put("code", -1);
				result.put("text", Tips.WRONGPARAMS.getText());
			} else {
				String name =(new StringBuilder(groupId).append("_").append(FunctionName.SHUTUP.getName())).toString();
				TFunction tf = functionDao.getFunctionStatus(name);
				String status = "0";
				
				if (tf != null) {
					status = tf.getIsOpen();
				}
				result.put("code", 1);
				result.put("text", status);
				
			/*	List<GagGroupUser> memList = RongCloudUtils.getInstance().getShutUpGroupMember(groupId);
				boolean status = false;
				
				if (memList != null && memList.size() > 0) {
					status = true;
				}
				
				result.put("code", 1);
				result.put("text", status);
				*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result.toString();
	}

	@Override
	public String getShutUpGroupMember(String groupId) {
		JSONObject result = new JSONObject();
		
		try {
			if (StringUtils.getInstance().isBlank(groupId)) {
				result.put("code", -1);
				result.put("text", Tips.WRONGPARAMS.getText());
			} else {
				List<GagGroupUser> memList = RongCloudUtils.getInstance().getShutUpGroupMember(groupId);
				
				if (memList == null) {
					result.put("code", 0);
					result.put("text", Tips.NULLGROUPMEMBER.getText());
				} else {
					JSONArray ja = new JSONArray();
					for(int i = 0; i < memList.size(); i++) {
						GagGroupUser ggu = memList.get(i);
						JSONObject jo = new JSONObject();
						
						jo.put("userId", ggu.getUserId());
						jo.put("time", ggu.getTime());
						ja.add(jo);
					}
					result.put("code", 1);
					result.put("text", ja.toString());
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result.toString();
	}


	@Override
	public String getGroupOnLineMember(String userId) {
		JSONObject result = new JSONObject();
		
		try {
			if (StringUtils.getInstance().isBlank(userId)) {
				result.put("code", -1);
				result.put("text", Tips.WRONGPARAMS.getText());
			} else {
				int userIdInt = StringUtils.getInstance().strToInt(userId);
				//获取用户的所有群组
				List<TGroupMember> groupMemList = groupMemberDao.getGroupMemberForUserId(userIdInt);
				boolean f = true;
				
				if (groupMemList != null) {
					int len = groupMemList.size();
					Integer[] groupIds = new Integer[len];
					
					for(int i = 0; i < len; i++) {
						TGroupMember tm = groupMemList.get(i);
						groupIds[i] = tm.getGroupId();
					}
					
					ArrayList<String> userIds = new ArrayList<String>();
					
					//获取所有群组的所有成员
					List<TGroupMember> groupMems = groupMemberDao.getGroupMemberByGroupIds(groupIds);
					
					if (groupMems != null) {
						for(int i = 0; i < groupMems.size(); i++) {
							TGroupMember tm = groupMems.get(i);
							int memberId = tm.getMemberId();
							
							if (!userIds.contains(memberId+"")) {
								userIds.add(memberId+"");
							}
						}

						//各成员在线状态
						Map<String, String> statusMap = new HashMap<String, String>();
						
						for (int i = 0; i < userIds.size(); i++) {
							String id = userIds.get(i);
							String status = RongCloudUtils.getInstance().checkOnLine(userIds.get(i));
							statusMap.put(id, status );
						}
						
						Map<String, Integer> countMap = new HashMap<String, Integer>();			//在线人数
						Map<String, Integer> countMembers = new HashMap<String, Integer>();		//群组总人数
						
						//用来判断重复
						ArrayList<String> ids = new ArrayList<String>();	
						int count = 0;
						int countM = 0;
						
						for (int i = 0; i < groupMems.size(); i++) {
							TGroupMember tm = groupMems.get(i);
							String memberId = tm.getMemberId()+"";
							String groupId = tm.getGroupId()+"";
							String doubleId = memberId+"_"+groupId;
							
							if (countMap.get(groupId) != null) {
								count = countMap.get(groupId);
							} else {
								count = 0;
							}
							
							if (countMembers.get(groupId) != null) {
								countM = countMembers.get(groupId);
							} else {
								countM = 0;
							}
							
							if (ids.contains(doubleId)) continue;
							if (statusMap.get(memberId).equals("1")) {
								count += 1;
								countMap.put(groupId, count);
							}
							countM += 1;
							countMembers.put(groupId, countM);
							ids.add(doubleId);
						}
						
						JSONObject jo = new JSONObject();
						
						//生成返回结果
						for(Map.Entry<String, Integer> ct: countMembers.entrySet()) {
							int[] s = {0, 0};
							if (countMap.containsKey(ct.getKey())) {
								s[0] = countMap.get(ct.getKey());
							}
							s[1] = ct.getValue();
							jo.put(ct.getKey(), s);
						}
						
						result.put("code", 1);
						result.put("text", jo.toString());
					} else {
						f = false;
						result.put("text", Tips.NULLGROUPMEMBER.getText());
					}
				} else {
					f = false;
					result.put("text", Tips.NULLGROUP.getText());
				}
				
				if (!f) {
					result.put("code", 0);
				}
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}
		
		return result.toString();
	}
	
	private MemberDao memberDao;
	private GroupDao groupDao;
	private GroupMemberDao groupMemberDao;
	private DontDistrubDao dontDistrubDao;
	private PrivDao privDao;
	private MemberRoleDao memberRoleDaoImpl;
	private RolePrivDao rolePrivDao;
	private FunctionDao functionDao;
	
	public FunctionDao getFunctionDao() {
		return functionDao;
	}

	public void setFunctionDao(FunctionDao functionDao) {
		this.functionDao = functionDao;
	}

	public RolePrivDao getRolePrivDao() {
		return rolePrivDao;
	}

	public void setRolePrivDao(RolePrivDao rolePrivDao) {
		this.rolePrivDao = rolePrivDao;
	}

	public MemberRoleDao getMemberRoleDaoImpl() {
		return memberRoleDaoImpl;
	}

	public void setMemberRoleDaoImpl(MemberRoleDao memberRoleDaoImpl) {
		this.memberRoleDaoImpl = memberRoleDaoImpl;
	}

	public PrivDao getPrivDao() {
		return privDao;
	}

	public void setPrivDao(PrivDao privDao) {
		this.privDao = privDao;
	}

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
