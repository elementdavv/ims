package com.sealtalk.common;

public enum FunctionName {
	NOTRECEIVEMSG("notreceivemsg"),
	SYSTIPVOICE("systipvoice");
	
	private String name;
	
	private FunctionName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
