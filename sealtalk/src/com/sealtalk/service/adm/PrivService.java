package com.sealtalk.service.adm;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.sealtalk.model.TPriv;

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
	
	/**
	 * 根据用户Id获取权限
	 * @param id
	 * @return
	 */
	public List getRoleIdForId(int id);
	
	/**
	 * 使用初始化账号登陆，获取满权限 
	 * @return
	 */
	public ArrayList<JSONObject> getInitLoginPriv();
	
}
