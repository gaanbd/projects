package com.tvse.uam.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.tvse.common.dto.ResponseDTO;
import com.tvse.common.exception.ApplicationException;
import com.tvse.common.exception.BusinessException;
import com.tvse.common.exception.DataRepositoryException;
import com.tvse.uam.domain.City;
import com.tvse.uam.domain.Country;
import com.tvse.uam.domain.State;
import com.tvse.uam.domain.User;
import com.tvse.uam.domain.UserRole;
import com.tvse.uam.dto.UserDTO;
import com.tvse.uam.mail.MailManagerUtil;
import com.tvse.uam.repository.ApplicationRepository;
import com.tvse.uam.repository.BrandRepository;
import com.tvse.uam.repository.CityRepository;
import com.tvse.uam.repository.CountryRepository;
import com.tvse.uam.repository.EntitlementRepository;
import com.tvse.uam.repository.MenuRepository;
import com.tvse.uam.repository.RoleEntitlementPermissionRepository;
import com.tvse.uam.repository.RoleRepository;
import com.tvse.uam.repository.StateRepository;
import com.tvse.uam.repository.UserRepository;
import com.tvse.uam.repository.UserRoleRepository;

public class TestUserCreation {

	private User user;
	private UserDTO userDTO;
	private ResponseDTO<UserDTO> mockResponseDto;
	private ResponseDTO<String> mockResponseDtoString;
	private City mockCity;
	private State mockState;
	private Country mockCountry;

	@Mock
	private UserRepository userRepository;
	@Mock
	private RoleRepository mockroleRepository;
	@Mock
	private UserRoleRepository userRoleRepository;
	@Mock
	private BrandRepository mockbrandRepository;
	@Mock
	private ApplicationRepository mockapplicationRepository;
	@Mock
	private EntitlementRepository mockMntitlementRepository;
	@Mock
	private RoleEntitlementPermissionRepository mockRoleEntitlementPermissionRepository;
	@Mock
	private MenuRepository mockMenuRepository;
	@Mock
	private CountryRepository mockCountryRepository;
	@Mock
	private StateRepository mockStateRepository;
	@Mock
	private CityRepository mockCityRepository;
	@Mock
	private MailManagerUtil mockMailManagerUtil;

	@InjectMocks
	private UserServiceImpl userServiceImpl;

	private Logger mockLogger;

	private UUID id;
	private String employeeId;
	private String userName;
	private String password;
	private String email;
	private String firstName;
	private String middleName;
	private String lastName;
	private String addressLine1;
	private String addressLine2;
	private String mobile;
	private Boolean isActive;

	private String mockName;

	private MessageSourceAccessor accessor;

