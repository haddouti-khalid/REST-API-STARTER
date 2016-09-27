package com.sqli.myApp.security.authentication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.sqli.myApp.security.Right;
import com.sqli.myApp.security.Role;

/**
 * The Class UserDetailsProvider.
 * 
 * @param <T>
 *            the generic type
 */
public class UserDetailsProvider<T extends PreAuthenticatedAuthenticationToken> implements AuthenticationUserDetailsService<T> {

	/** The Constant ROLE_PREFIX. */
	private static final String ROLE_PREFIX = "ROLE_";

	/**
	 * Builds the authorities.
	 * 
	 * @param rights
	 *            the rights
	 * @return the collection<? extends granted authority>
	 */
	private static Collection<? extends GrantedAuthority> buildAuthorities(List<Right> rights) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (Right right : rights) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(ROLE_PREFIX + right.name());
			if (!authorities.contains(grantedAuthority)) {
				authorities.add(grantedAuthority);
			}
		}

		return authorities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.
	 * AuthenticationUserDetailsService
	 * #loadUserDetails(org.springframework.security.core.Authentication)
	 */
	@Override
	public UserDetails loadUserDetails(T token) {
		if (token == null || token.getDetails() == null) {
			throw new UsernameNotFoundException("No user is authenticated");
		}
		AuthenticationDetail details = (AuthenticationDetail) token.getDetails();
		List<Role> roles = details.getRoles();

		for (Role role : roles) {
			details.getRights().addAll(Arrays.asList(role.getRights()));
		}

		return new User(token.getPrincipal().toString(), "", buildAuthorities(details.getRights()));
	}

}
