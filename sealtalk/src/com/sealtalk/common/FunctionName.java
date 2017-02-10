package com.sealtalk.common;

public enum FunctionName {
	SYSTIPVOICE("systipvoice"),
	MSGTOP("msgtop");
	
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
