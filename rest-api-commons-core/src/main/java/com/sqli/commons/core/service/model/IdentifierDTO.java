package com.sqli.commons.core.service.model;

import java.io.Serializable;

/**
 * The Class IdentifierDTO.
 */
public class IdentifierDTO<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9111843629030298506L;

	/** The id. */
	private T id;

	/**
	 * Instantiates a new identifier dto.
	 * 
	 * @param id
	 *            the id
	 */
	public IdentifierDTO(T id) {
		this.id = id;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public T getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(T id) {
		this.id = id;
	}

}