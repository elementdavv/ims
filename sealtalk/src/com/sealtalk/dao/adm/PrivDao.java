package com.sealtalk.dao.adm;

import java.util.List;

import com.sealtalk.common.IBaseDao;
import com.sealtalk.model.TPriv;

public interface PrivDao extends IBaseDao<TPriv, Integer> {

	/**
	 * 根据url获取权限
	 * @param url
	 * @return
	 */
	public TPriv getPrivByUrl(String url);

	/**
	 * 根据权限id获取用户属性
	 * @param privId
	 * @return
	 */
	public List getMemberByPrivId(int privId);

}