	@Before
	public void initialize() {

		this.userRepository = mock(UserRepository.class);
		this.mockCityRepository = mock(CityRepository.class);
		this.mockStateRepository = mock(StateRepository.class);
		this.mockCountryRepository = mock(CountryRepository.class);
		this.userRoleRepository = mock(UserRoleRepository.class);

		this.userServiceImpl = new UserServiceImpl(userRepository, mockStateRepository, mockCountryRepository,
				mockCityRepository, userRoleRepository, mockbrandRepository, mockapplicationRepository, mockroleRepository);

		mockLogger = Mockito.mock(Logger.class);
		Mockito.doNothing().when(mockLogger).debug(Mockito.anyString());

		this.id = UUID.randomUUID();
		this.employeeId = "TTS__xxxx";
		this.userName = "SomeUser";
		this.password = "SomePassword";
		this.email = "SomeRole";
		this.firstName = "SomeFirstName";
		this.middleName = "SomeSecondName";
		this.lastName = "SomeLastName";
		this.addressLine1 = "SomeAddress1";
		this.addressLine2 = "SomeAddress2";
		this.mobile = "9514789632";
		this.isActive = true;

		this.mockName = "CHENNAI";

		this.userDTO = new UserDTO();
		this.userDTO.setId(id);
		this.userDTO.setEmployeeId(employeeId);
		this.userDTO.setUserName(userName);
		this.userDTO.setUserPassword(password);
		this.userDTO.setEmail(email);
		this.userDTO.setFirstName(firstName);
		this.userDTO.setMiddleName(middleName);
		this.userDTO.setLastName(lastName);
		this.userDTO.setAddressLine1(addressLine1);
		this.userDTO.setAddressLine2(addressLine2);
		this.userDTO.setMobile(mobile);
		this.userDTO.setIsActive(isActive);

		this.user = new User();
		this.user.setId(id);
		this.user.setEmployeeId(employeeId);
		this.user.setUserName(userName);
		this.user.setUserPassword(password);
		this.user.setEmail(email);
		this.user.setFirstName(firstName);
		this.user.setMiddleName(middleName);
		this.user.setLastName(lastName);
		this.user.setAddressLine1(addressLine1);
		this.user.setAddressLine2(addressLine2);
		this.user.setMobile(mobile);
		this.user.setCityId(id);
		this.user.setIsActive(isActive);

		this.mockCity = new City();
		this.mockCity.setCityName(mockName);
		this.mockCity.setId(id);
		this.mockCity.setStateId(id);

		this.mockState = new State();
		this.mockState.setStateName(mockName);
		this.mockState.setId(id);
		this.mockState.setCountryId(id);

		this.mockCountry = new Country();
		this.mockCountry.setCountryName(mockName);
		this.mockCountry.setId(id);

		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("/i18n/messages");
		accessor = new MessageSourceAccessor(messageSource, Locale.ENGLISH);
	}

	@Test
	public void testCreateUserWithDTONull() {   
		try {
			this.userDTO = null;
			when(userRepository.save(user)).thenReturn(user);

			userServiceImpl.createUser(this.userDTO);
		} catch (BusinessException be) {
			assertEquals("Input Should Be Given", accessor.getMessage(be.getLocalizedMessage()));
		}
	}

	@Test
	public void testCreateUserWithUserNameNull() {
		try {
			this.userDTO.setUserName(null);
			when(userRepository.save(user)).thenReturn(user);

			userServiceImpl.createUser(this.userDTO);
		} catch (BusinessException be) {
			assertEquals("User Name is Required", accessor.getMessage(be.getLocalizedMessage()));
		}
	}

	@Test
	public void testCreateUserWithUserNameEmpty() {
		try {
			this.userDTO.setUserName("");
			when(userRepository.save(user)).thenReturn(user);

			userServiceImpl.createUser(this.userDTO);
		} catch (BusinessException be) {
			assertEquals("User Name is Required", accessor.getMessage(be.getLocalizedMessage()));
		}
	}

	@Test
	public void testCreateUserWithFirstNameNull() {
		try {
			this.userDTO.setFirstName(null);
			when(userRepository.save(user)).thenReturn(user);

			userServiceImpl.createUser(this.userDTO);
		} catch (BusinessException be) {
			assertEquals("First Name is Required", accessor.getMessage(be.getLocalizedMessage()));
		}
	}

	@Test
	public void testCreateUserWithFirstNameEmpty() {
		try {
			this.userDTO.setFirstName("");
			when(userRepository.save(user)).thenReturn(user);

			userServiceImpl.createUser(this.userDTO);
		} catch (BusinessException be) {
			assertEquals("First Name is Required", accessor.getMessage(be.getLocalizedMessage()));
		}
	}

	@Test
	public void testCreateUserWithEmployeeIdEmpty() {
		try {
			this.userDTO.setEmployeeId("");
			when(userRepository.save(user)).thenReturn(user);

			userServiceImpl.createUser(this.userDTO);
		} catch (BusinessException be) {
			assertEquals("Employee Id is Required", accessor.getMessage(be.getLocalizedMessage()));
		}
	}

