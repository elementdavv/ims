package com.sealtalk.dao.adm.impl;

import java.util.List;

import org.hibernate.SQLQuery;

import com.sealtalk.common.BaseDao;
import com.sealtalk.dao.adm.BranchDao;
import com.sealtalk.model.TBranch;

public class BranchDaoImpl extends BaseDao<TBranch, Long> implements BranchDao {

	@SuppressWarnings("unchecked")
	@Override
	public List getBranchTree() {
		
		String sql = "select id,parent_id,name from t_branch";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		
		List list = query.list();
		
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getBrancTreeAndMember() {
		String sql = "select " +
				"BM.branch_id," +
				"BM.member_id," +
				"BM.position_id," +
				"BM.is_master," +
				"BC.id BCID," +
				"BC.parent_id," +
				"BC.name," +
				"M.id MID," +
				"M.account," +
				"M.fullname," +
				"M.logo," +
				"M.telephone," +
				"M.email," +
				"M.address," +
				"M.token," +
				"M.sex," +
				"M.birthday," +
				"M.workno," +
				"M.mobile," +
				"M.groupmax," +
				"M.groupuse," +
				"M.intro " +
				"from t_branch_member BM right join t_branch BC on BM.branch_id=BC.id left join t_member M on BM.member_id=M.id"; 
		
		try {
			SQLQuery query = this.getSession().createSQLQuery(sql);
			
			List list = query.list();
			
			/*for(int i = 0; i < list.size(); i++) {
				Object [] k = (Object[]) list.get(i);
				for(int j = 0; j < k.length;j++) {
					System.out.println(i + "--" + j + "--" + k[j]);
				}
			}*/
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getBranchMember(String branchId) {
		String sql = "select M.id,M.account,M.fullname,M.logo,M.telephone,M.email," +
			"M.address,M.token,M.sex,M.birthday,M.workno,M.mobile,M.groupmax,M.groupuse," +
			"M.intro from t_branch_member BM inner join t_member M on BM.member_id=M.id where BM.branch_id=" + branchId;	
		
		try {
			SQLQuery query = this.getSession().createSQLQuery(sql);
			
			List list = query.list();
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
