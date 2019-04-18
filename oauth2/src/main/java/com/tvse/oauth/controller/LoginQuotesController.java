package com.tvse.oauth.controller;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tvse.common.dto.ResponseDTO;
import com.tvse.oauth.constants.ApplicationConstants;
import com.tvse.oauth.domain.LoginQuotes;
import com.tvse.oauth.service.LoginQuotesService;

/**
 * @author techmango
 *
 */
@RestController
public class LoginQuotesController {

	private static final Logger LOGGER = LogManager.getLogger(LoginQuotesController.class.getName());

	private LoginQuotesService loginQuotesService;

	@Inject
	public LoginQuotesController(LoginQuotesService loginQuotesService) {
		this.loginQuotesService = loginQuotesService;
	}

	/**
	 * @return
	 */
	@GetMapping(value = ApplicationConstants.LOGIN_QUOTES, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<LoginQuotes>> getLoginQuotes() {
		LOGGER.info("Start getLoginQuotes() in Controller");
		return new ResponseEntity<>(loginQuotesService.getLoginQuotes(), HttpStatus.OK);
	}

}
