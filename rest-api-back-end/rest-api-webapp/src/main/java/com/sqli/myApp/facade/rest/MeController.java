package com.sqli.myApp.facade.rest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sqli.myApp.security.Right;
import com.sqli.myApp.security.UserHasRight;
import com.sqli.myApp.security.authentication.AuthenticationDetail;

/**
 * The Class MeController.
 */
@Controller
public class MeController {

	/**
	 * Retrieve current user.
	 * 
	 * @return the user dto
	 */
	@UserHasRight(Right.GET_ME)
	@ResponseBody
	public AuthenticationDetail get() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		AuthenticationDetail detail = null;
		if (auth.getDetails() != null) {
			detail = (AuthenticationDetail) auth.getDetails();
		}
		return detail;
	}
}
