package com.sealtalk.dao.map;

import java.util.List;

import com.sealtalk.common.IBaseDao;
import com.sealtalk.model.TMap;

public interface MapDao extends IBaseDao<TMap, Long> {

	/**
	 * 获取单人坐标
	 * @param targetId
	 * @return
	 */
	public Object[] getLocation(int targetId);

	/**
	 * 获取群成员坐标
	 * @param targetIdInt
	 * @return
	 */
	public List<Object[]> getLocationForGroupId(int[] targetIdInt);

	/**
	 * 提交个人位置
	 * @param tm
	 */
	public void subLocation(TMap tm);

}
