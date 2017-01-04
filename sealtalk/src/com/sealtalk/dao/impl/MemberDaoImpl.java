package com.sealtalk.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.sealtalk.common.BaseDao;
import com.sealtalk.dao.MemberDao;
import com.sealtalk.model.TMember;
import com.sealtalk.utils.PasswordGenerator;

/**
 * @功能  成员数据管理层
 * @author hao_dy
 * @date 2017/01/04
 * @since jdk1.7
 */
public class MemberDaoImpl extends BaseDao<TMember, Long> implements MemberDao {

	@Override
	public TMember searchSigleUser(String name, String password) {
		
		System.out.println(password);
		try {
			String pwd = PasswordGenerator.getInstance().getMD5Str(password);
			System.out.println(pwd);
			
			Criteria ctr = getCriteria();
			ctr.add(Restrictions.eq("account", name));
			ctr.add(Restrictions.eq("password", pwd));
			
			List list = ctr.list();
			
			if (list.size() > 0) {
				return (TMember) list.get(0);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
