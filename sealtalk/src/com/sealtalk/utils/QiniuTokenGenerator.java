package com.sealtalk.utils;


import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

public class QiniuTokenGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(generatorToken());
	}
	
	public static String generatorToken() {
		
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
			System.out.println(expire);
			Auth testAuth = Auth.create(accessKey, secretKey);
			
			StringMap policy = new StringMap().put("endUser", "y");
	        String token = testAuth.uploadTokenWithDeadline(bucketName, key, expire, policy, false);
	        
	        return token;
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return null;
	}

}
