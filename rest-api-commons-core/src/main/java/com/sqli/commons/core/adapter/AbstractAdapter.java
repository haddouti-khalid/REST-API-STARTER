package com.sqli.commons.core.adapter;

import java.util.ArrayList;
import java.util.List;

import com.sqli.commons.core.service.model.IDTO;

/**
 * The Class AbstractAdapter.
 * 
 * @param <MODEL>
 *            the generic type
 * @param <DTO>
 *            the generic type
 */
public abstract class AbstractAdapter<MODEL extends IAdaptable, DTO extends IDTO<?>> {

	/**
	 * Adapt to dto.
	 * 
	 * @param model
	 *            the model
	 * @return the DTO adapted from the given model
	 */
	public abstract DTO adaptToDTO(MODEL model);

	/**
	 * Adapt to model.
	 * 
	 * @param dto
	 *            the dto
	 * @return the model adapted from the given DTO
	 */
	public abstract MODEL adaptToModel(DTO dto);

	/**
	 * Adapt to dto.
	 * 
	 * @param model
	 *            the model
	 * @return the list
	 */
	public final List<DTO> adaptToDTO(List<MODEL> models) {
		List<DTO> result = new ArrayList<DTO>();
		if (models != null) {
			for (MODEL model : models) {
				result.add(adaptToDTO(model));
			}
		}

		return result;
	}

	/**
	 * Adapt to model.
	 * 
	 * @param dto
	 *            the dto
	 * @return the list
	 */
	public final List<MODEL> adaptToModel(List<DTO> dtos) {
		List<MODEL> result = new ArrayList<MODEL>();
		if (dtos != null) {
			for (DTO dto : dtos) {
				result.add(adaptToModel(dto));
			}
		}

		return result;
	}
}