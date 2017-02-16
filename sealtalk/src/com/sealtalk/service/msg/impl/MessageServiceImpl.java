package com.sealtalk.service.msg.impl;

import com.sealtalk.service.msg.MessageService;
import com.sealtalk.utils.RongCloudUtils;
import com.sealtalk.utils.StringUtils;

public class MessageServiceImpl implements MessageService {

	@Override
	public String sendSysMsg(String fromId, String targetIds, String msg,
			String pushContent, String pushData, String isPersisted,
			String isCounted) {
		
		try {
			if (!StringUtils.getInstance().isBlank(fromId) && 
					!StringUtils.getInstance().isBlank(targetIds) &&
					!StringUtils.getInstance().isBlank(msg)) {
				targetIds = StringUtils.getInstance().replaceChar(targetIds, "\"", "");
				targetIds = StringUtils.getInstance().replaceChar(targetIds, "[", "");
				targetIds = StringUtils.getInstance().replaceChar(targetIds, "]", "");
				
				String[] targetIdsArr = targetIds.split(",");
				
				for(int i = 0; i < targetIdsArr.length; i++) {
					targetIdsArr[i] = targetIdsArr[i].trim();
				}
				
				String msgType = "1";
				
				return RongCloudUtils.getInstance().sendSysMsg(fromId, targetIdsArr, msg, pushContent, pushData, isPersisted, isCounted, msgType);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public String sendPrivateMsg(String fromId, String targetIds, String msg,
			String pushContent, String count, String verifyBlacklist,
			String isPersisted, String isCounted) {
		
		try {
			if (!StringUtils.getInstance().isBlank(fromId) && 
					!StringUtils.getInstance().isBlank(targetIds) &&
					!StringUtils.getInstance().isBlank(msg)) {
				targetIds = StringUtils.getInstance().replaceChar(targetIds, "\"", "");
				targetIds = StringUtils.getInstance().replaceChar(targetIds, "[", "");
				targetIds = StringUtils.getInstance().replaceChar(targetIds, "]", "");
				
				String[] targetIdsArr = targetIds.split(",");
				
				for(int i = 0; i < targetIdsArr.length; i++) {
					targetIdsArr[i] = targetIdsArr[i].trim();
				}
				
				String msgType = "1";
				
				return RongCloudUtils.getInstance().sendPrivateMsg(fromId, targetIdsArr, msg, pushContent, count, verifyBlacklist, isPersisted, isCounted, msgType);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	
}
