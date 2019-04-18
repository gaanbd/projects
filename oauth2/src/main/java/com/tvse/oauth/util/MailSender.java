package com.tvse.oauth.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @author techmango
 *
 */
public class MailSender extends JavaMailSenderImpl {

	@Value("${email.support.username}")
	public String emailUserName;

	@Value("${email.support.password}")
	public String emailPassword;

	public MailSender() {
		super.setUsername(emailUserName);
		super.setPassword(emailPassword);
	}

}