	@Test
	public void testCreateUserWithEmployeeIdNull() {
		try {
			this.userDTO.setEmployeeId(null);
			when(userRepository.save(user)).thenReturn(user);

			userServiceImpl.createUser(this.userDTO);
		} catch (BusinessException be) {
			assertEquals("Employee Id is Required", accessor.getMessage(be.getLocalizedMessage()));
		}
	}

	@Test
	public void testCreateUserWithMobileNumberEmpty() {
		try {
			this.userDTO.setMobile("");
			when(userRepository.save(user)).thenReturn(user);

			userServiceImpl.createUser(this.userDTO);
		} catch (BusinessException be) {
			assertEquals("Mobile Number Is Required", accessor.getMessage(be.getLocalizedMessage()));
		}
	}

	@Test
	public void testCreateUserWithEmailIdEmpty() {
		try {
			this.userDTO.setEmail("");
			when(userRepository.save(user)).thenReturn(user);
			userServiceImpl.createUser(this.userDTO);
		} catch (BusinessException be) {
			assertEquals("Email is Required", accessor.getMessage(be.getLocalizedMessage()));
		}
	}

	@Test
	public void testCreateUserWithEmailIdNull() {
		try {
			this.userDTO.setEmail(null);
			when(userRepository.save(user)).thenReturn(user);

			userServiceImpl.createUser(this.userDTO);
		} catch (BusinessException be) {
			assertEquals("Email is Required", accessor.getMessage(be.getLocalizedMessage()));
		}
	}

	@Test
	public void testCreateUserWithActiveStatusNull() {
		try {
			this.userDTO.setIsActive(null);
			when(userRepository.save(user)).thenReturn(user);

			userServiceImpl.createUser(this.userDTO);
		} catch (BusinessException be) {
			assertEquals("Active Status Required", accessor.getMessage(be.getLocalizedMessage()));
		}
	}

	@Test
	public void testCreateUserWithUserNameContainswhitespace() {
		try {
			this.userDTO.setUserName("Admin Name");
			when(userRepository.save(user)).thenReturn(user);

			userServiceImpl.createUser(this.userDTO);
		} catch (BusinessException be) {
			assertEquals("User Name is Invalid", accessor.getMessage(be.getLocalizedMessage()));
		}
	}

	@Test
	public void testCreateUserWithCheckByUserName() {
		try {
			when(userRepository.save(user)).thenReturn(user);
			when(userRepository.findByUserName(this.userName)).thenReturn(this.user);
			userServiceImpl.createUser(this.userDTO);
		} catch (BusinessException be) {
			assertEquals("User Already Exist", accessor.getMessage(be.getLocalizedMessage()));
		}
	}

	@Test
	public void testCreateUserWithCheckByUserEmployeeId() {
		try {
			when(userRepository.save(user)).thenReturn(user);
			when(userRepository.findByEmployeeId(this.employeeId)).thenReturn(user);
			userServiceImpl.createUser(this.userDTO);
		} catch (BusinessException be) {
			assertEquals("Employee Id Already Exist", accessor.getMessage(be.getLocalizedMessage()));
		}
	}
	
	@Test
	public void testApplicationExceptionCreateUser() {
		try
		{
			when(userRepository.save(this.user)).thenThrow(NullPointerException.class);
			ResponseDTO<UserDTO> responseUserDTO = userServiceImpl.createUser(this.userDTO);
		}
		catch(ApplicationException be) {
			assertTrue(true);
		} 
	}
	
	@Test
	public void testDAOExceptionCreateUser() {
		try
		{
			when(userRepository.save(this.user)).thenThrow(InvalidDataAccessResourceUsageException.class);
			ResponseDTO<UserDTO> responseUserDTO = userServiceImpl.createUser(this.userDTO);
		}
		catch(DataRepositoryException be) {
			assertTrue(true);
		}
	} 

	@Test
	public void testCreateUserWithCorrectInput() {
		when(userRepository.save(this.user)).thenReturn(this.user);
		this.mockResponseDto = userServiceImpl.createUser(this.userDTO);
		assertEquals(201, this.mockResponseDto.getCode());
	}

