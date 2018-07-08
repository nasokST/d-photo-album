package com.dphotoalbum.config;

import java.util.HashMap;
import java.util.Map;

public enum PhotoCategoryType {
	UNKNOWN(0),
	FAMILY(1),
	SEA(2),
	MONTAIN(3);

	private int value;

	public int getValue() {
		return this.value;
	}

	private PhotoCategoryType(int value) {
		this.value = value;
	}

	private static final Map<Integer, PhotoCategoryType> typesByValue = new HashMap<Integer, PhotoCategoryType>();

	static {
		for (PhotoCategoryType type : PhotoCategoryType.values()) {
			typesByValue.put(type.value, type);
		}
	}

	public static PhotoCategoryType forValue(int value) {
		return typesByValue.get(value);
	}
}


