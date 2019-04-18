package com.tvse.oauth.service.impl;

import java.util.Base64;

import javax.inject.Inject;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.tvse.common.dto.ResponseDTO;
import com.tvse.common.exception.ApplicationException;
import com.tvse.common.exception.BusinessException;
import com.tvse.common.exception.DataRepositoryException;
import com.tvse.oauth.constants.ApplicationConstants;
import com.tvse.oauth.constants.MessageConstants;
import com.tvse.oauth.domain.User;
import com.tvse.oauth.repository.UserRepository;
import com.tvse.oauth.service.PasswordService;
import com.tvse.oauth.util.CommonUtil;
import com.tvse.oauth.util.MailManagerUtil;

/**
 * @author techmango
 *
 */
@Service
public class PasswordServiceImpl implements PasswordService {

	private static final Logger LOGGER = LogManager.getLogger(PasswordServiceImpl.class.getName());

	private UserRepository userRepository;

	private MailManagerUtil mailManagerUtil;

	private static final String FORGOT_PASS_MAIL = "ForgotPassword";

	@Inject
	public PasswordServiceImpl(UserRepository userRepository, MailManagerUtil mailManagerUtil) {
		this.userRepository = userRepository;
		this.mailManagerUtil = mailManagerUtil;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tvse.oauth.service.PasswordService#createForgotPassword(java.lang.String)
	 */
	@Override
	public ResponseDTO<String> createForgotPassword(String email) {
		LOGGER.info("PasswordServiceImpl createForgotPassword() Intiated :");
		try {
			User user = userRepository.findByEmailId(email);
			if (ObjectUtils.isEmpty(user)) {
				throw new BusinessException(MessageConstants.NON_REGISTERED_USER);
			} else if (user.getIsActive()) {
				throw new BusinessException(MessageConstants.INACTIVE_USER);
			} else {
				String password = RandomStringUtils.randomAlphanumeric(6);
				String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes("utf-8"));
				user.setUserPassword(encodedPassword);
				userRepository.save(user);
				mailManagerUtil.sendUserCreationNotificationMail(user.getEmail(), password, FORGOT_PASS_MAIL,
						user.getFirstName());
				LOGGER.info("PasswordServiceImpl createForgotPassword() Terminated :");
				return new ResponseDTO<>(false, ApplicationConstants.SUCCESS_CODE_201,
						MessageConstants.FORGOT_PASSWRD_MAIL, null);
			}
		} catch (BusinessException be) {
			LOGGER.error(CommonUtil.errorFormat(be));
			throw be;
		} catch (DataAccessException dae) {
			LOGGER.error(CommonUtil.errorFormat(dae));
			throw new DataRepositoryException();
		} catch (Exception e) {
			LOGGER.error(CommonUtil.errorFormat(e));
			throw new ApplicationException();
		}
	}

}
