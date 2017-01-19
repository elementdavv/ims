package com.sealtalk.service.upload.impl;

import net.sf.json.JSONObject;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.sealtalk.service.upload.UploadService;
import com.sealtalk.utils.PropertiesUtils;
import com.sealtalk.utils.StringUtils;

public class UploadServiceImpl implements UploadService {

	@Override
	public String getUploadQiniuToken() {
		JSONObject jo = new JSONObject();
		
		try {
			String accessKey = PropertiesUtils.getStringByKey("qiniu.accessKey");
			String secretKey = PropertiesUtils.getStringByKey("qiniu.secretKey");
			String bucketName = PropertiesUtils.getStringByKey("qiniu.bucketname");
			String key = PropertiesUtils.getStringByKey("qiniu.key");
			String expireStr = PropertiesUtils.getStringByKey("qiniu.expires");
			
			long expire = 0;
			
			if (expireStr.equals("max")) {
				expire = Long.MAX_VALUE;
			} else if (expireStr.equals("min")) {
				expire = 0;
			} else {
				expire = StringUtils.getInstance().strToLong(expireStr);
			}
			
			Auth testAuth = Auth.create(accessKey, secretKey);
			StringMap policy = new StringMap().put("endUser", "y");
	        String token = testAuth.uploadTokenWithDeadline(bucketName, key, expire, policy, false);
	        
	        jo.put("code", 1);
	        jo.put("text", token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jo.toString();
	}

}
