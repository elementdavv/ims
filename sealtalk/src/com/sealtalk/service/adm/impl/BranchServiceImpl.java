package com.sealtalk.service.adm.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.sealtalk.dao.adm.BranchDao;
import com.sealtalk.service.adm.BranchService;
import com.sealtalk.utils.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BranchServiceImpl implements BranchService {

	private Logger logger = Logger.getLogger(BranchServiceImpl.class);
	
	@SuppressWarnings("unchecked")
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

	@SuppressWarnings("unchecked")
	@Override
	public String getBranchTreeAndMember() {
		List list = branchDao.getBrancTreeAndMember();
		
		JSONArray ja = new JSONArray();
		
		ArrayList<Object> branchList = new ArrayList<Object>();
		
		try {
			for(int i = 0; i < list.size(); i++) {
				Object[] o = (Object[])list.get(i);
				JSONObject jm = new JSONObject();
				
				if (!StringUtils.getInstance().isNull(o[0])) {
					jm.put("flag", 1);
					jm.put("pid", isBlank(o[4]));
					jm.put("id", isBlank(o[7]));
					jm.put("account", isBlank(o[8]));
					jm.put("name", isBlank(o[9]));
					jm.put("logo", isBlank(o[10]));
					jm.put("telephone", isBlank(o[11]));
					jm.put("email", isBlank(o[12]));
					jm.put("address", isBlank(o[13]));
					jm.put("token", isBlank(o[14]));
					jm.put("sex", isBlank(o[15]));
					jm.put("birthday", isBlank(o[16]));
					jm.put("workno", isBlank(o[17]));
					jm.put("mobile", isBlank(o[18]));
					jm.put("groupmax", isBlank(o[19]));
					jm.put("groupuse", isBlank(o[20]));
					jm.put("intro", isBlank(o[21]));
					
					if (!branchList.contains(o[4])) {
						JSONObject jb = new JSONObject();
						jb.put("flag", 0);
						jb.put("id", isBlank(o[4]));
						jb.put("pid", isBlank(o[5]));
						jb.put("name", isBlank(o[6]));
						ja.add(jb);
						branchList.add(o[4]);
					}
				} else {
					jm.put("id", isBlank(o[4]));
					jm.put("pid", isBlank(o[5]));
					jm.put("name", isBlank(o[6]));
					jm.put("flag", 0);  
				}
				ja.add(jm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info(ja.toString());
		return ja.toString();
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public String getBranchMember(String branchId) {
		String result = null;
		boolean status = true;
		
		if (StringUtils.getInstance().isBlank(branchId)) {
			status = false;
		} else {
			List list = branchDao.getBranchMember(branchId);
			JSONArray ja = new JSONArray();
			
			try {
				for(int i = 0; i < list.size(); i++) {
					Object[] o = (Object[])list.get(i);
				
					if (!StringUtils.getInstance().isBlank((String)o[1])) {
						JSONObject jm = new JSONObject();
						jm.put("code", 1);
						jm.put("text", "ok");
						jm.put("id", isBlank(o[0]));
						jm.put("account", isBlank(o[1]));
						jm.put("fullname", isBlank(o[2]));
						jm.put("logo", isBlank(o[3]));
						jm.put("telephone", isBlank(o[4]));
						jm.put("email", isBlank(o[5]));
						jm.put("address", isBlank(o[6]));
						jm.put("token", isBlank(o[7]));
						jm.put("sex", isBlank(o[8]));
						jm.put("birthday", isBlank(o[9]));
						jm.put("workno", isBlank(o[10]));
						jm.put("mobile", isBlank(o[11]));
						jm.put("groupmax", isBlank(o[12]));
						jm.put("groupuse", isBlank(o[13]));
						jm.put("intro", isBlank(o[14]));
						ja.add(jm);
						jm = null;
					}
				}
				result = ja.toString();
			} catch (Exception e) {
				status = false;
				e.printStackTrace();
			}
		}
		
		if (!status) {
			JSONObject jo = new JSONObject();
			 
			 jo.put("code", -1);
			 jo.put("text", "err");
			 result = jo.toString();
		}
		
		return result;
	}
	
	private String isBlank(Object o) {
		return o == null ? "" : o + "";
	}
	
	private BranchDao branchDao;
	
	public BranchDao getBranchDao() {
		return branchDao;
	}

	public void setBranchDao(BranchDao branchDao) {
		this.branchDao = branchDao;
	}

}
