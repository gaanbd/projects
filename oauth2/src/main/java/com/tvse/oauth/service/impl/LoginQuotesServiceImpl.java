package com.tvse.oauth.service.impl;

import java.util.Random;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.tvse.common.dto.ResponseDTO;
import com.tvse.common.exception.ApplicationException;
import com.tvse.common.exception.DataRepositoryException;
import com.tvse.oauth.constants.ApplicationConstants;
import com.tvse.oauth.constants.MessageConstants;
import com.tvse.oauth.domain.LoginQuotes;
import com.tvse.oauth.repository.LoginQuotesRepository;
import com.tvse.oauth.service.LoginQuotesService;
import com.tvse.oauth.util.CommonUtil;

/**
 * @author techmango
 *
 */
@Service
@Transactional
public class LoginQuotesServiceImpl implements LoginQuotesService {

	public static final int MAX_RANDOM_NO = 10;
	public static final int MIN_RANDOM_NO = 1;

	private static final Logger LOGGER = LogManager.getLogger(LoginQuotesServiceImpl.class.getName());

	private LoginQuotesRepository loginQuotesRepository;

	@Inject
	public LoginQuotesServiceImpl(@NotNull LoginQuotesRepository loginQuotesRepository) {
		this.loginQuotesRepository = loginQuotesRepository;
	}

	/* (non-Javadoc)
	 * @see com.tvse.oauth.service.LoginQuotesService#getLoginQuotes()
	 */
	@Override
	public ResponseDTO<LoginQuotes> getLoginQuotes() {
		LOGGER.info("LoginQuotesServiceImpl getLoginQuotes() Intiated :");
		LoginQuotes quotes = null;
		try {
			int num = new Random().nextInt((MAX_RANDOM_NO - MIN_RANDOM_NO) + 1) + MIN_RANDOM_NO;
			quotes = loginQuotesRepository.getById(num);
			if (ObjectUtils.isEmpty(quotes)) {
				quotes = new LoginQuotes();
				quotes.setId(5);
				quotes.setQuotes(MessageConstants.STATIC_QUOTES_HTML_CONTENT);
				quotes.setAuthor(MessageConstants.STATIC_QUOTES_AUTHOR);
			} else {
				LOGGER.info("LoginQuotesServiceImpl getLoginQuotes() Terminated :");
				return new ResponseDTO<>(false, ApplicationConstants.SUCCESS_CODE_200,
						CommonUtil.populateMessage(MessageConstants.SUCCESS_MESSAGE_QUOTES), quotes);
			}
		}  catch (DataAccessException dae) {
			LOGGER.error(CommonUtil.errorFormat(dae));
			throw new DataRepositoryException();
		} catch (Exception e) {
			LOGGER.error(CommonUtil.errorFormat(e));
			throw new ApplicationException();
		}
		return new ResponseDTO<>(false, ApplicationConstants.SUCCESS_CODE_200,
				CommonUtil.populateMessage(MessageConstants.SUCCESS_MESSAGE_QUOTES), quotes);
	}

}
