package com.tvse.oauth.constants;

/**
 * MessageConstants Class to keep the constant values
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
public class MessageConstants {

	private MessageConstants() {
	}

	public static final String INVALID_USER = "Unable to Login";// dont move this to message_property
	public static final String SUCCESS_MESSAGE_QUOTES = "success.message.quotes";
	public static final String STATIC_QUOTES_HTML_CONTENT = "<p style=\"font-style: normal;font-weight: 300;line-height: normal;font-size: 30px;color: #555555;margin: 0;\">Imagine your customer is your best friend — listen to their concerns, be a shoulder to lean on, and then shift the focus from what went wrong to how you can help make it right.</p>";
	public static final String STATIC_QUOTES_AUTHOR = "Rachel Hogue";

	public static final String MAILER_ERROR_MESSAGE_IS_INVALID = "error.mailer.message.invalid";
	public static final String MAILER_ERROR_TO_FIELD_IS_INVALID = "error.mailer.field.invalid";
	public static final String MAILER_ERROR_SUBJECT_IS_INVALID = "error.mailer.subject.invalid";
	public static final String FORGOT_PASSWRD_MAIL = "A mail has been sent to your account";
	public static final String NON_REGISTERED_USER ="error.mail.not.registered";
	public static final String INACTIVE_USER ="error.user.inactive";
	public static final String LOGIN_INACTIVE_USER = "Disabled account";
}