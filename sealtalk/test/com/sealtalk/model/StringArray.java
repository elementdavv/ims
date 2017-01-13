package com.sealtalk.model;

import java.util.ArrayList;

public class StringArray {
	public static void main(String args[]) {
		/*String str = "admin";
		String[] array = {str};
		for(int i = 0;i < array.length; i++) {
			System.out.println(i + ": " + array[i]);
		}*/
	
		
		ArrayList<String> a = new ArrayList<String>();
		
		a.add("1");
		a.add("2");
		a.add("3");
		
		String[] b = new String[3];
		a.toArray(b);
		for(int i = 0; i < b.length; i++) {
			System.out.println(b[i]);
		}
	}
}
