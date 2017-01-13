/**
 * 
 */
package com.sealtalk.action.adm;

import javax.servlet.ServletException;

import com.sealtalk.common.BaseAction;
import com.sealtalk.service.adm.BranchService;
import com.sealtalk.utils.StringUtils;

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
		
		/*
		 * this value should comes from login info, just for test this time
		 */
		Integer organId = 1;
		
		String result = branchService.getOrganTree(organId);
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
	
	private BranchService branchService;
	
	public BranchService getBranchService() {
		return branchService;
	}

	public void setBranchService(BranchService branchService) {
		this.branchService = branchService;
	}
	
}
