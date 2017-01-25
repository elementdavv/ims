package com.sealtalk.service.adm;

import java.util.List;

public interface PrivService {

	List getRoleList();
	List getMemberByRole(Integer roleId);
	void delMemberRole(Integer id);
	List getPrivByRole(Integer roleId);
	void saveRole(Integer roleId, String roleName, String privs);
	void delRole(Integer roleId);
	void saveRoleMember(Integer roleId, String memberlist);
}
