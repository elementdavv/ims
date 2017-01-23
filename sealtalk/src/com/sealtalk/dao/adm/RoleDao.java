package com.sealtalk.dao.adm;

import java.util.List;

import com.sealtalk.common.IBaseDao;
import com.sealtalk.model.TRole;

public interface RoleDao extends IBaseDao<TRole, Integer> {

	List getMemberByRole(Integer roleId);
	List getPrivByRole(Integer roleId);
}
