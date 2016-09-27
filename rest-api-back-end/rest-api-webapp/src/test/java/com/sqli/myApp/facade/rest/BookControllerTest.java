package com.sqli.myApp.facade.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqli.myApp.facade.AbstractRestTestSupport;
import com.sqli.myApp.model.BookDTO;
import com.sqli.myApp.security.Right;

/**
 * The Class BookControllerTest.
 */
public class BookControllerTest extends AbstractRestTestSupport {

	/**
	 * All.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void all() throws Exception {
		authenticate(new Right[] { Right.GET_ALL_BOOK });

		MvcResult result = mockMvc.perform(get("/books").header("host", "localhost:80").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		ObjectMapper mapper = new ObjectMapper();
		List<BookDTO> list = mapper.readValue(result.getResponse().getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, BookDTO.class));
		Assert.assertEquals(3, list.size());
	}
}
