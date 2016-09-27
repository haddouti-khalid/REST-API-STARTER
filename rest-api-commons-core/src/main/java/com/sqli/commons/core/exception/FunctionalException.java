package com.sqli.commons.core.exception;

/**
 * The Class FunctionalException.
 */
public class FunctionalException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6751810024555728387L;

	/** The code. */
	private final Long code;

	/** The message. */
	private final String message;

	/**
	 * Gets the code.
	 * 
	 * @return the code
	 */
	public Long getCode() {
		return code;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Throwable#getMessage()
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Instantiates a new functional exception.
	 *
	 * @param code
	 *            the code
	 * @param message
	 *            the message
	 * @param e
	 *            the e
	 */
	public FunctionalException(Long code, String message, Throwable e) {
		super(e);
		this.code = code;
		this.message = message;
	}

	/**
	 * Instantiates a new functional exception.
	 *
	 * @param code
	 *            the code
	 * @param message
	 *            the message
	 */
	public FunctionalException(Long code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

}
