package com.sqli.myApp.model;

import java.util.List;

import com.sqli.commons.core.service.model.AbstractDTO;

/**
 * The Class ErrorDTO.
 */
public class ErrorDTO extends AbstractDTO<Long> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5522333403182196554L;

	/** The message. */
	private String message;

	/** The stacktrace. */
	private List<String> stacktrace;

	/**
	 * Gets the message.
	 * 
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 * 
	 * @param message
	 *            the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the stacktrace.
	 * 
	 * @return the stacktrace
	 */
	public List<String> getStacktrace() {
		return stacktrace;
	}

	/**
	 * Sets the stacktrace.
	 * 
	 * @param stacktrace
	 *            the new stacktrace
	 */
	public void setStacktrace(List<String> stacktrace) {
		this.stacktrace = stacktrace;
	}

}
