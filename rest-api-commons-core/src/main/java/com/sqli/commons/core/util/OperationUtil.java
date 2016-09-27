package com.sqli.commons.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang3.StringUtils;

/**
 * Utility class to make addition and substraction with n number of parameters.
 */
public class OperationUtil {

	private OperationUtil() {
	}

	/**
	 * Substract.
	 * 
	 * @param num1
	 *            the num1
	 * @param nums
	 *            the nums
	 * @return the string
	 */
	public static String substract(String num1, String... nums) {
		BigDecimal result;
		if (StringUtils.isBlank(num1)) {
			result = BigDecimal.ZERO;
		} else {
			result = new BigDecimal(num1);
		}

		if (nums != null) {
			for (String num : nums) {
				if (StringUtils.isNotBlank(num)) {
					result = result.subtract(new BigDecimal(num));
				}
			}
		}

		if (result.scale() > 2) {
			result = result.setScale(2, RoundingMode.HALF_DOWN);
		}

		return result.toPlainString();
	}

	/**
	 * Adds the.
	 * 
	 * @param num1
	 *            the num1
	 * @param nums
	 *            the nums
	 * @return the string
	 */
	public static String add(String num1, String... nums) {
		BigDecimal result;
		if (StringUtils.isBlank(num1)) {
			result = BigDecimal.ZERO;
		} else {
			result = new BigDecimal(num1);
		}
		if (nums != null) {
			for (String num : nums) {
				if (StringUtils.isNotBlank(num)) {
					result = result.add(new BigDecimal(num));
				}
			}
		}

		if (result.scale() > 2) {
			result = result.setScale(2, RoundingMode.HALF_DOWN);
		}

		return result.toPlainString();
	}
}
