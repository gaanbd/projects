package com.tvse.uam.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;

import com.tvse.common.dto.ResponseDTO;
import com.tvse.common.exception.ApplicationException;
import com.tvse.common.exception.BusinessException;
import com.tvse.common.exception.DataRepositoryException;
import com.tvse.uam.constants.ApplicationConstants;
import com.tvse.uam.domain.Application;
import com.tvse.uam.domain.Brand;
import com.tvse.uam.domain.User;
import com.tvse.uam.dto.ApplicationDTO;
import com.tvse.uam.dto.BrandDTO;
import com.tvse.uam.dto.RoleDTO;
import com.tvse.uam.dto.UserRoleDTO;
import com.tvse.uam.mail.MailManagerUtil;
import com.tvse.uam.repository.ApplicationRepository;
import com.tvse.uam.repository.BranchRepository;
import com.tvse.uam.repository.BrandRepository;
import com.tvse.uam.repository.RoleRepository;
import com.tvse.uam.repository.UserRepository;
import com.tvse.uam.repository.UserRoleRepository;
import com.tvse.uam.repository.ZoneRepository;

public class TestUserRole {

	@Mock
	private BrandRepository brandRepository;

	@Mock
	private RoleRepository roleRepository;

	@Mock
	private ApplicationRepository applicationRepository;

	@Mock
	private UserRoleRepository userRoleRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private MailManagerUtil mailManagerutil;
	
	@Mock
	private ZoneRepository zoneRepository;
	
	@Mock
	private BranchRepository branchRepository; 

	@InjectMocks
	private UserRoleServiceImpl userRoleServiceImpl;

	private MessageSourceAccessor accessor;

