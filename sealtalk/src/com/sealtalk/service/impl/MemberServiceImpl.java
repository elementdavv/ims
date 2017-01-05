package com.sealtalk.service.impl;

import com.sealtalk.dao.MemberDao;
import com.sealtalk.model.TMember;
import com.sealtalk.service.MemberService;
import com.sealtalk.utils.PasswordGenerator;

public class MemberServiceImpl implements MemberService {

	@Override
	public TMember searchSigleUser(String name, String password) {
		TMember memeber = null;
		
		try {
			password = PasswordGenerator.getInstance().getMD5Str(password);
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

	
	private MemberDao memberDao;
	
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
}
