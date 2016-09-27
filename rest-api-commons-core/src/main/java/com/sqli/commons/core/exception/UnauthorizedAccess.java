package com.sqli.commons.core.exception;

/**
 * When user access to an unauthorized resource.
 */
public class UnauthorizedAccess extends TechnicalException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3834250606455260412L;

	/**
	 * Instantiates a new unauthorized access.
	 * 
	 * @param ressourceIdentifier
	 *            the resource identifier
	 */
	public UnauthorizedAccess(String ressourceIdentifier) {
		super("Unauthorized ressource access " + ressourceIdentifier, null);
	}

}
