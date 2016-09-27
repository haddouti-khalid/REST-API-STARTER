package com.sqli.commons.core.util;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * Regular Epression Regexp utility classe. <br/>
 * 
 * Provide classic regexp pattern.
 * 
 */
public final class RegexUtil {

	public static final String TIME_HHMM_LONG_REGEX = "[0-9]+:[0-9][0-9]";
	public static final String FLOAT_REGEX = "-?[0-9]+(\\.[0-9]+)?";

	/**
	 * Checks if is long time hhmm.
	 * 
	 * @param toCheck
	 *            the to check
	 * @return true, if is long time hhmm
	 */
	public static boolean isLongTimeHHMM(String toCheck) {
		return !StringUtils.isEmpty(toCheck) && Pattern.matches(TIME_HHMM_LONG_REGEX, toCheck);
	}

	/**
	 * Checks if is float.
	 * 
	 * @param toCheck
	 *            the to check
	 * @return true, if is float
	 */
	public static boolean isFloat(String toCheck) {
		return !StringUtils.isEmpty(toCheck) && Pattern.matches(FLOAT_REGEX, toCheck);
	}
}
