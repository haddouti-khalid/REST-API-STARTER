/**
 * 	
 */
package com.sqli.myApp.dao;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sqli.commons.core.data.GenericDao;
import com.sqli.commons.test.data.DaoTestSupport;
import com.sqli.myApp.model.Book;

/**
 * The Class BookDaoTest.
 */
public class BookDaoTest extends DaoTestSupport<Book, Long> {

	/** The dao. */
	@Autowired
	private BookDao dao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sqli.commons.test.data.DaoTestSupport# getNewInstance (int)
	 */
	@Override
	protected Book getNewInstance(int index) {
		Book instance = new Book();
		setAllParameters(instance, index);
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sqli.commons.test.data.DaoTestSupport#getDao ()
	 */
	@Override
	protected GenericDao<Book, Long> getDao() {
		return dao;
	}

	/**
	 * Test all.
	 */
	@Test
	public void testAll() {
		Assert.assertEquals(5, dao.findAll().size());
	}
}
