package com.sqli.myApp.converter;

import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.stereotype.Component;

/**
 * The Class ConversionService.
 */
@Component
public class ConversionService extends DefaultFormattingConversionService {

	/**
	 * Instantiates a new conversion service.
	 */
	public ConversionService() {
		// DefaultFormattingConversionService's default constructor
		// creates default formatters and converters
		super();
		// no need for explicit super()?

		// add custom formatters and converters
		addConverter(new DateConverter());
	}

}
