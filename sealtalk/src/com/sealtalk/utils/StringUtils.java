package com.sealtalk.utils;

public class StringUtils {
	private StringUtils (){}
	
	private static class Inner {
		private static final StringUtils SU = new StringUtils();
	}
	
	public static StringUtils getInstance() {
		return Inner.SU;
	}
	
	public boolean isBlank(String str) {
		return str == null || "".equals(str);
	}
	
	public boolean isNull(Object o) {
		return o == null;
	}
	
	public String careNull(String str) {
		return str == null ? "" : str;
	}
}
