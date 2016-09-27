package com.sqli.commons.test.dialect;

import org.hibernate.dialect.HSQLDialect;

public class HSQLTestDialect extends HSQLDialect {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.hibernate.dialect.HSQLDialect#getSequenceNextValString(java.lang.
	 * String)
	 */
	public String getSequenceNextValString(String sequenceName) {
		// In HSQL, in not possible to have schema in sequence name
		sequenceName = sequenceName.replaceFirst(".*\\.", "");
		/** Override sequence in uni test **/
		return "call next value for " + sequenceName;
	}

	@Override
	protected String getCreateSequenceString(String sequenceName) {
		// In HSQL, in not possible to have schema in sequence name
		sequenceName = sequenceName.replaceFirst(".*\\.", "");
		return "create sequence " + sequenceName + " start with 999";
	}
}
