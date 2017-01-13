package com.sealtalk.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	public boolean isArrayBlank(String[] strs) {
		return strs == null || strs.length == 0;
	}
	
	public boolean isNull(Object o) {
		return o == null;
	}
	
	public String careNull(String str) {
		return str == null ? "" : str;
	}
	
	public int strToInt(String str) {
		if(isNumeric(str)) {
			return Integer.parseInt(str);
		} else {
			return -1;
		}
	}
	
	public boolean isNumeric(String str){ 
	   Pattern pattern = Pattern.compile("[0-9]*"); 
	   Matcher isNum = pattern.matcher(str);
	   if( !isNum.matches() ){
	       return false; 
	   } 
	   return true; 
	}
}