	@Before
	public void setUp() throws Exception {
		brandRepository = Mockito.mock(BrandRepository.class);
		applicationRepository = Mockito.mock(ApplicationRepository.class);
		roleRepository = Mockito.mock(RoleRepository.class);
		userRoleRepository = Mockito.mock(UserRoleRepository.class);
		userRepository = Mockito.mock(UserRepository.class);
		mailManagerutil = Mockito.mock(MailManagerUtil.class);
		userRoleServiceImpl = new UserRoleServiceImpl(userRepository, roleRepository, userRoleRepository,
				brandRepository, applicationRepository, mailManagerutil, zoneRepository, branchRepository);
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("/i18n/messages");
		accessor = new MessageSourceAccessor(messageSource, Locale.ENGLISH);
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testBrandList() {

		Brand brandOne = new Brand();
		brandOne.setId(UUID.randomUUID());
		brandOne.setBrandName("DELL");
		brandOne.setIsActive(true);

		List<Brand> brandList = new ArrayList<>();
		brandList.add(brandOne);

		when(brandRepository.findAllBrandDetails()).thenReturn(brandList);
		ResponseDTO<List<BrandDTO>> result = userRoleServiceImpl.getAllBrands();
		assertEquals(ApplicationConstants.SUCCESS_CODE_200, result.getCode());

	}

	@Test
	public void testBrandListNull() {
		try {
			when(brandRepository.findAllBrandDetails()).thenReturn(null);
			userRoleServiceImpl.getAllBrands();

		} catch (BusinessException b) {
			assertEquals("error.brand.not.available", b.getMessage());
		}
	}

	@Test
	public void testBrandListEmpty() {
		try {
			List<Brand> brand = new ArrayList<>();
			when(brandRepository.findAllBrandDetails()).thenReturn(brand);
			userRoleServiceImpl.getAllBrands();

		} catch (BusinessException b) {

			assertEquals("error.brand.not.available", b.getMessage());
		}
	}

	@Test
	public void testApplicationExceptionBrandList() {
		try {
			when(brandRepository.findAllBrandDetails()).thenThrow(NullPointerException.class);
			ResponseDTO<List<BrandDTO>> responseUserDTO = userRoleServiceImpl.getAllBrands();
		} catch (ApplicationException be) {
			assertTrue(true);
		}
	}

	@Test
	public void testDAOExceptionBrandList() {
		try {
			when(brandRepository.findAllBrandDetails()).thenThrow(InvalidDataAccessResourceUsageException.class);
			ResponseDTO<List<BrandDTO>> responseUserDTO = userRoleServiceImpl.getAllBrands();
		} catch (DataRepositoryException be) {
			assertTrue(true);
		}
	}

	@Test
	public void testApplicationList() {

		Application applicationOne = new Application();
		applicationOne.setId(UUID.randomUUID());
		applicationOne.setApplicationName("B2B");
		applicationOne.setApplicationDescription("Business to business");

		List<Application> applicationList = new ArrayList<>();
		applicationList.add(applicationOne);

		when(applicationRepository.findAll()).thenReturn(applicationList);

		ResponseDTO<List<ApplicationDTO>> result = userRoleServiceImpl.getAllApplications();

		assertEquals(ApplicationConstants.SUCCESS_CODE_200, result.getCode());

	}

	@Test
	public void testApplicationListNull() {
		try {
			when(applicationRepository.findAll()).thenReturn(null);
			userRoleServiceImpl.getAllApplications();

		} catch (BusinessException b) {
			assertEquals("error.application.not.available", b.getMessage());
		}
	}

	@Test
	public void testApplicationListEmpty() {
		try {

			List<Application> app = new ArrayList<>();
			when(applicationRepository.findAll()).thenReturn(app);
			userRoleServiceImpl.getAllApplications();
		} catch (BusinessException b) {
			assertEquals("error.application.not.available", b.getMessage());
		}
	}

	@Test
	public void testRoleByApplicationList() {
		UUID uuid1 = UUID.randomUUID();
		UUID uuid2 = UUID.randomUUID();
		List<UUID> uuidList = new ArrayList<UUID>();
		uuidList.add(uuid1);
		uuidList.add(uuid2);

		Application applicationOne = new Application();
		applicationOne.setId(UUID.randomUUID());
		applicationOne.setApplicationName("B2B");

		Application applicationTwo = new Application();
		applicationTwo.setId(UUID.randomUUID());
		applicationTwo.setApplicationName("B2C");

		List<Application> applicationList = new ArrayList<>();
		applicationList.add(applicationOne);
		applicationList.add(applicationTwo);

		RoleDTO roleDTO1 = new RoleDTO();
		roleDTO1.setId(UUID.randomUUID());
		roleDTO1.setRoleName("Super Admin");
		RoleDTO roleDTO2 = new RoleDTO();
		roleDTO2.setId(UUID.randomUUID());
		roleDTO2.setRoleName("Admin");
		List<RoleDTO> roleList = new ArrayList<>();
		roleList.add(roleDTO1);
		roleList.add(roleDTO2);

		when(roleRepository.findRoleByApplicationId(uuidList)).thenReturn(roleList);

//		ResponseDTO<List<RoleDTO>> result = userRoleServiceImpl.getRolesByApplicationId(uuidList);
//		assertEquals("Success", result.getMessage());

	}

	@Test
	public void testRoleByApplicationListWithSingleApplicationId() {
		UUID uuid1 = UUID.randomUUID();
		UUID uuid2 = UUID.randomUUID();
		List<UUID> uuidList = new ArrayList<UUID>();
		uuidList.add(uuid1);
		Application applicationOne = new Application();
		applicationOne.setId(UUID.randomUUID());
		applicationOne.setApplicationName("B2B");

		Application applicationTwo = new Application();
		applicationTwo.setId(UUID.randomUUID());
		applicationTwo.setApplicationName("B2C");

		List<Application> applicationList = new ArrayList<>();
		applicationList.add(applicationOne);
		applicationList.add(applicationTwo);

		RoleDTO roleDTO1 = new RoleDTO();
		roleDTO1.setId(UUID.randomUUID());
		roleDTO1.setRoleName("Super Admin");
		RoleDTO roleDTO2 = new RoleDTO();
		roleDTO2.setId(UUID.randomUUID());
		roleDTO2.setRoleName("Admin");
		List<RoleDTO> roleList = new ArrayList<>();
		roleList.add(roleDTO1);
		roleList.add(roleDTO2);
		when(roleRepository.findRoleByApplicationId(uuidList.get(0))).thenReturn(roleList);

//		ResponseDTO<List<RoleDTO>> result = userRoleServiceImpl.getRolesByApplicationId(uuidList);
//		assertEquals("Success", result.getMessage());

	}

	@Test
	public void testRoleByApplicationList_RoleListEmpty() {
		try {
			UUID uuid1 = UUID.randomUUID();
			UUID uuid2 = UUID.randomUUID();
			List<UUID> uuidList = new ArrayList<UUID>();
			uuidList.add(uuid1);
			uuidList.add(uuid2);
			List<RoleDTO> roleList = new ArrayList<>();
			when(roleRepository.findRoleByApplicationId(uuidList)).thenReturn(roleList);
			userRoleServiceImpl.getRolesByApplicationId(uuidList);
		} catch (BusinessException b) {
			assertEquals("error.no.role.available", b.getMessage());
		}
	}

	@Test
	public void testRoleByApplicationList_RoleListNull() {
		try {
			UUID uuid1 = UUID.randomUUID();
			UUID uuid2 = UUID.randomUUID();
			List<UUID> uuidList = new ArrayList<UUID>();
			uuidList.add(uuid1);
			uuidList.add(uuid2);
			when(roleRepository.findRoleByApplicationId(uuidList)).thenReturn(null);
			userRoleServiceImpl.getRolesByApplicationId(uuidList);
		} catch (BusinessException b) {
			assertEquals("error.no.role.available", b.getMessage());
		}
	}

	@Test
	public void testRoleByApplicationList_ApplicationIdEmpty() {
		try {
			UUID uuid1 = UUID.randomUUID();
			UUID uuid2 = UUID.randomUUID();
			List<UUID> uuidList = new ArrayList<UUID>();
			List<RoleDTO> roleList = new ArrayList<>();
			when(roleRepository.findRoleByApplicationId(uuidList)).thenReturn(roleList);
			userRoleServiceImpl.getRolesByApplicationId(uuidList);
		} catch (BusinessException b) {
			assertEquals("error.no.role.available", b.getMessage());
		}
	}

	@Test
	public void testApplicationExceptionGetRolesByApplicationId() {
		try {
			UUID uuid1 = UUID.randomUUID();
			UUID uuid2 = UUID.randomUUID();
			List<UUID> uuidList = new ArrayList<UUID>();
			uuidList.add(uuid1);
			Application applicationOne = new Application();
			applicationOne.setId(UUID.randomUUID());
			applicationOne.setApplicationName("B2B");

			Application applicationTwo = new Application();
			applicationTwo.setId(UUID.randomUUID());
			applicationTwo.setApplicationName("B2C");

			List<Application> applicationList = new ArrayList<>();
			applicationList.add(applicationOne);
			applicationList.add(applicationTwo);

			RoleDTO roleDTO1 = new RoleDTO();
			roleDTO1.setId(UUID.randomUUID());
			roleDTO1.setRoleName("Super Admin");
			RoleDTO roleDTO2 = new RoleDTO();
			roleDTO2.setId(UUID.randomUUID());
			roleDTO2.setRoleName("Admin");
			List<RoleDTO> roleList = new ArrayList<>();
			roleList.add(roleDTO1);
			roleList.add(roleDTO2);
			when(roleRepository.findRoleByApplicationId(uuidList.get(0))).thenThrow(NullPointerException.class);
			ResponseDTO<List<RoleDTO>> responseUserDTO = userRoleServiceImpl.getRolesByApplicationId(uuidList);
		} catch (ApplicationException be) {
			assertTrue(true);
		}
	}

	@Test
	public void testDAOExceptionGetRolesByApplicationId() {
		try {
			UUID uuid1 = UUID.randomUUID();
			UUID uuid2 = UUID.randomUUID();
			List<UUID> uuidList = new ArrayList<UUID>();
			uuidList.add(uuid1);
			Application applicationOne = new Application();
			applicationOne.setId(UUID.randomUUID());
			applicationOne.setApplicationName("B2B");

			Application applicationTwo = new Application();
			applicationTwo.setId(UUID.randomUUID());
			applicationTwo.setApplicationName("B2C");

			List<Application> applicationList = new ArrayList<>();
			applicationList.add(applicationOne);
			applicationList.add(applicationTwo);

			RoleDTO roleDTO1 = new RoleDTO();
			roleDTO1.setId(UUID.randomUUID());
			roleDTO1.setRoleName("Super Admin");
			RoleDTO roleDTO2 = new RoleDTO();
			roleDTO2.setId(UUID.randomUUID());
			roleDTO2.setRoleName("Admin");
			List<RoleDTO> roleList = new ArrayList<>();
			roleList.add(roleDTO1);
			roleList.add(roleDTO2);
			when(roleRepository.findRoleByApplicationId(uuidList.get(0)))
					.thenThrow(InvalidDataAccessResourceUsageException.class);
			ResponseDTO<List<RoleDTO>> responseUserDTO = userRoleServiceImpl.getRolesByApplicationId(uuidList);
		} catch (DataRepositoryException be) {
			assertTrue(true);
		}
	}

	@Test
	public void testApplicationExceptionGetAllApplications() {
		try {
			when(applicationRepository.findAll()).thenThrow(NullPointerException.class);
			ResponseDTO<List<ApplicationDTO>> responseUserDTO = userRoleServiceImpl.getAllApplications();
		} catch (ApplicationException be) {
			assertTrue(true);
		}
	}

	@Test
	public void testDAOExceptionGetAllApplications() {
		try {
			when(applicationRepository.findAll()).thenThrow(InvalidDataAccessResourceUsageException.class);
			ResponseDTO<List<ApplicationDTO>> responseUserDTO = userRoleServiceImpl.getAllApplications();
		} catch (DataRepositoryException be) {
			assertTrue(true);
		}
	}

	@Test
	public void testUserRolecreateBusinessExceptionOne() {
		try {
			List<UserRoleDTO> userRoleDTOList = new ArrayList<>();
			UserRoleDTO userRoleDTO = new UserRoleDTO();
			userRoleDTOList.add(userRoleDTO);
			userRoleServiceImpl.createUserRole(userRoleDTOList);
		} catch (BusinessException e) {
			assertEquals("error.role.norole.present", e.getMessage());
		}
	}

	@Test
	public void testUserRolecreateBusinessExceptionTwo() {
		try {
			UserRoleDTO userRoleDTO = new UserRoleDTO();
			userRoleDTO.setRoleId(UUID.randomUUID());

			Application application = new Application();
			application.setId(UUID.randomUUID());
			application.setApplicationName("B2B");

			List<Application> applicationList = new ArrayList<>();
			applicationList.add(application);
			userRoleDTO.setApplicationList(applicationList);
			userRoleDTO.setBrandList(null);

			List<UserRoleDTO> userRoleDTOList = new ArrayList<>();
			userRoleDTOList.add(userRoleDTO);
			Optional<Application> app = Optional.of(application);

			when(applicationRepository.findById(application.getId())).thenReturn(app);
			when(userRoleRepository.checkUserRoleDetails(userRoleDTO.getUserId(), userRoleDTO.getRoleId(),
					userRoleDTO.getBrandId())).thenReturn((long) 1);
			userRoleServiceImpl.createUserRole(userRoleDTOList);
		} catch (BusinessException e) {
			assertEquals("error.role.brand.not.found", e.getMessage());
		}
	}

	@Test
	public void createUserRoleSuccessTest() {
		try {
			UserRoleDTO userRoleDTO = new UserRoleDTO();
			userRoleDTO.setRoleId(UUID.randomUUID());
			userRoleDTO.setUserId(UUID.randomUUID());

			Application application = new Application();
			application.setId(UUID.randomUUID());
			application.setApplicationName("B2B");

			List<Application> applicationList = new ArrayList<>();
			applicationList.add(application);
			userRoleDTO.setApplicationList(applicationList);

			Brand brandOne = new Brand();
			brandOne.setId(UUID.randomUUID());
			brandOne.setBrandName("DELL");
			brandOne.setIsActive(true);
			List<Brand> brandList = new ArrayList<>();
			brandList.add(brandOne);
			userRoleDTO.setBrandList(brandList);

			List<UserRoleDTO> userRoleDTOList = new ArrayList<>();

			Optional<Application> app = Optional.of(application);

			User user = new User();
			user.setId(UUID.randomUUID());

			when(applicationRepository.findById(application.getId())).thenReturn(app);
			when(userRoleRepository.checkUserRoleDetails(userRoleDTO.getUserId(), userRoleDTO.getRoleId(),
					userRoleDTO.getBrandId())).thenReturn((long) 1);
			when(userRepository.findbyId(userRoleDTO.getUserId())).thenReturn(user);
			doNothing().when(mailManagerutil).sendUserCreationNotificationMail("", "", "", "");

			ResponseDTO<UserRoleDTO> response = userRoleServiceImpl.createUserRole(userRoleDTOList);
		} catch (Exception e) {
			assertEquals("error.role.norole.present", e.getMessage());
		}
	}

	@Test
	public void createUserRoleSuccessTest1() {
		UserRoleDTO userRoleDTO = new UserRoleDTO();
		userRoleDTO.setRoleId(UUID.randomUUID());
		userRoleDTO.setUserId(UUID.randomUUID());

		Application application = new Application();
		application.setId(UUID.randomUUID());
		application.setApplicationName("B2B");

		List<Application> applicationList = new ArrayList<>();
		applicationList.add(application);
		userRoleDTO.setApplicationList(applicationList);

		Brand brandOne = new Brand();
		brandOne.setId(UUID.randomUUID());
		brandOne.setBrandName("DELL");
		brandOne.setIsActive(true);
		List<Brand> brandList = new ArrayList<>();
		brandList.add(brandOne);
		userRoleDTO.setBrandList(brandList);

		List<UserRoleDTO> userRoleDTOList = new ArrayList<>();
		userRoleDTOList.add(userRoleDTO);
		Optional<Application> app = Optional.of(application);

		User user = new User();
		user.setId(UUID.randomUUID());

		when(applicationRepository.findById(application.getId())).thenReturn(app);
		when(userRoleRepository.checkUserRoleDetails(userRoleDTO.getUserId(), userRoleDTO.getRoleId(),
				userRoleDTO.getBrandId())).thenReturn((long) 1);
		when(userRepository.findbyId(userRoleDTO.getUserId())).thenReturn(user);
		doNothing().when(mailManagerutil).sendUserCreationNotificationMail("", "", "", "");

		ResponseDTO<UserRoleDTO> response = userRoleServiceImpl.createUserRole(userRoleDTOList);

		assertEquals(ApplicationConstants.SUCCESS_CODE_201, response.getCode());
	}

	@Test
	public void testApplicationExceptionCreateUserRole() {
		try {
			UserRoleDTO userRoleDTO = new UserRoleDTO();
			userRoleDTO.setRoleId(UUID.randomUUID());
			userRoleDTO.setUserId(UUID.randomUUID());

			Application application = new Application();
			application.setId(UUID.randomUUID());
			application.setApplicationName("B2B");

			List<Application> applicationList = new ArrayList<>();
			// applicationList.add(application);
			userRoleDTO.setApplicationList(applicationList);

			Brand brandOne = new Brand();
			brandOne.setId(UUID.randomUUID());
			brandOne.setBrandName("DELL");
			brandOne.setIsActive(true);
			List<Brand> brandList = new ArrayList<>();
			brandList.add(brandOne);
			userRoleDTO.setBrandList(brandList);
			// userRoleDTO.setOldRole("e4d95f44-51e3-11e9-8647-d663bd873d93");

			List<UserRoleDTO> userRoleDTOList = new ArrayList<>();
			userRoleDTOList.add(userRoleDTO);
			Optional<Application> app = Optional.of(application);

			User user = new User();
			user.setId(UUID.randomUUID());
			when(userRepository.findbyId(userRoleDTO.getUserId())).thenThrow(NullPointerException.class);
			ResponseDTO<UserRoleDTO> response = userRoleServiceImpl.createUserRole(userRoleDTOList);
		} catch (ApplicationException be) {
			assertTrue(true);
		}
	}

	@Test
	public void testDAOExceptionCreateUserRole() {
		try {
			UserRoleDTO userRoleDTO = new UserRoleDTO();
			userRoleDTO.setRoleId(UUID.randomUUID());
			userRoleDTO.setUserId(UUID.randomUUID());

			Application application = new Application();
			application.setId(UUID.randomUUID());
			application.setApplicationName("B2B");

			List<Application> applicationList = new ArrayList<>();
			// applicationList.add(application);
			userRoleDTO.setApplicationList(applicationList);

			Brand brandOne = new Brand();
			brandOne.setId(UUID.randomUUID());
			brandOne.setBrandName("DELL");
			brandOne.setIsActive(true);
			List<Brand> brandList = new ArrayList<>();
			brandList.add(brandOne);
			userRoleDTO.setBrandList(brandList);
			// userRoleDTO.setOldRole("e4d95f44-51e3-11e9-8647-d663bd873d93");

			List<UserRoleDTO> userRoleDTOList = new ArrayList<>();
			userRoleDTOList.add(userRoleDTO);
			Optional<Application> app = Optional.of(application);

			User user = new User();
			user.setId(UUID.randomUUID());
			when(userRepository.findbyId(userRoleDTO.getUserId()))
					.thenThrow(InvalidDataAccessResourceUsageException.class);
			ResponseDTO<UserRoleDTO> response = userRoleServiceImpl.createUserRole(userRoleDTOList);
		} catch (DataRepositoryException be) {
			assertTrue(true);
		}
	}

	@Test
	public void testDeleteUserRole() {
		doNothing().when(userRoleRepository).deleteByUserId(UUID.randomUUID());
		ResponseDTO<HttpStatus> response = userRoleServiceImpl.deleteUserRole(UUID.randomUUID());
		assertEquals(ApplicationConstants.SUCCESS_CODE_200, response.getCode());
	}

	@Test
	public void testGetUserRoleByUserNull() {
		try {
			List<UserRoleDTO> userRoleDTOList = new ArrayList<>();
			when(userRoleRepository.getUserRoleByUserId(UUID.randomUUID())).thenReturn(userRoleDTOList);
			userRoleServiceImpl.getUserRoleByUserId(UUID.randomUUID());
		} catch (BusinessException e) {
			assertEquals("error.user.role.not.found", e.getMessage());
		}
	}

	@Test
	public void testGetUserRoleByUserIdWithCorrestInput() {
		try {
			ApplicationDTO applicationDTO = new ApplicationDTO();
			applicationDTO.setId(UUID.randomUUID());
			applicationDTO.setApplicationName("AppName");
			applicationDTO.setApplicationDescription("AppDesc");
			
			List<ApplicationDTO> applicationDTOList = new ArrayList<>();
			applicationDTOList.add(applicationDTO);
			
			UserRoleDTO userRoleDTO = new UserRoleDTO();
			userRoleDTO.setRoleId(UUID.randomUUID());
			userRoleDTO.setUserId(UUID.randomUUID());
			userRoleDTO.setAction("Action"); 
			
			List<UserRoleDTO> userRoleDTOList = new ArrayList<>();
			userRoleDTOList.add(userRoleDTO);
			
			List<Object> obj =new ArrayList<>();
			
			when(userRoleRepository.getUserRoleByUserId(UUID.randomUUID())).thenReturn(userRoleDTOList);
			when(applicationRepository.getApplicationsByRoleId(UUID.randomUUID())).thenReturn(applicationDTOList);
			when(userRoleRepository.getUserRole(UUID.randomUUID().toString(), UUID.randomUUID().toString())).thenReturn(obj);
			userRoleServiceImpl.getUserRoleByUserId(UUID.randomUUID());
		} catch (BusinessException e) {
			assertEquals("error.user.role.not.found", e.getMessage());
		}
	}
}
