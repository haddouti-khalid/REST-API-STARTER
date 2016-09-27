package com.sqli.commons.core.config.core.properties.bean;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * The Class PropertyModifiedEvent.
 */
public class PropertyModifiedEvent {

	/** The property name. */
	private final String propertyName;

	/** The old value. */
	private final Object oldValue;

	/** The new value. */
	private final Object newValue;

	/**
	 * Instantiates a new property modified event.
	 *
	 * @param propertyName
	 *            the property name
	 * @param oldValue
	 *            the old value
	 * @param newValue
	 *            the new value
	 */
	public PropertyModifiedEvent(final String propertyName, final Object oldValue, final Object newValue) {
		this.propertyName = propertyName;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	/**
	 * Gets the property name.
	 *
	 * @return the property name
	 */
	public String getPropertyName() {
		return this.propertyName;
	}

	/**
	 * Gets the old value.
	 *
	 * @return the old value
	 */
	public Object getOldValue() {
		return this.oldValue;
	}

	/**
	 * Gets the new value.
	 *
	 * @return the new value
	 */
	public Object getNewValue() {
		return this.newValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(this.propertyName, this.oldValue, this.newValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object object) {
		if (object instanceof PropertyModifiedEvent) {
			final PropertyModifiedEvent that = (PropertyModifiedEvent) object;
			return Objects.equal(this.propertyName, that.propertyName) && Objects.equal(this.oldValue, that.oldValue) && Objects.equal(this.newValue, that.newValue);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("propertyName", this.propertyName).add("oldValue", this.oldValue).add("newValue", this.newValue).toString();
	}

}
