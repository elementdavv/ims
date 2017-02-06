package com.sealtalk.dao.adm;

import java.util.List;

import com.sealtalk.common.IBaseDao;
import com.sealtalk.model.TRole;

public interface RoleDao extends IBaseDao<TRole, Integer> {

	List getMemberByRole(Integer roleId, Integer page, Integer itemsperpage);
	int getMemberCountByRole(Integer roleId);
	/**
	 * 返回角色权限（会返回所有权限,做角色修改用的）
	 * @param roleId
	 * @return
	 */
	List getPrivByRole(Integer roleId);
	/**
	 * 返回角色权限（严格满足条件）
	 * @param roleId
	 * @return
	 */
	List getPrivilegeById(int roleId);
}
