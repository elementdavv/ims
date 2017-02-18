package com.sealtalk.dao.adm;

import java.util.List;

import com.sealtalk.common.IBaseDao;
import com.sealtalk.model.TMemberRole;

public interface MemberRoleDao extends IBaseDao<TMemberRole, Integer> {

	public TMemberRole getRoleForId(int id);

	/**
	 * 获取多个成员的角色
	 * @param ids
	 * @return
	 */
	public List<TMemberRole> getRolesForIds(Integer[] ids);


}
