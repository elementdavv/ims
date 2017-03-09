/**
 * 
 */
package com.sealtalk.action.adm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.ServletException;

import com.googlecode.sslplugin.annotation.Secured;
import com.sealtalk.common.BaseAction;
import com.sealtalk.dao.adm.BranchMemberDao;
import com.sealtalk.model.TBranch;
import com.sealtalk.model.TBranchMember;
import com.sealtalk.model.TMember;
import com.sealtalk.model.TMemberRole;
import com.sealtalk.service.adm.BranchService;
import com.sealtalk.utils.PasswordGenerator;
import com.sealtalk.utils.PinyinGenerator;
import com.sealtalk.utils.StringUtils;
import com.sealtalk.utils.TextHttpSender;
import com.sealtalk.utils.TimeGenerator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.bcloud.msg.http.HttpSender;

/**
 * @author alopex
 *
 */

public class BranchAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	/*
	 * 取部门树
	 * by alopex
	 */
	public String getOrganTree() throws ServletException {
		
		String result = branchService.getOrganTree(this.getOrganId());
		returnToClient(result);
		
		return "text";
	}
	
	/*
	 * 取部门树
	 * by alopex
	 */
	public String getOrganOnlyTree() throws ServletException {
		
		String result = branchService.getOrganOnlyTree(this.getOrganId());
		returnToClient(result);
		
		return "text";
	}
	
	/*
	 * 取部门通过部门id
	 * by alopex
	 */
	public String getBranchById() throws ServletException {
		
		String branchId = this.request.getParameter("id");
		
		String result = branchService.getBranchById(Integer.valueOf(branchId));
		returnToClient(result);
		
		return "text";
	}
	
	/*
	 * 取人员通过人员id
	 * by alopex
	 */
	public String getMemberById() throws ServletException {
		
		String memberId = this.request.getParameter("id");
		
		String result = branchService.getMemberById(Integer.valueOf(memberId));
		returnToClient(result);
		
		return "text";
	}

	/*
	 * 取部门人员通过id
	 * by alopex
	 */
	public String getBranchMemberById() throws ServletException {
		
		String branchMemberId = this.request.getParameter("branchmemberid");
		TBranchMember branchMember = branchService.getBranchMemberById(Integer.parseInt(branchMemberId));
		
		JSONObject jo = new JSONObject();
		jo.put("branchid", branchMember.getBranchId());
		jo.put("positionid", branchMember.getPositionId());
		
		returnToClient(jo.toString());
		
		return "text";
	}

	/*
	 * 取人员所在部门
	 */
	public String getMemberBranchById() throws ServletException {
		
		String memberId = this.request.getParameter("memberid");
		List list = branchService.getMemberBranchById(Integer.parseInt(memberId));
		
		ArrayList<JSONObject> joa = new ArrayList<JSONObject>();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Object[] o = (Object[])it.next();
			JSONObject jo = new JSONObject();
			jo.put("branchmemberid", o[0]);
			jo.put("branchname", o[1]);
			jo.put("positionname", o[2] == null ? "(未知职务)" : o[2]);
			jo.put("ismaster", o[3]);
			joa.add(jo);
		}
		returnToClient(joa.toString());
		
		return "text";
	}
	
	/*
	 * 取字典
	 * by alopex
	 */
	public String getRole() throws ServletException {
		
		String result = branchService.getRole();
		returnToClient(result);
		
		return "text";
	}
	public String getSex() throws ServletException {
		
		String result = branchService.getSex();
		returnToClient(result);
		
		return "text";
	}
	public String getPosition() throws ServletException {
		
		String result = branchService.getPosition();
		returnToClient(result);
		
		return "text";
	}
	
	public String saveBranch() throws ServletException {
		
		TBranch branch = null;
		String id = this.request.getParameter("branchid");
		if (id != null) {
			branch = branchService.getBranchObjectById(Integer.parseInt(id));
			if (!branch.getName().equalsIgnoreCase(this.request.getParameter("branchname"))) {
				if (branchService.getBranchByName(this.request.getParameter("branchname")) != null) {
					JSONObject jo = new JSONObject();
					jo.put("branchid", 0);
					returnToClient(jo.toString());
					return "text";
				}
			}
		}
		else {
			if (branchService.getBranchByName(this.request.getParameter("branchname")) != null) {
				JSONObject jo = new JSONObject();
				jo.put("branchid", 0);
				returnToClient(jo.toString());
				return "text";
			}
			branch = new TBranch();
			branch.setListorder(0);
		}
		if (this.request.getParameter("branchaddress") != null)
			branch.setAddress(this.request.getParameter("branchaddress"));
		if (this.request.getParameter("branchfax") != null)
			branch.setFax(this.request.getParameter("branchfax"));
		if (this.request.getParameter("branchintro") != null)
			branch.setIntro(this.request.getParameter("branchintro"));
		if (this.request.getParameter("branchmanagerid") != null)
			branch.setManagerId(Integer.parseInt(this.request.getParameter("branchmanagerid")));
		if (this.request.getParameter("branchname") != null)
			branch.setName(this.request.getParameter("branchname"));
		if (this.request.getParameter("branchparentid") != null)
			branch.setParentId(Integer.parseInt(this.request.getParameter("branchparentid")));
		if (this.request.getParameter("branchtelephone") != null)
			branch.setTelephone(this.request.getParameter("branchtelephone"));
		if (this.request.getParameter("branchwebsite") != null)
			branch.setWebsite(this.request.getParameter("branchwebsite"));

		branch.setOrganId(this.getOrganId());
		
		Integer branchId = branchService.saveBranch(branch);
		
		JSONObject jo = new JSONObject();
		jo.put("branchid", branchId);
		
		returnToClient(jo.toString());

		return "text";
	}
	
	
	public String saveMember() throws ServletException {
		
		TMember member = null;
		String id = this.request.getParameter("memberid");
		if (id != null) {
			member = branchService.getMemberObjectById(Integer.parseInt(id));
			if (!member.getAccount().equalsIgnoreCase(this.request.getParameter("memberaccount"))) {
				if (branchService.getMemberByAccount(this.request.getParameter("memberaccount")) != null) {
					JSONObject jo = new JSONObject();
					jo.put("memberid", 0);
					returnToClient(jo.toString());
					return "text";
				}
			}
		}
		else {
			if (branchService.getMemberByAccount(this.request.getParameter("memberaccount")) != null) {
				JSONObject jo = new JSONObject();
				jo.put("memberid", 0);
				returnToClient(jo.toString());
				return "text";
			}
			member = new TMember();
			member.setGroupmax(0);
			member.setGroupuse(0);
			member.setPassword(PasswordGenerator.getInstance().getMD5Str("111111"));
		}
		if (this.request.getParameter("memberaccount") != null)
			member.setAccount(this.request.getParameter("memberaccount"));
		if (this.request.getParameter("memberaddress") != null)
			member.setAddress(this.request.getParameter("memberaddress"));
		if (this.request.getParameter("memberbirthday") != null) {
			String bd = this.request.getParameter("memberbirthday");
			if (bd.length() == 10) {
				member.setBirthday(bd.substring(0,4) + bd.substring(5,7) + bd.substring(8,10));
			}
		}
		if (this.request.getParameter("memberemail") != null)
			member.setEmail(this.request.getParameter("memberemail"));
		if (this.request.getParameter("memberfullname") != null) {
			member.setFullname(this.request.getParameter("memberfullname"));
			member.setPinyin(PinyinGenerator.getPinYinHeadChar(this.request.getParameter("memberfullname")));
			member.setAllpinyin(PinyinGenerator.getPinYin(this.request.getParameter("memberfullname")));
		}
		if (this.request.getParameter("memberintro") != null)
			member.setIntro(this.request.getParameter("memberintro"));
		if (this.request.getParameter("membermobile") != null)
			member.setMobile(this.request.getParameter("membermobile"));
		if (this.request.getParameter("membersex") != null)
			member.setSex(this.request.getParameter("membersex"));
		if (this.request.getParameter("membertelephone") != null)
			member.setTelephone(this.request.getParameter("membertelephone"));
		if (this.request.getParameter("memberworkno") != null)
			member.setWorkno(this.request.getParameter("memberworkno"));
		
		member.setOrganId(this.getOrganId());

		long now = TimeGenerator.getInstance().getUnixTime();
		member.setCreatetokendate(Integer.valueOf(String.valueOf(now)));
		
		Integer memberId = branchService.saveMember(member);

		//部门职务
		TBranchMember branchMember = null;
		if (this.request.getParameter("branchmemberid") != null) {
			branchMember = branchService.getBranchMemberById(Integer.parseInt(this.request.getParameter("branchmemberid")));
		}
		else {
			branchMember = new TBranchMember();
			branchMember.setListorder(0);
			branchMember.setIsMaster("1");
		}
		branchMember.setMemberId(memberId);
		if (this.request.getParameter("memberbranchid") != null) {
			branchMember.setBranchId(Integer.parseInt(this.request.getParameter("memberbranchid")));
		}
		if (this.request.getParameter("memberpositionid") != null) {
			branchMember.setPositionId(Integer.parseInt(this.request.getParameter("memberpositionid")));
		}
		branchService.saveBranchMember(branchMember);
		
		//人员角色
		if (this.request.getParameter("memberroleid") != null) {
			TMemberRole memberRole = null;
			memberRole = branchService.getMemberRoleByMemberId(memberId);
			if (memberRole == null) {
				memberRole = new TMemberRole();
				memberRole.setMemberId(memberId);
				memberRole.setListorder(0);
			}
			memberRole.setRoleId(Integer.parseInt(this.request.getParameter("memberroleid")));
			branchService.saveMemberRole(memberRole);
		}
		
		//发短信
		String msg = "您的IMS产品帐号" + member.getAccount() + ", 密码111111.";
		TextHttpSender.getInstance().sendText(member.getMobile(), msg);
		
		JSONObject jo = new JSONObject();
		jo.put("memberid", memberId);
		
		returnToClient(jo.toString());
		
		return "text";
	}
	
	public String savePosition() throws ServletException {

		String branchmemberid = this.request.getParameter("branchmemberid");
		String memberid = this.request.getParameter("memberid");
		String branchid = this.request.getParameter("branchid");
		String positionid = this.request.getParameter("positionid");

		JSONObject jo = new JSONObject();
		Integer result = 0;
		
		TBranchMember branchMember = branchService.getBranchMemberByBranchPosition(
				Integer.parseInt(branchid),Integer.parseInt(positionid));
		if (branchMember == null) {
			branchMember = branchService.getBranchMemberById(Integer.parseInt(branchmemberid));
			//新增
			if (branchMember == null) {
				branchMember = new TBranchMember();
				branchMember.setBranchId(Integer.parseInt(branchid));
				branchMember.setMemberId(Integer.parseInt(memberid));
				branchMember.setPositionId(Integer.parseInt(positionid));
				branchMember.setIsMaster("0");
				branchMember.setListorder(0);
				result = branchService.saveBranchMember(branchMember);
			}
			//编辑
			else if (branchMember.getBranchId() != Integer.parseInt(branchid)
						|| branchMember.getPositionId() != Integer.parseInt(positionid)) {
				branchMember.setBranchId(Integer.parseInt(branchid));
				branchMember.setPositionId(Integer.parseInt(positionid));
				result = branchService.saveBranchMember(branchMember);
			}
		}

		jo.put("branchmemberid", result);
		returnToClient(jo.toString());
		return "text";
	}

	public String delBranchMember() throws ServletException {

		String branchmemberid = this.request.getParameter("branchmemberid");
		Integer result = branchService.delBranchMember(Integer.parseInt(branchmemberid));
		
		JSONObject jo = new JSONObject();
		jo.put("branchmemberid", result);
		returnToClient(jo.toString());
		return "text";
	}
	
	public String setMaster() throws ServletException {
		
		String branchmemberid = this.request.getParameter("branchmemberid");
		branchService.setMaster(Integer.parseInt(branchmemberid));
		
		JSONObject jo = new JSONObject();
		jo.put("branchmemberid", branchmemberid );
		returnToClient(jo.toString());
		return "text";
	}
	
	public String reset() throws ServletException {
		
		String memberid = this.request.getParameter("memberid");
		String newpassword = this.request.getParameter("newpassword");
		
		String md5password = PasswordGenerator.getInstance().getMD5Str(newpassword);
		
		branchService.reset(Integer.parseInt(memberid), md5password);

		// 发短信
		TMember member = branchService.getMemberObjectById(Integer.parseInt(memberid));
		String msg = "您的IMS密码已重置为" + newpassword;
		TextHttpSender.getInstance().sendText(member.getMobile(), msg);
		
		JSONObject jo = new JSONObject();
		jo.put("branchmemberid", memberid );
		returnToClient(jo.toString());
		return "text";
	}
	
	public String del() throws ServletException {
		
		Integer id = Integer.parseInt(this.request.getParameter("id"));
		Integer r = Integer.parseInt(this.request.getParameter("r"));
		
		// 删除组织
		if (id < 101) {
		}
		// 删除部门
		else if (id < 10001) {
			branchService.delBranch(id, r, this.getOrganId());
		}
		// 删除人员
		else {
			branchService.delMember(id);
		}
		
		JSONObject jo = new JSONObject();
		jo.put("id", id);
		returnToClient(jo.toString());
		return "text";
	}

	public String mov() throws ServletException {
		
		Integer id = Integer.parseInt(this.request.getParameter("id"));
		Integer pid = Integer.parseInt(this.request.getParameter("pid"));
		Integer toid = Integer.parseInt(this.request.getParameter("toid"));

		// 移动组织
		if (id < 101) {
		}
		// 移动部门
		else if (id < 10001) {
			id = branchService.movBranch(id, toid);
		}
		// 移动人员
		else {
			branchService.movMember(id, pid, toid);
		}
		JSONObject jo = new JSONObject();
		jo.put("id", id);
		returnToClient(jo.toString());
		return "text";
	}
	
	public String impcheck() throws ServletException {
		
		String jtext = this.request.getParameter("jtext");
		JSONArray ja = JSONArray.fromObject(jtext);
		
		JSONObject js = branchService.testUsers(ja);
		
		returnToClient(js.toString());
		
		return "text";
	}
	
	public String impsave() throws ServletException, FileNotFoundException, IOException {

		String jtext = this.request.getParameter("jtext");
		JSONArray ja = JSONArray.fromObject(jtext);

		branchService.saveimp(ja, this.getOrganId());
		
		String path = request.getSession().getServletContext().getRealPath("./upload/导入成功.xlsx");
		branchService.impexcel(ja, path);
		
		JSONObject js = new JSONObject();
		js.put("status", 0);
		js.put("succeed", ja.size());
		js.put("fail", 0);

		returnToClient(js.toString());

		return "text";
	}

	/*
	 * 取部门树
	 */
	public String getBranchTree() throws ServletException {
		
		String result = branchService.getBranchTree();
		returnToClient(result);
		
		return "text";
	}

	/**
	 * 取得部门+成员数据
	 * @return
	 * @throws ServletException
	 */
	public String getBranchTreeAndMember() throws ServletException {
		String result = branchService.getBranchTreeAndMember();
			
		returnToClient(result);
		
		return "text";
	}
	
	/**
	 * 取得指定部门的成员
	 * @return
	 * @throws ServletException
	 */
	public String getBranchMember() throws ServletException {
		
		String result = branchService.getBranchMember(branchId);
		
		returnToClient(result);
		return "text";
	}
	
	private BranchService branchService;

	public BranchService getBranchService() {
		return branchService;
	}

	public void setBranchService(BranchService branchService) {
		this.branchService = branchService;
	}
	
	private String branchId;

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	
	
}
