package com.sqli.commons.core.data.model;

/**
 * The Interface PKEntity. <br/>
 * <br/>
 * Usage:
 * 
 * <pre>
 * public class Activity implements PKEntity&lt;Long&gt;, Serializable {
 * </pre>
 * 
 * @param <PK>
 *            the generic type
 * @author SQLI
 */
public abstract interface PKEntity<PK> {

	/**
	 * Retourne la clé primaire de l'entité.
	 * 
	 * @return the pK
	 */
	PK getPK();

	/**
	 * 
	 * @param pk
	 */
	void setPK(PK pk);

	/**
	 * hashCode
	 * 
	 * @return
	 */
	public int hashCode();

	/**
	 * equals
	 * 
	 * @param obj
	 * @return
	 */
	public boolean equals(Object obj);
}
