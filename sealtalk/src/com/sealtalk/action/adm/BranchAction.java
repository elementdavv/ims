/**
 * 
 */
package com.sealtalk.action.adm;

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
	public String getBranchTree() {
		
		String result = branchService.getBranchTree();
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
