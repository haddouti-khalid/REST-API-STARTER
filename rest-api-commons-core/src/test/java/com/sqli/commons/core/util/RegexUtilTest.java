package com.sqli.commons.core.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.sqli.commons.core.util.RegexUtil;

public class RegexUtilTest {

	@Test
	public void isTimeHHMMTest() {
		assertTrue(RegexUtil.isLongTimeHHMM("01:00"));
		assertTrue(RegexUtil.isLongTimeHHMM("81:00"));
		assertTrue(RegexUtil.isLongTimeHHMM("10:00"));
		assertFalse(RegexUtil.isLongTimeHHMM("10"));
		assertFalse(RegexUtil.isLongTimeHHMM("1.0"));
		assertFalse(RegexUtil.isLongTimeHHMM("10:"));
		assertFalse(RegexUtil.isLongTimeHHMM(":10"));
		assertFalse(RegexUtil.isLongTimeHHMM("1:0"));
	}

	@Test
	public void isFloatTest() {
		assertTrue(RegexUtil.isFloat("1"));
		assertTrue(RegexUtil.isFloat("1.1"));
		assertTrue(RegexUtil.isFloat("1111111.111111"));
		assertTrue(RegexUtil.isFloat("-1111111.111111"));
		assertFalse(RegexUtil.isFloat("abc"));
		assertFalse(RegexUtil.isFloat(".1111"));
	}

}
