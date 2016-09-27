package com.sqli.commons.core.data;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sqli.commons.core.data.model.PKEntity;

/**
 * The JpaGenericDao interface. <br/>
 * Provide basic functionalities such as findAll(), createOrUpdate(), etc.<br/>
 * <br/>
 * Usage:
 * 
 * <pre>
 * public class ActivityDao extends JpaGenericDao&lt;Activity, Long&gt; {
 * </pre>
 * 
 * @param <T>
 *            the generic entity
 * @param <PK>
 *            the generic primary key
 * @author dguerin
 * 
 */

@Repository
public abstract class AbstractDao<T extends PKEntity<PK>, PK extends Serializable>
		implements GenericDao<T, PK> {

	/** The logger */
	protected Logger logger;

	/** The type. */
	private final Class<T> type;

	/** Entity Manager . */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Constructor.
	 * 
	 * @param type
	 *            type of Entity
	 */
	public AbstractDao(Class<T> type) {
		this.type = type;
		/** Logger. */
		logger = LoggerFactory.getLogger(getClass());

	}

	/**
	 * @see com.sqli.commons.core.data.GenericDao#createOrUpdate(java.lang
	 *      .Object)
	 */
	public final void createOrUpdate(T entity) {

		if (entity.getPK() == null) {
			// new
			entityManager.persist(entity);
		} else {
			// update
			entityManager.merge(entity);
		}

	}

	/**
	 * @see com.sqli.commons.core.data.GenericDao#get(java.io.
	 *      Serializable)
	 */
	public final T get(PK id) {
		return entityManager.find(type, id);
	}

	/**
	 * @see com.sqli.commons.core.data.GenericDao#update(java.lang
	 *      .Object)
	 */
	public final T update(T entity) {
		// update
		return entityManager.merge(entity);
	}

	/**
	 * @see com.sqli.commons.core.data.GenericDao#remove(java.lang
	 *      .Object)
	 */
	public final void remove(T entity) {
		entityManager.remove(entity);
	}

	/**
	 * @see com.sqli.commons.core.data.GenericDao#remove(java.lang
	 *      .Object)
	 */
	public final void remove(PK id) {
		try {
			T instance = type.newInstance();
			instance.setPK(id);
			instance = entityManager.merge(instance);
			entityManager.remove(instance);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see com.sqli.commons.core.data.GenericDao#findAll()
	 */
	@SuppressWarnings("unchecked")
	public final List<T> findAll() {
		return this.entityManager.createQuery("from " + type.getName())
				.getResultList();
	}

	/**
	 * Retourne le résultat unique ou null.
	 * 
	 * @param query
	 *            the query
	 * @return the single result
	 */
	protected final T getSingleResult(Query query) {
		return getSingleResult(query, type);
	}

	/**
	 * Retourne le résultat unique ou null.
	 * 
	 * @param <R>
	 *            the generic type
	 * @param query
	 *            the query
	 * @param type
	 *            the type
	 * @return the single result
	 */
	@SuppressWarnings("unchecked")
	protected final <R> R getSingleResult(Query query, Class<R> type) {
		query.setMaxResults(1);
		try {
			return (R) query.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	/**
	 * Gets the entity manager.
	 * 
	 * @return the entityManager
	 */
	public final EntityManager getEntityManager() {
		return entityManager;
	}
}
