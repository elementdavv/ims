package com.sealtalk.service.msg.impl;

import com.sealtalk.service.msg.MessageService;
import com.sealtalk.utils.RongCloudUtils;

public class MessageServiceImpl implements MessageService {

	@Override
	public String sendSysMsg(String fromid, String targetids, String msg, String extraMsg) {
		try {
			String[] strArr = null;
			
			if (targetids.startsWith("[") && targetids.endsWith("]")) {
				targetids = targetids.substring(1, targetids.length());
			}
			
			strArr = targetids.split(",");
			
			String result = RongCloudUtils.getInstance().sendSysMsg(fromid, strArr, msg, extraMsg);
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
