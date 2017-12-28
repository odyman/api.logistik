package co.id.exml.logistikdr.utils;

import android.annotation.SuppressLint;

public class Ayeuna {

	@SuppressLint("SimpleDateFormat")
	public static String getAyeuna(){
		java.util.Date dt = new java.util.Date();

		java.text.SimpleDateFormat sdf = 
				new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String currentTime = sdf.format(dt);
		return currentTime;
	}

}