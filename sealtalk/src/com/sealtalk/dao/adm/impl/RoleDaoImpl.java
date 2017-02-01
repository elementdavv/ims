package com.sealtalk.dao.adm.impl;

import java.util.List;

import com.sealtalk.common.BaseDao;
import com.sealtalk.dao.adm.RoleDao;
import com.sealtalk.model.TRole;

public class RoleDaoImpl extends BaseDao<TRole, Integer> implements RoleDao {

	@Override
	public int getMemberCountByRole(Integer roleId) {

		String sql = "select count(mr.id) memberrolecount"
				+ " from t_member_role mr"
				+ " left join t_member m on m.id = mr.member_id"
				+ " left join t_branch_member bm on m.id = bm.member_id"
				+ " left join t_branch b on b.id = bm.branch_id"
				+ " left join t_position p on p.id = bm.position_id"
				+ " where bm.is_master = 1 and mr.role_id = " + roleId;
		
		List list = runSql(sql);
		String c = String.valueOf(list.get(0));
		
		return Integer.parseInt(c);
	}

	@Override
	public List getMemberByRole(Integer roleId, Integer page, Integer itemsperpage) {
		
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
				+ " where bm.is_master = 1 and mr.role_id = " + roleId;
		if (page != null)
			sql += " limit " + page * itemsperpage + ", " + itemsperpage;
		
		return runSql(sql);
	}

	@Override
	public List getPrivByRole(Integer roleId) {

		String sql = "select p.id, p.name, p.parent_id parentid, p.grouping, rp.role_id roleid, p.url url"
				+ " from t_priv p"
				+ " left join t_role_priv rp"
				+ " on p.id = rp.priv_id and rp.role_id =" + roleId
				+ " order by p.parent_id desc, p.listorder desc";
		
		return runSql(sql);
	}

	@Override
	public List getPrivByMember(Integer memberId) {
		
		String sql = "select distinct p.id, p.name, p.parent_id parentid, p.grouping, p.url url"
				+ " from t_priv p"
				+ " left join t_role_priv rp on rp.priv_id = p.id"
				+ " left join t_member_role mr on mr.role_id = rp.role_id"
				+ " where mr.member_id = " + memberId;
		
		return runSql(sql);
	}

	
}
