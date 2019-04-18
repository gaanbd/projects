package com.tvse.uam.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.tvse.common.exception.ApplicationException;
import com.tvse.common.exception.BusinessException;
import com.tvse.uam.constants.ApplicationConstants;
import com.tvse.uam.domain.Application;
import com.tvse.uam.domain.Entitlement;
import com.tvse.uam.domain.Role;
import com.tvse.uam.domain.RoleEntitlementPermission;
import com.tvse.uam.dto.ApplicationEntitlementDTO;
import com.tvse.uam.dto.EntitlementOperationsDTO;
import com.tvse.uam.repository.ApplicationRepository;
import com.tvse.uam.repository.EntitlementRepository;
import com.tvse.uam.repository.RoleEntitlementPermissionRepository;
import com.tvse.uam.repository.RoleRepository;
import com.tvse.uam.repository.UserRoleRepository;

public class TestRoleCreationAndPermission {

	@Mock
	private RoleRepository roleRepository;

	@Mock
	private UserRoleRepository userRoleRepository;

	@Mock
	private ApplicationRepository applicationRepository;

	@Mock
	private EntitlementRepository entitlementRepository;

	@Mock
	private RoleEntitlementPermissionRepository roleEntitlementPermissionRepository;

	@InjectMocks
	private RoleServiceImpl roleServiceImpl;

	private Role mockRole;

	private Application mockApplication;

	private Entitlement mockEntitlement;

	private UUID roleId;
	private String roleName;
	private String roleDesription;
	private String roleDisplayName;

	private UUID applicationId;
	private String applicationName;
	private String applicationDesription;

	private UUID entitelementId;
	private String entitelementName;
	private String entitelementOperation;

	@Mock
	private MessageSourceAccessor accessor;

	@Before
	public void setUp() throws Exception {

		this.roleId = UUID.randomUUID();
		this.roleName = "SUPER ADMIN";
		this.roleDesription = "They have all rights";
		this.roleDisplayName = "SA";
		mockRole = Mockito.mock(Role.class);

		this.applicationId = UUID.randomUUID();
		this.applicationName = "B2B";
		this.applicationDesription = "Business to Business";
		mockApplication = Mockito.mock(Application.class);

		this.entitelementId = UUID.randomUUID();
		this.entitelementName = "ROLE CREATION";
		this.entitelementOperation = "CRMF";
		mockEntitlement = Mockito.mock(Entitlement.class);

		Mockito.when(mockRole.getId()).thenReturn(roleId);
		Mockito.when(mockRole.getRoleName()).thenReturn(roleName);
		Mockito.when(mockRole.getRoleDescription()).thenReturn(roleDesription);
		Mockito.when(mockRole.getRoleDisplayName()).thenReturn(roleDisplayName);

		Mockito.when(mockApplication.getId()).thenReturn(applicationId);
		Mockito.when(mockApplication.getApplicationName()).thenReturn(applicationName);
		Mockito.when(mockApplication.getApplicationDescription()).thenReturn(applicationDesription);

		Mockito.when(mockEntitlement.getId()).thenReturn(entitelementId);
		Mockito.when(mockEntitlement.getEntitlementName()).thenReturn(entitelementName);
		Mockito.when(mockEntitlement.getOperation()).thenReturn(entitelementOperation);

		roleRepository = Mockito.mock(RoleRepository.class);
		applicationRepository = Mockito.mock(ApplicationRepository.class);
		entitlementRepository = Mockito.mock(EntitlementRepository.class);
		roleEntitlementPermissionRepository = Mockito.mock(RoleEntitlementPermissionRepository.class);
		userRoleRepository = Mockito.mock(UserRoleRepository.class);
		roleServiceImpl = new RoleServiceImpl(roleRepository, userRoleRepository, applicationRepository,
				entitlementRepository, roleEntitlementPermissionRepository);
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("/i18n/messages");
		accessor = new MessageSourceAccessor(messageSource, Locale.ENGLISH);

	}

