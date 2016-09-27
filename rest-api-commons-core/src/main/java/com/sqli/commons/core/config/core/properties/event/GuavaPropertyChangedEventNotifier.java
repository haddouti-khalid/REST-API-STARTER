package com.sqli.commons.core.config.core.properties.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.EventBus;
import com.sqli.commons.core.config.core.properties.bean.PropertyModifiedEvent;
import com.sqli.commons.core.config.core.properties.internal.ReloadablePropertyPostProcessor;

/**
 * The Class GuavaPropertyChangedEventNotifier.
 */
@Component
public class GuavaPropertyChangedEventNotifier implements
		PropertyChangedEventNotifier {

	/** The event bus. */
	private final EventBus eventBus;

	/**
	 * Instantiates a new guava property changed event notifier.
	 *
	 * @param eventBus
	 *            the event bus
	 */
	@Autowired
	public GuavaPropertyChangedEventNotifier(
			@Qualifier("propertiesEventBus") final EventBus eventBus) {
		this.eventBus = eventBus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.morgan.design.properties.event.PropertyChangedEventNotifier#post(
	 * com.morgan.design.properties.bean.PropertyModifiedEvent)
	 */
	public void post(final PropertyModifiedEvent propertyChangedEvent) {
		this.eventBus.post(propertyChangedEvent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.morgan.design.properties.event.PropertyChangedEventNotifier#unregister
	 * (com.morgan.design.properties.internal.ReloadablePropertyPostProcessor)
	 */
	public void unregister(
			final ReloadablePropertyPostProcessor ReloadablePropertyPostProcessor) {
		this.eventBus.unregister(ReloadablePropertyPostProcessor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.morgan.design.properties.event.PropertyChangedEventNotifier#register
	 * (com.morgan.design.properties.internal.ReloadablePropertyPostProcessor)
	 */
	public void register(
			final ReloadablePropertyPostProcessor ReloadablePropertyPostProcessor) {
		this.eventBus.register(ReloadablePropertyPostProcessor);
	}

}
