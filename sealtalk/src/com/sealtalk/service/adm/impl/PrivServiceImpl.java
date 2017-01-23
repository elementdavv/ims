package com.sealtalk.service.adm.impl;

import java.util.List;

import com.sealtalk.dao.adm.MemberRoleDao;
import com.sealtalk.dao.adm.PrivDao;
import com.sealtalk.dao.adm.RoleDao;
import com.sealtalk.dao.adm.RolePrivDao;
import com.sealtalk.model.TRole;
import com.sealtalk.model.TRolePriv;
import com.sealtalk.service.adm.PrivService;

public class PrivServiceImpl implements PrivService {

	RoleDao roleDao;
	PrivDao privDao;
	RolePrivDao rolePrivDao;
	MemberRoleDao memberRoleDao;
	
	public RoleDao getRoleDao() {
		return roleDao;
	}
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	public PrivDao getPrivDao() {
		return privDao;
	}
	public void setPrivDao(PrivDao privDao) {
		this.privDao = privDao;
	}
	public MemberRoleDao getMemberRoleDao() {
		return memberRoleDao;
	}
	public void setMemberRoleDao(MemberRoleDao memberRoleDao) {
		this.memberRoleDao = memberRoleDao;
	}

	public RolePrivDao getRolePrivDao() {
		return rolePrivDao;
	}
	public void setRolePrivDao(RolePrivDao rolePrivDao) {
		this.rolePrivDao = rolePrivDao;
	}

	@Override
	public List getRoleList() {
		
		return roleDao.find("from TRole order by listorder desc");
	}

	@Override
	public List getMemberByRole(Integer roleId) {
		
		return roleDao.getMemberByRole(roleId);
	}

	@Override
	public void delMemberRole(Integer roleId) {

		memberRoleDao.deleteById(roleId);
	}

	@Override
	public List getPrivByRole(Integer roleId) {
		
		return roleDao.getPrivByRole(roleId);
	}

	@Override
	public void saveRole(Integer roleId, String roleName, String privs) {

		TRole role = roleDao.get(roleId);
		if (role == null) {
			role = new TRole();
			role.setName(roleName);
			role.setListorder(roleDao.count("id") + 1);
			roleDao.save(role);
		}
		
		rolePrivDao.delete("delete from TRolePriv where roleId = " + role.getId());
		
		String[] pa = privs.split(",");
		Integer i = pa.length;
		while(i-- > 0) {
			TRolePriv rolePriv = new TRolePriv();
			rolePriv.setRoleId(roleId);
			rolePriv.setPrivId(Integer.parseInt(pa[i]));
			rolePrivDao.save(rolePriv);
		}
	}
	@Override
	public void delRole(Integer roleId) {
		
		rolePrivDao.delete("delete from TRolePriv where roleId = " + roleId);
		memberRoleDao.delete("delete from TMemberRole where roleId =" + roleId);
		roleDao.deleteById(roleId);
	}
	
}
