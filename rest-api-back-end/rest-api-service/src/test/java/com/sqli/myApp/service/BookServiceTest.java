package com.sqli.myApp.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import com.sqli.commons.test.service.ServiceTestSupport;
import com.sqli.myApp.dao.BookDao;
import com.sqli.myApp.model.Book;

/**
 * The Class BookServiceTest.
 */
public class BookServiceTest extends ServiceTestSupport {

	/** The book service. */
	@Autowired
	private BookService bookService;

	@Before
	public void setUp() {
		BookDao mockBookDao = Mockito.mock(BookDao.class);
		// the mock
		Book mockResult = new Book();
		mockResult.setPK(Long.valueOf(1));
		mockResult.setTitle("My Book title");
		mockResult.setDescription("My Book description");
		Mockito.when(mockBookDao.findById(Mockito.any(Long.class))).thenReturn(mockResult);
		ReflectionTestUtils.setField(bookService, "bookDao", mockBookDao);
	}

	/**
	 * test Get By Id.
	 */
	@Test
	public void testGetById() {
		Long id = Long.valueOf(1);
		Assert.assertNotNull(bookService.get(id));
		Assert.assertEquals(id, bookService.get(id).getId());
	}
}
