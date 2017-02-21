package com.sealtalk.dao.adm;

import java.util.List;

import com.sealtalk.common.IBaseDao;
import com.sealtalk.model.TOrgan;

public interface OrgDao extends IBaseDao<TOrgan, Integer> {

	public List getProvince(); 
	public List getCity(Integer provinceId); 
	public List getDistrict(Integer cityId);
	public List getInward();
	public List getIndustry();
	public List getSubdustry(Integer industryId);
	public TOrgan getInfo(Integer orgId);
	public List getInfos(String soStr);
}
