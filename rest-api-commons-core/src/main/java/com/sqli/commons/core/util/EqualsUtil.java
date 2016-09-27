package com.sqli.commons.core.util;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * Null safe methods for object comparison.
 */
public final class EqualsUtil {

	public static boolean equals(String s1, String s2) {
		return StringUtils.equals(s1, s2) || StringUtils.isBlank(s1) && StringUtils.isBlank(s2);
	}

	public static boolean equals(Date date1, Date date2) {
		return date1 == null && date2 == null || date1.getTime() == date2.getTime();
	}

}
