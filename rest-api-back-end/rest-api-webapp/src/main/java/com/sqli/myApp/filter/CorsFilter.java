package com.sqli.myApp.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Allow cross domain operations, ie for development environment.
 */
public class CorsFilter extends OncePerRequestFilter {

    /* (non-Javadoc)
     * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        response.addHeader("Access-Control-Allow-Origin", getRequestedServer(request));
        response.addHeader("Access-Control-Allow-Methods", "PUT, GET, POST, DELETE");
        response.addHeader("Access-Control-Allow-Headers",
                "X-Requested-With,Origin,Content-Type, Accept, SPRING_USER_ID, TABLE_PAGINATED_TOTAL");
        response.addHeader("Access-Control-Max-Age", "1800");
        response.addHeader("Access-Control-Expose-Headers", "TABLE_PAGINATED_TOTAL");
        response.addHeader("Access-Control-Allow-Credentials", "true");

        if ("OPTIONS".equals(request.getMethod())) {
            return;
        }
        filterChain.doFilter(request, response);
    }
    
    
    /**
	 * Gets the requested server.
	 * 
	 * @param request
	 *            the request
	 * @return the requested server
	 */
	private String getRequestedServer(HttpServletRequest request) {
		String referer = request.getHeader("referer");

		if (referer != null) {
			referer = referer.substring(0, referer.indexOf('/', referer.indexOf("://") + 3));
		}

		if (referer != null) {
			return referer;
		} else {
			return "http://localhost:9000";
		}
	}
}
