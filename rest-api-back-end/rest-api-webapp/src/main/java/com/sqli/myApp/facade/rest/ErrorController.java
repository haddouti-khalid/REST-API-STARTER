package com.sqli.myApp.facade.rest;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sqli.commons.core.controller.AbstractController;
import com.sqli.commons.core.service.model.IdentifierDTO;
import com.sqli.myApp.model.ErrorDTO;

/**
 * The Class ErrorController.
 */
@Controller
public class ErrorController extends AbstractController<ErrorDTO> {

	/**
	 * Creates the.
	 *
	 * @param error
	 *            the error
	 * @param response
	 *            the response
	 * @return the identifier dto
	 */
	@ResponseBody
	public IdentifierDTO<Long> create(@RequestBody ErrorDTO error, HttpServletResponse response) {
		StringBuilder globalError = new StringBuilder();
		if (error != null) {
			globalError.append(error.getMessage());
			if (error.getStacktrace() != null) {
				for (String line : error.getStacktrace()) {
					globalError.append("\n\t");
					globalError.append(line);
				}
			}
			logger.error(globalError.toString());
		}
		response.setStatus(HttpServletResponse.SC_CREATED);

		return null;
	}
}
