package com.tvse.oauth.controller;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tvse.common.dto.ResponseDTO;
import com.tvse.oauth.constants.ApplicationConstants;
import com.tvse.oauth.service.PasswordService;

/**
 * @author techmango
 *
 */
@RestController
public class PasswordController {

	private static final Logger LOGGER = LogManager.getLogger(PasswordController.class.getName());

	private PasswordService passwordService;

	@Inject
	public PasswordController(PasswordService passwordService) {
		this.passwordService = passwordService;
	}

	/**
	 * @param email
	 * @return
	 */
	@PostMapping(ApplicationConstants.FORGOT_PASS)
	public ResponseDTO<String> forgotPassword(@RequestParam("email") String email) {
		LOGGER.info("Start forgotPassword() in Controller");
		return passwordService.createForgotPassword(email);
	}
}
