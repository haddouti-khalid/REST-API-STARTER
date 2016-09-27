package com.sqli.commons.core.config.core.properties.conversion;

import java.lang.reflect.Field;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Maps;
import com.sqli.commons.core.config.core.util.JodaUtils;

/**
 * Default implementation of {@link PropertyConversionService}, attempting to
 * convert an object otherwise utilising {@link SimpleTypeConverter} if no
 * matching converter is found.
 * 
 * @author James Morgan
 */
@Component
public class DefaultPropertyConversionService implements
		PropertyConversionService {

	/** The converts. */
	private static Map<Class<? extends Object>, Function<Object, ?>> CONVERTS = Maps
			.newHashMap();
	static {
		CONVERTS.put(Period.class, new PeriodConverter());
		CONVERTS.put(LocalDateTime.class, new LocalDateTimeConverter());
		CONVERTS.put(LocalDate.class, new LocalDateConverter());
		CONVERTS.put(LocalTime.class, new LocalTimeConverter());
	}

	/** The default. */
	private static SimpleTypeConverter DEFAULT = new SimpleTypeConverter();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.morgan.design.properties.conversion.PropertyConversionService#
	 * convertPropertyForField(java.lang.reflect.Field, java.lang.Object)
	 */
	public Object convertPropertyForField(final Field field,
			final Object property) {
		try {
			return Functions
					.forMap(CONVERTS, new DefaultConverter(field.getType()))
					.apply(field.getType()).apply(property);
		} catch (final Throwable e) {
			throw new BeanInitializationException(
					String.format(
							"Unable to convert property for field [%s].  Value [%s] cannot be converted to [%s]",
							field.getName(), property, field.getType()), e);
		}
	}

	/**
	 * The Class DefaultConverter.
	 */
	private static class DefaultConverter implements Function<Object, Object> {

		/** The type. */
		private final Class<?> type;

		/**
		 * Instantiates a new default converter.
		 *
		 * @param type
		 *            the type
		 */
		public DefaultConverter(final Class<?> type) {
			this.type = type;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.google.common.base.Function#apply(java.lang.Object)
		 */
		public Object apply(final Object input) {
			return DEFAULT.convertIfNecessary(input, this.type);
		}
	}

	/**
	 * The Class PeriodConverter.
	 */
	private static class PeriodConverter implements Function<Object, Period> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.google.common.base.Function#apply(java.lang.Object)
		 */
		public Period apply(final Object input) {
			return JodaUtils.timeStringToPeriodOrNull((String) input);
		}
	}

	/**
	 * The Class LocalDateTimeConverter.
	 */
	private static class LocalDateTimeConverter implements
			Function<Object, LocalDateTime> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.google.common.base.Function#apply(java.lang.Object)
		 */
		public LocalDateTime apply(final Object input) {
			return JodaUtils
					.timestampStringToLocalDateTimeOrNull((String) input);
		}
	}

	/**
	 * The Class LocalDateConverter.
	 */
	private static class LocalDateConverter implements
			Function<Object, LocalDate> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.google.common.base.Function#apply(java.lang.Object)
		 */
		public LocalDate apply(final Object input) {
			return JodaUtils.dateStringToLocalDateOrNull((String) input);
		}
	}

	/**
	 * The Class LocalTimeConverter.
	 */
	private static class LocalTimeConverter implements
			Function<Object, LocalTime> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.google.common.base.Function#apply(java.lang.Object)
		 */
		public LocalTime apply(final Object input) {
			return JodaUtils.timeStringToLocalTimeOrNull((String) input);
		}
	}
}
