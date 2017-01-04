package com.sealtalk.model;

public class StringArray {
	public static void main(String args[]) {
		String str = "admin";
		String[] array = {str};
		for(int i = 0;i < array.length; i++) {
			System.out.println(i + ": " + array[i]);
		}
	}
}
