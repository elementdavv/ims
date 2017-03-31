package com.sealtalk.dao.privilege.impl;

import com.sealtalk.common.BaseDao;
import com.sealtalk.dao.privilege.MemberRoleDao;
import com.sealtalk.dao.privilege.PrivilegeDao;
import com.sealtalk.model.TMemberRole;
import com.sealtalk.model.TPriv;

public class MemberRoleDaoImpl extends BaseDao<TMemberRole, Long> implements MemberRoleDao {

	@Override
	public int deleteRelationByIds(String userids) {
		try {
			String hql = (new StringBuilder("delete from TMemberRole where memberId in (").append(userids).append(")")).toString();
			return delete(hql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
}
