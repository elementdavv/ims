package com.sealtalk.utils;

import io.rong.RongCloud;
import io.rong.models.TokenReslut;

/**
 * 融云sdk工具
 * @author hao_dy
 * @since jdk1.7
 * @date 2017/01/10
 *
 */
public class RongCloudUtils {
	private static final String JSONFILE = RongCloudUtils.class.getClassLoader().getResource("jsonsource").getPath()+"/";
	private static RongCloud rongCloud = null;
	
	private RongCloudUtils(){}
	
	private static class Inner {
		private static final RongCloudUtils RCU = new RongCloudUtils();
	}
	
	public static RongCloudUtils getInstance() {
		return Inner.RCU;
	}
	
	/**
	 * 初始化
	 */
	private void init() {
		String appKey = PropertiesUtils.getStringByKey("db.appKey");
		String appSecret = PropertiesUtils.getStringByKey("db.appSecret");
		
		rongCloud = RongCloud.getInstance(appKey, appSecret);
	}
	
	/**
	 * 获取Token结果集
	 * @param userId
	 * @param userName
	 * @param url
	 * @return
	 */
	public TokenReslut getTokenResult(String userId, String userName, String url) {
		try {
			if (rongCloud == null) {
				this.init();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TokenReslut userGetTokenResult = null;
		
		try {
			if (url == null || "".equals(url)) {
				url = "http://www.rongcloud.cn/images/logo.png";
			}
			userGetTokenResult = rongCloud.user.getToken(userId, userName, url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return userGetTokenResult;
	}
	
	/**
	 * 获取token
	 * @param userId
	 * @param userName
	 * @param url
	 * @return
	 */
	public String getToken(String userId, String userName, String url) {
		try {
			if (rongCloud == null) {
				this.init();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TokenReslut userGetTokenResult = null;
		String token = null;
		
		try {	
			userGetTokenResult = this.getTokenResult(userId, userName, url);
			token = userGetTokenResult.getToken();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return token;
	}
	
	
	
}
