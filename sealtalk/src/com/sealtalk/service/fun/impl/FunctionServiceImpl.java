package com.sealtalk.service.fun.impl;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.sealtalk.common.FunctionName;
import com.sealtalk.common.Tips;
import com.sealtalk.dao.fun.FunctionDao;
import com.sealtalk.model.TFunction;
import com.sealtalk.service.fun.FunctionService;
import com.sealtalk.utils.JSONUtils;
import com.sealtalk.utils.StringUtils;

public class FunctionServiceImpl implements FunctionService {

	private static final Logger logger = Logger.getLogger(FunctionServiceImpl.class);

	@Override
	public String setNotRecieveMsg(String status) {
		JSONObject jo = new JSONObject();
		
		try {
			if (!StringUtils.getInstance().isBlank(status)) {
				TFunction tf = new TFunction();
				
				tf.setIsOpen(status);
				tf.setName(FunctionName.NOTRECEIVEMSG.getName());
				tf.setListorder(0);
				
				functionDao.setNotRecieveMsg(tf);
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
	public String getNotRecieveMsg() {
		JSONObject jo = new JSONObject();
		
		try {
			TFunction tf = functionDao.getNotRecieveMsg(FunctionName.NOTRECEIVEMSG.getName());
			
			if (tf != null) {
				JSONObject jk = JSONUtils.getInstance().modelToJSONObj(tf);
				jo.put("code", 1);
				jo.put("text", jk.toString());
			} else {
				jo.put("code", 0);
				jo.put("text", Tips.FAIL.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jo.toString();
	}
	
	
	private FunctionDao functionDao;

	public FunctionDao getFunctionDao() {
		return functionDao;
	}

	public void setFunctionDao(FunctionDao functionDao) {
		this.functionDao = functionDao;
	}

}
