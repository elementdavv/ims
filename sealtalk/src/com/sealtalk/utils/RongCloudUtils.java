package com.sealtalk.utils;

import io.rong.RongCloud;
import io.rong.messages.TxtMessage;
import io.rong.models.CheckOnlineReslut;
import io.rong.models.CodeSuccessReslut;
import io.rong.models.TokenReslut;
import net.sf.json.JSONObject;

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
				String domain = PropertiesUtils.getDomain();
				String uploadDir = PropertiesUtils.getUploadDir();
				String logo = PropertiesUtils.getDefaultLogo();
				
				url = domain + uploadDir + logo;
				//url = "http://www.rongcloud.cn/update/images/logo.png";
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
		TokenReslut userGetTokenResult = null;
		String token = null;
		
		try {	
			if (rongCloud == null) {
				this.init();
			}
			userGetTokenResult = this.getTokenResult(userId, userName, url);
			token = userGetTokenResult.getToken();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return token;
	}
	
	public int refreshUser(String userId, String userName, String url) {
		CodeSuccessReslut codeSuccessReslut = null;
		
		try {
			if (rongCloud == null) {
				this.init();
			}
			if (url == null || "".equals(url)) {
				String domain = PropertiesUtils.getDomain();
				String uploadDir = PropertiesUtils.getUploadDir();
				String logo = PropertiesUtils.getDefaultLogo();
				
				url = domain + uploadDir + logo;
			}
			codeSuccessReslut = rongCloud.user.refresh(userId, userName, url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return codeSuccessReslut.getCode();
	}

	/**
	 * 检测在线状态
	 * @param userId
	 * @return
	 */
	public String checkOnLine(String userId) {
		CheckOnlineReslut checkOnlineReslut = null;
		JSONObject jo = new JSONObject();
		
		try {
			if (rongCloud == null) {
				this.init();
			}
			checkOnlineReslut = rongCloud.user.checkOnline(userId);
			if (checkOnlineReslut != null) {
				jo.put("code", checkOnlineReslut.getCode());
				jo.put("status", checkOnlineReslut.getStatus());
				jo.put("text", "ok");
			} else {
				jo.put("code", 0);
				jo.put("text", "fail");
			}
		} catch (Exception e) {
			jo.put("code", 0);
			jo.put("text", "fail");
			e.printStackTrace();
		}
		
		return jo.toString();
	}
	
	/**
	 * 发系统消息
	 * @param fromId
	 * @param targetIds
	 * @param msg
	 * @param extraMsg
	 * @return
	 */
	public String sendSysMsg(String fromId, String[] targetIds, String msg, String extraMsg) {
		JSONObject jo = new JSONObject();
		
		try {
			if (rongCloud == null) {
				this.init();
			}

			JSONObject pushMsg = new JSONObject();
			jo.put("pushData", msg);
			
			TxtMessage messagePublishSystemTxtMessage = new TxtMessage(msg, extraMsg);
			CodeSuccessReslut messagePublishSystemResult = 
				rongCloud.message.PublishSystem(fromId, targetIds, messagePublishSystemTxtMessage, "thisisapush", pushMsg.toString(), 0, 0);
			if (messagePublishSystemResult != null) {
				jo.put("code", messagePublishSystemResult.getCode());
				jo.put("text", "ok");
			} else {
				jo.put("code", 0);
				jo.put("text", "fail");
			}
		} catch (Exception e) {
			jo.put("code", 0);
			jo.put("text", "fail");
			e.printStackTrace();
		}
		
		return jo.toString();
	}
	
	/**
	 * 创建群组
	 * @param userId	 加入群的用户id组
	 * @param groupId
	 * @param groupName
	 * @return
	 */
	public String createGroup(String[] userIds, String groupId, String groupName) {
		String result = null;
		
		try {
			if (!StringUtils.getInstance().isArrayBlank(userIds) &&
					!StringUtils.getInstance().isBlank(groupId) &&
					!StringUtils.getInstance().isBlank(groupName)) {
				
				CodeSuccessReslut groupCreateResult = rongCloud.group.create(userIds, groupId, groupName);
				
				if (groupCreateResult != null) {
					result = groupCreateResult.getCode().toString();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 加入群
	 * @param userIds
	 * @param groupId
	 * @param groupName
	 * @return
	 */
	public String joinGroup(String[] userIds, String groupId, String groupName) {
		String result = null;
		
		try {
			if (!StringUtils.getInstance().isArrayBlank(userIds) &&
					!StringUtils.getInstance().isBlank(groupId) &&
					!StringUtils.getInstance().isBlank(groupName)) {
				
				CodeSuccessReslut groupJoinResult = rongCloud.group.join(userIds, groupId, groupName);
				
				if (groupJoinResult != null) {
					result = groupJoinResult.getCode().toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 退出群
	 * @param userId
	 * @param groupId
	 * @return
	 */
	public String leftGroup(String[] userIds, String groupId) {
		String result = null;
		
		try {
			if (!StringUtils.getInstance().isArrayBlank(userIds) &&
					!StringUtils.getInstance().isBlank(groupId)) {
				CodeSuccessReslut groupQuitResult = rongCloud.group.quit(userIds, groupId);
				
				if (groupQuitResult != null) {
					result = groupQuitResult.getCode().toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String dissLoveGroup(String userId, String groupId) {
		String result = null;
		
		try {
			if (!StringUtils.getInstance().isBlank(userId) &&
					!StringUtils.getInstance().isBlank(groupId)) {
				CodeSuccessReslut groupDismissResult = rongCloud.group.dismiss(userId, groupId);
				
				if (groupDismissResult != null) {
					result = groupDismissResult.getCode().toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	

}
