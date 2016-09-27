package com.sqli.commons.test.util;

import java.io.File;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class DBLoaderUtilSQL {
	/** The logger */
	final static protected Log log = LogFactory.getLog("com.sqli.commons.test.data");

	protected abstract DataSource getDataSource();

	protected boolean isOnlyDrop() {
		return false;
	}

	public void processFile(String fileName) throws Exception {
		@SuppressWarnings("unchecked")
		List<String> lines = FileUtils.readLines(new File(fileName), "UTF8");
		Connection connection = getDataSource().getConnection();
		try {
			String currentStatement = "";
			int lineNumber = 0;
			for (String line : lines) {
				lineNumber++;
				line = line.trim();
				if (!line.isEmpty() && !line.startsWith("--")) {
					try {

						if (line.endsWith(";")) {
							Statement st = connection.createStatement();
							if (currentStatement.contains("DROP") || !isOnlyDrop()) {
								st.execute(currentStatement + line);
							}
							currentStatement = "";
						} else {
							currentStatement = currentStatement + " " + line + "\n";
						}

					} catch (Exception e) {
						log.debug(fileName + " : Error line " + lineNumber + " " + e + "\n" + currentStatement);
						currentStatement = "";

						if (!e.getMessage().contains("PRIMARY KEY") && !e.getMessage().contains("UNIQUE KEY")) {
							log.debug(e.getMessage() + " : " + line);
						}

						if (e.getMessage().contains("Incorrect syntax")) {
							log.debug(e.getMessage() + " : " + line);
						}

					}
				}
			}
		} catch (Exception e2) {
			log.error(e2);
			throw e2;
		} finally {
			connection.close();
		}
	}

	public void processFileTrigger(String fileName) throws Exception {
		@SuppressWarnings("unchecked")
		List<String> lines = FileUtils.readLines(new File(fileName), "UTF8");
		Connection connection = getDataSource().getConnection();
		try {
			String currentStatement = "";
			int lineNumber = 0;
			for (String line : lines) {
				lineNumber++;
				line = line.trim();
				if (!line.isEmpty() && !line.startsWith("--")) {
					try {
						if (line.endsWith("GO")) {
							Statement st = connection.createStatement();
							st.execute(currentStatement);
							currentStatement = "";
						} else {
							currentStatement = currentStatement + " " + line + "\n";
						}
					} catch (Exception e) {
						log.debug(fileName + " : Error line " + lineNumber + " " + e + "\n" + currentStatement);
						currentStatement = "";

						if (!e.getMessage().contains("PRIMARY KEY") && !e.getMessage().contains("UNIQUE KEY")) {
							log.debug(e.getMessage() + " : " + line);
						}

						if (e.getMessage().contains("Incorrect syntax")) {
							log.debug(e.getMessage() + " : " + line);
						}

					}
				}
			}
		} catch (Exception e2) {
			log.error(e2);
			throw e2;
		} finally {
			connection.close();
		}
	}

}
