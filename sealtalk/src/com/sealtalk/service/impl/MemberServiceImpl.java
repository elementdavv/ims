package com.sealtalk.service.impl;

import net.sf.json.JSONObject;

import com.sealtalk.common.Tips;
import com.sealtalk.dao.MemberDao;
import com.sealtalk.model.TMember;
import com.sealtalk.service.MemberService;
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
			TMember member = memberDao.getOneOfMember(account);
			
			if (member != null) {
				jo.put("code", 0);
				jo.put("text", Tips.NULLUSER);
			} else {
				jo.put("code", 1);
				jo.put("fullname", member.getFullname());
				jo.put("logo", member.getLogo());
				jo.put("telephone", member.getTelephone());
				jo.put("email", member.getEmail());
				jo.put("address", member.getAddress());
				jo.put("organname", "组织暂定");
				jo.put("branchname", "部门暂定");
				jo.put("positionname", "职位暂定");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo.toString();
	}
	
	private MemberDao memberDao;
	
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

}
