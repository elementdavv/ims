package com.sealtalk.service.group.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.sealtalk.common.Tips;
import com.sealtalk.dao.group.GroupDao;
import com.sealtalk.dao.group.GroupMemberDao;
import com.sealtalk.model.TGroup;
import com.sealtalk.model.TGroupMember;
import com.sealtalk.service.group.GroupService;
import com.sealtalk.utils.JSONUtils;
import com.sealtalk.utils.RongCloudUtils;
import com.sealtalk.utils.StringUtils;

public class GroupServiceImpl implements GroupService {

	/**
	 * 业务复杂，自己看吧
	 */
	@Override
	public String createGroup(String userId, String groupIds, String groupName) {
		JSONObject jo = new JSONObject();
		String result = null;
		
		try {
			if (StringUtils.getInstance().isBlank(userId)) {
				jo.put("code", -1);
				jo.put("text", Tips.NULLUSER.getText());
			} else if(StringUtils.getInstance().isBlank(groupIds)) {
				jo.put("code", -1);
				jo.put("text", Tips.NULLGROUPMEMBER.getText());
			} else if (StringUtils.getInstance().isBlank(groupName)) {
				jo.put("code", -1);
				jo.put("text", Tips.NULLGROUPNAME.getText());
			} else {
				int userIdInt = Integer.parseInt(userId);
				//创建群组
				String code = groupDao.createGroup(userIdInt, groupName);
			
				//查找群组id
				TGroup tg = groupDao.getGroupForIdAndCode(userId, code);
				
				if (tg != null) {
					int groupId = tg.getId();
					
					//保存群组成员关系
					if (groupIds.startsWith("[") && groupIds.endsWith("]")) {
						groupIds = groupIds.substring(1, groupIds.length() - 1);
					}
					
					String[] groupIdsArr = groupIds.split(",");
					
					ArrayList<TGroupMember> tgmList = new ArrayList<TGroupMember>();
					
					for(int i = 0; i < groupIdsArr.length; i++) {
						String flag = "0";
						int id = Integer.parseInt(groupIdsArr[i]);
						
						flag = (id == userIdInt) ? "1" : "0";
						
						tgmList.add(new TGroupMember(groupId, id, flag, 0));
					}
					
					groupMemberDao.saveGroupMemeber(tgmList);
					
					//查询成员关系是否正确
					List<TGroupMember> tgmMember = groupMemberDao.getTGroupMemberList(groupId);
					List<String> tgmIds = new ArrayList<String>();
					List<String> delIds = new ArrayList<String>();
					List<String> notDelIds = new ArrayList<String>();
					
					String[] delIdsArray = null;
					String[] sendRCIds = null;
					       
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
						
						RongCloudUtils.getInstance().createGroup(groupIdsArr, groupId + "", groupName);
						jo.put("code", 200);
						jo.put("text", JSONUtils.getInstance().modelToJSONObj(tg));
					} else {
						groupDao.removeGroup(tg);
						jo.put("code", 0);
						jo.put("text", Tips.NULLGROUPMEMBER.getText());
					}
					
				} else {
					jo.put("code", 0);
					jo.put("text", "fail");
				}
				
			}
			result = jo.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return result;
	}

	@Override
	public String disslovedGroup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String joinGroup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String leftGroup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String listGroupMemebers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String refreshGroup() {
		// TODO Auto-generated method stub
		return null;
	}

	private GroupDao groupDao;
	private GroupMemberDao groupMemberDao;
	
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
