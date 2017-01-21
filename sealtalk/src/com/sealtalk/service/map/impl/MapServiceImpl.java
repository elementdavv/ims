package com.sealtalk.service.map.impl;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sealtalk.common.Tips;
import com.sealtalk.dao.group.GroupMemberDao;
import com.sealtalk.dao.map.MapDao;
import com.sealtalk.model.TGroupMember;
import com.sealtalk.model.TMap;
import com.sealtalk.service.map.MapService;
import com.sealtalk.utils.StringUtils;

public class MapServiceImpl implements MapService {

	@Override
	public String getLocation(String userId, String targetId, String type) {
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		
		if (StringUtils.getInstance().isBlank(userId) || 
				StringUtils.getInstance().isBlank(targetId) ||
				StringUtils.getInstance().isBlank(type)) {
			jo.put("code", -1);
			jo.put("text", Tips.WRONGPARAMS.getText());
		} else {
			boolean status = true;
			int targetIdInt = StringUtils.getInstance().strToInt(targetId);
			
			try {
				if (type.equals("1")) {		//群
					List<TGroupMember>  memberList = groupMemeberDao.listGroupMembers(targetIdInt);
					
					if (memberList != null) {
						int[] ids = new int[memberList.size()];
						
						for(int i = 0; i < memberList.size(); i++) {
							ids[i] = memberList.get(i).getMemberId();
						}
						
						List<Object[]> locations = mapDao.getLocationForGroupId(ids);
						
						if (locations != null) {
							for(int i = 0; i < locations.size(); i++) {
								Object[] ret = locations.get(i);
								JSONObject t = new JSONObject();
								t.put("userID", ret[0]);
								t.put("logo", ret[1]);
								t.put("latitude", ret[2]);
								t.put("longtitude", ret[3]);
								ja.add(t);
								
							}
						} else {
							status = false;
						}
					} else {
						status = false;
					}
				} else {					//人
					Object[] tmap = mapDao.getLocation(targetIdInt);
					
					if (tmap != null) {
						
						JSONObject m = new JSONObject();
						m.put("userID", userId);
						m.put("logo", tmap[0]);
						m.put("latitude", tmap[1]);
						m.put("longtitude", tmap[2]);
						
						ja.add(m);
						jo.put("code", 1);
						jo.put("text", ja.toString());
						
					} else {
						status = false;
					}
				}
				
				if (!status) {
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
	public String subLocation(String userId, String latitude, String longtitude) {
		JSONObject jo = new JSONObject();
		
		if (StringUtils.getInstance().isBlank(userId) ||
				StringUtils.getInstance().isBlank(latitude) ||
				StringUtils.getInstance().isBlank(longtitude)) {
			jo.put("code", 0);
			jo.put("text", Tips.WRONGPARAMS.getText());
		} else {
			try {
				int userIdInt = StringUtils.getInstance().strToInt(userId);
				TMap tm = new TMap();
				
				tm.setUserId(userIdInt);
				tm.setLatitude(latitude);
				tm.setLongitude(longtitude);
				
				mapDao.subLocation(tm);
				jo.put("code", 1);
				jo.put("text", Tips.OK.getText());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return jo.toString();
	}
	
	
	private MapDao mapDao;
	private GroupMemberDao groupMemeberDao;

	public GroupMemberDao getGroupMemeberDao() {
		return groupMemeberDao;
	}

	public void setGroupMemeberDao(GroupMemberDao groupMemeberDao) {
		this.groupMemeberDao = groupMemeberDao;
	}

	public MapDao getMapDao() {
		return mapDao;
	}

	public void setMapDao(MapDao mapDao) {
		this.mapDao = mapDao;
	}

}