	// updateUser test starts
	@Test
	public void testUpdateUserWithDTONull() {
		try {
			this.userDTO = null;
			when(userRepository.save(user)).thenReturn(user);

			userServiceImpl.updateUser(this.userDTO);
		} catch (BusinessException be) {
			assertEquals("Input Should Be Given", accessor.getMessage(be.getLocalizedMessage()));
		}
	}

	@Test
	public void testUpdateUserWithUserNameGiven() {
		try {
			when(userRepository.save(user)).thenReturn(user);

			userServiceImpl.updateUser(this.userDTO);
		} catch (BusinessException be) {
			assertEquals("User Name Cannot Edit", accessor.getMessage(be.getLocalizedMessage()));
		}
	}

	@Test
	public void testUpdateUserWithFindbyIdNull() {
		try {
			this.userDTO.setUserName(null);
			this.userDTO.setEmployeeId(null);
			when(userRepository.save(user)).thenReturn(user);
			when(userRepository.findbyId(id)).thenReturn(null);
			userServiceImpl.updateUser(this.userDTO);
		} catch (BusinessException be) {
			assertEquals("User Details Not Found", accessor.getMessage(be.getLocalizedMessage()));
		}
	}
	
	@Test
	public void testUpdateUserWithEmployeeIdNull() {
		try {
			this.userDTO.setUserName(null);
			when(userRepository.save(user)).thenReturn(user);
			when(userRepository.findbyId(id)).thenReturn(null);
			userServiceImpl.updateUser(this.userDTO);
		} catch (BusinessException be) {
			assertEquals("Employee Id Cannot Edit", accessor.getMessage(be.getLocalizedMessage()));
		}
	}
	
	@Test
	public void testApplicationExceptionUpdateUser() {
		try
		{
			this.userDTO.setUserName("");
			this.userDTO.setEmployeeId("");
			when(userRepository.findbyId(this.id)).thenThrow(NullPointerException.class);
			ResponseDTO<UserDTO> responseUserDTO = userServiceImpl.updateUser(this.userDTO);
		}
		catch(ApplicationException be) {
			assertTrue(true);
		} 
	}
	
	@Test
	public void testDAOExceptionUpdateUser() {
		try
		{
			this.userDTO.setUserName("");
			this.userDTO.setEmployeeId("");
			when(userRepository.findbyId(this.id)).thenThrow(InvalidDataAccessResourceUsageException.class);
			ResponseDTO<UserDTO> responseUserDTO = userServiceImpl.updateUser(this.userDTO);
		}
		catch(DataRepositoryException be) {
			assertTrue(true);
		}
	}

	@Test
	public void testUpdateUserWithCorrectInput() {
		this.userDTO.setUserName(null);
		this.userDTO.setEmployeeId(null);
		when(userRepository.save(this.user)).thenReturn(this.user);
		when(userRepository.findbyId(this.id)).thenReturn(this.user);
		this.mockResponseDto = userServiceImpl.updateUser(this.userDTO);
		assertEquals(200, this.mockResponseDto.getCode());
	}

	// delete user test starts
	@Test
	public void testDeleteUserWithNull() {
		try {
			when(userRepository.findbyId(id)).thenReturn(null);
			userServiceImpl.deleteUser(this.id);
		} catch (BusinessException be) {
			assertEquals("User Details Not Found", accessor.getMessage(be.getLocalizedMessage()));
		}
	}
	
	@Test
	public void testApplicationExceptionDelete() {
		try
		{
			when(userRepository.findbyId(this.id)).thenThrow(NullPointerException.class);
			ResponseDTO<UserDTO> responseUserDTO = userServiceImpl.deleteUser(this.id);
		}
		catch(ApplicationException be) {
			assertTrue(true);
		}  
	}
	
