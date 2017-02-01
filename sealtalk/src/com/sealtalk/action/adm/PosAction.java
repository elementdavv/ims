package com.sealtalk.action.adm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sealtalk.common.BaseAction;
import com.sealtalk.model.TMember;
import com.sealtalk.model.TPosition;
import com.sealtalk.service.adm.PositionService;

import net.sf.json.JSONObject;

public class PosAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PositionService positionService;
	
	public PositionService getPositionService() {
		return positionService;
	}
	public void setPositionService(PositionService positionService) {
		this.positionService = positionService;
	}

	public String getList() {
		
		TMember m = (TMember)this.getSessionAttribute("member");
		Integer organId = m.getOrganId();

		List list = positionService.getByOrgan(organId);
		
		ArrayList<JSONObject> js = new ArrayList<JSONObject>(); 
		Iterator it = list.iterator();
		while (it.hasNext()) {
			TPosition p = (TPosition)it.next();
			JSONObject jo = new JSONObject();
			jo.put("id", p.getId());
			jo.put("name", p.getName());
			js.add(jo);
		}
		
		returnToClient(js.toString());
		return "text";
	}
	
	public String del() {
		
		Integer id = Integer.parseInt(this.request.getParameter("id"));
		
		positionService.del(id);
		
		return returnajaxid(id);
	}
	
	public String save() {

		TMember m = (TMember)this.getSessionAttribute("member");
		Integer organId = m.getOrganId();

		String name = this.request.getParameter("name");
		
		TPosition p = positionService.save(name, organId);
		
		if (p == null) return this.returnajaxid(0);
		
		JSONObject js = new JSONObject();
		js.put("id", p.getId());
		js.put("name", p.getName());
		returnToClient(js.toString());
		
		return "text";
	}
}