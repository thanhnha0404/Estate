package com.javaweb.utils;

public class StringUtil {
	public static boolean CheckEmpty(String str) {
		if(str == null || str.equals("") || str.equals("null")) {
			return false;
		}
		return true;
	}
}
