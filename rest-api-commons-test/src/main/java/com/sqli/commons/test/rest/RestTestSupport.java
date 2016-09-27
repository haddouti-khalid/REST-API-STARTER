package com.sqli.commons.test.rest;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Arrays;

import org.dbunit.DatabaseUnitException;
import org.springframework.test.context.transaction.BeforeTransaction;

import com.sqli.commons.test.data.DataSourceTestLoader;

public abstract class RestTestSupport extends DataSourceTestLoader {
	
	/** The done once. */
	private static boolean doneOnce = false;
	
	private static String[] lastDataset = null;
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

	/* (non-Javadoc)
	 * @see com.sqli.commons.test.data.DataSourceTestLoader#onSetUpInTransaction()
	 */
	@BeforeTransaction
	public void onSetUpInTransaction() throws DatabaseUnitException,
			SQLException, FileNotFoundException {
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
}