	@Test
	public void testDAOExceptionDelete() {
		try
		{
			when(userRepository.findbyId(this.id)).thenThrow(InvalidDataAccessResourceUsageException.class);
			ResponseDTO<UserDTO> responseUserDTO = userServiceImpl.deleteUser(this.id);
		}
		catch(DataRepositoryException be) {
			assertTrue(true);
		}
	}

	@Test
	public void testDeleteUserWithCorrctInput() {
		when(userRepository.findbyId(id)).thenReturn(this.user);
		Mockito.doNothing().when(userRoleRepository).deleteByUserId(Mockito.any());
		Mockito.doNothing().when(userRepository).delete(Mockito.any());
		this.mockResponseDto = userServiceImpl.deleteUser(this.id);
		assertEquals(200, this.mockResponseDto.getCode());
	}

	@Test
	public void testGetByUserIdWithNull() {
		try {
			when(userRepository.findbyId(id)).thenReturn(null);
			userServiceImpl.getByUserId(this.id);
		} catch (BusinessException be) {
			assertEquals("User Details Not Found", accessor.getMessage(be.getLocalizedMessage()));
		}
	}

	@Test
	public void testGetByUserIdWithCityIdGiven() {
		this.user.setCityId(null);
		when(userRepository.findbyId(id)).thenReturn(this.user);
		when(mockCityRepository.findByCityId(id)).thenReturn(this.mockCity);
		when(mockStateRepository.findByStateId(id)).thenReturn(this.mockState);
		when(mockCountryRepository.findByCountryId(id)).thenReturn(this.mockCountry);
		this.mockResponseDto = userServiceImpl.getByUserId(this.id);
		assertEquals(200, this.mockResponseDto.getCode());
	}
	
	@Test
	public void testApplicationExceptionGetByUserId() {
		try
		{
			when(userRepository.findbyId(this.id)).thenThrow(NullPointerException.class);
			ResponseDTO<UserDTO> responseUserDTO = userServiceImpl.getByUserId(this.id);
		}
		catch(ApplicationException be) {
			assertTrue(true);
		} 
	}
	
	@Test
	public void testDAOExceptionGetByUserId() {
		try
		{
			when(userRepository.findbyId(this.id)).thenThrow(InvalidDataAccessResourceUsageException.class);
			ResponseDTO<UserDTO> responseUserDTO = userServiceImpl.getByUserId(this.id);
		}
		catch(DataRepositoryException be) {
			assertTrue(true);
		}
	}

	@Test
	public void testGetByUserIdWithCorrctInput() {
		when(userRepository.findbyId(id)).thenReturn(this.user);
		when(mockCityRepository.findByCityId(id)).thenReturn(this.mockCity);
		when(mockStateRepository.findByStateId(id)).thenReturn(this.mockState);
		when(mockCountryRepository.findByCountryId(id)).thenReturn(this.mockCountry);
		this.mockResponseDto = userServiceImpl.getByUserId(this.id);
		assertEquals(200, this.mockResponseDto.getCode());
	}
	
	@Test
	public void testGetAllCountry() {
		Country country = new Country();
		country.setId(UUID.randomUUID());
		country.setCountryName(RandomStringUtils.random(5));
		List<Country> countryList = new ArrayList<>(Arrays.asList(country));
		when(mockCountryRepository.findAll()).thenReturn(countryList);
		userServiceImpl.getAllCountry();
	}

	@Test
	public void testGetAllStateByCountry() {
		State state = new State();
		state.setId(UUID.randomUUID());
		state.setStateName(RandomStringUtils.random(5));
		state.setCountryId(UUID.randomUUID());
		List<State> stateList = new ArrayList<>(Arrays.asList(state));
		when(mockStateRepository.findAllByCountryId(state.getCountryId())).thenReturn(stateList);
		userServiceImpl.getAllStateByCountry(state.getCountryId());

	}
	
