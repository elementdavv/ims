package com.sealtalk.service.adm.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sealtalk.dao.adm.BranchDao;
import com.sealtalk.model.TBranch;
import com.sealtalk.service.adm.BranchService;

import net.sf.json.JSONObject;

public class BranchServiceImpl implements BranchService {

	@Override
	public String getBranchTree() {
		
		List list = branchDao.getBranchTree();
		Iterator it = list.iterator();
		ArrayList<JSONObject> jl = new ArrayList<JSONObject>();
		
		while(it.hasNext()) {
			JSONObject jo = new JSONObject();
			Object[] br = (Object[])it.next();
			jo.put("id", br[0]);
			jo.put("pId", br[1]);
			jo.put("name", br[2]);
			jl.add(jo);
		}
		
		return jl.toString();
	}

	private BranchDao branchDao;
	
	public BranchDao getBranchDao() {
		return branchDao;
	}

	public void setBranchDao(BranchDao branchDao) {
		this.branchDao = branchDao;
	}

}
