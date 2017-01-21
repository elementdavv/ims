package com.sealtalk.service.privilege;

import com.sealtalk.model.SessionPrivilege;

public interface PrivilegeService {

	/**
	 * 生成权限session
	 * @param account
	 * @return
	 */
	public SessionPrivilege setPrivilege(String account);
	
}
