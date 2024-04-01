package com.openclassrooms.rentals.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DateUtil {

	public static String parseTimestamp(Timestamp timestamp) {
		if (timestamp == null) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		return dateFormat.format(timestamp);
	}
}
