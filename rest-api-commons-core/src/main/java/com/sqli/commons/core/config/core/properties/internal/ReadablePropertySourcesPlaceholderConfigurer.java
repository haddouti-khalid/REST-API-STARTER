package com.sqli.commons.core.config.core.properties.internal;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.sqli.commons.core.config.core.properties.bean.PropertyModifiedEvent;
import com.sqli.commons.core.config.core.properties.event.PropertyChangedEventNotifier;
import com.sqli.commons.core.config.core.properties.internal.PropertiesWatcher.EventPublisher;
import com.sqli.commons.core.config.core.properties.resolver.PropertyResolver;

/**
 * Specialisation of {@link PropertySourcesPlaceholderConfigurer} that can react
 * to changes in the resources specified. The watching process does not start by
 * default, initiation is triggered by calling
 * <code>ReadablePropertySourcesPlaceholderConfigurer.startWatching()</code>
 * 
 * @author James Morgan
 */
public class ReadablePropertySourcesPlaceholderConfigurer extends
		PropertySourcesPlaceholderConfigurer implements EventPublisher {

	/** The log. */
	protected static Logger log = LoggerFactory
			.getLogger(ReadablePropertySourcesPlaceholderConfigurer.class);

	/** The event notifier. */
	private final PropertyChangedEventNotifier eventNotifier;

	/** The property resolver. */
	private final PropertyResolver propertyResolver;

	/** The properties. */
	private Properties properties;

	/** The locations. */
	private Resource[] locations;

	/** The cache. */
	private Integer cache;

	/**
	 * Sets the cache.
	 *
	 * @param cache
	 *            the new cache
	 */
	protected void setCache(Integer cache) {
		this.cache = cache;
	}

	/**
	 * Instantiates a new readable property sources placeholder configurer.
	 *
	 * @param eventNotifier
	 *            the event notifier
	 * @param propertyResolver
	 *            the property resolver
	 */
	@Autowired
	public ReadablePropertySourcesPlaceholderConfigurer(
			final PropertyChangedEventNotifier eventNotifier,
			final PropertyResolver propertyResolver) {
		this.eventNotifier = eventNotifier;
		this.propertyResolver = propertyResolver;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.core.io.support.PropertiesLoaderSupport#loadProperties
	 * (java.util.Properties)
	 */
	@Override
	protected void loadProperties(final Properties props) throws IOException {
		super.loadProperties(props);
		this.properties = props;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.core.io.support.PropertiesLoaderSupport#setLocations
	 * (org.springframework.core.io.Resource[])
	 */
	@Override
	public void setLocations(final Resource[] locations) {
		super.setLocations(locations);
		this.locations = locations;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.morgan.design.properties.internal.PropertiesWatcher.EventPublisher
	 * #onResourceChanged(org.springframework.core.io.Resource)
	 */
	public void onResourceChanged(final Resource resource) {
		try {
			final Properties reloadedProperties = PropertiesLoaderUtils
					.loadProperties(resource);
			for (final String property : this.properties.stringPropertyNames()) {

				final String oldValue = this.properties.getProperty(property);
				final String newValue = reloadedProperties
						.getProperty(property);

				if (propertyExistsAndNotNull(property, newValue)
						&& propertyChange(oldValue, newValue)) {

					// Update locally stored copy of properties
					this.properties.setProperty(property, newValue);

					// Post change event to notify any potential listeners
					this.eventNotifier.post(new PropertyModifiedEvent(property,
							oldValue, newValue));
				}
			}
		} catch (final IOException e) {
			log.error("Failed to reload properties file once change", e);
		}
	}

	/**
	 * Gets the properties.
	 *
	 * @return the properties
	 */
	public Properties getProperties() {
		return this.properties;
	}

	/**
	 * Start watching.
	 */
	public void startWatching() {
		if (null == this.eventNotifier) {
			throw new BeanInitializationException(
					"Event bus not setup, you should not be calling this method...!");
		}
		try {
			// Here we actually create and set a FileWatcher to monitor the
			// given locations
			Executors.newSingleThreadExecutor().execute(
					new PropertiesWatcher(this.locations, this, this.cache));
		} catch (final IOException e) {
			log.error("Unable to start properties file watcher", e);
		}
	}

	/**
	 * Resolve property.
	 *
	 * @param property
	 *            the property
	 * @return the object
	 */
	public Object resolveProperty(final Object property) {
		Object resolvedPropertyValue = this.properties
				.get(this.propertyResolver.resolveProperty(property));
		if (notStringpropertyToSubstitute(resolvedPropertyValue)) {
			return resolvedPropertyValue;
		}
		while (this.propertyResolver
				.requiresFurtherResoltuion(resolvedPropertyValue)) {
			resolvedPropertyValue = buildResolvedString(resolvedPropertyValue);
		}
		return resolvedPropertyValue;
	}

	/**
	 * Builds the resolved string.
	 *
	 * @param resolvedPropertyValue
	 *            the resolved property value
	 * @return the object
	 */
	private Object buildResolvedString(final Object resolvedPropertyValue) {
		final String resolvedValueStr = resolvedPropertyValue.toString();

		final int startingIndex = resolvedValueStr.indexOf("${");
		final int endingIndex = resolvedValueStr.indexOf("}", startingIndex) + 1;

		final String toResolve = resolvedValueStr.substring(startingIndex,
				endingIndex);
		final String resolved = resolveProperty(toResolve).toString();

		return new StringBuilder()
				.append(resolvedValueStr.substring(0, startingIndex))
				.append(resolved)
				.append(resolvedValueStr.substring(endingIndex)).toString();
	}

	/**
	 * Not stringproperty to substitute.
	 *
	 * @param resolvedPropertyValue
	 *            the resolved property value
	 * @return true, if successful
	 */
	private boolean notStringpropertyToSubstitute(
			final Object resolvedPropertyValue) {
		return !(resolvedPropertyValue instanceof String);
	}

	/**
	 * Property change.
	 *
	 * @param oldValue
	 *            the old value
	 * @param newValue
	 *            the new value
	 * @return true, if successful
	 */
	private boolean propertyChange(final String oldValue, final String newValue) {
		return null == oldValue || !oldValue.equals(newValue);
	}

	/**
	 * Property exists and not null.
	 *
	 * @param property
	 *            the property
	 * @param newValue
	 *            the new value
	 * @return true, if successful
	 */
	private boolean propertyExistsAndNotNull(final String property,
			final String newValue) {
		return this.properties.containsKey(property) && null != newValue;
	}

}
