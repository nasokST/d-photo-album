package com.dphotoalbum.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DPhotoAlbumUtils {
	
	public static boolean isByteArrayEmpty(byte[] byteAttay) {
		
        int sum = 0;
        for (byte b : byteAttay) {
            sum |= b;
        }

        return (sum == 0);		
	}

	public static String dateTimeToIsoStr(LocalDateTime dateTime) {
		if (null != dateTime) {
			return dateTime.format(DateTimeFormatter.ISO_DATE_TIME);			
		}

		return "";
	}
	
	public static LocalDateTime stringToDateTimeTo(String dateStr) {
		return LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_DATE_TIME);
	}
}
