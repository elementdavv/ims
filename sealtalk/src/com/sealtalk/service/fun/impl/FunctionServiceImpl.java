package com.sealtalk.service.fun.impl;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.sealtalk.common.FunctionName;
import com.sealtalk.common.Tips;
import com.sealtalk.dao.fun.DontDistrubDao;
import com.sealtalk.dao.fun.FunctionDao;
import com.sealtalk.model.TDontDistrub;
import com.sealtalk.model.TFunction;
import com.sealtalk.service.fun.FunctionService;
import com.sealtalk.utils.JSONUtils;
import com.sealtalk.utils.StringUtils;

public class FunctionServiceImpl implements FunctionService {

	private static final Logger logger = Logger.getLogger(FunctionServiceImpl.class);
	
	@Override
	public String setNotRecieveMsg(String status, String groupId, String userId) {
		JSONObject jo = new JSONObject();
		
		try {
			if (!StringUtils.getInstance().isBlank(status) &&
					!StringUtils.getInstance().isBlank(groupId) &&
					!StringUtils.getInstance().isBlank(userId)) {
				TDontDistrub tf = new TDontDistrub();
				
				int groupIdInt = StringUtils.getInstance().strToInt(groupId);
				int userIdInt = StringUtils.getInstance().strToInt(userId);
				
				tf.setGroupId(groupIdInt);
				tf.setMemberId(userIdInt);
				tf.setIsOpen(status);
				tf.setListOrder(0);
				
				dontDistrubDao.setDontDistrub(tf);
				jo.put("code", 1);
				jo.put("text", Tips.OK.getText());
			} else {
				jo.put("code", -1);
				jo.put("text", Tips.NOTSETFUN.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jo.toString();
	}
	
	@Override
	public String getNotRecieveMsg(String groupId, String userId) {
		JSONObject jo = new JSONObject();
		
		try {
			if (!StringUtils.getInstance().isBlank(groupId) &&
					!StringUtils.getInstance().isBlank(userId)) {
				TFunction tf = functionDao.getFunctionStatus(FunctionName.NOTRECEIVEMSG.getName() + "_" + groupId + "_" + userId);
				
				if (tf != null) {
					JSONObject jk = JSONUtils.getInstance().modelToJSONObj(tf);
					jo.put("code", 1);
					jo.put("text", jk.toString());
				} else {
					jo.put("code", 0);
					jo.put("text", Tips.FAIL.getText());
				}
			} else {
				jo.put("code", -1);
				jo.put("text", Tips.NOTSETFUN.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jo.toString();
	}
	
	@Override
	public String getSysTipVoice(String userId) {
		JSONObject jo = new JSONObject();
		
		try {
			String name = userId + "_" + FunctionName.SYSTIPVOICE.getName();
			TFunction tf = functionDao.getFunctionStatus(name);
			
			jo.put("code", 1);
			
			if (tf != null) {
				jo.put("text", 1);
			} else {
				jo.put("text", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String result = jo.toString();
		
		System.out.println(result);
		return result;
	}

	@Override
	public String setSysTipVoice(String userId, String status) {
		JSONObject jo = new JSONObject();
		
		try {
			if (!StringUtils.getInstance().isBlank(status)) {
				String name = userId + "_" + FunctionName.SYSTIPVOICE.getName();
				TFunction tf1 = functionDao.getFunctionStatus(name);
				
				if (tf1 == null) {
					TFunction tf = new TFunction();
					
					tf.setIsOpen(status);
					tf.setName(name);
					tf.setListorder(0);
					
					functionDao.setFunctionStatus(tf);
				} else {
					functionDao.updateFunctionStatus(name, status);
				}
				jo.put("code", 1);
				jo.put("text", Tips.OK.getText());
			} else {
				jo.put("code", -1);
				jo.put("text", Tips.NOTSETFUN.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jo.toString();
	}
	
	@Override
	public String setMsgTop(String userId, String topId, String topType) {
		JSONObject jo = new JSONObject();
		
		try {
			if (!StringUtils.getInstance().isBlank(userId) && 
					!StringUtils.getInstance().isBlank(topId) &&
					!StringUtils.getInstance().isBlank(topType)) {
				
				String name = (new StringBuilder(userId).append("_").append(FunctionName.MSGTOP.getName())).toString();
				TFunction tf1 = functionDao.getFunctionStatus(name);
				String status = (new StringBuilder(topId).append("_").append(topType)).toString();
				
				if (tf1 == null) {
					TFunction tf = new TFunction();
					tf.setIsOpen(status);
					tf.setName(name);
					tf.setListorder(0);
					functionDao.setFunctionStatus(tf);
				} else {
					functionDao.updateFunctionStatus(name, status);
				}
				jo.put("code", 1);
				jo.put("text", Tips.OK.getText());
			} else {
				jo.put("code", -1);
				jo.put("text", Tips.WRONGPARAMS.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jo.toString();
	}
	
	@Override
	public String getMsgTop(String userId) {
		JSONObject jo = new JSONObject();
		
		try {
			if (!StringUtils.getInstance().isBlank(userId)) {
				String name = (new StringBuilder(userId).append("_").append(FunctionName.MSGTOP.getName())).toString();
				TFunction tf1 = functionDao.getFunctionStatus(name);
				
				if (tf1 != null) {
					String topMsg = tf1.getIsOpen();
					String[] split = topMsg.split("_");
					
					JSONObject j = new JSONObject();
					j.put("id", split[0]);
					j.put("type", split[1]);
					
					jo.put("code", 1);
					jo.put("text", j.toString());
					
				} else {
					jo.put("code", 0);
					jo.put("text", Tips.NOTSETFUN.getText());
				}
			} else {
				jo.put("code", -1);
				jo.put("text", Tips.WRONGPARAMS.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jo.toString();
	}
	
	private DontDistrubDao dontDistrubDao;
	private FunctionDao functionDao;
	
	public FunctionDao getFunctionDao() {
		return functionDao;
	}

	public void setFunctionDao(FunctionDao functionDao) {
		this.functionDao = functionDao;
	}

	public DontDistrubDao getDontDistrubDao() {
		return dontDistrubDao;
	}

	public void setDontDistrubDao(DontDistrubDao dontDistrubDao) {
		this.dontDistrubDao = dontDistrubDao;
	}

}
