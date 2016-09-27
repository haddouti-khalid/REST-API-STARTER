package com.sqli.commons.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility methods for Date and Time transformation to or from String.
 */
public final class DateTimeUtil {

	/** The format datetime display. */
	public static final String FORMAT_DATETIME_DISPLAY = "dd/MM/yyyy HH:mm";

	/** The format date. */
	public static final String FORMAT_DATE = "dd/MM/yyyy";

	/** The format datetime db. */
	public static final String FORMAT_DATETIME_DB = "yyyy-MM-dd HH:mm:ss";

	/** The logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(DateTimeUtil.class);

	/**
	 * Convert float to time HH:MM.
	 * 
	 * 
	 * @param f
	 *            the f
	 * @return the string
	 */
	public static String convertFloatToTime(Float f) {
		if (f == null) {
			return null;
		}

		Integer hh = (int) f.floatValue();
		Integer mm = Math.round((f - hh) * 60);
		StringBuffer result = new StringBuffer();
		if (hh < 0) {
			result.append('-');
		}
		if (Math.abs(hh) < 10) {
			result.append('0');
		}
		result.append(Math.abs(hh));
		result.append(':');

		if (mm < 10) {
			result.append('0');
		}
		result.append(mm);

		return result.toString();
	}

	/**
	 * Convert string to time.
	 * 
	 * @param s
	 *            the s
	 * @return the string
	 */
	public static String convertStringToTime(String s) {
		if (s == null) {
			return null;
		}

		Float f = Float.valueOf(s);
		return convertFloatToTime(f);
	}

	/**
	 * Convert date to string.
	 * 
	 * @param date
	 *            the date
	 * @return the string
	 */
	public static String convertDateTimeToString(Date date) {
		if (date == null) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATETIME_DISPLAY);
		return sdf.format(date);
	}

	/**
	 * Convert date to string.
	 * 
	 * @param date
	 *            the date
	 * @return the string
	 */
	public static String convertDateToString(Date date) {
		if (date == null) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		return sdf.format(date);
	}

	/**
	 * Convert date to string to be stored in db.
	 * 
	 * @param date
	 *            the date
	 * @return the string
	 */
	public static String convertDateToStringDB(Date date) {
		if (date == null) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATETIME_DB);
		return sdf.format(date);
	}

	/**
	 * Convert a Display String date ("dd/MM/yyyy") to a Db string date
	 * ("yyyy-MM-dd HH:mm:ss").
	 * 
	 * @param dateDb
	 *            the date db
	 * @return the string
	 */
	public static String convertDisplayDateStringToDBDateString(String dateDb) {
		if (dateDb == null) {
			return null;
		}
		Date date = convertStringDateDisplayToDate(dateDb);
		return convertDateToStringDB(date);
	}

	/**
	 * Convert time HH:MM to float.
	 * 
	 * @param hhmm
	 *            the hhmm
	 * @return the float
	 */
	public static Float convertTimeToFloat(String hhmm) {
		if (hhmm == null) {
			return null;
		}

		String[] timeSplit = hhmm.split(":");
		try {
			return Float.parseFloat(timeSplit[0])
					+ Float.parseFloat(timeSplit[1]) / 60;
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * Convert a string date in db (dd/MM/yyyy hh:mm) to date.
	 * 
	 * @param time
	 *            the time
	 * @return the date
	 */
	public static Date convertStringDateDBToDate(String time) {
		if (time != null) {
			try {
				return new SimpleDateFormat(FORMAT_DATETIME_DB).parse(time);
			} catch (ParseException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return null;
	}

	/**
	 * Convert a string date used for display (dd/MM/yyyy) to date.
	 * 
	 * @param time
	 *            the time
	 * @return the date
	 */
	public static Date convertStringDateDisplayToDate(String time) {
		if (time != null) {
			try {
				return new SimpleDateFormat(FORMAT_DATE).parse(time);
			} catch (ParseException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return null;
	}

	public static Date convertStringDateTimeDisplayToDate(String time) {
		if (time != null) {
			try {
				return new SimpleDateFormat(FORMAT_DATETIME_DISPLAY)
						.parse(time);
			} catch (ParseException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return null;
	}
}