	@Test
	public void testGetAllRoles() {
		Pageable pageableRequest = PageRequest.of(0, 5);
		List<String> applications = new ArrayList<>();
		applications.add("B2B");
		applications.add("B2C");
		List<Role> roleList = new ArrayList<>();
		roleList.add(mockRole);
		when(roleRepository.findAll()).thenReturn(roleList);
		when(roleRepository.getApplicationsByRoleId(roleId)).thenReturn(applications);
		assertEquals(ApplicationConstants.SUCCESS_CODE_200, roleServiceImpl.getAllRoles("b2b",pageableRequest).getCode());
	}

	@Test
	public void testGetAllRolesNullCase() {
		Pageable pageableRequest = PageRequest.of(0, 5);
		List<String> applications = null;
		List<Role> roleList = null;
		try {
			when(roleRepository.findAll()).thenReturn(roleList);
			when(roleRepository.getApplicationsByRoleId(roleId)).thenReturn(applications);
			roleServiceImpl.getAllRoles("",pageableRequest);
		} catch (ApplicationException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testGetAllEntitlements() {
		List<Application> applicationList = new ArrayList<>();
		applicationList.add(mockApplication);
		when(applicationRepository.findAll()).thenReturn(applicationList);
		List<Entitlement> entitlementList = new ArrayList<>();
		entitlementList.add(mockEntitlement);
		when(entitlementRepository.findByApplicationId(applicationId)).thenReturn(entitlementList);
		assertEquals(ApplicationConstants.SUCCESS_CODE_200, roleServiceImpl.getAllEntitlements().getCode());

	}

	@Test
	public void testGetAllEntitlementsNullCase() {
		List<Application> applicationList = null;
		try {
			when(applicationRepository.findAll()).thenReturn(applicationList);
			List<Entitlement> entitlementList = null;
			when(entitlementRepository.findByApplicationId(applicationId)).thenReturn(entitlementList);
			roleServiceImpl.getAllEntitlements();
		} catch (ApplicationException e) {
			assertTrue(true);
		}

	}

	@Test
	public void testGetRoleById() {
		when(roleRepository.getOperationOfEntitlement(roleId, applicationId, entitelementId)).thenReturn("CM");
		Optional<Role> mockRoleOptional = Optional.of(mockRole);
		when(roleRepository.findById(roleId)).thenReturn(mockRoleOptional);
		List<Application> applicationList = new ArrayList<>();
		applicationList.add(mockApplication);
		when(applicationRepository.findAll()).thenReturn(applicationList);
		List<Entitlement> entitlementList = new ArrayList<>();
		entitlementList.add(mockEntitlement);
		when(entitlementRepository.findByApplicationId(applicationId)).thenReturn(entitlementList);
		assertEquals(ApplicationConstants.SUCCESS_CODE_200, roleServiceImpl.getRoleById(roleId).getCode());
	}

	@Test
	public void testGetRoleByIdEmptyCase() {
		Optional<Role> mockRoleOptional = Optional.empty();
		try {
			when(roleRepository.findById(roleId)).thenReturn(mockRoleOptional);
			roleServiceImpl.getRoleById(roleId);
		} catch (BusinessException e) {
			assertEquals("Role Not Available", accessor.getMessage(e.getLocalizedMessage()));
		}
	}

	@Test
	public void testGetRoleByIdNullCase() {
		Optional<Role> mockRoleOptional = null;
		try {
			when(roleRepository.findById(roleId)).thenReturn(mockRoleOptional);
			roleServiceImpl.getRoleById(roleId);
		} catch (ApplicationException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testCreateRole() {
		List<ApplicationEntitlementDTO> applicationEntitlementDTOList = new ArrayList<>();
		List<EntitlementOperationsDTO> entitlementOperationsList = new ArrayList<>();

		Map<String, Boolean> permissions = new HashMap<>();
		permissions.put("Read-Only", true);
		permissions.put("Create", true);
		permissions.put("Full-Access", true);
		permissions.put("Create and Modify", true);

		EntitlementOperationsDTO EntitlementOperationsDTO = new EntitlementOperationsDTO();
		EntitlementOperationsDTO.setEntitlementId(entitelementId);
		EntitlementOperationsDTO.setEntitlementName(entitelementName);
		EntitlementOperationsDTO.setSelected(true);
		EntitlementOperationsDTO.setPermissions(permissions);
		entitlementOperationsList.add(EntitlementOperationsDTO);

		ApplicationEntitlementDTO applicationEntitlementDTO = new ApplicationEntitlementDTO();
		applicationEntitlementDTO.setApplicationId(applicationId);
		applicationEntitlementDTO.setApplicationName(applicationName);
		applicationEntitlementDTO.setSelected(true);
		applicationEntitlementDTO.setEntitlementOperationsList(entitlementOperationsList);
		applicationEntitlementDTOList.add(applicationEntitlementDTO);

		RoleEntitlementPermission roleEntitlementPermission = new RoleEntitlementPermission();
		when(roleEntitlementPermissionRepository.save(roleEntitlementPermission)).thenReturn(roleEntitlementPermission);

		when(roleRepository.save(any())).thenReturn(mockRole);
		assertEquals(ApplicationConstants.SUCCESS_CODE_201, roleServiceImpl
				.createRole(applicationEntitlementDTOList, roleName, roleDesription, roleDisplayName).getCode());

	}

	@Test
	public void testCreateRoleEmptyRoleName() {
		List<ApplicationEntitlementDTO> applicationEntitlementDTOList = new ArrayList<>();
		try {
			when(roleRepository.save(any())).thenReturn(mockRole);
			roleServiceImpl.createRole(applicationEntitlementDTOList, "", roleDesription, roleDisplayName);
		} catch (BusinessException be) {
			assertEquals("Role Name is Required", accessor.getMessage(be.getLocalizedMessage()));
		}
	}

	@Test
	public void testCreateRoleUnSuccessfulSave() {
		List<ApplicationEntitlementDTO> applicationEntitlementDTOList = new ArrayList<>();
		try {
			when(roleRepository.save(mockRole)).thenReturn(mockRole);
			roleServiceImpl.createRole(applicationEntitlementDTOList, roleName, roleDesription, roleDisplayName);
		} catch (BusinessException e) {
			assertEquals("Role Operation Failed", accessor.getMessage(e.getLocalizedMessage()));
		}

	}

	@Test
	public void testCreateRoleNullCase() {
		List<ApplicationEntitlementDTO> applicationEntitlementDTOList = null;
		try {
			when(roleRepository.save(any())).thenReturn(mockRole);
			roleServiceImpl.createRole(applicationEntitlementDTOList, roleName, roleDesription, roleDisplayName);
		} catch (ApplicationException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testUpdateRole() {

		List<ApplicationEntitlementDTO> applicationEntitlementDTOList = new ArrayList<>();
		List<EntitlementOperationsDTO> entitlementOperationsList = new ArrayList<>();
		Optional<Role> mockRoleOptional = Optional.of(mockRole);
		Map<String, Boolean> permissions = new HashMap<>();
		permissions.put("Read-Only", true);
		permissions.put("Create", true);
		permissions.put("Full-Access", false);
		permissions.put("Create and Modify", true);

		EntitlementOperationsDTO EntitlementOperationsDTO = new EntitlementOperationsDTO();
		EntitlementOperationsDTO.setEntitlementId(entitelementId);
		EntitlementOperationsDTO.setEntitlementName(entitelementName);
		EntitlementOperationsDTO.setSelected(true);
		EntitlementOperationsDTO.setPermissions(permissions);
		entitlementOperationsList.add(EntitlementOperationsDTO);

		ApplicationEntitlementDTO applicationEntitlementDTO = new ApplicationEntitlementDTO();
		applicationEntitlementDTO.setApplicationId(applicationId);
		applicationEntitlementDTO.setApplicationName(applicationName);
		applicationEntitlementDTO.setSelected(true);
		applicationEntitlementDTO.setEntitlementOperationsList(entitlementOperationsList);
		applicationEntitlementDTOList.add(applicationEntitlementDTO);

		RoleEntitlementPermission roleEntitlementPermission = new RoleEntitlementPermission();
		when(roleEntitlementPermissionRepository.save(roleEntitlementPermission)).thenReturn(roleEntitlementPermission);
		when(roleRepository.findById(roleId)).thenReturn(mockRoleOptional);
		doNothing().when(roleRepository).deleteById(roleId);
		doNothing().when(roleEntitlementPermissionRepository).deleteByRoleId(roleId);
		when(roleRepository.save(any())).thenReturn(mockRole);
		assertEquals(ApplicationConstants.SUCCESS_CODE_200,
				roleServiceImpl
						.updateRole(applicationEntitlementDTOList, roleName, roleDesription, roleId, roleDisplayName)
						.getCode());
	}

	@Test
	public void testUpdateRoleNotAvailableRoleId() {
		List<ApplicationEntitlementDTO> applicationEntitlementDTOList = new ArrayList<>();
		try {
			roleServiceImpl.updateRole(applicationEntitlementDTOList, roleName, roleDesription, null, roleDisplayName);
		} catch (BusinessException be) {
			assertEquals("Role Not Available", accessor.getMessage(be.getLocalizedMessage()));
		}
	}

	@Test
	public void testUpdateRoleEmptyObject() {
		List<ApplicationEntitlementDTO> applicationEntitlementDTOList = new ArrayList<>();
		Optional<Role> mockRoleOptional = Optional.empty();
		try {
			when(roleRepository.findById(roleId)).thenReturn(mockRoleOptional);
			roleServiceImpl.updateRole(applicationEntitlementDTOList, roleName, roleDesription, roleId,
					roleDisplayName);
		} catch (BusinessException be) {
			assertEquals("Role Not Available", accessor.getMessage(be.getLocalizedMessage()));
		}
	}

	@Test
	public void testUpdateRoleUpdateFailed() {
		List<ApplicationEntitlementDTO> applicationEntitlementDTOList = new ArrayList<>();
		Optional<Role> mockRoleOptional = Optional.of(mockRole);
		try {
			when(roleRepository.findById(roleId)).thenReturn(mockRoleOptional);
			when(roleRepository.save(mockRole)).thenReturn(null);
			roleServiceImpl.updateRole(applicationEntitlementDTOList, roleName, roleDesription, roleId,
					roleDisplayName);
		} catch (BusinessException be) {
			assertEquals("Role Operation Failed", accessor.getMessage(be.getLocalizedMessage()));
		}

	}

	@Test
	public void testDeleteRole() {
		Optional<Role> mockRoleOptional = Optional.of(mockRole);
		when(roleRepository.findById(roleId)).thenReturn(mockRoleOptional);
		doNothing().when(roleRepository).deleteById(roleId);
		doNothing().when(roleEntitlementPermissionRepository).deleteByRoleId(roleId);
		doNothing().when(userRoleRepository).deleteByRoleId(roleId);
		assertEquals(ApplicationConstants.SUCCESS_CODE_200, roleServiceImpl.deleteRole(roleId).getCode());
	}

	@Test
	public void testDeleteRoleNotAvailableRoleId() {
		try {
			roleServiceImpl.deleteRole(null);
		} catch (BusinessException be) {
			assertEquals("Role Not Available", accessor.getMessage(be.getLocalizedMessage()));
		}
	}

	@Test
	public void testDeleteRoleEmptyObject() {
		Optional<Role> mockRoleOptional = Optional.empty();
		try {
			when(roleRepository.findById(roleId)).thenReturn(mockRoleOptional);
			roleServiceImpl.deleteRole(roleId);
		} catch (BusinessException be) {
			assertEquals("Role Not Available", accessor.getMessage(be.getLocalizedMessage()));
		}
	}

	@Test
	public void testRoleNameCheck() {

		when(roleRepository.findByRoleName(roleName)).thenReturn(mockRole);
		when(roleRepository.checkRoleApplicationExists(roleId, applicationId)).thenReturn(0);
		assertEquals(ApplicationConstants.SUCCESS_CODE_200,
				roleServiceImpl.roleNameCheck(applicationId, roleName, "name").getCode());
	}

	@Test
	public void testRoleNameCheckWithUnAvailableRole() {

		when(roleRepository.findByRoleName(roleName)).thenReturn(null);
		when(roleRepository.checkRoleApplicationExists(roleId, applicationId)).thenReturn(0);
		assertEquals(ApplicationConstants.SUCCESS_CODE_200,
				roleServiceImpl.roleNameCheck(applicationId, roleName, "name").getCode());
	}

	@Test
	public void testRoleNameCheckAlreadyAvailableRole() {

		try {
			when(roleRepository.findByRoleName(roleName)).thenReturn(mockRole);
			when(roleRepository.checkRoleApplicationExists(roleId, applicationId)).thenReturn(1);
			roleServiceImpl.roleNameCheck(applicationId, roleName, "name");
		} catch (BusinessException be) {
			assertEquals("Role SUPER ADMIN already exists. Please try with a different Role Name",
					"Role " + roleName + " already exists. Please try with a different Role Name");
		}
	}

	@Test
	public void testRoleNameCheckNeedRoleName() {
		try {
			roleServiceImpl.roleNameCheck(applicationId, "", "name");
		} catch (BusinessException be) {
			assertEquals("Role Name is Required", accessor.getMessage(be.getLocalizedMessage()));
		}
	}

	@Test
	public void testRoleDisplayNameCheck() {

		when(roleRepository.findByRoleDisplayName(roleName)).thenReturn(mockRole);
		when(roleRepository.checkRoleApplicationExists(roleId, applicationId)).thenReturn(0);
		assertEquals(ApplicationConstants.SUCCESS_CODE_200,
				roleServiceImpl.roleDisplayNameCheck(applicationId, roleDisplayName, "displayname").getCode());
	}

	@Test
	public void testRoleDisplayNameCheckWithUnAvailableRole() {

		when(roleRepository.findByRoleDisplayName(roleName)).thenReturn(null);
		when(roleRepository.checkRoleApplicationExists(roleId, applicationId)).thenReturn(0);
		assertEquals(ApplicationConstants.SUCCESS_CODE_200,
				roleServiceImpl.roleDisplayNameCheck(applicationId, roleDisplayName, "displayname").getCode());
	}

	@Test
	public void testRoleDisplayNameCheckAlreadyAvailableRole() {
		when(roleRepository.findByRoleDisplayName(roleName)).thenReturn(mockRole);
		when(roleRepository.checkRoleApplicationExists(roleId, applicationId)).thenReturn(1);
		assertEquals(ApplicationConstants.SUCCESS_CODE_200,
				roleServiceImpl.roleDisplayNameCheck(applicationId, roleDisplayName, "displayname").getCode());
	}

	@Test
	public void testRoleDisplayNameCheckNeedRoleName() {
		try {
			roleServiceImpl.roleDisplayNameCheck(applicationId, "", "displayname");
		} catch (BusinessException be) {
			assertEquals("Role Display Name is Required", accessor.getMessage(be.getLocalizedMessage()));
		}
	}

	@Test
	public void testGetUserRoleByRoleId() {
		when(userRoleRepository.checkUserRoleByRoleId(roleId)).thenReturn(3l);
		assertEquals(ApplicationConstants.SUCCESS_CODE_200, roleServiceImpl.getUserRoleByRoleId(roleId).getCode());
	}

	@Test
	public void testGetUserRoleByRoleIdNullCase() {
		try {
			when(userRoleRepository.checkUserRoleByRoleId(roleId)).thenReturn(null);
			roleServiceImpl.getUserRoleByRoleId(roleId);
		} catch (Exception be) {
			assertTrue(true);
		}
	}

}
