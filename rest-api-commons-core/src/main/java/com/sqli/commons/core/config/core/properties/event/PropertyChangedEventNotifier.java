package com.sqli.commons.core.config.core.properties.event;

import com.sqli.commons.core.config.core.properties.bean.PropertyModifiedEvent;
import com.sqli.commons.core.config.core.properties.internal.ReloadablePropertyPostProcessor;

/**
 * The Interface PropertyChangedEventNotifier.
 */
public interface PropertyChangedEventNotifier {

	/**
	 * Post.
	 *
	 * @param propertyChangedEvent
	 *            the property changed event
	 */
	void post(PropertyModifiedEvent propertyChangedEvent);

	/**
	 * Unregister.
	 *
	 * @param reloadablePropertyProcessor
	 *            the reloadable property processor
	 */
	void unregister(ReloadablePropertyPostProcessor reloadablePropertyProcessor);

	/**
	 * Register.
	 *
	 * @param reloadablePropertyProcessor
	 *            the reloadable property processor
	 */
	void register(ReloadablePropertyPostProcessor reloadablePropertyProcessor);

}
