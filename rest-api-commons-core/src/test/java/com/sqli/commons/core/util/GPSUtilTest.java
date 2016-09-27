package com.sqli.commons.core.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.sqli.commons.core.util.GPSUtil;

public class GPSUtilTest {
	@Test
	public void convertGPSDegMinSecToDecimalTest() {
		assertEquals((Float) 43.604164F, GPSUtil.convertGPSDegMinSecToDecimal("43", "36", "15"));
		assertEquals((Float) 15.000000F, GPSUtil.convertGPSDegMinSecToDecimal("15", "0", "0"));
		assertNull(GPSUtil.convertGPSDegMinSecToDecimal("", "", ""));
		assertNull(GPSUtil.convertGPSDegMinSecToDecimal(null, null, null));
	}

	@Test
	public void convertGPSDecimalToDegMinSecTest() {
		String[] expected = new String[] { "43", "36", "14.99" };
		String[] result = GPSUtil.convertGPSDecimalToDegMinSec(43.604164F);
		assertNotNull(result);
		assertEquals(expected.length, result.length);
		assertEquals(expected[0], result[0]);
		assertEquals(expected[1], result[1]);
		assertEquals(expected[2], result[2]);
		assertNull(GPSUtil.convertGPSDecimalToDegMinSec(null));
	}
}
