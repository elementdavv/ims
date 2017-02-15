package com.sealtalk.utils;

import java.util.ArrayList;
import java.util.List;
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
		
		str = str.replace("\"", "");
		
		if(isNumeric(str)) {
			return Integer.parseInt(str);
		} else {
			return -1;
		}
	}
	
	public long strToLong(String str) {
		str = str.replace("\"", "");
		
		if(isNumeric(str)) {
			return Long.parseLong(str);
		} else {
			return -1;
		}
	}
	
	public String replaceChar(String str, String srcChars, String distChars) {
		if (!isBlank(str) && !isBlank(srcChars) && !isNull(distChars)) {
			return str.replace(srcChars, distChars);
		} 
		return str;
	}
	
	public boolean isNumeric(String str){ 
	   Pattern pattern = Pattern.compile("[0-9]*"); 
	   Matcher isNum = pattern.matcher(str);
	   if( !isNum.matches() ){
	       return false; 
	   } 
	   return true; 
	}
	
	public boolean isStartChar(String str, String seper) {
		return str.startsWith(seper);
	}
	
	public boolean isEndChar(String str, String seper) {
		return str.endsWith(seper);
	}

	public String subString(String str, int start, int end) {
		return str.substring(start, end);
	}
	
	public String clearNumPoint(String str) {
		if (str != null) {
			int pos = str.lastIndexOf(".");
			
			if (pos == -1) {
				return str;
			} 
			return str.substring(0,pos);
		}
		return str;
	}
	
	public String[] stringSplit(String str, String seper) {
 		if (!isBlank(str)) {
			return str.split(seper);
		}
		
		return null;
	}
	
	public Integer[] stringArrToIntArr(String[] str) {
		
		if (!isArrayBlank(str)) {
			Integer [] tempIds = new Integer[str.length];
			
			for(int i = 0; i < str.length; i++) {
				tempIds[i] = strToInt(str[i]);	
			}
			
			return tempIds;
		}
		
		return null;
	}
	
	public List<Integer> stringArrToListInt(String[] str) {
		
		if (!isArrayBlank(str)) {
			List<Integer> tempIds = new ArrayList<Integer>();
			
			for(int i = 0; i < str.length; i++) {
				tempIds.add(strToInt(str[i]));	
			}
			
			return tempIds;
		}
		
		return null;
		
	}
	
	/**
	 * 转义字符+
	 */
	public String plusSlash(String str) {
        return str.replaceAll("+", "\\+");
	}

}
