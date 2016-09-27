package com.sqli.commons.core.util;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class XMLUtil.
 */
public final class XMLUtil {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(XMLUtil.class);

	/** The instance. */
	private static XMLUtil instance = null;

	/**
	 * Instantiates a new xML util.
	 */
	private XMLUtil() {
	}

	/**
	 * Gets the single instance of XMLUtil.
	 * 
	 * @return single instance of XMLUtil
	 */
	public static synchronized XMLUtil getInstance() {
		if (instance == null) {
			instance = new XMLUtil();
		}
		return instance;
	}

	/**
	 * To bean.
	 * 
	 * @param value
	 *            the value
	 * @param type
	 *            the type
	 * @return the object
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object toBean(String value, Class type) {
		Object result = null;
		if (value == null || value.trim().length() == 0) {
			return null;
		}
		try {
			result = JAXB.unmarshal(new StringReader(value), type);
		} catch (RuntimeException e) {
			LOGGER.error("String to bean conversion error", e);
		}
		return result;
	}

	/**
	 * To string.
	 * 
	 * @param object
	 *            the object
	 * @return the string
	 */
	public String toString(Object object) {
		String result = null;
		if (object == null) {
			return null;
		}
		StringWriter w = new StringWriter();
		try {
			JAXB.marshal(object, w);
			result = w.toString();
		} catch (RuntimeException e) {
			LOGGER.error("Bean to string conversion error", e);
		}

		return result;
	}

}