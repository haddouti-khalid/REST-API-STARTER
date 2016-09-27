package com.sqli.commons.core.exception;

/**
 * The Class TechnicalException.
 */
public class TechnicalException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6751810024555728387L;

	/** The message. */
	private final String message;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Throwable#getMessage()
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Instantiates a new technical exception.
	 * 
	 * @param message
	 *            the message
	 * @param e
	 *            the e
	 */
	public TechnicalException(String message, Throwable e) {
		super(e);
		this.message = message;
	}

	/**
	 * Instantiates a new technical exception.
	 * 
	 * @param message
	 *            the message
	 */
	public TechnicalException(String message) {
		super();
		this.message = message;
	}

}
