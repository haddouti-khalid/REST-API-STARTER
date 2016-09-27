package com.sqli.commons.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sqli.commons.core.service.model.IDTO;

/**
 * The Class AbstractService.
 * 
 * @param <DTO>
 *            the generic type
 */
public abstract class AbstractService<DTO extends IDTO<?>> {

	/** The logger. */
	protected final Logger logger;

	/**
	 * Instantiates a new base service.
	 */
	public AbstractService() {
		/** Logger. */
		logger = LoggerFactory.getLogger(getClass());
	}

	/**
	 * All.
	 * 
	 * @return the list
	 */
	public List<DTO> all() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Gets the.
	 * 
	 * @param id
	 *            the id
	 * @return the dto
	 */
	public DTO get(Long id) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Delete.
	 * 
	 * @param id
	 *            the id
	 */
	public void delete(Long id) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Save.
	 * 
	 * @param dto
	 *            the dto
	 * @return the long
	 */
	public Long save(DTO dto) {
		throw new UnsupportedOperationException();
	}
}