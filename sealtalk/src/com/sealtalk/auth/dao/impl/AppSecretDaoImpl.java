package com.sealtalk.auth.dao.impl;


import org.apache.log4j.Logger;
import com.sealtalk.auth.dao.AppSecretDao;
import com.sealtalk.auth.model.AppSecret;
import com.sealtalk.common.BaseDao;
import com.sealtalk.utils.LogUtils;

/**
 * 验证管理 
 * @author hao_dy
 * @date 2017/03/08
 * @since jdk1.7
 */
public class AppSecretDaoImpl extends BaseDao<AppSecret, Integer> implements AppSecretDao {
	private static final Logger logger = Logger.getLogger(AppSecretDaoImpl.class);
	
	@Override
	public void setAppIDAndSecretAndUrl(AppSecret as) {
		try {
			save(as);
			logger.info("AppId and Secret and callbackUrl is saved!");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(LogUtils.getInstance().getErrorInfoFromException(e));
		}
	}

}
