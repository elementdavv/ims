package com.sealtalk.service.msg.impl;

import java.util.List;

import net.sf.json.JSONObject;

import com.sealtalk.auth.dao.AppSecretDao;
import com.sealtalk.auth.model.AppSecret;
import com.sealtalk.common.Tips;
import com.sealtalk.dao.member.MemberDao;
import com.sealtalk.service.msg.MessageService;
import com.sealtalk.utils.RongCloudUtils;
import com.sealtalk.utils.StringUtils;

public class MessageServiceImpl implements MessageService {

	@Override
	public String sendSysMsg(String fromId, String targetIds,
			String targetNames, String msg, String extraMsg,
			String pushContent, String pushData, String isPersisted,
			String isCounted) {

		try {
			if (!StringUtils.getInstance().isBlank(fromId)
					&& !StringUtils.getInstance().isBlank(msg)) {

				boolean s = true;
				String[] targetIdsArr = null;

				if (!StringUtils.getInstance().isBlank(targetIds)) {
					targetIds = StringUtils.getInstance().replaceChar(
							targetIds, "\"", "");
					targetIds = StringUtils.getInstance().replaceChar(
							targetIds, "[", "");
					targetIds = StringUtils.getInstance().replaceChar(
							targetIds, "]", "");

					targetIdsArr = targetIds.split(",");
				} else if (!StringUtils.getInstance().isBlank(targetNames)) {
					targetNames = StringUtils.getInstance().replaceChar(
							targetNames, "\"", "");
					targetNames = StringUtils.getInstance().replaceChar(
							targetNames, "[", "");
					targetNames = StringUtils.getInstance().replaceChar(
							targetNames, "]", "");
					targetNames = StringUtils.getInstance().replaceChar(
							targetNames, " ", "");

					List list = memberDao.getMemberIdsByAccount(targetNames);

					if (list != null) {
						int len = list.size();
						targetIdsArr = new String[len];
						for (int i = 0; i < len; i++) {
							targetIdsArr[i] = list.get(i) + "";
						}
					}
				} else {
					s = false;
				}

				if (s) {
					for (int i = 0; i < targetIdsArr.length; i++) {
						targetIdsArr[i] = targetIdsArr[i].trim();
					}

					String msgType = "1";

					return RongCloudUtils.getInstance().sendSysMsg(fromId,
							targetIdsArr, msg, extraMsg, pushContent, pushData,
							isPersisted, isCounted, msgType);
				} else {
					JSONObject jo = new JSONObject();
					jo.put("code", 0);
					jo.put("text", Tips.WRONGPARAMS.getText());
					return jo.toString();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String sendPrivateMsg(String fromId, String targetIds,
			String targetNames, String msg, String extraMsg,
			String pushContent, String count, String verifyBlacklist,
			String isPersisted, String isCounted) {

		try {
			if (!StringUtils.getInstance().isBlank(fromId)
					&& !StringUtils.getInstance().isBlank(msg)) {
				
				boolean s = true;
				String[] targetIdsArr = null;

				if (!StringUtils.getInstance().isBlank(targetIds)) {
					targetIds = StringUtils.getInstance().replaceChar(
							targetIds, "\"", "");
					targetIds = StringUtils.getInstance().replaceChar(
							targetIds, "[", "");
					targetIds = StringUtils.getInstance().replaceChar(
							targetIds, "]", "");

					targetIdsArr = targetIds.split(",");
				} else if (!StringUtils.getInstance().isBlank(targetNames)) {
					targetNames = StringUtils.getInstance().replaceChar(
							targetNames, "\"", "");
					targetNames = StringUtils.getInstance().replaceChar(
							targetNames, "[", "");
					targetNames = StringUtils.getInstance().replaceChar(
							targetNames, "]", "");
					targetNames = StringUtils.getInstance().replaceChar(
							targetNames, " ", "");

					List list = memberDao.getMemberIdsByAccount(targetNames);

					if (list != null) {
						int len = list.size();
						targetIdsArr = new String[len];
						for (int i = 0; i < len; i++) {
							targetIdsArr[i] = list.get(i) + "";
						}
					}
				} else {
					s = false;
				}

				if (s) {
					for (int i = 0; i < targetIdsArr.length; i++) {
						targetIdsArr[i] = targetIdsArr[i].trim();
					}
					
					String msgType = "1";

					return RongCloudUtils.getInstance().sendPrivateMsg(fromId,
							targetIdsArr, msg, extraMsg, pushContent, count,
							verifyBlacklist, isPersisted, isCounted, msgType);
				} else {
					JSONObject jo = new JSONObject();
					jo.put("code", 0);
					jo.put("text", Tips.WRONGPARAMS.getText());
					return jo.toString();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean validAppIdAndSecret(String appId, String secret) {
		try {
			AppSecret as = appSecretDao.getAppSecretByAppIdAndSecret(appId,
					secret);
			return as != null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private AppSecretDao appSecretDao;
	private MemberDao memberDao;

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public void setAppSecretDao(AppSecretDao appSecretDao) {
		this.appSecretDao = appSecretDao;
	}

}
