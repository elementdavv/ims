package com.sealtalk.dao.adm;

import java.util.List;

import com.sealtalk.common.IBaseDao;
import com.sealtalk.model.TGroup;

public interface GrpDao extends IBaseDao<TGroup, Integer> {

	public List getList(Integer page, Integer itemsperpage);
	public void delGroupMemberByGroup(Integer id);
	public Integer getMemberCountByGrp(Integer id);
	public List getMemberByGrp(Integer id, Integer page, Integer itemsperpage);
	public void changeCreator(Integer groupId, Integer groupMemberId);
}
