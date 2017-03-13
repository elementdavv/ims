package com.sealtalk.auth.service.impl;

import java.util.ArrayList;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.sealtalk.auth.dao.AppSecretDao;
import com.sealtalk.auth.model.AppSecret;
import com.sealtalk.auth.service.AppSecretService;
import com.sealtalk.common.AuthTips;
import com.sealtalk.dao.member.MemberDao;
import com.sealtalk.model.SessionUser;
import com.sealtalk.model.TMember;
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
	public String setAppIDAndSecretAndUrl(String appId, String secret,
			String url) {
		JSONObject jo = new JSONObject();
		String code = null;
		String text = null;

		try {
			if (StringUtils.getInstance().isBlank(appId)
					|| StringUtils.getInstance().isBlank(secret)
					|| StringUtils.getInstance().isBlank(url)) {
				code = "500";
				text = AuthTips.WORNGPARAM.getText();
			} else {
				long valideTime = 0;
				long now = TimeGenerator.getInstance().getUnixTime();

				String authTime = PropertiesUtils
						.getStringByKey("auth.appidtime");

				if (!StringUtils.getInstance().isBlank(authTime)) {
					if (!authTime.toLowerCase().equals("max")) {
						valideTime = Long.parseLong(authTime);
						valideTime += now;
					} else {
						valideTime = -1;
					}
				}

				AppSecret as = new AppSecret();
				as.setAppId(appId);
				as.setSecert(secret);
				as.setCallBackUrl(url);
				as.setAppTime(valideTime);

				appSecretDao.setAppIDAndSecretAndUrl(as);
				code = "200";
				text = AuthTips.OK.getText();
			}

			jo.put("code", code);
			jo.put("text", text);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(LogUtils.getInstance().getErrorInfoFromException(e));
			code = "500";
			text = AuthTips.ERROR.getText();
		}

		return jo.toString();
	}

	@Override
	public JSONObject getTempTokenSceneOne(String appId) {
		JSONObject jo = new JSONObject();
		String code = "500";
		String text = null;

		try {
			if (StringUtils.getInstance().isBlank(appId)) {
				text = AuthTips.WORNGPARAM.getText();
			} else {
				long now = TimeGenerator.getInstance().getUnixTime();
				AppSecret as = appSecretDao.getAppSecretByAppId(appId);

				if (as == null) {
					text = AuthTips.WORNGAPPID.getText();
				} else {
					String dbAppId = as.getAppId();

					if (!dbAppId.equals(appId)) {
						text = AuthTips.INVALIDAPPID.getText();
					} else {
						long appTime = as.getAppTime();

						// appid已超时，-1表示无时效限制
						if (appTime != -1 && now >= appTime) {
							text = AuthTips.INVALIDAPPID.getText();
						} else {
							// 生成临时令牌
							long tokenTimeL = 0;
							String tokenTime = PropertiesUtils
									.getStringByKey("auth.unauthtime");

							if (tokenTime != null) {
								tokenTimeL = Long.parseLong(tokenTime);
							}

							long tokenValidTime = now + tokenTimeL;

							appId += tokenValidTime;

							String unAuthToken = makeCode(appId);
							as.setUnAuthToken(unAuthToken);
							as.setUnAuthTokenTime(tokenValidTime);
							appSecretDao.updateAppSecret(as);

							code = "200";
							text = unAuthToken;
						}
					}
				}
			}
			jo.put("code", code);
			jo.put("text", text);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(LogUtils.getInstance().getErrorInfoFromException(e));
		}

		return jo;
	}

	@Override
	public JSONObject reqAuthorizeOne(String unAuthToken, String userName,
			String userPwd) {
		JSONObject jo = new JSONObject();
		String code = "500";
		String text = null;

		try {
			if (StringUtils.getInstance().isBlank(unAuthToken)) {
				text = AuthTips.INVALTOKEN.getText();
			} else if (StringUtils.getInstance().isBlank(userName)
					|| StringUtils.getInstance().isBlank(userPwd)) {
				text = AuthTips.INVALUSER.getText();
			} else {
				String appIdCode = coverCode(unAuthToken);
				String appId = appIdCode.substring(0, appIdCode.length() - 10);
				AppSecret as = appSecretDao.getAppSecretByAppId(appId);
				long now = TimeGenerator.getInstance().getUnixTime();

				if (as != null) {
					String unAuthTokenDB = as.getUnAuthToken();
					String callBackUrl = as.getCallBackUrl();

					long unAuthTokenTime = as.getUnAuthTokenTime();

					if (!unAuthTokenDB.equals(unAuthToken)
							|| (now >= unAuthTokenTime)) {
						text = AuthTips.INVALTOKEN.getText();
					} else {
						TMember tm = memberDao.searchSigleUser(userName,
								userPwd);

						if (tm != null) {
							long authTokenTimeL = 0;
							String authTokenTime = PropertiesUtils
									.getStringByKey("auth.authtime");
							appId += now;
							String authToken = makeCode(appId);
							authTokenTimeL = authTokenTime != null ? Long
									.parseLong(authTokenTime) : 0;
							as.setAuthToken(authToken);
							as.setAuthTokenTime(now + authTokenTimeL);
							as.setUserId(tm.getId());
							appSecretDao.updateAppSecret(as);
							code = "200";
							text = authToken;
							jo.put("url", callBackUrl);
						} else {
							text = AuthTips.INVALUSER.getText();
						}
					}
				} else {
					text = AuthTips.INVALIDAPPID.getText();
				}
			}
			jo.put("code", code);
			jo.put("text", text);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(LogUtils.getInstance().getErrorInfoFromException(e));
		}
		return jo;
	}

	@Override
	public String getRealToken(String secret, String authToken) {
		JSONObject jo = new JSONObject();
		String code = "500";
		String text = null;

		try {
			if (StringUtils.getInstance().isBlank(secret)) {
				text = AuthTips.WORNGSECRET.getText();
			} else if (StringUtils.getInstance().isBlank(authToken)) {
				text = AuthTips.INVALTOKEN.getText();
			} else {
				AppSecret as = appSecretDao.getAppSecretBySecret(secret);
				long now = TimeGenerator.getInstance().getUnixTime();

				if (as != null) {
					String secretDB = as.getSecert();
					String authTokenDB = as.getAuthToken();
					long authTokenTimeDB = as.getAuthTokenTime();

					if (!secretDB.equals(secret)) {
						text = AuthTips.WORNGSECRET.getText();
					} else if (!authTokenDB.equals(authToken)) {
						text = AuthTips.INVALTOKEN.getText();
					} else {
						if (now >= authTokenTimeDB) {
							text = AuthTips.INVALTOKEN.getText();
						} else {
							String nowEncry = makeCode(now + "");
							String secretPart = StringUtils.getInstance()
									.getRandomString(nowEncry + secret, 10);
							String realToken = makeCode(secretPart);
							as.setVisitToken(realToken);
							as.setVisitTokenTime(now);
							appSecretDao.updateAppSecret(as);
							code = "200";
							text = realToken;
						}
					}
				} else {
					text = AuthTips.WORNGSECRET.getText();
				}
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
	public String getAuthResource(String visitToken) {
		JSONObject ret = new JSONObject();
		String text = null;
		String code = "500";

		try {
			if (StringUtils.getInstance().isBlank(visitToken)) {
				text = AuthTips.INVALTOKEN.getText();
			} else {
				AppSecret as = appSecretDao.getAppSecretByRealToken(visitToken);
				if (as != null) {
					String visitTokenDB = as.getVisitToken();
					long now = TimeGenerator.getInstance().getUnixTime();
					long visitTokenTime = as.getVisitTokenTime();

					if (!visitTokenDB.equals(visitToken)
							|| now >= visitTokenTime) {
						text = AuthTips.INVALTOKEN.getText();
					} else {
						int userId = as.getUserId();
						Object[] member = memberDao.getOneOfMember(userId);
						JSONObject jo = new JSONObject();

						for (int i = 0; i < member.length; i++) {
							jo.put("id", isBlank(member[0]));
							jo.put("account", isBlank(member[1]));
							jo.put("name", isBlank(member[2]));
							jo.put("logo", isBlank(member[3]));
							jo.put("telephone", isBlank(member[4]));
							jo.put("email", isBlank(member[5]));
							jo.put("address", isBlank(member[6]));
							jo.put("token", isBlank(member[7]));
							jo.put("sex", isBlank(member[8]));
							jo.put("birthday", isBlank(member[9]));
							jo.put("workno", isBlank(member[10]));
							jo.put("mobile", isBlank(member[11]));
							jo.put("intro", isBlank(member[12]));
							jo.put("branchid", isBlank(member[13]));
							jo.put("branchname", isBlank(member[14]));
							jo.put("positionid", isBlank(member[15]));
							jo.put("positionname", isBlank(member[16]));
							jo.put("organid", isBlank(member[17]));
							jo.put("organname", isBlank(member[18]));
						}
						code = "200";
						text = jo.toString();
					}
				}
			}
			ret.put("code", code);
			ret.put("text", text);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(LogUtils.getInstance().getErrorInfoFromException(e));
		}

		return ret.toString();
	}

	@Override
	public String reqAuthorizeTwo(SessionUser su, String unAuthToken) {
		JSONObject ret = new JSONObject();
		String code = "500";
		String text = null;

		try {
			if (su == null) {
				text = AuthTips.NOTLOGIN.getText();
			} else if (StringUtils.getInstance().isBlank(unAuthToken)) {
				text = AuthTips.INVALTOKEN.getText();			
			} else {
				String appIdCode = coverCode(unAuthToken);
				String appId = appIdCode.substring(0, appIdCode.length() - 10);
				AppSecret as = appSecretDao.getAppSecretByAppId(appId);
				long now = TimeGenerator.getInstance().getUnixTime();

				if (as != null) {
					String unAuthTokenDB = as.getUnAuthToken();
					long unAuthTokenTime = as.getUnAuthTokenTime();

					if (!unAuthTokenDB.equals(unAuthToken)
							|| (now >= unAuthTokenTime)) {
						text = AuthTips.INVALTOKEN.getText();
					} else {
						long authTokenTimeL = 0;
						String authTokenTime = PropertiesUtils.getStringByKey("auth.authtime");
						appId += now;
						String authToken = makeCode(appId);
						authTokenTimeL = authTokenTime != null ? Long
								.parseLong(authTokenTime) : 0;
						as.setAuthToken(authToken);
						as.setAuthTokenTime(now + authTokenTimeL);
						as.setUserId(su.getId());
						appSecretDao.updateAppSecret(as);
						code = "200";
						text = authToken;
					}
				} else {
					text = AuthTips.INVALIDAPPID.getText();
				}
			}
			ret.put("code", code);
			ret.put("text", text);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(LogUtils.getInstance().getErrorInfoFromException(e));
		}

		return ret.toString();
	}

	private ArrayList<String> makeAppId() {
		ArrayList<String> as = new ArrayList<String>();

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

	private String makeCode(String str) {
		String code = null;

		str = str == null ? "" : str;
		try {
			code = new SecretUtils().encrypt(str);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(LogUtils.getInstance().getErrorInfoFromException(e));
		}

		return code;
	}

	private String coverCode(String str) {
		String code = null;

		str = str == null ? "" : str;
		try {
			code = new SecretUtils().decrypt(str);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(LogUtils.getInstance().getErrorInfoFromException(e));
		}

		return code;
	}

	private String isBlank(Object o) {
		return o == null ? "" : o + "";
	}

	private AppSecretDao appSecretDao;
	private MemberDao memberDao;

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public void setAppSecretDao(AppSecretDao appSecretDao) {
		this.appSecretDao = appSecretDao;
	}

}
