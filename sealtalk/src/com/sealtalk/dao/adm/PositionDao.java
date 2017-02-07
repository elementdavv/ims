package com.sealtalk.dao.adm;

import com.sealtalk.common.IBaseDao;
import com.sealtalk.model.TPosition;

public interface PositionDao extends IBaseDao<TPosition, Integer> {

	public TPosition getPositionByName(Integer organId, String name);
}
