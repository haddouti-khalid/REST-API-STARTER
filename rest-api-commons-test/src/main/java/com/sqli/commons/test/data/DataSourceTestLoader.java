package com.sqli.commons.test.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;

/**
 * Base class to load data set, prepare and delete data.
 */
public abstract class DataSourceTestLoader extends AbstractTransactionalJUnit4SpringContextTests {

	/** The log. */
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/** The Constant FILE_SEPARATOR. */
	protected static final String FILE_SEPARATOR = System.getProperty("file.separator");

	/** The data source. */
	@Autowired
	private DataSource dataSource;

	/**
	 * The data set path. Override this method in your project to declare the
	 * dataset used to pass the tests.
	 */

	protected abstract String[] getDatasetPath();

	/**
	 * On set up in transaction.
	 * 
	 * @throws DatabaseUnitException
	 *             the database unit exception
	 * @throws SQLException
	 *             the sQL exception
	 * @throws FileNotFoundException
	 *             the file not found exception
	 */
	@BeforeTransaction
	public void onSetUpInTransaction() throws DatabaseUnitException, SQLException, FileNotFoundException {
		final Connection con = DataSourceUtils.getConnection(dataSource);
		con.prepareStatement("SET DATABASE REFERENTIAL INTEGRITY FALSE").execute();
		IDatabaseConnection dbUnitCon = new DatabaseConnection(con);
		DatabaseConfig config = dbUnitCon.getConfig();

		config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());
		FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
		flatXmlDataSetBuilder.setColumnSensing(true);
		try {
			for (String file : getDatasetPath()) {
				IDataSet dataSet = flatXmlDataSetBuilder.build(new FileInputStream("src/test/resources/" + file));
				DatabaseOperation.CLEAN_INSERT.execute(dbUnitCon, dataSet);
			}
		} finally {
			DataSourceUtils.releaseConnection(con, dataSource);
			if (!con.isClosed()) {
				con.close();
			}
		}
	}

}