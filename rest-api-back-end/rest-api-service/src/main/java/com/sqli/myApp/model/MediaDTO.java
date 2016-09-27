package com.sqli.myApp.model;

import com.sqli.commons.core.service.model.AbstractDTO;

/**
 * The Class BookDTO.
 */
public class MediaDTO extends AbstractDTO<Long> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5522333403182196554L;

	/** The title. */
	private String name;

	/** The description. */
	private String content;

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the content.
	 * 
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the content.
	 * 
	 * @param content
	 *            the new content
	 */
	public void setContent(String content) {
		this.content = content;
	}

}
