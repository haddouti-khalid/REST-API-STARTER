package com.sqli.myApp.security;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.annotation.AnnotationMetadataExtractor;

/**
 * The Class UserRoleAnnotationMetadataExtractor.
 */
public class UserRoleAnnotationMetadataExtractor implements AnnotationMetadataExtractor<UserHasRight> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.access.annotation.AnnotationMetadataExtractor
	 * #extractAttributes(java.lang.annotation.Annotation)
	 */
	@Override
	public Collection<? extends ConfigAttribute> extractAttributes(final UserHasRight securityAnnotation) {
		final List<ConfigAttribute> attributes = new LinkedList<ConfigAttribute>();
		final Right[] attributeTokens = securityAnnotation.value();

		for (final Right token : attributeTokens) {
			attributes.add(new SecurityConfig(constructRoleToken(token)));
		}

		return attributes;
	}

	/**
	 * Construct role token.
	 * 
	 * @param right
	 *            the right
	 * @return the string
	 */
	private static String constructRoleToken(Right right) {
		assert right != null : "right is mandatory";
		return "ROLE_" + right.name();
	}
}
