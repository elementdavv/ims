package com.sealtalk.service.adm;

import java.util.List;

public interface PrivService {

	List getRoleList();
	int getMemberCountByRole(Integer roleId);
	List getMemberByRole(Integer roleId, Integer page, Integer itemsperpage);
	void delMemberRole(Integer id);
	List getPrivByRole(Integer roleId);
	Integer saveRole(Integer roleId, String roleName, String privs);
	void delRole(Integer roleId);
	void saveRoleMember(Integer roleId, String memberlist);
	String getPrivStringByMember(Integer memberId);
	public List getRoleIdForId(int id);
}
