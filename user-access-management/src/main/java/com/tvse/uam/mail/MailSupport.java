package com.tvse.uam.mail;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.tvse.common.exception.ApplicationException;
import com.tvse.common.exception.BusinessException;
import com.tvse.uam.constants.MessageConstants;
import com.tvse.uam.domain.EmailSettings;

/**
 * @author techmango
 *
 */
public class MailSupport {

	private static final Logger LOGGER = LogManager.getLogger(MailSupport.class.getName());

	private MailSender mailSender = new MailSender();

	@Value("${server.context-path}")
	private String contextPathUrl;

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendHtmlMail(String to, String subject, EmailSettings emailSettings, String message) {
		LOGGER.info("Processing request to send mail");
		if (StringUtils.isBlank(to)) {
			LOGGER.error(MessageConstants.MAILER_ERROR_TO_FIELD_IS_INVALID);
			throw new BusinessException(MessageConstants.MAILER_ERROR_TO_FIELD_IS_INVALID);
		} else if (StringUtils.isBlank(subject)) {
			LOGGER.error(MessageConstants.MAILER_ERROR_SUBJECT_IS_INVALID);
			throw new BusinessException(MessageConstants.MAILER_ERROR_SUBJECT_IS_INVALID);
		} else if (StringUtils.isBlank(message)) {
			LOGGER.error(MessageConstants.MAILER_ERROR_MESSAGE_IS_INVALID);
			throw new BusinessException(MessageConstants.MAILER_ERROR_MESSAGE_IS_INVALID);
		} else {
			getConfig(emailSettings);
			MimeMessage mimeMessage = this.mailSender.createMimeMessage();
			try {
				mimeMessage.setContent(message, "text/html");
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
				helper.setFrom(emailSettings.getSenderEmail());
				if (to.contains(",")) {
					helper.setTo(to.split(","));
				} else {
					helper.setTo(to);
				}
				helper.setSubject(subject);
				helper.setText(message, true);

				this.mailSender.send(mimeMessage);
			} catch (MessagingException e) {
				LOGGER.debug("MessagingException while sendHtmlMail() :: {}", e);
				throw new ApplicationException();
			} catch (Exception ex) {
				LOGGER.debug("Exception while sendHtmlMail() :: {}", ex);
				throw new ApplicationException();
			}
			LOGGER.info("Mail has been sent with HTML content.");
		}
	}

	private void getConfig(EmailSettings emailSettings) {
		mailSender.setHost(emailSettings.getHost());
		mailSender.setPort(Integer.parseInt(emailSettings.getPort()));
		mailSender.setUsername(emailSettings.getSenderEmail());
		Properties javaMailProperties = new Properties();
		String smtpgmail = "smtp.gmail.com";
		String appHost = emailSettings.getHost();
		javaMailProperties.put("mail.transport.protocol", "smtp");
		if (StringUtils.isNotBlank(appHost) && StringUtils.equalsIgnoreCase(appHost, smtpgmail)) {
			mailSender.setPassword(emailSettings.getSenderPassword());
			javaMailProperties.put("mail.smtp.auth", "true");
			javaMailProperties.put("mail.smtp.starttls.enable", "true");
			javaMailProperties.put("mail.smtp.host", smtpgmail);
			javaMailProperties.put("mail.smtp.port", "587");
			javaMailProperties.put("mail.smtp.ssl.trust", smtpgmail);
		} else {
			mailSender.setPassword(emailSettings.getSenderPassword());
			javaMailProperties.put("mail.smtp.auth", "false");
			javaMailProperties.put("mail.smtp.starttls.enable", "true");
			javaMailProperties.put("mail.smtp.host", appHost);
			javaMailProperties.put("mail.smtp.port", emailSettings.getPort());

		}
		mailSender.setJavaMailProperties(javaMailProperties);
	}
}