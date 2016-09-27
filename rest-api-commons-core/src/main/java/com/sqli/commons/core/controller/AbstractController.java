package com.sqli.commons.core.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sqli.commons.core.service.model.AbstractDTO;
import com.sqli.commons.core.service.model.IdentifierDTO;

/**
 * The Class AbstractController.
 * 
 * @param <DTO>
 *            the generic type
 */
public abstract class AbstractController<DTO extends AbstractDTO<?>> {

	/** The logger. */
	protected final Logger logger;

	/**
	 * Instantiates a new abstract controller.
	 */
	public AbstractController() {
		/** Logger. */
		logger = LoggerFactory.getLogger(getClass());
	}

	/**
	 * Gets the.
	 * 
	 * @param id
	 *            the id
	 * @return the dto
	 */
	public @ResponseBody DTO get(@PathVariable Long id) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Find.
	 * 
	 * @return the list
	 */
	public @ResponseBody List<DTO> all() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Creates the.
	 * 
	 * @param dto
	 *            the dto
	 * @return the long
	 */
	public @ResponseBody IdentifierDTO<?> create(@RequestBody DTO dto) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Update.
	 * 
	 * @param dto
	 *            the dto
	 */
	public @ResponseBody void update(@RequestBody DTO dto) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Delete.
	 * 
	 * @param id
	 *            the id
	 */
	public @ResponseBody void delete(@PathVariable Long id) {
		throw new UnsupportedOperationException();
	}
}
