package com.sealtalk.service.privilege;

import com.sealtalk.model.SessionPrivilege;

public interface PrivilegeService {

	/**
	 * 生成权限session
	 * @param id
	 * @return
	 */
	public SessionPrivilege setPrivilege(int id);
	
}
