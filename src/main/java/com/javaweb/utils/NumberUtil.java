package com.javaweb.utils;

public class NumberUtil {
	public static boolean isNumber(String str) {
		try {
			Long result = Long.parseLong(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
}
