/**
 * 
 */
package com.sealtalk.action.adm;


import javax.servlet.ServletException;

import com.sealtalk.common.BaseAction;
import com.sealtalk.service.adm.BranchService;

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
