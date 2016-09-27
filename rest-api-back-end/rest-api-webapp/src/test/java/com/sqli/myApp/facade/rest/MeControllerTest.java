package com.sqli.myApp.facade.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;

import com.sqli.myApp.facade.AbstractRestTestSupport;
import com.sqli.myApp.security.Right;

/**
 * The Class MeControllerTest.
 */
public class MeControllerTest extends AbstractRestTestSupport {

	/**
	 * Me.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void me() throws Exception {
		authenticate(new Right[] { Right.GET_ME });

		mockMvc.perform(get("/me").header("host", "localhost:80").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("login").value("admin"));
	}

	/**
	 * Me Forbidden.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void meForbidden() throws Exception {
		authenticate(new Right[] {});

		mockMvc.perform(get("/me").header("host", "localhost:80").accept(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
	}
}
