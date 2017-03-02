package com.sealtalk.service.map.impl;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sealtalk.common.Tips;
import com.sealtalk.dao.friend.FriendDao;
import com.sealtalk.dao.group.GroupMemberDao;
import com.sealtalk.dao.map.MapDao;
import com.sealtalk.dao.member.MemberDao;
import com.sealtalk.model.TFriend;
import com.sealtalk.model.TGroupMember;
import com.sealtalk.model.TMap;
import com.sealtalk.model.TMember;
import com.sealtalk.service.map.MapService;
import com.sealtalk.utils.PropertiesUtils;
import com.sealtalk.utils.StringUtils;
import com.sealtalk.utils.TimeGenerator;

public class MapServiceImpl implements MapService {

	@Override
	public String getLocation(String userId, String targetId, String type) {
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();

		if (StringUtils.getInstance().isBlank(userId)
				|| StringUtils.getInstance().isBlank(targetId)
				|| StringUtils.getInstance().isBlank(type)) {
			jo.put("code", -1);
			jo.put("text", Tips.WRONGPARAMS.getText());
		} else {
			boolean status = true;
			int targetIdInt = StringUtils.getInstance().strToInt(targetId);
			int userIdInt = StringUtils.getInstance().strToInt(userId);

			try {
				StringBuilder sb = new StringBuilder();
				String idStr = null;

				if (type.equals("1")) { // 群
					List<TGroupMember> memberList = groupMemeberDao
							.listGroupMembers(targetIdInt);

					if (memberList != null) {

						for (int i = 0; i < memberList.size(); i++) {
							sb.append(memberList.get(i).getMemberId()).append(
									",");
						}
					}
				} else if (type.equals("2")) {
					sb.append(targetIdInt).append(",").append(userIdInt);
				} else if (type.equals("3")) {
					int mapMax = StringUtils.getInstance().strToInt(
							PropertiesUtils.getStringByKey("map.max"));
					List<TMember> memberIds = memberDao
							.getLimitMemberIds(mapMax);

					if (memberIds != null) {
						int len = memberIds.size();
						len = len >= mapMax ? mapMax : len;
						for (int i = 0; i < len; i++) {
							TMember tm = memberIds.get(i);
							sb.append(tm.getId()).append(",");
						}
					}
				} else {
					int mapMax = StringUtils.getInstance().strToInt(
							PropertiesUtils.getStringByKey("map.max"));
					List<TFriend> friends = friendDao
							.getFriendRelationForIdWithLimit(userIdInt, mapMax);
					if (friends != null) {
						int len = friends.size();

						len = len >= mapMax ? mapMax : len;

						for (int i = 0; i < len; i++) {
							TFriend friend = (TFriend) friends.get(i);
							sb.append(friend.getFriendId()).append(",");
						}
					}
				}

				idStr = sb.toString();

				System.out.println("map->type: " + type + "->ids: " + idStr);

				if (StringUtils.getInstance().isEndChar(idStr, ",")) {
					idStr = idStr.substring(0, idStr.length() - 1);
				}

				List<Object[]> locations = mapDao.getLocationForMultId(idStr);

				if (locations != null) {
					long now = TimeGenerator.getInstance().getUnixTime();
					long shareTime = 0;

					String mapShareTime = PropertiesUtils
							.getStringByKey("map.sharetime");

					if (mapShareTime != null) {
						shareTime = Long.parseLong(mapShareTime);
					}

					for (int i = 0; i < locations.size(); i++) {
						Object[] ret = locations.get(i);
						JSONObject t = new JSONObject();
						long time = Long.parseLong(String.valueOf(ret[4]));
						long timeVal = now - time;
						
						t.put("userID", ret[0]);
						t.put("logo", ret[1]);

						if (timeVal >= shareTime) {		
							t.put("latitude", 90);
							t.put("longtitude", 180);
						} else {
							t.put("latitude", ret[2]);
							t.put("longtitude", ret[3]);
						}
						ja.add(t);

					}
					jo.put("code", 1);
					jo.put("text", ja.toString());
				} else {
					status = false;
				}
				/*
				 * } else { //人 Object[] tmap = mapDao.getLocation(targetIdInt);
				 * 
				 * if (tmap != null) {
				 * 
				 * JSONObject m = new JSONObject(); m.put("userID",
				 * targetIdInt); m.put("logo", tmap[0]); m.put("latitude",
				 * tmap[1]); m.put("longtitude", tmap[2]);
				 * 
				 * ja.add(m); jo.put("code", 1); jo.put("text", ja.toString());
				 * 
				 * } else { status = false; } }
				 */
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

		try {
			if (StringUtils.getInstance().isBlank(userId)
					|| StringUtils.getInstance().isBlank(latitude)
					|| StringUtils.getInstance().isBlank(longtitude)) {
				jo.put("code", 0);
				jo.put("text", Tips.WRONGPARAMS.getText());
			} else {
				int userIdInt = StringUtils.getInstance().strToInt(userId);
				long now = TimeGenerator.getInstance().getUnixTime();

				TMap t = mapDao.getLaLongtitudeForUserId(userIdInt);

				if (t != null) {
					String la = t.getLatitude();
					String longt = t.getLongitude();

					if (la.equals(latitude)) {
						latitude = null;
					}
					if (longt.equals(longtitude)) {
						longtitude = null;
					}
					mapDao.updateLocation(userIdInt, latitude, longtitude, now);
				} else {
					TMap tm = new TMap();
					tm.setUserId(userIdInt);
					tm.setLatitude(latitude);
					tm.setLongitude(longtitude);
					tm.setSubDate(now);

					mapDao.saveLocation(tm);
				}

				jo.put("code", 1);
				jo.put("text", Tips.OK.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("code", 0);
			jo.put("text", Tips.FAIL.getText());
		}

		return jo.toString();
	}

	private MemberDao memberDao;
	private FriendDao friendDao;
	private MapDao mapDao;
	private GroupMemberDao groupMemeberDao;

	public MemberDao getMemberDao() {
		return memberDao;
	}

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public FriendDao getFriendDao() {
		return friendDao;
	}

	public void setFriendDao(FriendDao friendDao) {
		this.friendDao = friendDao;
	}

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
