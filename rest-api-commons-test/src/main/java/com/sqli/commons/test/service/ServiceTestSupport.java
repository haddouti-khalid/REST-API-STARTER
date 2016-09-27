package com.sqli.commons.test.service;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.dbunit.DatabaseUnitException;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

import com.sqli.commons.test.data.DataSourceTestLoader;

/**
 * Unit test base class for Service unit testing. <br/>
 * <h3>Spring xml configuration:</h3>
 * <b>classpath:/spring/test-persistence-context.xml</b>
 * @author csoullard
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = { "classpath:/spring/business-context.xml", "classpath:/spring/test-business-context.xml", "classpath:/spring/test-persistence-context.xml", "classpath:/spring/test-datasource-context.xml",
		"classpath:/spring/data-context.xml" })
public abstract class ServiceTestSupport extends DataSourceTestLoader {
	
	/** The done once. */
	private static boolean doneOnce = false;

	private static String[] lastDataset = null;

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Inits the once.
	 * 
	 * @return true, if successful
	 */
	protected boolean initOnce() {
		return true;
	}

	/**
	 * Dataset changed.
	 * 
	 * @return true, if successful
	 */
	private boolean datasetNotChanged() {
		String[] current = getDatasetPath();
		if (lastDataset == null) {
			return true;
		} else {
			boolean same = true;
			for (String file : lastDataset) {
				if (!Arrays.asList(current).contains(file)) {
					same = false;
					break;
				}
			}

			for (String file : current) {
				if (!Arrays.asList(lastDataset).contains(file)) {
					same = false;
					break;
				}
			}

			return same;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sqli.commons.test.data.DataSourceTestLoader
	 * #onSetUpInTransaction()
	 */
	@BeforeTransaction
	public void onSetUpInTransaction() throws DatabaseUnitException, SQLException, FileNotFoundException {
		if ((!initOnce() || (initOnce() && !doneOnce)) || !datasetNotChanged()) {
			super.onSetUpInTransaction();
			doneOnce = true;
		}

		lastDataset = getDatasetPath();
	}

	@Override
	protected String[] getDatasetPath() {
		return new String[] { "/dbunit/test-dataset.xml" };
	}

	@After
	public void flush() {
		if (entityManager != null) {
			entityManager.flush();
		}
	}
}
