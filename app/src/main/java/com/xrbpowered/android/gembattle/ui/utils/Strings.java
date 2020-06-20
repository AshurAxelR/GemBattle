package com.xrbpowered.android.gembattle.ui.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Strings {

	private final static SimpleDateFormat clockFormat = new SimpleDateFormat("HH:mm", Locale.US);

	public static String clock() {
		return clockFormat.format(Calendar.getInstance().getTime());
	}

	@SuppressWarnings("ConstantConditions")
	public static String format(String format, Object... args) {
		return String.format(null, format, args);
	}

}
