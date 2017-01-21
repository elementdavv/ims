package com.sealtalk.action.test;

import javax.servlet.ServletException;

import com.googlecode.sslplugin.annotation.Secured;
import com.sealtalk.common.BaseAction;
@Secured
public class TestAction extends BaseAction {

	private static final long serialVersionUID = -3827421291421868917L;

	public String test() throws ServletException {
		return "test";
	}
}
