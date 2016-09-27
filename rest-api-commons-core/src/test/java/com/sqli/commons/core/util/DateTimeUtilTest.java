package com.sqli.commons.core.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import com.sqli.commons.core.util.DateTimeUtil;

public class DateTimeUtilTest {

	@Test
	public void convertFloatToTimeTest() {
		assertEquals("10:00", DateTimeUtil.convertFloatToTime(10.0F));
		assertEquals("00:06", DateTimeUtil.convertFloatToTime(0.10F));
		assertEquals("00:00", DateTimeUtil.convertFloatToTime(0F));
		assertEquals("00:59", DateTimeUtil.convertFloatToTime(0.99F));
		assertEquals("01:59", DateTimeUtil.convertFloatToTime(1.99F));
		assertNull(DateTimeUtil.convertFloatToTime(null));
	}

	@Test
	public void convertStringToTimeTest() {
		assertEquals("10:00", DateTimeUtil.convertStringToTime("10.0"));
		assertEquals("00:06", DateTimeUtil.convertStringToTime("0.10"));
		assertEquals("00:00", DateTimeUtil.convertStringToTime("0"));
		assertEquals("00:59", DateTimeUtil.convertStringToTime("0.99"));
		assertEquals("01:59", DateTimeUtil.convertStringToTime("1.99"));
		assertNull(DateTimeUtil.convertStringToTime(null));
	}

	@Test
	public void convertDatetimeToStringTest() {
		GregorianCalendar cal = new GregorianCalendar(2013, 5, 1, 12, 00);
		assertEquals("01/06/2013 12:00",
				DateTimeUtil.convertDateTimeToString(cal.getTime()));
		assertNull(DateTimeUtil.convertDateTimeToString(null));
	}

	@Test
	public void convertDateToStringTest() {
		GregorianCalendar cal = new GregorianCalendar(2013, 5, 1, 12, 00);
		assertEquals("01/01/1970",
				DateTimeUtil.convertDateToString(new Date(0)));
		assertEquals("01/06/2013",
				DateTimeUtil.convertDateToString(cal.getTime()));
		assertNull(DateTimeUtil.convertDateTimeToString(null));
	}

	@Test
	public void convertTimeToFloatTest() {
		assertEquals((Float) 10.0F, DateTimeUtil.convertTimeToFloat("10:00"));
		assertEquals((Float) 0.10F, DateTimeUtil.convertTimeToFloat("0:06"));
		assertEquals((Float) 0F, DateTimeUtil.convertTimeToFloat("0:00"));
		assertEquals((Float) 1.0F, DateTimeUtil.convertTimeToFloat("0:60"));
		assertEquals((Float) 2.0F, DateTimeUtil.convertTimeToFloat("1:60"));
		assertNull(DateTimeUtil.convertTimeToFloat(null));
	}

	@Test
	public void convertStringDateDBToDateTest() {
		GregorianCalendar cal = new GregorianCalendar(2013, 5, 1, 12, 00);
		assertEquals(cal.getTime(),
				DateTimeUtil.convertStringDateDBToDate("2013-06-01 12:00:00"));
		assertNull(DateTimeUtil.convertStringDateDBToDate(null));
	}

	@Test
	public void convertStringDateDisplayToDateTest() {
		GregorianCalendar cal = new GregorianCalendar(2013, 5, 1, 00, 00);
		assertEquals(cal.getTime(),
				DateTimeUtil.convertStringDateDisplayToDate("01/06/2013"));
		assertNull(DateTimeUtil.convertStringDateDisplayToDate(null));
	}
}
