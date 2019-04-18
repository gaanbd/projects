package com.tvse.oauth.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.tvse.oauth.constants.ApplicationConstants;
import com.tvse.oauth.domain.EmailConfiguration;
import com.tvse.oauth.repository.EmailConfigurationRepository;
import com.tvse.oauth.repository.EmailSettingsRepository;

/**
 * @author techmango
 *
 */
@Component
public class MailManagerUtil {

	private static final Logger LOGGER = LogManager.getLogger(MailManagerUtil.class.getName());

	@Value("${spring.mail-uri.domain-url}")
	public String domainUrl;

	@Value("${spring.mail.logo}")
	private String logo;

	private static final String EXCEPTION_WHILE_SENDING_MAIL = "Exception while sending mail";
	public static final String DATE_FORMAT = "dd-MMM-yyyy";

	private EmailConfigurationRepository emailConfigurationRepository;
	private EmailSettingsRepository emailSettingsRepository;

	@Inject
	public MailManagerUtil(EmailConfigurationRepository emailConfigurationRepository,
			EmailSettingsRepository emailSettingsRepository) {
		this.emailConfigurationRepository = emailConfigurationRepository;
		this.emailSettingsRepository = emailSettingsRepository;
	}

	public void sendUserCreationNotificationMail(String emailId, String tempPassword, String configName,
			String userFullName) {
		try {
			LOGGER.debug("MailManagerUtil sendUserCreationNotificationMail() Initiated");
			Map<String, Object> metaEmailContent = new HashMap<>();
			DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			String formatDateTime = LocalDateTime.now().format(format);

			EmailConfiguration emailConfig = getEmailConfigurationByConfigName(configName);
			if (!ObjectUtils.isEmpty(emailConfig) && null != emailId && null != tempPassword) {
				metaEmailContent.put(ApplicationConstants.TEMP_USERP, tempPassword);
				metaEmailContent.put(ApplicationConstants.USERNAME, userFullName);
				metaEmailContent.put(ApplicationConstants.DATE, formatDateTime);
				metaEmailContent.put(ApplicationConstants.CREATE_PASS_LINK, domainUrl);
				metaEmailContent.put(ApplicationConstants.TVSE_LOGO, logo);
				sendHtmlMail(emailId, metaEmailContent, emailConfig);
			} else {
				LOGGER.error("Exception caught in sendUserCreationNotificationMail");
			}
			LOGGER.debug("MailManagerUtil sendUserCreationNotificationMail() Ends");
		} catch (Exception e) {
			LOGGER.error("Exception caught in sendUserCreationNotificationMail : {}", e);
		}
	}

	private void sendHtmlMail(String emailId, Map<String, Object> metaEmailContent, EmailConfiguration emailConfig) {
		try {
			LOGGER.debug("MailManagerUtil sendHtmlMail() Initiated");
			new MailSupport().sendHtmlMail(emailId, emailConfig.getSubject(), emailSettingsRepository.findAll().get(0),
					mergeTemplateWithData(emailConfig.getEmailContent(), metaEmailContent));
			LOGGER.debug("MailManagerUtil sendHtmlMail() Ends");
		} catch (RuntimeException re) {
			LOGGER.error(EXCEPTION_WHILE_SENDING_MAIL, re);
		} catch (Exception ex) {
			LOGGER.error(EXCEPTION_WHILE_SENDING_MAIL, ex);
		}
	}

	@SuppressWarnings("rawtypes")
	public static String mergeTemplateWithData(String templateContent, Map<String, Object> mergeDetails) {
		LOGGER.debug("MailManagerUtil mergeTemplateWithData() Initiated");
		String templateContentParam = templateContent;
		for (Iterator iter = mergeDetails.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			if (null != mergeDetails.get(key)) {
				templateContentParam = StringUtils.replace(templateContentParam, key, mergeDetails.get(key).toString());
			}
		}
		LOGGER.debug("MailManagerUtil mergeTemplateWithData() Ends");
		return templateContentParam;
	}

	public EmailConfiguration getEmailConfigurationByConfigName(String configName) {
		LOGGER.debug("MailManagerUtil getEmailConfigurationByConfigName() Initiated");
		EmailConfiguration emailConfiguration = new EmailConfiguration();
		if (StringUtils.isNotBlank(configName)) {
			emailConfiguration = emailConfigurationRepository.findEmailConfigurationByEmailConfigName(configName);
		}
		LOGGER.debug("MailManagerUtil getEmailConfigurationByConfigName() Ends");
		return emailConfiguration;
	}

}