package com.sealtalk.auth.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.sealtalk.auth.dao.AppSecretDao;
import com.sealtalk.auth.model.AppSecret;
import com.sealtalk.common.BaseDao;
import com.sealtalk.utils.LogUtils;

/**
 * 验证管理
 * 
 * @author hao_dy
 * @date 2017/03/08
 * @since jdk1.7
 */
public class AppSecretDaoImpl extends BaseDao<AppSecret, Integer> implements AppSecretDao {
	private static final Logger logger = Logger
			.getLogger(AppSecretDaoImpl.class);

	@Override
	public void setAppIDAndSecretAndUrl(AppSecret as) {
		try {
			save(as);
			logger.info("AppId and Secret and callbackUrl were saved!");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(LogUtils.getInstance().getErrorInfoFromException(e));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public AppSecret getAppSecretByAppId(String appId) {
		try {
			Criteria ctr = getCriteria();
			ctr.add(Restrictions.eq("appId", appId));

			List<AppSecret> list = ctr.list();

			if (list.size() > 0) {
				return (AppSecret) list.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void updateAppSecret(AppSecret as) {
		try {
			update(as);
			logger.info("AppIdSecret was updated, !");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(LogUtils.getInstance().getErrorInfoFromException(e));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public AppSecret getAppSecretByRealToken(String visitToken) {
		try {
			Criteria ctr = getCriteria();
			ctr.add(Restrictions.eq("visitToken", visitToken));

			List<AppSecret> list = ctr.list();

			if (list.size() > 0) {
				return (AppSecret) list.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public AppSecret getAppSecretBySecret(String secret) {
		try {
			Criteria ctr = getCriteria();
			ctr.add(Restrictions.eq("secert", secret));

			List<AppSecret> list = ctr.list();

			if (list.size() > 0) {
				return (AppSecret) list.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
