package com.sealtalk.service.member.impl;

import net.sf.json.JSONObject;

import com.sealtalk.common.Tips;
import com.sealtalk.dao.member.MemberDao;
import com.sealtalk.model.TMember;
import com.sealtalk.service.member.MemberService;
import com.sealtalk.utils.PasswordGenerator;

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
	public String getOneOfMember(String account) {

		JSONObject jo = new JSONObject();
		
		try {
			Object[] member = memberDao.getOneOfMember(account);
			
			if (member == null) {
				jo.put("code", 0);
				jo.put("text", Tips.NULLUSER);
			} else {
				for(int i = 0; i < member.length; i++) {
					jo.put("id", isBlank(member[0]));
					jo.put("account", isBlank(member[1]));
					jo.put("fullname", isBlank(member[2]));
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
	
	private String isBlank(Object o) {
		return o == null ? "" : o + "";
	}
	
	private MemberDao memberDao;
	
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

}
