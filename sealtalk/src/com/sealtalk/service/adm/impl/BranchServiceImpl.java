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
	@SuppressWarnings("rawtypes")
	@Override
	public String getMemberById(Integer memberId) {
		
		TMember member = memberDao.get(memberId);

		JSONObject jo = JSONObject.fromObject(member);
		
		/*
		 * 取职务
		 */
		List list1 = memberDao.getMemberPosition(memberId);
		Iterator it1 = list1.iterator();
		if (it1.hasNext()) {
			Object pos = (Object)it1.next();
			jo.put("positionId", pos);
		}
		
		/*
		 * 取角色
		 */
		List list2 = memberDao.getMemberRole(memberId);
		Iterator it2 = list2.iterator();
		if (it2.hasNext()) {
			Object rol = (Object)it2.next();
			jo.put("roleId", rol);
		}
		
		/*
		 * 取所在部门
		 */
		ArrayList<JSONObject> js = new ArrayList<JSONObject>();
		List list3 = branchDao.getBranchMember(memberId);
		Iterator it3 = list3.iterator();
		while (it3.hasNext()) {
			JSONObject j = new JSONObject();
			Object[] bm = (Object[])it3.next();
			j.put("branchmemberid", bm[0]);
			j.put("branchname", bm[1]);
			j.put("positionname", bm[2]);
			j.put("ismaster", bm[3]);
			js.add(j);
		}
		jo.put("branchmember", js);
		
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

	private BranchDao branchDao;
	
	public BranchDao getBranchDao() {
		return branchDao;
	}

	public void setBranchDao(BranchDao branchDao) {
		this.branchDao = branchDao;
	}

}
