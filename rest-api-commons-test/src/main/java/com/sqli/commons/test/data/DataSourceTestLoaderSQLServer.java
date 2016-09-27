package com.sqli.commons.test.data;

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
import org.dbunit.ext.mssql.InsertIdentityOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;

/**
 * DataSourceTestLoader for SQL server.
 */
public abstract class DataSourceTestLoaderSQLServer extends
		AbstractTransactionalJUnit4SpringContextTests {

	/** The Constant FILE_SEPARATOR. */
	protected static final String FILE_SEPARATOR = System
			.getProperty("file.separator");

	/** The data source. */
	@Autowired
	private DataSource dataSource;

	/** The data set path. */
	private String dataSetPath = "/dbunit/test-dataset.xml";

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
	public void onSetUpInTransaction() throws DatabaseUnitException,
			SQLException, FileNotFoundException {
		final Connection con = DataSourceUtils.getConnection(dataSource);

		IDatabaseConnection dbUnitCon = new DatabaseConnection(con);
		DatabaseConfig config = dbUnitCon.getConfig();

		config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
				new HsqldbDataTypeFactory());
		FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
		flatXmlDataSetBuilder.setColumnSensing(true);
		IDataSet dataSet = flatXmlDataSetBuilder.build(getClass().getResourceAsStream(dataSetPath));
		try {
			InsertIdentityOperation.CLEAN_INSERT.execute(dbUnitCon, dataSet);

		} finally {
			DataSourceUtils.releaseConnection(con, dataSource);
			if (!con.isClosed()) {
				con.close();
			}
		}
	}

	/**
	 * Clean datas.
	 */
	@AfterTransaction
	public void cleanDatas() {
	}

	/**
	 * Prepare datas.
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@BeforeTransaction
	public void prepareDatas() throws IOException {
	}
}
