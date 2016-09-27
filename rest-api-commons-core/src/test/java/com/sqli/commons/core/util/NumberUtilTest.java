package com.sqli.commons.core.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.sqli.commons.core.util.NumberUtil;

public class NumberUtilTest {
	@Test
	public void roundFloatTest() {
		assertEquals((Float) 2F, NumberUtil.roundFloat(1.999F));
		assertEquals((Float) 1.1F, NumberUtil.roundFloat(1.1F));
		assertEquals((Float) 1.0F, NumberUtil.roundFloat(1.001F));
		assertNull(NumberUtil.roundFloat(null));
	}
}
