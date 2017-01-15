package com.sealtalk.service.adm.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sealtalk.dao.adm.BranchDao;
import com.sealtalk.dao.adm.BranchMemberDao;
import com.sealtalk.dao.adm.MemberRoleDao;
import com.sealtalk.dao.member.MemberDao;
import com.sealtalk.model.TBranch;
import com.sealtalk.model.TBranchMember;
import com.sealtalk.model.TMember;
import com.sealtalk.model.TMemberRole;
import com.sealtalk.service.adm.BranchService;
import com.sealtalk.utils.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BranchServiceImpl implements BranchService {

	private BranchDao branchDao;
	private MemberDao memberDao;
	private BranchMemberDao branchMemberDao;
	private MemberRoleDao memberRoleDao;
	
	public BranchDao getBranchDao() {
		return branchDao;
	}
	public void setBranchDao(BranchDao branchDao) {
		this.branchDao = branchDao;
	}
	public MemberDao getMemberDao() {
		return memberDao;
	}
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	public BranchMemberDao getBranchMemberDao() {
		return branchMemberDao;
	}
	public void setBranchMemberDao(BranchMemberDao branchMemberDao) {
		this.branchMemberDao = branchMemberDao;
	}
	public MemberRoleDao getMemberRoleDao() {
		return memberRoleDao;
	}
	public void setMemberRoleDao(MemberRoleDao memberRoleDao) {
		this.memberRoleDao = memberRoleDao;
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
			jo.put("isParent", "true");
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

	@SuppressWarnings("unchecked")
	@Override
	public String getOrganOnlyTree(Integer organId) {
		
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
		
		return jl.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see com.sealtalk.service.adm.BranchService#getBranchById(java.lang.Long)
	 * by alopex
	 */
	@Override
	public String getBranchById(Integer branchId) {

		TBranch branch = branchDao.get(branchId);
		JSONObject jo = JSONObject.fromObject(branch);
		
		TMember manager = memberDao.get(branch.getManagerId());
		jo.put("manager", manager == null ? "" : manager.getFullname());
		
		return jo.toString();
	}

	@Override
	public TBranch getBranchObjectById(Integer branchId) {
		
		return branchDao.get(branchId);
	}

	@Override
	public TMember getMemberObjectById(Integer memberId) {
		
		return memberDao.get(memberId);
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
			Object[] pos = (Object[])it1.next();
			jo.put("positionId", pos[0]);
			jo.put("branchId", pos[1]);
			jo.put("branchMemberId", pos[2]);
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

	@Override
	public List getMemberBranchById(Integer memberId) {
		
		return branchDao.getBranchMember(memberId);
	}

	@Override
	public TBranchMember getBranchMemberByBranchPosition(Integer branchId, Integer positionId) {
		
		return branchMemberDao.getBranchMemberByBranchPosition(branchId, positionId);
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

	@Override
	public String saveBranch(TBranch branch) {
		
		branchDao.saveOrUpdate(branch);
		JSONObject jo = new JSONObject();
		jo.put("branchid", branch.getId());
		
		return jo.toString();
	}

	@Override
	public Integer saveMember(TMember member) {
		
		memberDao.saveOrUpdate(member);
		return member.getId();
	}

	@Override
	public TBranchMember getBranchMemberById(Integer branchMemberId) {
		
		return branchMemberDao.get(branchMemberId);
	}
	@Override
	public TMemberRole getMemberRoleByMemberId(Integer memberId) {
		
		List list = memberRoleDao.find("from TMemberRole where member_id = " + memberId);
		
		if (list == null) return null;
		return (TMemberRole)list.get(0);
	}
	@Override
	public Integer saveBranchMember(TBranchMember branchMember) {
		
		branchMemberDao.saveOrUpdate(branchMember);
		return branchMember.getId();
	}
	@Override
	public Integer saveMemberRole(TMemberRole memberRole) {
		
		memberRoleDao.saveOrUpdate(memberRole);
		return memberRole.getId();
	}
	@Override
	public void delBranchMember(Integer branchMemberId) {
		
		TBranchMember branchMember = branchMemberDao.get(branchMemberId);
		if (branchMember == null) return;
		
		if ("1".equals(branchMember.getIsMaster())) {
			branchMemberDao.selectMaster(branchMember.getMemberId());
		}
		branchMemberDao.delete(branchMember);
	}
	@Override
	public void setMaster(Integer branchMemberId) {
		
		TBranchMember branchMember = branchMemberDao.get(branchMemberId);
		if (branchMember == null) return;

		branchMemberDao.executeUpdate("update TBranchMember set isMaster = '0' where memberId = " + branchMember.getMemberId());
		branchMember.setIsMaster("1");
		branchMemberDao.update(branchMember);
	}

}
