package com.sealtalk.auth.service.impl;

import java.util.ArrayList;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.sealtalk.auth.dao.AppSecretDao;
import com.sealtalk.auth.model.AppSecret;
import com.sealtalk.auth.service.AppSecretService;
import com.sealtalk.common.AuthTips;
import com.sealtalk.utils.LogUtils;
import com.sealtalk.utils.PropertiesUtils;
import com.sealtalk.utils.SecretUtils;
import com.sealtalk.utils.StringUtils;
import com.sealtalk.utils.TimeGenerator;

public class AppSecretServiceImpl implements AppSecretService {
	private static final Logger logger = Logger
			.getLogger(AppSecretServiceImpl.class);

	@Override
	public String getAppIDAndSecret() {
		JSONObject jo = new JSONObject();
		ArrayList<String> as = null;

		try {
			as = makeAppId();
			String code = "200";
			String text = "";

			if (as != null) {
				JSONObject jo1 = new JSONObject();
				jo1.put("appid", as.get(0));
				jo1.put("secret", as.get(1));
				text = jo1.toString();
			} else {
				code = "500";
				text = AuthTips.WORNGMAKEAS.getText();
			}
			jo.put("code", code);
			jo.put("text", text);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(LogUtils.getInstance().getErrorInfoFromException(e));
		}

		return jo.toString();
	}

	@Override
	public String setAppIDAndSecretAndUrl(String appId, String secret, String url) {
		JSONObject jo = new JSONObject();
		String code = null;
		String text = null;
		
		try {
			if (StringUtils.getInstance().isBlank(appId) || 
					StringUtils.getInstance().isBlank(secret) || 
					StringUtils.getInstance().isBlank(url)) {
				code = "500";
				text = AuthTips.WORNGPARAM.getText();
			} else {
				long valideTime = 0;
				long now = TimeGenerator.getInstance().getUnixTime();
				
				String authTime = PropertiesUtils.getStringByKey("auth.appvalidetime");
	
				if (!StringUtils.getInstance().isBlank(authTime)) {
					valideTime = Long.parseLong(authTime);
				}
				
				AppSecret as = new AppSecret();
				as.setAppId(appId);
				as.setSecert(secret);
				as.setCallBackUrl(url);
				as.setTime(now + valideTime);
				
				appSecretDao.setAppIDAndSecretAndUrl(as);
				code = "200";
				text = AuthTips.OK.getText();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(LogUtils.getInstance().getErrorInfoFromException(e));
			code = "500";
			text = AuthTips.ERROR.getText();
		}
		
		jo.put("code", code);
		jo.put("text", text);
		
		return jo.toString();
	}
	
	private ArrayList<String> makeAppId() {
		ArrayList<String> as = new ArrayList<String>();
		long now = TimeGenerator.getInstance().getUnixTimeMills();
		
		try {
			UUID uuid = UUID.randomUUID();
			String uuidStr = uuid.toString();
			uuidStr = StringUtils.getInstance().replaceChar(uuidStr, "-", "");
			as.add(uuidStr);
			uuidStr = new SecretUtils().encrypt(uuidStr);
			as.add(uuidStr);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(LogUtils.getInstance().getErrorInfoFromException(e));
		}

		return as;
	}
	
	@Override
	public String getTempToken(String appId) {
		JSONObject jo = new JSONObject();
		String code = "500";
		String text = null;
		
		try {
			if (StringUtils.getInstance().isBlank(appId)) {
				text = AuthTips.WORNGPARAM.getText();
			} else {
				AppSecret as = appSecretDao.getAppSecretByAppId(appId);
				
				if (as == null) {
					text = AuthTips.WORNGAPPID.getText();
				} else {
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(LogUtils.getInstance().getErrorInfoFromException(e));
		}
		
		jo.put("code", code);
		jo.put("text", text);
		return jo.toString();
	}
	
	private AppSecretDao appSecretDao;

	public void setAppSecretDao(AppSecretDao appSecretDao) {
		this.appSecretDao = appSecretDao;
	}

}
