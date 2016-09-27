package com.sqli.commons.test.data;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dbunit.DatabaseUnitException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.DependsOn;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.BeforeTransaction;

import com.sqli.commons.core.data.GenericDao;
import com.sqli.commons.core.data.model.PKEntity;
import com.sqli.commons.test.service.ServiceTestSupport;

/**
 * Base class for Dao testing - <b>See BaseTestCase javadoc for Spring
 * configuration file</b>.<br/>
 * Usage:
 * 
 * <pre>
 * public class ActivityDaoTest extends GenericDaoTestSupport&lt;Activity, Long&gt; {
 * </pre>
 * 
 * @param <T>
 *            the entity object
 * @param <PK>
 *            the primary key
 * @see ServiceTestSupport
 */
@ContextConfiguration({ "classpath:/spring/test-persistence-context.xml",
		"classpath:/spring/test-datasource-context.xml",
		"classpath:/spring/data-context.xml",
		"classpath:/spring/test-data-context.xml" })
public abstract class DaoTestSupport<T extends PKEntity<PK>, PK extends Serializable>
		extends DataSourceTestLoader {

	/** The Constant SETTER_PREFIX. */
	private static final String SETTER_PREFIX = "set";

	/** doneOnce */
	private static boolean doneOnce = false;

	/** lastDataset */
	private static String[] lastDataset = null;

	/** GenericDao */
	protected abstract GenericDao<T, PK> getDao();

	/** getNewInstance */
	protected abstract T getNewInstance(int index);

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
	 * @see com.sqli.commons.test.data.DataSourceTestLoader
	 * #onSetUpInTransaction()
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

	@Test
	public void testFindAll() {
		List<T> allElements = getDao().findAll();
		Assert.assertNotNull(allElements);
		Assert.assertFalse(allElements.isEmpty());
	}

	@Test
	public void testCreateOrUpdate() {
		// creer une nouvelle instance
		T newInstance = getNewInstance(0);
		if (newInstance != null) {
			// persiste le nouvel objet
			getDao().createOrUpdate(newInstance);
			Assert.assertNotNull(newInstance.getPK());
			// rechercher de l'instance cree
			T updatedInstance = getDao().get(newInstance.getPK());
			Assert.assertNotNull(updatedInstance);
		}
	}

	@Test
	public void testGet() {
		// recupere une instance depuis sa PK
		List<T> allElements = getDao().findAll();
		PK pk = allElements.get(allElements.size() - 1).getPK();
		T element = getDao().get(pk);
		Assert.assertNotNull(element);
	}

	@Test
	@DependsOn(value = { "testCreate", "testGet" })
	public void testUpdate() {
		// met a jour une instance depuis sa PK
		List<T> allElements = getDao().findAll();
		PK pk = allElements.get(allElements.size() - 1).getPK();
		Assert.assertNotNull(pk);

		T element = getDao().get(pk);
		Assert.assertNotNull(element);

		// Modifier l'element (sauf la PK)
		getDao().update(element);
	}

	/** Remove an object from persistent storage in the database */
	@Test
	@DependsOn(value = "testUpdate")
	public void testDelete() {
		T newInstance = getNewInstance(0);
		if (newInstance != null) {
			// persiste le nouvel objet
			getDao().createOrUpdate(newInstance);
			Assert.assertNotNull(newInstance.getPK());
			// rechercher de l'instance cree
			T updatedInstance = getDao().get(newInstance.getPK());
			Assert.assertNotNull(updatedInstance);

			// recupere une instance depuis sa PK
			List<T> allElements = getDao().findAll();
			Assert.assertNotNull(allElements);
			Assert.assertFalse(allElements.isEmpty());

			int initialSize = allElements.size();

			getDao().remove(allElements.get(initialSize - 1));

			allElements = getDao().findAll();
			Assert.assertNotNull(allElements);
			Assert.assertFalse(allElements.isEmpty());

			int finalSize = allElements.size();

			Assert.assertEquals(
					"Un element aurait du etre supprime de la collection.",
					initialSize, finalSize + 1);
		}
	}

	private List<T> findAll() {
		List<T> allElements = getDao().findAll();
		Assert.assertNotNull(allElements);
		Assert.assertFalse(allElements.isEmpty());
		return allElements;
	}

	@Override
	protected String[] getDatasetPath() {
		return new String[] { "/dbunit/test-dataset.xml" };
	}

	/**
	 * Sets the all parameters.
	 * 
	 * @param instance
	 *            the instance
	 * @param index
	 *            the index
	 */
	protected final void setAllParameters(Object instance, int index) {
		for (Method method : instance.getClass().getMethods()) {
			if (method.getName().startsWith(SETTER_PREFIX)
					&& !method.getName().equals("setPK")) {
				// la methode ressemble a un setter
				Class<?>[] parameterTypes = method.getParameterTypes();
				if (parameterTypes.length == 1) {
					// un seul parametre
					Class<?> pType = parameterTypes[0];
					setParameter(instance, method, pType, index);
				}
			}
		}
	}

	/**
	 * Gets the int value.
	 * 
	 * @param pType
	 *            the type
	 * @param index
	 *            the index
	 * @return the int value
	 */
	private Integer getIntValue(Class<?> pType, int index) {
		Integer value = null;
		if (pType == Integer.class || pType == int.class) {
			value = Integer.valueOf(index);
		}
		return value;
	}

	/**
	 * Gets the long value.
	 * 
	 * @param pType
	 *            the type
	 * @param index
	 *            the index
	 * @return the long value
	 */
	private Long getLongValue(Class<?> pType, int index) {
		Long value = null;
		if (pType == Long.class || pType == long.class) {
			value = Long.valueOf(index);
		}
		return value;
	}

	/**
	 * Gets the double value.
	 * 
	 * @param pType
	 *            the type
	 * @param index
	 *            the index
	 * @return the double value
	 */
	private Double getDoubleValue(Class<?> pType, int index) {
		Double value = null;
		if (pType == Double.class || pType == double.class) {
			value = Double.valueOf(index);
		}
		return value;
	}

	/**
	 * Gets the short value.
	 * 
	 * @param pType
	 *            the type
	 * @param index
	 *            the index
	 * @return the short value
	 */
	private Short getShortValue(Class<?> pType, int index) {
		Short value = null;
		if (pType == Short.class || pType == short.class) {
			value = Short.valueOf((short) index);
		}
		return value;
	}

	/**
	 * Gets the boolean value.
	 * 
	 * @param pType
	 *            the type
	 * @return the boolean value
	 */
	private Boolean getBooleanValue(Class<?> pType) {
		Boolean value = null;
		if (pType == Boolean.class || pType == boolean.class) {
			value = Boolean.FALSE;
		}
		return value;
	}

	/**
	 * Gets the value.
	 * 
	 * @param method
	 *            the method
	 * @param pType
	 *            the type
	 * @param index
	 *            the index
	 * @return the value
	 */
	private Object getValue(Method method, Class<?> pType, int index) {
		String methodName = method.getName();
		Object value = null;
		if (pType == String.class) {
			value = getStringValue(methodName, index);
		} else if (pType == Integer.class || pType == int.class) {
			value = getIntValue(pType, index);
		} else if (pType == Long.class || pType == long.class) {
			value = getLongValue(pType, index);
		} else if (pType == Double.class || pType == double.class) {
			value = getDoubleValue(pType, index);
		} else if (pType == Short.class || pType == short.class) {
			value = getShortValue(pType, index);
		} else if (pType == Boolean.class || pType == boolean.class) {
			value = getBooleanValue(pType);
		}

		if (pType == Date.class) {
			value = new Date();
		}

		return value;
	}

	/**
	 * Sets the parameter.
	 * 
	 * @param instance
	 *            the instance
	 * @param method
	 *            the method
	 * @param pType
	 *            the type
	 * @param index
	 *            the index
	 * @throws LogbookException
	 */
	protected final void setParameter(Object instance, Method method,
			Class<?> pType, int index) {
		Object value = getValue(method, pType, index);
		if (value != null) {
			// alimentation de la valeur seulement si elle est non nulle (afin
			// de preserver les valeurs par defaut)
			try {
				method.invoke(instance, value);
			} catch (IllegalArgumentException e) {
				logger.error(e.getMessage(), e);
				throw new RuntimeException(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage(), e);
				throw new RuntimeException(e.getMessage(), e);
			} catch (InvocationTargetException e) {
				logger.error(e.getMessage(), e);
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}

	/**
	 * Gets the string value.
	 * 
	 * @param methodName
	 *            the method name
	 * @param index
	 *            the index
	 * @return the string value
	 */
	protected final String getStringValue(String methodName, int index) {
		return methodName.substring(SETTER_PREFIX.length()) + "_" + index;
	}

	/**
	 * Gets the new instances list.
	 * 
	 * @param index
	 *            the index
	 * @param number
	 *            the number
	 * @return the new instances list
	 */
	public final List<T> getNewInstancesList(int index, int number) {
		List<T> contractList = new ArrayList<T>();
		for (int i = index; i < index + number; i++) {
			contractList.add(getNewInstance(i));
		}
		return contractList;
	}

	/**
	 * Gets the new instances set.
	 * 
	 * @param index
	 *            the index
	 * @param number
	 *            the number
	 * @return the new instances set
	 */
	public final Set<T> getNewInstancesSet(int index, int number) {
		Set<T> contractList = new HashSet<T>();
		for (int i = index; i < index + number; i++) {
			contractList.add(getNewInstance(i));
		}
		return contractList;
	}
}
