package com.sqli.commons.core.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Util classe for GPS coordinates transformation from and to String/Float.
 */
public class GPSUtil {

	/**
	 * Convert gps decimal to deg min sec.
	 * 
	 * @param gps
	 *            the gps
	 * @return the string[]
	 */
	public static String[] convertGPSDecimalToDegMinSec(Float gps) {
		if (gps == null) {
			return null;
		}

		String[] tab = new String[3];
		int deg = (int) Math.floor(gps);
		int min = (int) Math.floor((gps - deg) * 60);
		float sec = NumberUtil.roundFloat((((gps - deg) * 60 - min) * 60));
		tab[0] = String.valueOf(deg);
		tab[1] = String.valueOf(min);
		tab[2] = String.valueOf(sec);
		return tab;
	}

	/**
	 * Convert gps deg min sec to decimal.
	 * 
	 * @param degString
	 *            the deg string
	 * @param minString
	 *            the min string
	 * @param secString
	 *            the sec string
	 * @return the float
	 */
	public static Float convertGPSDegMinSecToDecimal(String degString, String minString, String secString) {
		boolean gpsCheck = !StringUtils.isBlank(degString) || !StringUtils.isBlank(minString) || !StringUtils.isBlank(secString);

		if (gpsCheck) {
			Integer deg = 0;
			Integer min = 0;
			Float sec = (float) 0;
			if (!StringUtils.isBlank(degString)) {
				deg = Integer.parseInt(degString);
			}
			if (!StringUtils.isBlank(minString)) {
				min = Integer.parseInt(minString);
			}
			if (!StringUtils.isBlank(secString)) {
				sec = Float.parseFloat(secString);
			}
			return deg + ((float) min / 60) + (sec / (60 * 60));
		}
		return null;
	}

}
