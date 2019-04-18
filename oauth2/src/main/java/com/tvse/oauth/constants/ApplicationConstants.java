package com.tvse.oauth.constants;

import org.springframework.http.HttpStatus;

/**
 * ApplicationConstants Class to keep the constant values
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
public class ApplicationConstants {

	private ApplicationConstants() {
	}
	
	public static final String GRANT_TYPE = "password";
	public static final String AUTHORIZATION_CODE = "authorization_code";
	public static final String REFRESH_TOKEN = "refresh_token";
	public static final String IMPLICIT = "implicit";
	public static final String SCOPE_READ = "read";
	public static final String SCOPE_WRITE = "write";
	public static final String RESOURCE_ID = "resource_id";

	public static final int ACCESS_TOKEN_VALIDITY_SECONDS = 432000;
	public static final int REFRESH_TOKEN_VALIDITY_SECONDS = 864000;

	public static final int SUCCESS_CODE_200 = HttpStatus.OK.value();
	public static final int SUCCESS_CODE_201 = HttpStatus.CREATED.value();
	public static final int ERROR_CODE_400 = HttpStatus.BAD_REQUEST.value();
	public static final int ERROR_CODE_404 = HttpStatus.NOT_FOUND.value();
	public static final int ERROR_CODE_500 = HttpStatus.INTERNAL_SERVER_ERROR.value();

	public static final String SUCCESS_MESSAGE_200 = "success.message";
	public static final String ERROR_MESSAGE_404 = "Not Found";
	public static final String ERROR_MESSAGE_500 = "Internal Server Error";
	public static final String UNEXPECTED_ERROR = "error.unexpected";
	public static final String UNEXPECTED_DATA_ACCESS_ERROR = "error.unexpected.dataaccess";
	public static final String B2B_APPLICATION = "B2B";
	public static final String ERROR_MESSAGE_400 = "Bad Request";

	public static final String ERROR_MESSAGE_409 = "Conflict";
	public static final String ERROR_MESSAGE_417 = "Expectation Failed";

	public static final String LOGIN_QUOTES = "/loginquotes";
	public static final String SUCCESS_MESSAGE_QUOTES = "Quotes Returned Successfully";
	
	public static final String TEMP_USERP = "$TEMP_PASSWORD$";
	public static final String USERNAME = "$USERNAME$";
	public static final String CREATE_PASS_LINK = "$CREATEPASSURL$";
	public static final String DATE = "$DATETIME$";
	public static final String TVSE_LOGO = "$TVSE_LOGO$";
	
	public static final String FORGOT_PASS = "/forgotPassword";
}