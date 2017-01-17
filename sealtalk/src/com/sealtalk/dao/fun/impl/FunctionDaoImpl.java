package com.sealtalk.dao.fun.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.sealtalk.common.BaseDao;
import com.sealtalk.dao.fun.FunctionDao;
import com.sealtalk.model.TFunction;

/**
 * 其它功能管理层
 * @author hao_dy
 * @date 2017/01/04
 * @since jdk1.7
 */
public class FunctionDaoImpl extends BaseDao<TFunction, Long> implements FunctionDao {

	@SuppressWarnings("unchecked")
	@Override
	public TFunction getFunctionStatus(String string) {
		try {
			Criteria ctr = getCriteria();
			ctr.add(Restrictions.eq("name", string));
			
			List<TFunction> list = ctr.list();
			
			if (list.size() > 0) {
				return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void setFunctionStatus(TFunction tf) {
		try {
			saveOrUpdate(tf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
