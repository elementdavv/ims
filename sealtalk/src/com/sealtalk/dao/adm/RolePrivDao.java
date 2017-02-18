package com.sealtalk.dao.adm;

import java.util.List;

import com.sealtalk.common.IBaseDao;
import com.sealtalk.model.TRolePriv;

public interface RolePrivDao extends IBaseDao<TRolePriv, Integer> {

	/**
	 * 获取角色根据权限id
	 * @param privIds
	 * @return
	 */
	List<TRolePriv> getRolePrivsByPrivs(Integer[] privIds);

}
