package com.sqli.commons.core.data;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

/**
 * The GenericDao interface. <br/>
 * Provide basic functionalities such as findAll(), createOrUpdate(), etc.<br/>
 * <br/>
 * Usage:
 * 
 * <pre>
 * public class ActivityDao implements GenericDao&lt;Activity, Long&gt; {
 * </pre>
 * 
 * @param <T>
 *            the generic entity
 * @param <PK>
 *            the generic primary key
 * @author dguerin
 */
public interface GenericDao<T, PK extends Serializable> {

	/**
	 * createOrUpdate
	 * 
	 * @param newInstance
	 *            the new instance
	 */
	void createOrUpdate(T newInstance);

	/**
	 * get
	 * 
	 * @param id
	 *            the id
	 * @return the t
	 */
	T get(PK id);

	/**
	 * update
	 * 
	 * @param transientObject
	 *            the transient object
	 * @return the t
	 */
	T update(T transientObject);

	/**
	 * remove
	 * 
	 * @param persistentObject
	 *            the persistent object
	 */
	void remove(T persistentObject);

	/**
	 * remove
	 * 
	 * @param id
	 *            the id of the persistent object
	 */
	void remove(PK id);

	/**
	 * findAll
	 * 
	 * @return the list
	 */
	List<T> findAll();

	/**
	 * getEntityManager
	 * 
	 * @return the entityManager
	 */
	EntityManager getEntityManager();
}