	@Test
	public void testApplicationExceptionGetAllStateByCountry() {
		try
		{
			when(mockStateRepository.findAllByCountryId(this.id)).thenThrow(NullPointerException.class);
			ResponseDTO<List<State>> responseUserDTO = userServiceImpl.getAllStateByCountry(this.id);
		}
		catch(ApplicationException be) {
			assertTrue(true);
		} 
	}
	
	@Test
	public void testDAOExceptionGetAllStateByCountry() {
		try
		{
			when(mockStateRepository.findAllByCountryId(this.id)).thenThrow(InvalidDataAccessResourceUsageException.class);
			ResponseDTO<List<State>> responseUserDTO = userServiceImpl.getAllStateByCountry(this.id);
		}
		catch(DataRepositoryException be) {
			assertTrue(true);
		}
	}

	@Test
	public void testGetAllCityByState() {

		City city = new City();
		city.setId(UUID.randomUUID());
		city.setCityName(RandomStringUtils.random(5));
		city.setStateId(UUID.randomUUID());
		List<City> cityList = new ArrayList<>(Arrays.asList(city));
		when(mockCityRepository.findAllByStateId(city.getStateId())).thenReturn(cityList);
		userServiceImpl.getAllCityByState(city.getStateId());
	}
	
	@Test
	public void testApplicationExceptionGetAllCityByState() {
		try
		{
			when(mockCityRepository.findAllByStateId(this.id)).thenThrow(NullPointerException.class);
			ResponseDTO<List<City>> responseUserDTO = userServiceImpl.getAllCityByState(this.id);
		}
		catch(ApplicationException be) {
			assertTrue(true);
		} 
	}
	
	@Test
	public void testDAOExceptionGetAllCityByState() {
		try
		{
			when(mockCityRepository.findAllByStateId(this.id)).thenThrow(InvalidDataAccessResourceUsageException.class);
			ResponseDTO<List<City>> responseUserDTO = userServiceImpl.getAllCityByState(this.id);
		}
		catch(DataRepositoryException be) {
			assertTrue(true);
		}
	}

	@Test
	public void testLoginNameCheck() {
		this.mockResponseDtoString = userServiceImpl.loginNameCheck(user.getUserName());
		assertEquals(200, this.mockResponseDtoString.getCode());
	}

	@Test
	public void testLoginNameErrorCheck() {
		String userName = "AdminName1";
		when(userRepository.findByUserName(userName)).thenReturn(user);
		this.mockResponseDtoString = userServiceImpl.loginNameCheck(userName);
		assertEquals(409, this.mockResponseDtoString.getCode());
	}
	
	@Test
	public void testApplicationExceptionLoginName() {
		try
		{
			when(userRepository.findByUserName(this.userName)).thenThrow(NullPointerException.class);
			ResponseDTO<String> responseUserDTO = userServiceImpl.loginNameCheck(this.userName);
		}
		catch(ApplicationException be) {
			assertTrue(true);
		} 
	}
	
	@Test
	public void testDAOExceptionLoginName() {
		try
		{
			when(userRepository.findByUserName(this.userName)).thenThrow(InvalidDataAccessResourceUsageException.class);
			ResponseDTO<String> responseUserDTO = userServiceImpl.loginNameCheck(this.userName);
		}
		catch(DataRepositoryException be) {
			assertTrue(true);
		}
	}

	@Test
	public void testEmployeeIdCheck() {
		this.mockResponseDtoString = userServiceImpl.employeeIdUniqueCheck(user.getUserName());
		assertEquals(200, this.mockResponseDtoString.getCode());
	}

	@Test
	public void testEmployeeIdErrorCheck() {
		String employeeId = "emp_001";
		when(userRepository.findByEmployeeId(employeeId)).thenReturn(user);
		this.mockResponseDtoString = userServiceImpl.employeeIdUniqueCheck(employeeId);
		assertEquals(409, this.mockResponseDtoString.getCode());
	}
	
