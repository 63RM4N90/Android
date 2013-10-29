package com.example.utils;

import java.util.Locale;

public class MyDate {

	public static String convertDate(String date) {

		String year = date.substring(0, 4);
		String month = date.substring(5, 7);
		String day = date.substring(8, 10);

		String hour = date.substring(11, 13);
		String min = date.substring(14, 16);

		String lan = Locale.getDefault().getLanguage();
		
		return ((lan == "en") ? (month + '/' + day) : (day + '/' + month)) + "/" + year + " " + hour + ":" + min + " hs";
	}
}
