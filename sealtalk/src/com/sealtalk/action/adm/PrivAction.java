package com.sealtalk.action.adm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.googlecode.sslplugin.annotation.Secured;
import com.sealtalk.common.BaseAction;
import com.sealtalk.model.TPriv;
import com.sealtalk.model.TRole;
import com.sealtalk.service.adm.PrivService;

import net.sf.json.JSONObject;


public class PrivAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * this value should comes from login info, just for test this time
	 */
	Integer organId = 1;
	
	public String getRoleList() {
		
		List list = privService.getRoleList();
		
		ArrayList<JSONObject> ja = new ArrayList<JSONObject>();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			TRole p = (TRole)it.next();
			JSONObject js = new JSONObject();
			js.put("id", p.getId());
			js.put("name", p.getName());
			ja.add(js);
		}
		
		returnToClient(ja.toString());
		return "text";
	}
	
	public String getMemberCountByRole() {

		Integer roleId = Integer.parseInt(this.request.getParameter("roleid"));

		int c = privService.getMemberCountByRole(roleId);
		
		JSONObject js = new JSONObject();
		js.put("count", c);
		returnToClient(js.toString());
		return "text";
	}
	
	public String getMemberByRole() {
		
		Integer roleid = Integer.parseInt(this.request.getParameter("roleid"));
		String p = this.request.getParameter("page");
		Integer page = p == null ? null : Integer.parseInt(p);
		String s = this.request.getParameter("itemsperpage");
		Integer itemsperpage = s == null ? null : Integer.parseInt(s);
		
		List list = privService.getMemberByRole(roleid, page, itemsperpage);
		Iterator it = list.iterator();
		
		ArrayList<JSONObject> ja = new ArrayList<JSONObject>();
		while(it.hasNext()) {
			Object[] o = (Object[])it.next();
			JSONObject js = new JSONObject();
			js.put("memberroleid", o[0]);
			js.put("membername", o[1]);
			js.put("branchname", o[2] == null ? "(未分组人员)" : o[2]);
			js.put("positionname", o[3]);
			js.put("memberid", o[4]);
			ja.add(js);
		}
		
		returnToClient(ja.toString());
		return "text";
	}

	public String getPrivByRole() {
	
		Integer roleid = Integer.parseInt(this.request.getParameter("roleid"));

		// 返回权限列表，roleid表示该角色具有的权限
		List list = privService.getPrivByRole(roleid);
		Iterator it = list.iterator();
		
		ArrayList<JSONObject> ja = new ArrayList<JSONObject>();
		while(it.hasNext()) {
			Object[] o = (Object[])it.next();
			JSONObject js = new JSONObject();
			js.put("privid", o[0]);
			js.put("privname", o[1]);
			js.put("parentid", o[2]);
			js.put("grouping", o[3]);
			js.put("roleid", o[4] == null ? "" : o[4]);
			ja.add(js);
		}
		
		returnToClient(ja.toString());
		return "text";
	}
	
	public String delMemberRole() {
		
		Integer id = Integer.parseInt(this.request.getParameter("id"));
		
		privService.delMemberRole(id);
		
		return returnajaxid(0);
	}

	public String saveRole() {
		
		String id = this.request.getParameter("roleid");
		Integer roleId = (id == null ? 0 : Integer.parseInt(id));
		String roleName = this.request.getParameter("rolename");
		String privs = this.request.getParameter("privs");

		privService.saveRole(roleId, roleName, privs);
		
		return returnajaxid(0);
	}

	public String saveRoleMember() {
		
		Integer roleId = Integer.parseInt(this.request.getParameter("roleid"));
		String memberlist = this.request.getParameter("memberlist");
		
		privService.saveRoleMember(roleId, memberlist);
		
		return returnajaxid(0);
	}
	
	public String delRole() {
		
		Integer roleId = Integer.parseInt(this.request.getParameter("roleid"));

		return returnajaxid(0);
	}
	
	PrivService privService;

	public PrivService getPrivService() {
		return privService;
	}

	public void setPrivService(PrivService privService) {
		this.privService = privService;
	}
	
}
