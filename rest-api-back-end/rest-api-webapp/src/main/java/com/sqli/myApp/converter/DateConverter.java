package com.sqli.myApp.converter;

import java.util.Date;
import java.util.TimeZone;

import org.springframework.core.convert.converter.Converter;

/**
 * The Class DateConverter.
 */
public class DateConverter implements Converter<String, Date> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.core.convert.converter.Converter#convert(java.lang
	 * .Object)
	 */
	@Override
	public Date convert(String source) {
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));
		return new Date(Long.parseLong(source));
	}

}
