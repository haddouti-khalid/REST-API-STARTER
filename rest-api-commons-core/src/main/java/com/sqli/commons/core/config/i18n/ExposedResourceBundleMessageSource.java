package com.sqli.commons.core.config.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * The Class ExposedResourceBundleMessageSource.
 */
public class ExposedResourceBundleMessageSource extends AbstractMessageSource
		implements ResourceLoaderAware {

	/** The Constant PROPERTIES_SUFFIX. */
	private static final String PROPERTIES_SUFFIX = ".properties";

	/** The Constant XML_SUFFIX. */
	private static final String XML_SUFFIX = ".xml";

	/** The Constant JSON_SUFFIX. */
	private static final String JSON_SUFFIX = ".json";

	/** The basenames. */
	private String[] basenames = new String[0];

	/** The default encoding. */
	private String defaultEncoding;

	/** The file encodings. */
	private Properties fileEncodings;

	/** The fallback to system locale. */
	private boolean fallbackToSystemLocale = true;

	/** The cache millis. */
	private long cacheMillis = -1;

	/** The properties persister. */
	private JsonPropertiesPersister propertiesPersister = new JsonPropertiesPersister();

	/** The resource loader. */
	private ResourceLoader resourceLoader = new DefaultResourceLoader();

	/** Cache to hold filename lists per Locale. */
	private final Map<String, Map<Locale, List<String>>> cachedFilenames = new HashMap<String, Map<Locale, List<String>>>();

	/** Cache to hold already loaded properties per filename. */
	private final Map<String, PropertiesHolder> cachedProperties = new HashMap<String, PropertiesHolder>();

	/** Cache to hold merged loaded properties per locale. */
	private final Map<Locale, PropertiesHolder> cachedMergedProperties = new HashMap<Locale, PropertiesHolder>();

	/**
	 * Gets the all properties.
	 * 
	 * @param locale
	 *            the locale
	 * @return the all properties
	 */
	public Properties getAllProperties(Locale locale) {
		PropertiesHolder propertiesHolder = getMergedProperties(locale);

		long refreshTimestamp = (this.cacheMillis < 0 ? -1 : System
				.currentTimeMillis());

		if (propertiesHolder.getRefreshTimestamp() == -1) {
			propertiesHolder.setRefreshTimestamp(refreshTimestamp);
		}
		if (propertiesHolder != null
				&& (propertiesHolder.getRefreshTimestamp() < 0 || propertiesHolder
						.getRefreshTimestamp() > System.currentTimeMillis()
						- this.cacheMillis)) {
			// up to date
			if (logger.isDebugEnabled()) {
				logger.debug("up to date");
			}
		} else {
			clearCacheIncludingAncestors();
			propertiesHolder.setRefreshTimestamp(refreshTimestamp);
			propertiesHolder = getMergedProperties(locale);
		}

		return propertiesHolder.getProperties();
	}

	/**
	 * Sets the basename.
	 * 
	 * @param basename
	 *            the new basename
	 */
	public void setBasename(String basename) {
		setBasenames(basename);
	}

	/**
	 * Sets the basenames.
	 * 
	 * @param basenames
	 *            the new basenames
	 */
	public void setBasenames(String... basenames) {
		if (basenames != null) {
			this.basenames = new String[basenames.length];
			for (int i = 0; i < basenames.length; i++) {
				String basename = basenames[i];
				Assert.hasText(basename, "Basename must not be empty");
				this.basenames[i] = basename.trim();
			}
		} else {
			this.basenames = new String[0];
		}
	}

	/**
	 * Sets the default encoding.
	 * 
	 * @param defaultEncoding
	 *            the new default encoding
	 */
	public void setDefaultEncoding(String defaultEncoding) {
		this.defaultEncoding = defaultEncoding;
	}

	/**
	 * Sets the file encodings.
	 * 
	 * @param fileEncodings
	 *            the new file encodings
	 */
	public void setFileEncodings(Properties fileEncodings) {
		this.fileEncodings = fileEncodings;
	}

	/**
	 * Sets the fallback to system locale.
	 * 
	 * @param fallbackToSystemLocale
	 *            the new fallback to system locale
	 */
	public void setFallbackToSystemLocale(boolean fallbackToSystemLocale) {
		this.fallbackToSystemLocale = fallbackToSystemLocale;
	}

	/**
	 * Sets the cache seconds.
	 * 
	 * @param cacheSeconds
	 *            the new cache seconds
	 */
	public void setCacheSeconds(int cacheSeconds) {
		this.cacheMillis = (cacheSeconds * 1000);
	}

	/**
	 * Sets the properties persister.
	 * 
	 * @param propertiesPersister
	 *            the new properties persister
	 */
	public void setPropertiesPersister(
			JsonPropertiesPersister propertiesPersister) {
		this.propertiesPersister = (propertiesPersister != null ? propertiesPersister
				: new JsonPropertiesPersister());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ResourceLoaderAware#setResourceLoader(org
	 * .springframework.core.io.ResourceLoader)
	 */
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = (resourceLoader != null ? resourceLoader
				: new DefaultResourceLoader());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.context.support.AbstractMessageSource#
	 * resolveCodeWithoutArguments(java.lang.String, java.util.Locale)
	 */
	@Override
	protected String resolveCodeWithoutArguments(String code, Locale locale) {
		if (this.cacheMillis < 0) {
			PropertiesHolder propHolder = getMergedProperties(locale);
			String result = propHolder.getProperty(code);
			if (result != null) {
				return result;
			}
		} else {
			for (String basename : this.basenames) {
				List<String> filenames = calculateAllFilenames(basename, locale);
				for (String filename : filenames) {
					PropertiesHolder propHolder = getProperties(filename);
					String result = propHolder.getProperty(code);
					if (result != null) {
						return result;
					}
				}
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.support.AbstractMessageSource#resolveCode
	 * (java.lang.String, java.util.Locale)
	 */
	@Override
	protected MessageFormat resolveCode(String code, Locale locale) {
		if (this.cacheMillis < 0) {
			PropertiesHolder propHolder = getMergedProperties(locale);
			MessageFormat result = propHolder.getMessageFormat(code, locale);
			if (result != null) {
				return result;
			}
		} else {
			for (String basename : this.basenames) {
				List<String> filenames = calculateAllFilenames(basename, locale);
				for (String filename : filenames) {
					PropertiesHolder propHolder = getProperties(filename);
					MessageFormat result = propHolder.getMessageFormat(code,
							locale);
					if (result != null) {
						return result;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Gets the merged properties.
	 * 
	 * @param locale
	 *            the locale
	 * @return the merged properties
	 */
	protected PropertiesHolder getMergedProperties(Locale locale) {
		synchronized (this.cachedMergedProperties) {
			PropertiesHolder mergedHolder = this.cachedMergedProperties
					.get(locale);
			if (mergedHolder != null) {
				return mergedHolder;
			}
			Properties mergedProps = new Properties();
			mergedHolder = new PropertiesHolder(mergedProps, -1);
			for (int i = this.basenames.length - 1; i >= 0; i--) {
				List<String> filenames = calculateAllFilenames(
						this.basenames[i], locale);
				for (int j = filenames.size() - 1; j >= 0; j--) {
					String filename = (String) filenames.get(j);
					PropertiesHolder propHolder = getProperties(filename);
					if (propHolder.getProperties() != null) {
						mergedProps.putAll(propHolder.getProperties());
					}
				}
			}
			this.cachedMergedProperties.put(locale, mergedHolder);
			return mergedHolder;
		}
	}

	/**
	 * Calculate all filenames.
	 * 
	 * @param basename
	 *            the basename
	 * @param locale
	 *            the locale
	 * @return the list
	 */
	protected List<String> calculateAllFilenames(String basename, Locale locale) {
		synchronized (this.cachedFilenames) {
			Map<Locale, List<String>> localeMap = this.cachedFilenames
					.get(basename);
			if (localeMap != null) {
				List<String> filenames = localeMap.get(locale);
				if (filenames != null) {
					return filenames;
				}
			}
			List<String> filenames = new ArrayList<String>(7);
			filenames.addAll(calculateFilenamesForLocale(basename, locale));
			if (this.fallbackToSystemLocale
					&& !locale.equals(Locale.getDefault())) {
				List<String> fallbackFilenames = calculateFilenamesForLocale(
						basename, Locale.getDefault());
				for (String fallbackFilename : fallbackFilenames) {
					if (!filenames.contains(fallbackFilename)) {
						// Entry for fallback locale that isn't already in
						// filenames list.
						filenames.add(fallbackFilename);
					}
				}
			}
			filenames.add(basename);
			if (localeMap != null) {
				localeMap.put(locale, filenames);
			} else {
				localeMap = new HashMap<Locale, List<String>>();
				localeMap.put(locale, filenames);
				this.cachedFilenames.put(basename, localeMap);
			}
			return filenames;
		}
	}

	/**
	 * Calculate filenames for locale.
	 * 
	 * @param basename
	 *            the basename
	 * @param locale
	 *            the locale
	 * @return the list
	 */
	protected List<String> calculateFilenamesForLocale(String basename,
			Locale locale) {
		List<String> result = new ArrayList<String>(3);
		String language = locale.getLanguage();
		String country = locale.getCountry();
		String variant = locale.getVariant();
		StringBuilder temp = new StringBuilder(basename);

		temp.append('_');
		if (language.length() > 0) {
			temp.append(language);
			result.add(0, temp.toString());
		}

		temp.append('_');
		if (country.length() > 0) {
			temp.append(country);
			result.add(0, temp.toString());
		}

		if (variant.length() > 0
				&& (language.length() > 0 || country.length() > 0)) {
			temp.append('_').append(variant);
			result.add(0, temp.toString());
		}

		return result;
	}

	/**
	 * Gets the properties.
	 * 
	 * @param filename
	 *            the filename
	 * @return the properties
	 */
	protected PropertiesHolder getProperties(String filename) {
		synchronized (this.cachedProperties) {
			PropertiesHolder propHolder = this.cachedProperties.get(filename);
			if (propHolder != null
					&& (propHolder.getRefreshTimestamp() < 0 || propHolder
							.getRefreshTimestamp() > System.currentTimeMillis()
							- this.cacheMillis)) {
				// up to date
				return propHolder;
			}
			return refreshProperties(filename, propHolder);
		}
	}

	/**
	 * Refresh properties.
	 * 
	 * @param filename
	 *            the filename
	 * @param propHolder
	 *            the prop holder
	 * @return the properties holder
	 */
	protected PropertiesHolder refreshProperties(String filename,
			PropertiesHolder propHolder) {
		PropertiesHolder result = propHolder;
		long refreshTimestamp = (this.cacheMillis < 0 ? -1 : System
				.currentTimeMillis());

		Resource resource = this.resourceLoader.getResource(filename
				+ PROPERTIES_SUFFIX);
		if (!resource.exists()) {
			resource = this.resourceLoader.getResource(filename + XML_SUFFIX);
		}

		if (!resource.exists()) {
			resource = this.resourceLoader.getResource(filename + JSON_SUFFIX);
		}

		if (resource.exists()) {
			long fileTimestamp = -1;
			if (this.cacheMillis >= 0) {
				// Last-modified timestamp of file will just be read if caching
				// with timeout.
				try {
					fileTimestamp = resource.lastModified();
					if (result != null
							&& result.getFileTimestamp() == fileTimestamp) {
						if (logger.isDebugEnabled()) {
							logger.debug("Re-caching properties for filename ["
									+ filename
									+ "] - file hasn't been modified");
						}
						result.setRefreshTimestamp(refreshTimestamp);
						return result;
					}
				} catch (IOException ex) {
					// Probably a class path resource: cache it forever.
					if (logger.isDebugEnabled()) {
						logger.debug(
								resource
										+ " could not be resolved in the file system - assuming that is hasn't changed",
								ex);
					}
					fileTimestamp = -1;
				}
			}
			try {
				Properties props = loadProperties(resource, filename);
				result = new PropertiesHolder(props, fileTimestamp);
			} catch (IOException ex) {
				if (logger.isWarnEnabled()) {
					logger.warn(
							"Could not parse properties file ["
									+ resource.getFilename() + "]", ex);
				}
				// Empty holder representing "not valid".
				result = new PropertiesHolder();
			}
		}

		else {
			// Resource does not exist.
			if (logger.isDebugEnabled()) {
				logger.debug("No properties file found for [" + filename
						+ "] - neither plain properties nor XML");
			}
			// Empty folder representing "not found".
			result = new PropertiesHolder();
		}

		result.setRefreshTimestamp(refreshTimestamp);
		this.cachedProperties.put(filename, result);
		return result;
	}

	/**
	 * Load properties.
	 * 
	 * @param resource
	 *            the resource
	 * @param filename
	 *            the filename
	 * @return the properties
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected Properties loadProperties(Resource resource, String filename)
			throws IOException {
		InputStream is = resource.getInputStream();
		Properties props = new Properties();
		String encoding = null;
		if (this.fileEncodings != null) {
			encoding = this.fileEncodings.getProperty(filename);
		}
		if (encoding == null) {
			encoding = this.defaultEncoding;
		}
		try {
			if (resource.getFilename().endsWith(XML_SUFFIX)) {
				if (logger.isDebugEnabled()) {
					logger.debug("Loading properties ["
							+ resource.getFilename() + "]");
				}
				this.propertiesPersister.loadFromXml(props, is);
			}
			if (resource.getFilename().endsWith(JSON_SUFFIX)) {
				if (logger.isDebugEnabled()) {
					logger.debug("Loading properties ["
							+ resource.getFilename() + "]");
				}
				if (encoding != null) {
					this.propertiesPersister.loadFromJson(props,
							new InputStreamReader(is, encoding));
				} else {
					this.propertiesPersister.loadFromJson(props, is);
				}
			} else {

				if (encoding != null) {
					if (logger.isDebugEnabled()) {
						logger.debug("Loading properties ["
								+ resource.getFilename() + "] with encoding '"
								+ encoding + "'");
					}
					this.propertiesPersister.load(props, new InputStreamReader(
							is, encoding));
				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("Loading properties ["
								+ resource.getFilename() + "]");
					}
					this.propertiesPersister.load(props, is);
				}
			}
			return props;
		} finally {
			is.close();
		}
	}

	/**
	 * Clear cache.
	 */
	public void clearCache() {
		logger.debug("Clearing entire resource bundle cache");
		synchronized (this.cachedProperties) {
			this.cachedProperties.clear();
		}
		synchronized (this.cachedMergedProperties) {
			this.cachedMergedProperties.clear();
		}
	}

	/**
	 * Clear cache including ancestors.
	 */
	public void clearCacheIncludingAncestors() {
		clearCache();
		if (getParentMessageSource() instanceof ReloadableResourceBundleMessageSource) {
			((ReloadableResourceBundleMessageSource) getParentMessageSource())
					.clearCacheIncludingAncestors();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getName() + ": basenames=["
				+ StringUtils.arrayToCommaDelimitedString(this.basenames) + "]";
	}

	/**
	 * The Class PropertiesHolder.
	 */
	protected class PropertiesHolder {

		/** The properties. */
		private Properties properties;

		/** The file timestamp. */
		private long fileTimestamp = -1;

		/** The refresh timestamp. */
		private long refreshTimestamp = -1;

		/** Cache to hold already generated MessageFormats per message code. */
		private final Map<String, Map<Locale, MessageFormat>> cachedMessageFormats = new HashMap<String, Map<Locale, MessageFormat>>();

		/**
		 * Instantiates a new properties holder.
		 * 
		 * @param properties
		 *            the properties
		 * @param fileTimestamp
		 *            the file timestamp
		 */
		public PropertiesHolder(Properties properties, long fileTimestamp) {
			this.properties = properties;
			this.fileTimestamp = fileTimestamp;
		}

		/**
		 * Instantiates a new properties holder.
		 */
		public PropertiesHolder() {
		}

		/**
		 * Gets the properties.
		 * 
		 * @return the properties
		 */
		public Properties getProperties() {
			return properties;
		}

		/**
		 * Gets the file timestamp.
		 * 
		 * @return the file timestamp
		 */
		public long getFileTimestamp() {
			return fileTimestamp;
		}

		/**
		 * Sets the refresh timestamp.
		 * 
		 * @param refreshTimestamp
		 *            the new refresh timestamp
		 */
		public void setRefreshTimestamp(long refreshTimestamp) {
			this.refreshTimestamp = refreshTimestamp;
		}

		/**
		 * Gets the refresh timestamp.
		 * 
		 * @return the refresh timestamp
		 */
		public long getRefreshTimestamp() {
			return refreshTimestamp;
		}

		/**
		 * Gets the property.
		 * 
		 * @param code
		 *            the code
		 * @return the property
		 */
		public String getProperty(String code) {
			if (this.properties == null) {
				return null;
			}
			return this.properties.getProperty(code);
		}

		/**
		 * Gets the message format.
		 * 
		 * @param code
		 *            the code
		 * @param locale
		 *            the locale
		 * @return the message format
		 */
		public MessageFormat getMessageFormat(String code, Locale locale) {
			if (this.properties == null) {
				return null;
			}
			synchronized (this.cachedMessageFormats) {
				Map<Locale, MessageFormat> localeMap = this.cachedMessageFormats
						.get(code);
				if (localeMap != null) {
					MessageFormat result = localeMap.get(locale);
					if (result != null) {
						return result;
					}
				}
				String msg = this.properties.getProperty(code);
				if (msg != null) {
					if (localeMap == null) {
						localeMap = new HashMap<Locale, MessageFormat>();
						this.cachedMessageFormats.put(code, localeMap);
					}
					MessageFormat result = createMessageFormat(msg, locale);
					localeMap.put(locale, result);
					return result;
				}
				return null;
			}
		}
	}
}