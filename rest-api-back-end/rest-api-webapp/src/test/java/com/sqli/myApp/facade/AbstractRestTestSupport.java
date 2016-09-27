package com.sqli.myApp.facade;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.sqli.commons.test.rest.RestTestSupport;
import com.sqli.myApp.security.Right;
import com.sqli.myApp.security.authentication.AuthenticationDetail;

/**
 * The Class FacadeIntegrationTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/spring/servlet-context.xml", "classpath:/spring/test-persistence-context.xml", "classpath:/spring/test-datasource-context.xml",
		"classpath:/spring/application-context.xml", "classpath:/spring/test-servlet-context.xml" })
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
public abstract class AbstractRestTestSupport extends RestTestSupport {

	static {
		System.setProperty("spring.profiles.default", "dev");
	}

	/** The web application context. */
	@Autowired
	protected WebApplicationContext webApplicationContext;

	/** The spring security filter chain. */
	@Autowired
	protected Filter springSecurityFilterChain;

	/** The mock mvc. */
	protected MockMvc mockMvc;

	/** The session. */
	protected MockHttpSession session;

	/**
	 * Setup.
	 */
	@Before
	public final void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).addFilter(springSecurityFilterChain).build();
	}

	/**
	 * Authenticate.
	 * 
	 * @param user
	 *            the user
	 */
	public void authenticate(Right[] rights) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(rights.length);
		for (int i = 0; i < rights.length; i++) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + rights[i].name()));
		}
		User user = new User("admin", "", authorities);

		AuthenticationDetail details = new AuthenticationDetail(authorities);
		details.setLogin(user.getUsername());

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, "N/A", authorities);
		token.setDetails(details);

		SecurityContextHolder.setContext(new SecurityContextStub(token));

		session = new MockHttpSession();
		session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
	}

	/**
	 * The Class SecurityContextStub.
	 */
	private static class SecurityContextStub implements SecurityContext {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 4424343118741439954L;

		/** The authentication. */
		private Authentication authentication;

		/**
		 * Instantiates a new security context stub.
		 * 
		 * @param authentication
		 *            the authentication
		 */
		public SecurityContextStub(final Authentication authentication) {
			this.authentication = authentication;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.springframework.security.core.context.SecurityContext#
		 * getAuthentication()
		 */
		@Override
		public Authentication getAuthentication() {
			return this.authentication;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.springframework.security.core.context.SecurityContext#
		 * setAuthentication(org.springframework.security. core.Authentication)
		 */
		@Override
		public void setAuthentication(final Authentication authentication) {
			this.authentication = authentication;
		}
	}

	/**
	 * Hook method for running setup code only one time before all tests.
	 */
	protected void setupOnce() {
	}

}
