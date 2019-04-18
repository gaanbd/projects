package com.tvse.oauth.service.impl;

import java.util.Collections;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.tvse.common.exception.ApplicationException;
import com.tvse.common.exception.BusinessException;
import com.tvse.common.exception.DataRepositoryException;
import com.tvse.oauth.constants.MessageConstants;
import com.tvse.oauth.domain.User;
import com.tvse.oauth.repository.UserRepository;
import com.tvse.oauth.util.CommonUtil;

/**
 * @author techmango (https://www.techmango.net/)
 *
 */
@Service(value = "userService")
@Transactional
public class UserServiceImpl implements UserDetailsService {

	private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class.getName());

	public static final int MAX_RANDOM_NO = 10;
	public static final int MIN_RANDOM_NO = 1;

	private UserRepository userRepository;

	@Inject
	public UserServiceImpl(@NotNull UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) {
		LOGGER.info("UserServiceImpl loadUserByUsername() Intiated :");
		User user = null;
		try {
			user = userRepository.findByUserName(username);
			if (!ObjectUtils.isEmpty(user)) {
				if(!user.getIsActive()) {
					throw new BusinessException(MessageConstants.LOGIN_INACTIVE_USER);
				}
				LOGGER.info("UserServiceImpl loadUserByUsername() Ends :");
				return new org.springframework.security.core.userdetails.User(username, user.getUserPassword(),
						Collections.emptyList());
			} else {
				throw new BusinessException(MessageConstants.INVALID_USER);
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