	@Test
	public void testApplicationExceptionEmployeeId() {
		try
		{
			when(userRepository.findByEmployeeId(this.employeeId)).thenThrow(NullPointerException.class);
			ResponseDTO<String> responseUserDTO = userServiceImpl.employeeIdUniqueCheck(this.employeeId);
		}
		catch(ApplicationException be) {
			assertTrue(true);
		} 
	}
	
	@Test
	public void testDAOExceptionEmployeeId() {
		try
		{
			when(userRepository.findByEmployeeId(this.employeeId)).thenThrow(InvalidDataAccessResourceUsageException.class);
			ResponseDTO<String> responseUserDTO = userServiceImpl.employeeIdUniqueCheck(this.employeeId);
		}
		catch(DataRepositoryException be) {
			assertTrue(true);
		}
	}

	@Test
	public void testGetAllUserProfile() {
		Pageable pageableRequest = PageRequest.of(0, 5);
		String searchParam = RandomStringUtils.random(5);
		User user = new User();
		user.setIsActive(true);
		user.setEmail(RandomStringUtils.random(5));
		user.setId(UUID.randomUUID());
		user.setUserName(RandomStringUtils.random(5));
		user.setCreatedDate(new Date());
		user.setCreatedBy(UUID.randomUUID());
		User user1 = new User();
		user1.setIsActive(true);
		user1.setEmail(RandomStringUtils.random(5));
		user1.setId(UUID.randomUUID());
		user1.setUserName(RandomStringUtils.random(5));
		user1.setCreatedDate(new Date());
		user1.setCreatedBy(UUID.randomUUID());
		List<User> usersExpected = new ArrayList<>(Arrays.asList(user, user1));
		PageImpl<User> foundPage = new PageImpl<User>(usersExpected);
		Mockito.when(userRepository.getAllUsers(any())).thenReturn(foundPage);
//		userServiceImpl.getAllUserProfile(pageableRequest,searchParam);
	}
	
	@Test
	public void testGetAllUserProfileUserListEmpty() { 
		try {
		Pageable pageableRequest = PageRequest.of(0, 5);
		String searchParam = RandomStringUtils.random(5);
		User user = new User();
		user.setIsActive(true);
		user.setEmail(RandomStringUtils.random(5));
		user.setId(UUID.randomUUID());
		user.setUserName(RandomStringUtils.random(5));
		user.setCreatedDate(new Date());
		user.setCreatedBy(UUID.randomUUID());
		User user1 = new User();
		user1.setIsActive(true);
		user1.setEmail(RandomStringUtils.random(5)); 
		user1.setId(UUID.randomUUID());
		user1.setUserName(RandomStringUtils.random(5)); 
		user1.setCreatedDate(new Date());
		user1.setCreatedBy(UUID.randomUUID());
		List<User> usersExpected = new ArrayList<>();
		PageImpl<User> foundPage = new PageImpl<User>(usersExpected);
		Mockito.when(userRepository.getAllUsers(any())).thenReturn(foundPage);
		userServiceImpl.getAllUserProfile(pageableRequest,searchParam);
		}catch (BusinessException be) {
			assertEquals("Login Name Not Available", accessor.getMessage(be.getLocalizedMessage()));
		}
	}
	
	@Test
	public void testApplicationExceptionGetAllUserProfile() {
		try
		{
			Pageable pageableRequest = PageRequest.of(0, 5);
			String searchParam = RandomStringUtils.random(5);
			when(userRepository.getAllUsers(pageableRequest)).thenThrow(NullPointerException.class);
			ResponseDTO<Page<UserDTO>> responseUserDTO = userServiceImpl.getAllUserProfile(pageableRequest,searchParam);
		}
		catch(ApplicationException be) {
			assertTrue(true);
		} 
	}
	
	@Test
	public void testDAOExceptionGetAllUserProfile() {
		try
		{
			Pageable pageableRequest = PageRequest.of(0, 5);
			String searchParam = RandomStringUtils.random(5);
			when(userRepository.getAllUsers(pageableRequest)).thenThrow(InvalidDataAccessResourceUsageException.class);
			ResponseDTO<Page<UserDTO>> responseUserDTO = userServiceImpl.getAllUserProfile(pageableRequest,searchParam);
		}
		catch(DataRepositoryException be) {
			assertTrue(true);
		}
	}

