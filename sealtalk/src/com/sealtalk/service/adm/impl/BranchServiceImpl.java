package com.sealtalk.service.adm.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sealtalk.dao.adm.BranchDao;
import com.sealtalk.dao.adm.BranchMemberDao;
import com.sealtalk.dao.adm.MemberRoleDao;
import com.sealtalk.dao.adm.PositionDao;
import com.sealtalk.dao.member.MemberDao;
import com.sealtalk.model.ImpUser;
import com.sealtalk.model.TBranch;
import com.sealtalk.model.TBranchMember;
import com.sealtalk.model.TMember;
import com.sealtalk.model.TMemberRole;
import com.sealtalk.model.TPosition;
import com.sealtalk.service.adm.BranchService;
import com.sealtalk.utils.PasswordGenerator;
import com.sealtalk.utils.PinyinGenerator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BranchServiceImpl implements BranchService {

	private BranchDao branchDao;
	private MemberDao memberDao;
	private BranchMemberDao branchMemberDao;
	private MemberRoleDao memberRoleDao;
	private PositionDao positionDao;
	
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
	public PositionDao getPositionDao() {
		return positionDao;
	}
	public void setPositionDao(PositionDao positionDao) {
		this.positionDao = positionDao;
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
			jo.put("name", "<img src='images/orga.png' style='padding-right: 10px'>" + br[1]);
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
			jo.put("name", "<img src='images/work.png' style='padding-right: 10px'>" + br[2]);
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
			jo.put("name", "<img src='images/memb.png' style='padding-right: 10px'>" + br[2]);
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
			jo.put("isParent", "true");
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
			j.put("branchname", bm[1] != null ? bm[1] : "（未分组人员）");
			j.put("positionname", bm[2] == null ? "(未知职务)" : bm[2]);
			j.put("ismaster", bm[3]);
			js.add(j);
		}
		jo.put("branchmember", js);
		
		return jo.toString();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sealtalk.service.adm.BranchService#getMemberByAccount(java.lang.String)
	 * by alopex
	 */
	@Override
	public TMember getMemberByAccount(String account) {
		
		return memberDao.getOneOfMember(account);
	}

	/*
	 * (non-Javadoc)
	 * @see com.sealtalk.service.adm.BranchService#getMemberByAccount(java.lang.String)
	 * by alopex
	 */
	@Override
	public TBranch getBranchByName(String name) {
		
		return branchDao.getOneOfBranch(name);
	}

	@Override
	public List getMemberBranchById(Integer memberId) {
		
		return branchDao.getBranchMember(memberId);
	}

	@Override
	public TBranchMember getBranchMemberByBranchPosition(Integer branchId, Integer positionId) {
		
		return branchMemberDao.getBranchMemberByBranchPosition(branchId, positionId);
	}

	@Override
	public TBranchMember getBranchMemberByBranchMember(Integer branchId, Integer memberId) {
		
		return branchMemberDao.getBranchMemberByBranchMember(branchId, memberId);
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
	public Integer saveBranch(TBranch branch) {
		
		branchDao.saveOrUpdate(branch);
		return branch.getId();
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
		
		if (list.isEmpty()) return null;
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
	public Integer delBranchMember(Integer branchMemberId) {
		
		TBranchMember branchMember = branchMemberDao.get(branchMemberId);
		
		//不存在，不能删除
		if (branchMember == null) return -1;
		List list = branchMemberDao.getBranchMemberByMember(branchMember.getMemberId());
		
		//只有一个，不能删除
		if (list.size() == 1) return branchMemberId;
		
		if ("1".equals(branchMember.getIsMaster())) {
			branchMemberDao.selectMaster(branchMember.getMemberId());
		}
		branchMemberDao.delete(branchMember);
		
		return 0;
	}
	@Override
	public void setMaster(Integer branchMemberId) {
		
		TBranchMember branchMember = branchMemberDao.get(branchMemberId);
		if (branchMember == null) return;

		branchMemberDao.executeUpdate("update TBranchMember set isMaster = '0' where memberId = " + branchMember.getMemberId());
		branchMember.setIsMaster("1");
		branchMemberDao.update(branchMember);
	}
	@Override
	public void reset(Integer memberId, String password) {
		
		TMember member = memberDao.get(memberId);
		member.setPassword(password);
		memberDao.update(member);
		
		// 发短信
	}
	@Override
	public void delMember(Integer memberId) {
		
		branchMemberDao.executeUpdate("delete from TBranchMember where memberId = " + memberId);
		memberRoleDao.executeUpdate("delete from TMemberRole where memberId = " + memberId);
		memberDao.executeUpdate("delete from TMember where id = " + memberId);
	}
	@Override
	public void delBranch(Integer branchId, Integer r, Integer organId) {
		
		TBranch branch = branchDao.get(branchId);
		
		List list = branchMemberDao.getBranchMemberByBranch(branchId);
		Iterator it = list.iterator();
		while(it.hasNext()) {
			TBranchMember bm = (TBranchMember)it.next();
			List list2 = branchMemberDao.getBranchMemberByMember(bm.getMemberId());
			if (list2.size() == 1) {
				TBranchMember bm2 = (TBranchMember)list2.get(0);
				bm2.setBranchId(organId);
				branchMemberDao.saveOrUpdate(bm2);
			}
			else {
				if (bm.getIsMaster().equals("1")) {
					branchMemberDao.selectMaster(bm.getMemberId());
				}
				branchMemberDao.delete(bm);
			}
		}
		
		List list3 = branchDao.getChildren(branchId);
		Iterator it3 = list3.iterator();

		while(it3.hasNext()) {
			TBranch b = (TBranch)it3.next();
			
			if (r == 1) {
				this.delBranch(b.getId(), r, organId);
			}
			else {
				b.setParentId(branch.getParentId());
				branchDao.saveOrUpdate(b);
			}
		}
		
		branchDao.delete(branch);
	}
	@Override
	public void movMember(Integer memberId, Integer pId, Integer toId) {
		
		TBranchMember bm = this.getBranchMemberByBranchMember(pId, memberId);

		TBranchMember tobm = this.getBranchMemberByBranchMember(toId, memberId);
		if (tobm == null) {
			bm.setBranchId(toId);
			branchMemberDao.saveOrUpdate(bm);
		}
		else {
			branchMemberDao.delete(bm);
		}
	}
	@Override
	public Integer movBranch(Integer branchId, Integer toId) {
		
		if (this.isDecendant(toId, branchId)) return 0;
		
		TBranch branch = branchDao.get(branchId);
		branch.setParentId(toId);
		branchDao.saveOrUpdate(branch);
		
		return branchId;
	}
	
	private boolean isDecendant(Integer branchId, Integer pId) {
		
		if (branchId < 101) return false;
		if (branchId.intValue() == pId.intValue()) return true;
		
		TBranch branch = branchDao.get(branchId);
		
		if (branch.getParentId().intValue() == pId.intValue()) return true;
		if (branch.getParentId().intValue() == 0) return false;
		return this.isDecendant(branch.getParentId(), pId);
	}

	@Override
	public JSONObject testUsers(JSONArray ja) {
		
		return branchDao.testUsers(ja);
	}
	
	@Override
	public void saveimp(JSONArray ja, Integer organId) {
		
		ArrayList<ImpUser> ua = new ArrayList<ImpUser>();
		int i = 0;
		while(i < ja.size()) {
			JSONObject js = (JSONObject)ja.get(i);
			ImpUser user = jsonToUser(js);
			ua.add(user);
			i++;
		}
		
		// 存人员
		Iterator<ImpUser> it = ua.iterator();
		while(it.hasNext()) {
			ImpUser user = it.next();
			TMember m = new TMember();
			m.setMobile(user.getMobile());
			m.setFullname(user.getName());
			m.setPinyin(PinyinGenerator.getPinYin(user.getName()));
			m.setWorkno(user.getWorkno());
			m.setSex(user.getSex().equals("男") ? "1" : "2");
			m.setTelephone(user.getTelephone());
			m.setEmail(user.getEmail());
			m.setAccount(pinyin2account(m.getPinyin()));
			m.setOrganId(organId);
			m.setPassword(PasswordGenerator.getInstance().getMD5Str("111111"));
			m.setGroupmax(0);
			m.setGroupuse(0);
			memberDao.save(m);
			user.setId(m.getId());
		}

		// 存部门
		it = ua.iterator();
		while(it.hasNext()) {
			ImpUser user = it.next();
			TBranch br = this.getBranchByName(user.getBranch());
			if (br == null) {
				br = new TBranch();
				br.setName(user.getBranch());
				br.setOrganId(organId);
				br.setParentId(0);
				TMember m = memberDao.getMemberByName(user.getManager());
				if (m == null) {
					br.setManagerId(0);
				}
				else {
					br.setManagerId(m.getId());
				}
				br.setListorder(0);
				branchDao.save(br);
			}
			user.setBranchId(br.getId());
		}
		
		// 存职位
		it = ua.iterator();
		while(it.hasNext()) {
			ImpUser user = it.next();
			TPosition p = positionDao.getPositionByName(organId, user.getPosition());
			if (p == null) {
				p = new TPosition();
				p.setName(user.getPosition());
				p.setOrganId(organId);
				p.setListorder(0);
				positionDao.save(p);
			}
			user.setPositionId(p.getId());
		}		

		// 存部门人员
		it = ua.iterator();
		while(it.hasNext()) {
			ImpUser user = it.next();
			TBranchMember bm = new TBranchMember();
			bm.setBranchId(user.getBranchId());
			bm.setMemberId(user.getId());
			bm.setPositionId(user.getPositionId());
			bm.setIsMaster("1");
			bm.setListorder(0);
			branchMemberDao.save(bm);
		}		
		
		// 发短信
		it = ua.iterator();
		while(it.hasNext()) {
			ImpUser user = it.next();
			
		}
	}

	private String pinyin2account(String pinyin) {
	
		TMember m = memberDao.getOneOfMember(pinyin);
		if (m == null) return pinyin;
		
		int i = 0;
		while (true) {
			String account = pinyin + String.valueOf(i);
			m = memberDao.getOneOfMember(account);
			if (m == null) return account;
			i++;
		}
	}
	
	private ImpUser jsonToUser(JSONObject j) {
		
		ImpUser user = new ImpUser();

		user.setMobile((String)j.get("mobile"));
		user.setName((String)j.get("name"));
		user.setWorkno((String)j.get("workno"));
		user.setSex((String)j.get("sex"));
		user.setBranch((String)j.get("branch"));
		user.setManager((String)j.get("manager"));
		user.setPosition((String)j.get("position"));
		user.setTelephone((String)j.get("telephone"));
		user.setEmail((String)j.get("email"));

		return user;
	}

	@Override
	public void impexcel(JSONArray ja, String path) throws IOException {
		
		String[] head = {"手机号","姓名","工号","性别","所属部门","部门领导","职位","座机号","邮箱"};
		String[] code = {"mobile","name","workno","sex","branch","manager","position","telephone","email"};

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sh = wb.createSheet();
		
		int i = 9;
		while(i-- > 0) {
//			sh.setColumnWidth(i, 2000);
		}
		
		XSSFRow row = sh.createRow(0);
		i = 0;
		while (i < 9) {
			XSSFCell cell = row.createCell(i);
			cell.setCellValue(head[i]);
			i++;
		}
		
		i = 0;
		while (i < ja.size()) {
			XSSFRow r = sh.createRow(i + 1);
			JSONObject js = (JSONObject)ja.get(i);
			int j = 0;
			while (j < 9) {
				XSSFCell c = r.createCell(j);
				c.setCellValue((String)js.get(code[j]));
				j++;
			}
			i++;
		}
		
		FileOutputStream fo = new FileOutputStream(path);
		wb.write(fo);
		fo.close();
	}
}