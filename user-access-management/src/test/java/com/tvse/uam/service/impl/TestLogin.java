package com.tvse.uam.service.impl;

public class TestLogin {
	/*
	private User mockUser;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import com.tvse.uam.domain.User;
import com.tvse.uam.exception.BusinessException;
import com.tvse.uam.repository.UserRepository;

public class TestLogin {
	
	/*private User mockUser;
>>>>>>> uam_sprint_one
	private LoginQuotes mockLoginQuotes;
	
	private String username="tvse";
	private String password;

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private LoginQuotesRepository loginQuotesRepository;
	
	@InjectMocks
	private UserServiceImpl userServiceImpl;
	
	@Mock
	private MessageSourceAccessor accessor;
	
	
	@Before
	public void setUp() throws Exception {
		mockUser = Mockito.mock(User.class);
		mockLoginQuotes=Mockito.mock(LoginQuotes.class);
		this.username="tvse";
		this.password="tvse";
		Mockito.when(mockUser.getUserName()).thenReturn(username);
		Mockito.when(mockUser.getUserPassword()).thenReturn(password);
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("/i18n/messages");
		accessor = new MessageSourceAccessor(messageSource, Locale.ENGLISH);
		userRepository = Mockito.mock(UserRepository.class);
		loginQuotesRepository = Mockito.mock(LoginQuotesRepository.class);
		userServiceImpl = new UserServiceImpl(loginQuotesRepository, userRepository, null, null, null, null);
	}
	
	@Test
	public void testloadByUserName() {
		when(userRepository.findByUserName(username)).thenReturn(mockUser);
		userServiceImpl.loadUserByUsername(username);
	}
	
	@Test
	public void testLoadByUserNameException() {
		this.username = null;
		try {
			when(userRepository.findByUserName(username)).thenReturn(null);
			userServiceImpl.loadUserByUsername(username);
		}catch(BusinessException be) {
			assertEquals("Unable to Login.", be.getMessage());
		}
	}
	
	
	@Test
	public void testLoadByUserNameApplicationException() {
		this.username = null;
		try {
			when(userRepository.findByUserName(username)).thenThrow(Exception.class);
			userServiceImpl.loadUserByUsername(username);
		}catch(Exception be) {
			assertEquals("Unexpected Error!", accessor.getMessage(be.getLocalizedMessage()));
		}
	}
	
	@Test
	public void testGetLoginQuotes() {
		userServiceImpl.getLoginQuotes();
	}
	
	@Test
	public void testGetLoginQuotesEmpty() {
		when(loginQuotesRepository.getById(anyInt())).thenReturn(null);
		userServiceImpl.getLoginQuotes();
<<<<<<< HEAD
	}
	*/

}
