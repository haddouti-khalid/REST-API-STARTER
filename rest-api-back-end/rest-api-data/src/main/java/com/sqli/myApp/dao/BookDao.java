package com.sqli.myApp.dao;

import org.springframework.stereotype.Repository;

import com.sqli.commons.core.data.AbstractDao;
import com.sqli.myApp.model.Book;

/**
 * The Class BookDao.
 */
@Repository
public class BookDao extends AbstractDao<Book, Long> {

	/**
	 * Instantiates a new book dao.
	 */
	public BookDao() {
		super(Book.class);
	}

	/**
	 * find By Id
	 * 
	 * @param id
	 * @return Book
	 */
	public Book findById(Long id) {
		return super.get(id);
	}
}
