package com.sqli.myApp.security.authentication;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationDetailsSource;

import com.sqli.myApp.security.Right;
import com.sqli.myApp.security.Role;

/**
 * Retrieve user data from application server.
 */
public class ProdAuthenticationDetailProvider implements AuthenticationDetailsSource<HttpServletRequest, AuthenticationDetail> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.authentication.AuthenticationDetailsSource
	 * #buildDetails(java.lang.Object)
	 */
	@Override
	public AuthenticationDetail buildDetails(final HttpServletRequest context) {
		final List<Right> rights = new LinkedList<Right>();

		// Check for individual rights
		for (Right code : Right.values()) {
			if (context.isUserInRole(code.name())) {
				rights.add(Right.valueOf(code.name()));
			}
		}

		// Check for global role
		for (Role code : Role.values()) {
			if (context.isUserInRole(code.name())) {
				for (Right right : code.getRights()) {
					rights.add(right);
				}
			}
		}

		AuthenticationDetail auth = new AuthenticationDetail(rights);
		if (context.getUserPrincipal() != null) {
			auth.setLogin(context.getUserPrincipal().getName());
		}

		return auth;
	}
}
