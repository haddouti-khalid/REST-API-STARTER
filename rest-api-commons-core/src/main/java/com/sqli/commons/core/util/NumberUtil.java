package com.sqli.commons.core.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility classe for Number (Integer, Float, Double) transformation from and to String.
 */
public class NumberUtil {

	/** logger */
	private static Logger logger = LoggerFactory.getLogger(NumberUtil.class);

	/** for the display of float */
	private static NumberFormat floatFormat;
	/** For the display of integer */
	private static NumberFormat intFormat;

	static {
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
		otherSymbols.setDecimalSeparator('.');
		floatFormat = new DecimalFormat("0.0#", otherSymbols);
		floatFormat.setMaximumFractionDigits(2);
		floatFormat.setMaximumFractionDigits(2);
		intFormat = new DecimalFormat("0", otherSymbols);

	}

	/**
	 * The format of a float is marked by the addition of a .0
	 * 
	 * @param value
	 * @return
	 */
	public static String toFloatString(Float value) {
		if (value == null) {
			return null;
		}
		return floatFormat.format(value);
	}

	public static Float parseFloatLenient(String value) {
		try {
			return Float.parseFloat(value);
		} catch (NumberFormatException e) {
			logger.warn("Lenient float parsing warning: " + e.getMessage());
			return 0f;
		}
	}

	/**
	 * Round float.
	 * 
	 * @param f
	 *            the f
	 * @return the float
	 */
	public static Float roundFloat(Float f) {
		if (f == null) {
			return null;
		}

		return (float) Math.round(100 * f) / 100;
	}

	/**
	 * Convert a Float to a string formated as an integer or null.
	 * 
	 * @param toConvert
	 *            the to convert
	 * @return the string
	 */
	public static String toIntString(Float value) {
		if (value == null) {
			return null;
		}
		return intFormat.format(value);
	}

	/**
	 * Convert a Float to a rounded to 2 decimal string or null.
	 * 
	 * @param toConvert
	 *            the to convert
	 * @return the string
	 */
	public static String toRoundedString(Float toConvert) {
		if (toConvert == null) {
			return null;
		}
		return roundFloat(toConvert).toString();
	}

	/**
	 * Convert an Integer to string.
	 * 
	 * @param toConvert
	 *            the to convert
	 * @return the string
	 */
	public static String toString(Float toConvert) {
		if (toConvert == null) {
			return null;
		}
		return toConvert.toString();
	}

	/**
	 * Convert an Integer to a string or null.
	 * 
	 * @param toConvert
	 *            the to convert
	 * @return the string
	 */
	public static String toString(Integer toConvert) {
		if (toConvert == null) {
			return null;
		}
		return toConvert.toString();
	}

	/**
	 * Convert a Long to a string or null.
	 * 
	 * @param toConvert
	 *            the to convert
	 * @return the string
	 */
	public static String toString(Long toConvert) {
		if (toConvert == null) {
			return null;
		}
		return toConvert.toString();
	}
}
