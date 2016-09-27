package com.sqli.commons.core.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sqli.commons.core.util.OperationUtil;

/**
 * The Class OperationUtilTest.
 */
public class OperationUtilTest {

	/**
	 * Adds the test.
	 */
	@Test
	public void addTest() {
		assertEquals("1", OperationUtil.add("1"));
		assertEquals("3", OperationUtil.add("1", "2"));
		assertEquals("3.00", OperationUtil.add("1.00000000", "2.00000"));
		assertEquals("6", OperationUtil.add("1", "2", "3"));
		assertEquals("6.3", OperationUtil.add("1.1", "2.1", "3.1"));
		assertEquals("6.33", OperationUtil.add("1.1122222", "2.111", "3.111"));
		assertEquals("3006.33", OperationUtil.add("1001.1122222", "1002.111", "1003.111"));
		// null safe tests
		assertEquals("1", OperationUtil.add("1", ""));
		assertEquals("1", OperationUtil.add("1", (String) null));
	}

	/**
	 * Substract test.
	 */
	@Test
	public void substractTest() {
		assertEquals("1", OperationUtil.substract("1"));
		assertEquals("-1", OperationUtil.substract("1", "2"));
		assertEquals("-1.00", OperationUtil.substract("1.00000000", "2.00000"));
		assertEquals("-4", OperationUtil.substract("1", "2", "3"));
		assertEquals("-4.1", OperationUtil.substract("1.1", "2.1", "3.1"));
		// null safe tests
		assertEquals("1", OperationUtil.substract("1", ""));
		assertEquals("1", OperationUtil.substract("1", (String) null));
	}
}
