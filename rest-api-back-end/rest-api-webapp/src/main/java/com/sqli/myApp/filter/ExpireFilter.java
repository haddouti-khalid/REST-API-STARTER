package com.sqli.myApp.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * The Class ExpireFilter.
 */
public class ExpireFilter extends OncePerRequestFilter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		// Cache control for HTTP 1.1
		response.setHeader("Cache-Control", "max-age=0, no-cache, no-store, must-revalidate");
		// Cache control for HTTP 1.0
		response.setHeader("Pragma", "no-cache");
		response.addDateHeader("Expires", 0);
		filterChain.doFilter(request, response);
	}
}
