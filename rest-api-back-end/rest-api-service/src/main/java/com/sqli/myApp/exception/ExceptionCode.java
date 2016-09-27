/**
 * 
 */
package com.sqli.myApp.exception;

/**
 * ExceptionCode
 */
public enum ExceptionCode {
	BOOK_NOT_FOUND(Long.valueOf(1), "This Book is not found");

	/**
	 * code
	 */
	private Long code;

	/**
	 * message
	 */
	private String message;

	/**
	 * ExceptionCode
	 * 
	 * @param code
	 * @param message
	 */
	private ExceptionCode(Long code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * @return the code
	 */
	public Long getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(Long code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
