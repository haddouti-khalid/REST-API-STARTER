package com.sqli.myApp.facade.rest;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sqli.commons.core.config.i18n.ExposedResourceBundleMessageSource;

/**
 * The Class I18nController.
 */
@Controller
public class I18nController {

	/** The external i18n folder. */
	@Value("${external.i18n.folder}")
	private String externalI18nFolder;

	/** The external i18n cache. */
	@Value("${external.i18n.cache}")
	private int externalI18nCache;

	/** The resources. */
	private final Map<String, ExposedResourceBundleMessageSource> resources = new HashMap<String, ExposedResourceBundleMessageSource>();

	/**
	 * Gets the.
	 * 
	 * @param part
	 *            the part
	 * @param lang
	 *            the lang
	 * @return the properties
	 */
	@ResponseBody
	public Properties get(@RequestParam String part, @RequestParam String lang) {
		ExposedResourceBundleMessageSource messageSource = null;
		if (resources.get(lang + "-" + part) != null) {
			messageSource = resources.get(lang + "-" + part);
		} else {
			messageSource = new ExposedResourceBundleMessageSource();
			messageSource.setDefaultEncoding("UTF-8");
			messageSource.setBasenames("file:" + externalI18nFolder + "/" + lang + "/" + part, "classpath:external/i18n/" + lang + "/" + part);
			messageSource.setCacheSeconds(externalI18nCache);
			resources.put(lang + "-" + part, messageSource);
		}

		Properties result = messageSource.getAllProperties(Locale.getDefault());

		if (result.size() == 0) {
			resources.remove(lang + "-" + part);
		}
		return result;
	}
}
