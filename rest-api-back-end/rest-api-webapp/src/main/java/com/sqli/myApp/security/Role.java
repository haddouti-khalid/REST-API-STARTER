package com.sqli.myApp.security;

/**
 * The Enum Role.
 */
public enum Role {

	/** The admin. */
	ADMIN(Right.GET_ME, Right.ACCESS_TEAMCOPTER, Right.GET_ALL_BOOK, Right.GET_BOOK, Right.GET_PDF_BOOK, Right.CREATE_BOOK, Right.UPDATE_BOOK, Right.DELETE_BOOK),

	/** The customer. */
	CUSTOMER(Right.GET_ME, Right.ACCESS_KEYCOPTER, Right.GET_BOOK, Right.GET_PDF_BOOK);

	/** The rights. */
	private Right[] rights;

	/**
	 * Instantiates a new role.
	 * 
	 * @param rights
	 *            the rights
	 */
	Role(Right... rights) {
		this.rights = rights;
	}

	/**
	 * Gets the rights.
	 * 
	 * @return the rights
	 */
	public Right[] getRights() {
		return rights;
	}
}
