package com.sqli.myApp.model;

import com.sqli.commons.core.service.model.AbstractDTO;

/**
 * The Class BookDTO.
 */
public class BookDTO extends AbstractDTO<Long> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5522333403182196554L;

	/** The title. */
	private String title;

	/** The description. */
	private String description;

	/**
	 * Gets the title.
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 * 
	 * @param title
	 *            the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 * 
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
