package com.sealtalk.service.member.impl;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sealtalk.common.Tips;
import com.sealtalk.dao.member.MemberDao;
import com.sealtalk.dao.member.TextCodeDao;
import com.sealtalk.model.TMember;
import com.sealtalk.model.TextCode;
import com.sealtalk.service.member.MemberService;
import com.sealtalk.utils.PasswordGenerator;
import com.sealtalk.utils.PropertiesUtils;
import com.sealtalk.utils.StringUtils;
import com.sealtalk.utils.TimeGenerator;

public class MemberServiceImpl implements MemberService {

	@Override
	public TMember searchSigleUser(String name, String password) {
		TMember memeber = null;
		
		try {
			//password = PasswordGenerator.getInstance().getMD5Str(password);  //前端加密
			memeber = memberDao.searchSigleUser(name, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memeber;
	}
	
	@Override
	public boolean updateUserPwdForAccount(String account, String newPwd) {
		boolean status = false;
		
		try {
			//String md5Pwd = PasswordGenerator.getInstance().getMD5Str(newPwd);
			
			status = memberDao.updateUserPwdForAccount(account, newPwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return status;
	}

	@Override
	public boolean updateUserPwdForPhone(String phone, String newPwd) {
		boolean status = false;
		
		try {
			status = memberDao.updateUserPwdForPhone(phone, newPwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return status;
	}
	
	@Override
	public String getOneOfMember(String userId) {

		JSONObject jo = new JSONObject();
		
		try {
			int userIdInt = StringUtils.getInstance().strToInt(userId);
			
			Object[] member = memberDao.getOneOfMember(userIdInt);
			
			if (member == null) {
				jo.put("code", 0);
				jo.put("text", Tips.NULLUSER.getText());
			} else {
				for(int i = 0; i < member.length; i++) {
					jo.put("id", isBlank(member[0]));
					jo.put("account", isBlank(member[1]));
					jo.put("name", isBlank(member[2]));
					jo.put("logo", isBlank(member[3]));
					jo.put("telephone", isBlank(member[4]));
					jo.put("email", isBlank(member[5]));
					jo.put("address", isBlank(member[6]));
					jo.put("birthday", isBlank(member[7]));
					jo.put("workno", isBlank(member[8]));
					jo.put("mobile", isBlank(member[9]));
					jo.put("groupmax", isBlank(member[10]));
					jo.put("groupuse", isBlank(member[11]));
					jo.put("intro", isBlank(member[12]));
					jo.put("branchname", isBlank(member[13]));
					jo.put("positionname", isBlank(member[14]));
					jo.put("organname", isBlank(member[15]));
					jo.put("sex", isBlank(member[16]));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo.toString();
	}
	
	@Override
	public int updateUserTokenForId(String userId, String token) {
		int row = 0;
		
		try {
			row = memberDao.updateUserTokenForId(userId, token);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return row;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public String searchUser(String account) {
		JSONArray ja = new JSONArray();
		
		try {
			List members = memberDao.searchUser(account);
			
			if (members == null) {
				JSONObject jo = new JSONObject();
				jo.put("code", 0);
				jo.put("text", Tips.NULLUSER.getText());
			} else {
				for(int i = 0; i < members.size(); i++) {
					Object[] member = (Object[]) members.get(i);
					JSONObject jo = new JSONObject();
					jo.put("id", isBlank(member[0]));
					jo.put("account", isBlank(member[1]));
					jo.put("name", isBlank(member[2]));
					jo.put("logo", isBlank(member[3]));
					jo.put("telephone", isBlank(member[4]));
					jo.put("email", isBlank(member[5]));
					jo.put("address", isBlank(member[6]));
					jo.put("birthday", isBlank(member[7]));
					jo.put("workno", isBlank(member[8]));
					jo.put("mobile", isBlank(member[9]));
					jo.put("groupmax", isBlank(member[10]));
					jo.put("groupuse", isBlank(member[11]));
					jo.put("intro", isBlank(member[12]));
					jo.put("branchname", isBlank(member[13]));
					jo.put("positionname", isBlank(member[14]));
					jo.put("organname", isBlank(member[15]));
					jo.put("sex", isBlank(member[16]));
					ja.add(jo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ja.toString();
	}

	@Override
	public boolean valideOldPwd(String account, String oldPwd) {
		boolean b = false;
		
		try {
			b = memberDao.valideOldPwd(account, oldPwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
	
	@Override
	public String getTextCode(String phone) {
		String code = null;
		
		try {
			TextCode tc = textCodeDao.getTextCode(phone);
			
			if (tc != null) {
				long now = TimeGenerator.getInstance().getUnixTime();
				long createTime = tc.getCreateTime();
				long valideTime = StringUtils.getInstance().strToLong(PropertiesUtils.getStringByKey("code.validetime"));
				
				if ((now - createTime) >= valideTime) {
					code = "-1";
				} else {
					code = tc.getTextCode();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return code;
	}

	@Override
	public void saveTextCode(String phone, String code) {
		try {
			textCodeDao.deleteTextCode(phone);
			
			TextCode stc = new TextCode();
			stc.setPhoneNum(phone);
			stc.setTextCode(code);
			stc.setCreateTime(TimeGenerator.getInstance().getUnixTime());
			textCodeDao.saveTextCode(stc);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public String updateMemberInfoForWeb(String userId, String sex, String email, String phone, String sign) {
		
		JSONObject jo = new JSONObject();
		
		if (StringUtils.getInstance().isBlank(userId)) {
			jo.put("code", -1);
			jo.put("text", Tips.WRONGPARAMS.getText());
		} else {
			try {
				sex = sex.equals("男") ? "1" : "0";
				int userIdInt = StringUtils.getInstance().strToInt(userId);
				int ret = memberDao.updateMemeberInfoForWeb(userIdInt, sex, email, phone, sign);
				
				if (ret > 0) {
					jo.put("code", 1);
					jo.put("text", Tips.OK.getText());
				} else {
					jo.put("code", 0);
					jo.put("text", Tips.FAIL.getText());
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return jo.toString();
	}
	
	@Override
	public boolean updateUserPwd(String account, String newPwd) {
		boolean status = false;
		
		try {
			String md5Pwd = PasswordGenerator.getInstance().getMD5Str(newPwd);
			
			status = memberDao.updateUserPwd(account, md5Pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return status;
	}

	@Override
	public String updateMemberForApp(String userId, String email, String mobile, String phone, String address) {
		JSONObject jo = new JSONObject();
		
		if (StringUtils.getInstance().isBlank(userId)) {
			jo.put("code", -1);
			jo.put("text", Tips.WRONGPARAMS.getText());
		} else {
			try {
				int userIdInt = StringUtils.getInstance().strToInt(userId);
				int ret = memberDao.updateMemeberInfoForApp(userIdInt, email, mobile, phone, address);
				
				if (ret > 0) {
					jo.put("code", 1);
					jo.put("text", Tips.OK.getText());
				} else {
					jo.put("code", 0);
					jo.put("text", Tips.FAIL.getText());
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return jo.toString();
	}
	
	private String isBlank(Object o) {
		return o == null ? "" : o + "";
	}
	
	private TextCodeDao textCodeDao;
	private MemberDao memberDao;
	
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public TextCodeDao getTextCodeDao() {
		return textCodeDao;
	}

	public void setTextCodeDao(TextCodeDao textCodeDao) {
		this.textCodeDao = textCodeDao;
	}

	public MemberDao getMemberDao() {
		return memberDao;
	}

}
