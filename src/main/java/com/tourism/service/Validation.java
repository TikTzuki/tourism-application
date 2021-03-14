package com.tourism.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Validation {
	public static boolean checkName(String name) {
		if (name.matches("[^0-9]"))
			return true;
		return false;
	}

	public static boolean checkPhone_Number(String phone_number) {
		if (phone_number.matches("0\\d{9}") && phone_number.length() == 10)
			return true;
		return false;
	}

	public static boolean checkEmail(String email) {
		if (email.matches("\\w+@\\w+[.]\\w+"))
			return true;
		return false;
	}
	
	public static boolean checkDate(String dateString) {
		if (dateString.matches("^\\d{4}\\-(0[1-9]|1[0-2])\\-(0[1-9]|[12][0-9]|3[01])$"))
			return true;
		return false;
	}
	
	public static boolean checkDigit(String digitString) {
		if(digitString.matches("(\\d+)"))
			return true;
		return false;
	}
	
	public static boolean isBefore(String dateBefore, String dateAfter) {
		SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if(spf.parse(dateBefore).before(spf.parse(dateAfter))){
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
}