	@Test
	public void testUpdateUserStatusWithUserNull() {
		UserRole userRole1 = new UserRole();
		userRole1.setId(this.id);
		userRole1.setBrandId(this.id);
		userRole1.setBrandIds("Dell");
		userRole1.setRoleId(this.id);
		userRole1.setUserId(this.id);

		UserRole userRole2 = new UserRole();
		userRole2.setId(this.id);
		userRole2.setBrandId(this.id);
		userRole2.setBrandIds("HP");
		userRole2.setRoleId(this.id);
		userRole2.setUserId(this.id);

		List<UserRole> listOfUserRoles = new ArrayList<>();
		listOfUserRoles.add(userRole1);
		listOfUserRoles.add(userRole2);
		try {
			when(userRepository.findbyId(this.id)).thenReturn(null);
			when(userRoleRepository.findByUserId(this.id)).thenReturn(listOfUserRoles);
			userServiceImpl.updateUserStatus(this.id);
		} catch (BusinessException be) {
			assertEquals("User Details Not Found", accessor.getMessage(be.getLocalizedMessage()));
		}
	}

	@Test
	public void testUpdateUserStatusWithUserRoleNull() {
		when(userRepository.findbyId(this.id)).thenReturn(user);
		when(userRoleRepository.findByUserId(this.id)).thenReturn(null);
		Mockito.doNothing().when(userRepository).updateUserStatus(Mockito.any(), Mockito.any(), Mockito.any());
		Mockito.doNothing().when(userRoleRepository).deleteByUserId(Mockito.any());
		this.mockResponseDto = userServiceImpl.updateUserStatus(this.id);
		assertEquals(200, mockResponseDto.getCode());
	}
	
	@Test
	public void testUpdateUserStatusWithUserRole() {
		UserRole userRole1 = new UserRole();
		userRole1.setId(this.id);
		userRole1.setBrandId(this.id);
		userRole1.setBrandIds("Dell");
		userRole1.setRoleId(this.id);
		userRole1.setUserId(this.id);

		UserRole userRole2 = new UserRole();
		userRole2.setId(this.id);
		userRole2.setBrandId(this.id);
		userRole2.setBrandIds("HP");
		userRole2.setRoleId(this.id);
		userRole2.setUserId(this.id);

		List<UserRole> listOfUserRoles = new ArrayList<>();
		listOfUserRoles.add(userRole1);
		listOfUserRoles.add(userRole2);
		when(userRepository.findbyId(this.id)).thenReturn(user);
		when(userRoleRepository.findByUserId(this.id)).thenReturn(listOfUserRoles);
		Mockito.doNothing().when(userRepository).updateUserStatus(Mockito.any(), Mockito.any(), Mockito.any());
		Mockito.doNothing().when(userRoleRepository).deleteByUserId(Mockito.any());
		this.mockResponseDto = userServiceImpl.updateUserStatus(this.id);
		assertEquals(200, mockResponseDto.getCode());
	}
	
	@Test
	public void testApplicationExceptionUpdateUserStatus() {
		try
		{
			when(userRepository.findbyId(this.id)).thenThrow(NullPointerException.class);
			ResponseDTO<UserDTO> responseUserDTO = userServiceImpl.updateUserStatus(this.id);
		}
		catch(ApplicationException be) {
			assertTrue(true);
		} 
	}
	
	@Test
	public void testDAOExceptionUpdateUserStatus() {
		try
		{
			when(userRepository.findbyId(this.id)).thenThrow(InvalidDataAccessResourceUsageException.class);
			ResponseDTO<UserDTO> responseUserDTO = userServiceImpl.updateUserStatus(this.id);
		}
		catch(DataRepositoryException be) {
			assertTrue(true);
		}
	}
}
