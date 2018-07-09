package com.dphotoalbum.utils;

public class DPhotoAlbumUtils {
	
	public static boolean isByteArrayEmpty(byte[] byteAttay) {
		
        int sum = 0;
        for (byte b : byteAttay) {
            sum |= b;
        }

        return (sum == 0);		
	}

}
