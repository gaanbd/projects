package com.tvse.uam.util;

import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.dao.DataAccessException;

import com.tvse.common.exception.BusinessException;
import com.tvse.uam.domain.AbstractAudit;

/**
 * @author techmango (https://www.techmango.net/)
 *
 */
public class CommonUtil {
	
	private static final String MESSAGE_LABEL=" ( Error Message: ";
	private static final String LINE_NUMBER_LABEL=" on line Number ";


	private CommonUtil() {
	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(keyExtractor.apply(t));
	}

	public static AbstractAudit populateAuditFields(boolean isCreate, UUID userId, AbstractAudit abstractAudit) {
		abstractAudit.setUpdatedBy(userId);
		if (isCreate) {
			abstractAudit.setCreatedBy(userId);
		}
		return abstractAudit;
	}

	public static String errorFormat(BusinessException be) {
		if (be.getStackTrace() != null && be.getStackTrace().length > 0)
			return "BusinessException in Method " + be.getStackTrace()[0].getClassName() + ": " + be.getStackTrace()[0].getMethodName() + LINE_NUMBER_LABEL
					+ be.getStackTrace()[0].getLineNumber() + " with error code of  " + be.getErrorCode()
					+ MESSAGE_LABEL+ be.getMessage() + ")";
		else
			return "BusinessException" +MESSAGE_LABEL+ be.getMessage() + ")";
	}

	public static String errorFormat(DataAccessException de) {
		if (de.getStackTrace() != null && de.getStackTrace().length > 0)
			return "DataAccessException in Method " + de.getStackTrace()[0].getClassName() + ": " + de.getStackTrace()[0].getMethodName() + LINE_NUMBER_LABEL
					+ de.getStackTrace()[0].getLineNumber() + MESSAGE_LABEL+ de.getMessage() + ")";
		else
			return "DataAccessException" +MESSAGE_LABEL+ de.getMessage() + ")";
	}

	public static String errorFormat(Exception e) {
		if (e.getStackTrace() != null && e.getStackTrace().length > 0)
			return "Exception in Method of " + e.getStackTrace()[0].getClassName() + ": " + e.getStackTrace()[0].getMethodName() + LINE_NUMBER_LABEL
					+ e.getStackTrace()[0].getLineNumber() + MESSAGE_LABEL+ e.getMessage() + ")";
		else
			return "Exception" +MESSAGE_LABEL+ e.getMessage() + ")";
	}

	public static String populateMessage(String message) {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("/i18n/messages");
		MessageSourceAccessor accessor = new MessageSourceAccessor(messageSource, Locale.ENGLISH);
		return accessor.getMessage(message);
	}
}
