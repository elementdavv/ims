package com.sealtalk.service.adm.impl;

import java.util.List;

import com.sealtalk.dao.adm.MemberRoleDao;
import com.sealtalk.dao.adm.PrivDao;
import com.sealtalk.dao.adm.RoleDao;
import com.sealtalk.dao.adm.RolePrivDao;
import com.sealtalk.model.TMemberRole;
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
	public int getMemberCountByRole(Integer roleId) {
		
		return roleDao.getMemberCountByRole(roleId);
	}

	@Override
	public List getMemberByRole(Integer roleId, Integer page, Integer itemsperpage) {
		
		return roleDao.getMemberByRole(roleId, page, itemsperpage);
	}

	@Override
	public void delMemberRole(Integer id) {

		memberRoleDao.deleteById(id);
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
			role.setListorder(roleDao.getMax("listorder", "from TRole") + 1);
			roleDao.save(role);
		}
		
		rolePrivDao.delete("delete from TRolePriv where roleId = " + role.getId());
		
		String[] pa = privs.split(",");
		Integer i = pa.length;
		while(i-- > 0) {
			TRolePriv rolePriv = new TRolePriv();
			rolePriv.setRoleId(role.getId());
			rolePriv.setPrivId(Integer.parseInt(pa[i]));
			rolePrivDao.save(rolePriv);
		}
	}
	@Override
	public void delRole(Integer roleId) {
		
		rolePrivDao.delete("delete from TRolePriv where roleId = " + roleId);
		memberRoleDao.delete("delete from TMemberRole where roleId = " + roleId);
		roleDao.deleteById(roleId);
	}
	@Override
	public void saveRoleMember(Integer roleId, String memberlist) {
		
		memberRoleDao.delete("delete from TMemberRole where roleId = " + roleId);
		
		String[] ms = memberlist.split(",");
		Integer i = ms.length;
		while (i-- > 0) {
			TMemberRole mr = new TMemberRole();
			mr.setMemberId(Integer.parseInt(ms[i]));
			mr.setRoleId(roleId);
			mr.setListorder(0);
			memberRoleDao.save(mr);
		}
	}
	
}
