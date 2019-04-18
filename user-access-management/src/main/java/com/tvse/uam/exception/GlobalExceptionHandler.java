package com.tvse.uam.exception;

import java.util.List;
import java.util.Locale;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tvse.common.dto.ResponseDTO;
import com.tvse.common.exception.ApplicationException;
import com.tvse.common.exception.BusinessException;
import com.tvse.common.exception.DataRepositoryException;
import com.tvse.uam.constants.ApplicationConstants;
import com.tvse.uam.util.CommonUtil;

/**
 * @author techmango
 *
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

	private static final String MESSAGE_I18N = "/i18n/messages";

	@ExceptionHandler(BusinessException.class)
	private ResponseEntity<ResponseDTO<Object>> processException(BusinessException exc) {
		int code = exc.getErrorCode();
		ResponseDTO<Object> response = new ResponseDTO<>(true, (code != 0 ? code : HttpStatus.NO_CONTENT.value()),
				populateError(exc), null);
		code = verifyHttpCode(code);
		return new ResponseEntity<>(response, HttpStatus.valueOf(code));
	}

	@ExceptionHandler(DataRepositoryException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	private ResponseDTO<Object> processException(DataRepositoryException exc) {
		return new ResponseDTO<>(true, HttpStatus.INTERNAL_SERVER_ERROR.value(),
				CommonUtil.populateMessage(ApplicationConstants.UNEXPECTED_DATA_ACCESS_ERROR), null);
	}

	@ExceptionHandler(ApplicationException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	private ResponseDTO<Object> processException(ApplicationException exc) {
		return new ResponseDTO<>(true, HttpStatus.INTERNAL_SERVER_ERROR.value(),
				CommonUtil.populateMessage(ApplicationConstants.UNEXPECTED_ERROR), null);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	private ResponseDTO<Object> processException(Exception exc) {
		return new ResponseDTO<>(true, HttpStatus.INTERNAL_SERVER_ERROR.value(),
				CommonUtil.populateMessage(ApplicationConstants.UNEXPECTED_ERROR), null);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	private ResponseDTO<Object> processException(MethodArgumentNotValidException exc) {
		return new ResponseDTO<>(true, HttpStatus.NOT_FOUND.value(), populateError(exc), null);
	}

	private String populateError(Exception ex) {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename(MESSAGE_I18N);
		MessageSourceAccessor accessor = new MessageSourceAccessor(messageSource, Locale.ENGLISH);
		String message = "";
		if (ex instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException aexc = (MethodArgumentNotValidException) ex;
			List<FieldError> error = aexc.getBindingResult().getFieldErrors();
			if (CollectionUtils.isNotEmpty(error)) {
				message = getDefaultMessage(accessor, message, error);
			}
			message = message.substring(0, message.length() - 1);
		} else {
			try {
				message = accessor.getMessage(ex.getLocalizedMessage());
			} catch (NoSuchMessageException e) {
				message = ex.getLocalizedMessage();
			}
		}
		return message;
	}

	private String getDefaultMessage(MessageSourceAccessor accessor, String message, List<FieldError> error) {
		for (int i = 0; i < error.size(); i++) {
			if (error.get(i) != null) {
				String defaultMessage=error.get(i).getDefaultMessage();
				if (StringUtils.isNotBlank(defaultMessage)) {
					message = message.concat(accessor.getMessage(defaultMessage) + ", ");
				}	
			}
		}
		return message;
	}

	/**
	 * Verify HttpStatus's Code (for setting HttpStats in header; To set 400 in
	 * header if it is invalid HttpStatus)
	 * 
	 * @param code
	 * @return
	 */
	private int verifyHttpCode(int code) {
		try {
			if (!HttpStatus.valueOf(code).is4xxClientError() && !HttpStatus.valueOf(code).is5xxServerError())
				return HttpStatus.OK.value();
		} catch (Exception e) {
			return HttpStatus.OK.value();
		}
		return code;
	}

}