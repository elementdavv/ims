package com.sealtalk.dao.adm.impl;

import java.util.List;

import com.sealtalk.common.BaseDao;
import com.sealtalk.dao.adm.RoleDao;
import com.sealtalk.model.TRole;

public class RoleDaoImpl extends BaseDao<TRole, Integer> implements RoleDao {

	@Override
	public List getMemberByRole(Integer roleId) {
		
		String sql = "select mr.id memberroleid,"
				+ " m.fullname membername,"
				+ " b.name branchname,"
				+ " p.name positionname,"
				+ " m.id memberid"
				+ " from t_member_role mr"
				+ " left join t_member m on m.id = mr.member_id"
				+ " left join t_branch_member bm on m.id = bm.member_id"
				+ " left join t_branch b on b.id = bm.branch_id"
				+ " left join t_position p on p.id = bm.position_id"
				+ " where mr.role_id = " + roleId;
		
		return runSql(sql);
	}

	@Override
	public List getPrivByRole(Integer roleId) {

		String sql = "select p.id, p.name, p.parent_id parentid, p.grouping, rp.role_id roleid from t_priv p"
				+ " left join t_role_priv rp on p.id = rp.priv_id and rp.role_id =" + roleId
				+ " order by p.parent_id desc, p.listorder desc";
		
		return runSql(sql);
	}

	
}
