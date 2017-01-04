package com.sealtalk.service.impl;

import com.sealtalk.dao.MemberDao;
import com.sealtalk.model.TMember;
import com.sealtalk.service.MemberService;

public class MemberServiceImpl implements MemberService {

	@Override
	public TMember searchSigleUser(String name, String password) {
		TMember memeber = null;
		
		try {
			memeber = memberDao.searchSigleUser(name, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memeber;
	}
	
	private MemberDao memberDao;
	
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

}
