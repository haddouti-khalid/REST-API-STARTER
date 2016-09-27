package com.sqli.commons.core.config.core.properties.bean;

import java.lang.reflect.Field;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * The Class BeanPropertyHolder.
 */
public class BeanPropertyHolder {

	/** The bean. */
	private final Object bean;

	/** The field. */
	private final Field field;

	/**
	 * Instantiates a new bean property holder.
	 *
	 * @param bean
	 *            the bean
	 * @param field
	 *            the field
	 */
	public BeanPropertyHolder(Object bean, Field field) {
		this.bean = bean;
		this.field = field;
	}

	/**
	 * Gets the bean.
	 *
	 * @return the bean
	 */
	public Object getBean() {
		return this.bean;
	}

	/**
	 * Gets the field.
	 *
	 * @return the field
	 */
	public Field getField() {
		return this.field;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(this.bean, this.field);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (object instanceof BeanPropertyHolder) {
			BeanPropertyHolder that = (BeanPropertyHolder) object;
			return Objects.equal(this.bean, that.bean) && Objects.equal(this.field, that.field);
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
		return MoreObjects.toStringHelper(this).add("bean", this.bean).add("field", this.field).toString();
	}

}
