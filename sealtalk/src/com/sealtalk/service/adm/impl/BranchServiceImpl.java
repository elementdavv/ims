package com.sealtalk.service.adm.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sealtalk.dao.adm.BranchDao;
import com.sealtalk.dao.member.MemberDao;
import com.sealtalk.model.TBranch;
import com.sealtalk.model.TMember;
import com.sealtalk.service.adm.BranchService;
import com.sealtalk.utils.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BranchServiceImpl implements BranchService {

	/*
	 * by alopex
	 */
	private MemberDao memberDao;
	
	public MemberDao getMemberDao() {
		return memberDao;
	}

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	/*
	 * (non-Javadoc)
	 * @see com.sealtalk.service.adm.BranchService#getOrganTree(java.lang.Integer)
	 * by alopex
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getOrganTree(Integer organId) {
		
		ArrayList<JSONObject> jl = new ArrayList<JSONObject>();

		List list = branchDao.getOrgan(organId);
		Iterator it = list.iterator();
		
		while(it.hasNext()) {
			JSONObject jo = new JSONObject();
			Object[] br = (Object[])it.next();
			jo.put("id", br[0]);
			jo.put("pid", 0);
			jo.put("name", br[1]);
			jo.put("flag", 0);
			jl.add(jo);
		}
		
		list = branchDao.getBranch(organId);
		it = list.iterator();
		
		while(it.hasNext()) {
			JSONObject jo = new JSONObject();
			Object[] br = (Object[])it.next();
			jo.put("id", br[0]);
			jo.put("pid", (Integer)br[1] == 0 ? organId : br[1]);
			jo.put("name", br[2]);
			jo.put("flag", 1);
			jl.add(jo);
		}
		
		list = branchDao.getMember(organId);
		it = list.iterator();
		
		while(it.hasNext()) {
			JSONObject jo = new JSONObject();
			Object[] br = (Object[])it.next();
			jo.put("id", br[0]);
			jo.put("pid", br[1]);
			jo.put("name", br[2]);
			jo.put("flag", 2);
			jl.add(jo);
		}
		
		return jl.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see com.sealtalk.service.adm.BranchService#getBranchById(java.lang.Long)
	 * by alopex
	 */
	@Override
	public String getBranchById(Integer branchId) {

		TBranch branch = branchDao.getBranchById(branchId);
		JSONObject jo = JSONObject.fromObject(branch);
		
		TMember manager = memberDao.get(branch.getManagerId());
		jo.put("manager", manager == null ? "" : manager.getFullname());
		
		return jo.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see com.sealtalk.service.adm.BranchService#getMemberById(java.lang.Integer)
	 * by alopex
	 */
	@Override
	public String getMemberById(Integer memberId) {
		
		TMember member = memberDao.get(memberId);

		JSONObject jo = JSONObject.fromObject(member);
		
		/*
		 * 取职务
		 */
		
		/*
		 * 取角色
		 */
		
		return jo.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see com.sealtalk.service.adm.BranchService#getRole()
	 * by alopex
	 */
	@Override
	public String getRole() {

		ArrayList<JSONObject> jl = new ArrayList<JSONObject>();

		List list = branchDao.getRole();
		Iterator it = list.iterator();
		
		while(it.hasNext()) {
			JSONObject jo = new JSONObject();
			Object[] br = (Object[])it.next();
			jo.put("id", br[0]);
			jo.put("name", br[1]);
			jl.add(jo);
		}

		return jl.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see com.sealtalk.service.adm.BranchService#getRole()
	 * by alopex
	 */
	@Override
	public String getSex() {

		ArrayList<JSONObject> jl = new ArrayList<JSONObject>();

		List list = branchDao.getSex();
		Iterator it = list.iterator();
		
		while(it.hasNext()) {
			JSONObject jo = new JSONObject();
			Object[] br = (Object[])it.next();
			jo.put("id", br[0]);
			jo.put("name", br[1]);
			jl.add(jo);
		}

		return jl.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see com.sealtalk.service.adm.BranchService#getRole()
	 * by alopex
	 */
	@Override
	public String getPosition() {

		ArrayList<JSONObject> jl = new ArrayList<JSONObject>();

		List list = branchDao.getPosition();
		Iterator it = list.iterator();
		
		while(it.hasNext()) {
			JSONObject jo = new JSONObject();
			Object[] br = (Object[])it.next();
			jo.put("id", br[0]);
			jo.put("name", br[1]);
			jl.add(jo);
		}

		return jl.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getBranchTreeAndMember() {
		List list = branchDao.getBrancTreeAndMember();
		
		JSONArray ja = new JSONArray();
		
		try {
			for(int i = 0; i < list.size(); i++) {
				Object[] o = (Object[])list.get(i);
				JSONObject jo = new JSONObject();
				jo.put("id", isBlank(o[21]));
				jo.put("pid", isBlank(o[5]));
				jo.put("name", isBlank(o[6]));
				jo.put("flag", 0);
				ja.add(jo);
				
				jo = null;
				
				if (!StringUtils.getInstance().isBlank((String)o[7])) {
					JSONObject jm = new JSONObject();
					jm.put("flag", 1);
					jm.put("pid", isBlank(o[1]));
					jm.put("id", isBlank(o[22]));
					jm.put("account", isBlank(o[7]));
					jm.put("name", isBlank(o[8]));
					jm.put("logo", isBlank(o[9]));
					jm.put("telephone", isBlank(o[10]));
					jm.put("email", isBlank(o[11]));
					jm.put("address", isBlank(o[12]));
					jm.put("token", isBlank(o[13]));
					jm.put("sex", isBlank(o[14]));
					jm.put("birthday", isBlank(o[15]));
					jm.put("workno", isBlank(o[16]));
					jm.put("mobile", isBlank(o[17]));
					jm.put("groupmax", isBlank(o[18]));
					jm.put("groupuse", isBlank(o[19]));
					jm.put("intro", isBlank(o[20]));
					ja.add(jm);
					jm = null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
