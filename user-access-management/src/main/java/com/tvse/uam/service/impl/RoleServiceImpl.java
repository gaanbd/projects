package com.tvse.uam.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.tvse.common.dto.ResponseDTO;
import com.tvse.common.exception.ApplicationException;
import com.tvse.common.exception.BusinessException;
import com.tvse.common.exception.DataRepositoryException;
import com.tvse.uam.constants.ApplicationConstants;
import com.tvse.uam.constants.MessageConstants;
import com.tvse.uam.domain.Role;
import com.tvse.uam.domain.RoleEntitlementPermission;
import com.tvse.uam.dto.ApplicationEntitlementDTO;
import com.tvse.uam.dto.ApplicationRoleDTO;
import com.tvse.uam.dto.EntitlementOperationsDTO;
import com.tvse.uam.repository.ApplicationRepository;
import com.tvse.uam.repository.EntitlementRepository;
import com.tvse.uam.repository.RoleEntitlementPermissionRepository;
import com.tvse.uam.repository.RoleRepository;
import com.tvse.uam.repository.UserRoleRepository;
import com.tvse.uam.service.RoleService;
import com.tvse.uam.util.CommonUtil;

/**
 * RoleServiceImpl class to implements RoleService interface methods
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Service(value = "roleService")
@Transactional
public class RoleServiceImpl implements RoleService {

	private static final Logger LOGGER = LogManager.getLogger(RoleServiceImpl.class.getName());

	private RoleRepository roleRepository;
	private UserRoleRepository userRoleRepository;
	private ApplicationRepository applicationRepository;
	private EntitlementRepository entitlementRepository;
	private RoleEntitlementPermissionRepository roleEntitlementPermissionRepository;

	@Inject
	public RoleServiceImpl(@NotNull RoleRepository roleRepository, @NotNull UserRoleRepository userRoleRepository,
			@NotNull ApplicationRepository applicationRepository, @NotNull EntitlementRepository entitlementRepository,
			@NotNull RoleEntitlementPermissionRepository roleEntitlementPermissionRepository) {
		this.roleRepository = roleRepository;
		this.userRoleRepository = userRoleRepository;
		this.applicationRepository = applicationRepository;
		this.entitlementRepository = entitlementRepository;
		this.roleEntitlementPermissionRepository = roleEntitlementPermissionRepository;
	}

	/**
	 * This method is used to get all the roles and its application
	 * 
	 * @return List of roles and its application
	 */
	@Override
	public ResponseDTO<Page<ApplicationRoleDTO>> getAllRoles(String searchTerm, Pageable page) {
		LOGGER.debug("RoleServiceImpl getAllRoles() Intiated :");
		List<ApplicationRoleDTO> roleAndApplicationList = new ArrayList<>();
		List<ApplicationRoleDTO> roleAndApplicationSearchList = new ArrayList<>();
		try {
			List<Role> roleList = roleRepository.findAll();
			roleList.forEach(role -> {
				
				List<String> applications = roleRepository.getApplicationsByRoleId(role.getId());
				ApplicationRoleDTO applicationRoleDTO = new ApplicationRoleDTO();
				applicationRoleDTO.setRoleId(role.getId());
				applicationRoleDTO.setRoleName(role.getRoleName());
				applicationRoleDTO.setRoleDescription(role.getRoleDescription());
				applicationRoleDTO.setRoleDisplayName(role.getRoleDisplayName());
				applicationRoleDTO.setApplicationNames(applications);
				roleAndApplicationList.add(applicationRoleDTO);
			});
		
			if (!StringUtils.isBlank(searchTerm)) {
				roleAndApplicationList.forEach(applicationRoleDTO -> {
					if (applicationRoleDTO.getRoleDescription().toLowerCase().contains(searchTerm.toLowerCase())
							|| applicationRoleDTO.getRoleDisplayName().toLowerCase().contains(searchTerm.toLowerCase())
							|| applicationRoleDTO.getRoleName().toLowerCase().contains(searchTerm.toLowerCase())
							|| applicationRoleDTO.getApplicationNames().contains(searchTerm.toLowerCase())
							|| applicationRoleDTO.getApplicationNames().contains(searchTerm.toUpperCase())) {
						roleAndApplicationSearchList.add(applicationRoleDTO);
					}
				});

				return new ResponseDTO<>(false, ApplicationConstants.SUCCESS_CODE_200,
						CommonUtil.populateMessage(MessageConstants.SUCCESS_GET),
						new PageImpl<>(roleAndApplicationSearchList, page, roleAndApplicationSearchList.size()));
			}

		} catch (DataAccessException dae) {
			LOGGER.error(CommonUtil.errorFormat(dae));
			throw new DataRepositoryException();
		} catch (Exception e) {
			LOGGER.error(CommonUtil.errorFormat(e));
			throw new ApplicationException();
		}
		LOGGER.debug("RoleServiceImpl getAllRolesWithApplication() Terminated :");
		return new ResponseDTO<>(false, ApplicationConstants.SUCCESS_CODE_200,
				CommonUtil.populateMessage(MessageConstants.SUCCESS_GET), 
				new PageImpl<>(roleAndApplicationList, page, roleAndApplicationList.size()));

	}

	@Override
	public ResponseDTO<Map<String, Boolean>> getUserRoleByRoleId(UUID roleId) {
		Map<String, Boolean> userRole = new HashMap<>();
		try {
			Long count = userRoleRepository.checkUserRoleByRoleId(roleId);
			Boolean isDelete = true;
			if (count > 0) {
				isDelete = false;
			}
			userRole.put("canDelete", isDelete);
		} catch (DataAccessException dae) {
			LOGGER.error(CommonUtil.errorFormat(dae));
			throw new DataRepositoryException();
		} catch (Exception e) {
			LOGGER.error(CommonUtil.errorFormat(e));
			throw new ApplicationException();
		}
		return new ResponseDTO<>(false, ApplicationConstants.SUCCESS_CODE_200,
				CommonUtil.populateMessage(MessageConstants.SUCCESS_GET), userRole);
	}

	/**
	 * This method is used to check the role name/ display name already exists for
	 * this application
	 * 
	 * @return String Role name/ role display name, UUID applicationId
	 */
	@Override
	public ResponseDTO<HttpStatus> validateRole(UUID applicationId, String role, String from) {
		LOGGER.debug("RoleServiceImpl validateRole() Intiated :");
		if (from.equals("name")) {
			LOGGER.debug("RoleServiceImpl validateRole() Terminated :");
			return roleNameCheck(applicationId, role, from);
		} else {
			LOGGER.debug("RoleServiceImpl validateRole() Terminated :");
			return roleDisplayNameCheck(applicationId, role, from);

		}

	}

	/**
	 * This method is used to check the role name already exists for this
	 * application
	 * 
	 * @return String Role name, UUID applicationId
	 */
	public ResponseDTO<HttpStatus> roleNameCheck(UUID applicationId, String roleName, String from) {
		try {
			if (!StringUtils.isBlank(roleName)) {
				roleName = roleName.toUpperCase().trim();
				Role role = roleRepository.findByRoleName(roleName);
				if (ObjectUtils.isEmpty(role)) {
					return new ResponseDTO<>(false, ApplicationConstants.SUCCESS_CODE_200,
							CommonUtil.populateMessage(MessageConstants.SUCCESS_GET), HttpStatus.OK);
				} else {
					return checkAlreadyRoleExists(applicationId, role, from);
				}
			} else {
				throw new BusinessException(MessageConstants.ROLE_NAME_IS_REQUIRED);
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

	/**
	 * This method is used to check the role name already exists for this
	 * application
	 * 
	 * @return String Role Display Name, UUID applicationId
	 */
	public ResponseDTO<HttpStatus> roleDisplayNameCheck(UUID applicationId, String roleDisplayName, String from) {

		try {
			if (!StringUtils.isBlank(roleDisplayName)) {
				roleDisplayName = roleDisplayName.toUpperCase().trim();
				Role role = roleRepository.findByRoleDisplayName(roleDisplayName);
				if (ObjectUtils.isEmpty(role)) {
					return new ResponseDTO<>(false, ApplicationConstants.SUCCESS_CODE_200,
							CommonUtil.populateMessage(MessageConstants.SUCCESS_GET), HttpStatus.OK);
				} else {
					return checkAlreadyRoleExists(applicationId, role, from);
				}
			} else {
				throw new BusinessException(MessageConstants.ROLE_DISPLAY_NAME_IS_REQUIRED);
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

	private ResponseDTO<HttpStatus> checkAlreadyRoleExists(UUID applicationId, Role role, String from) {
		try {
			int count = roleRepository.checkRoleApplicationExists(role.getId(), applicationId);
			if (count == 0) {
				return new ResponseDTO<>(false, ApplicationConstants.SUCCESS_CODE_200,
						CommonUtil.populateMessage(MessageConstants.SUCCESS_GET), HttpStatus.OK);
			} else {
				if (from.equals("name")) {
					String message = CommonUtil.populateMessage(MessageConstants.ROLE_NAME_ALREADY_EXIST);
					message = message.replace("{rolename}", role.getRoleName());
					throw new BusinessException(message);
				} else {
					String message = CommonUtil.populateMessage(MessageConstants.ROLE_DISPLAY_NAME_ALREADY_EXIST);
					message = message.replace("{displayname}", role.getRoleDisplayName());
					throw new BusinessException(message);

				}

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

	/**
	 * This method is used to get all the applications and entitlements
	 * 
	 * @return List of applications and entitlements
	 */
	@Override
	public ResponseDTO<List<ApplicationEntitlementDTO>> getAllEntitlements() {
		LOGGER.debug("RoleServiceImpl getAllEntitlements() Intiated :");
		List<ApplicationEntitlementDTO> applicationEntitlementList = new ArrayList<>();
		try {
			applicationRepository.findAll().forEach(applciation -> {
				List<EntitlementOperationsDTO> entitlementOperationsList = new ArrayList<>();
				ApplicationEntitlementDTO applicationEntitlement = new ApplicationEntitlementDTO();
				applicationEntitlement.setApplicationId(applciation.getId());
				applicationEntitlement.setApplicationName(applciation.getApplicationName());
				entitlementRepository.findByApplicationId(applciation.getId()).forEach(entitlement -> {
					EntitlementOperationsDTO entitlementOperations = new EntitlementOperationsDTO();
					entitlementOperations.setEntitlementId(entitlement.getId());
					entitlementOperations.setEntitlementName(entitlement.getEntitlementName());
					entitlementOperations.setPermissions(parsePermission(entitlement.getOperation()));
					entitlementOperationsList.add(entitlementOperations);
				});
				applicationEntitlement.setEntitlementOperationsList(entitlementOperationsList);
				applicationEntitlementList.add(applicationEntitlement);
			});
			LOGGER.debug("RoleServiceImpl getAllEntitlements() Terminated :");

		} catch (DataAccessException dae) {
			LOGGER.error(CommonUtil.errorFormat(dae));
			throw new DataRepositoryException();
		} catch (Exception e) {
			LOGGER.error(CommonUtil.errorFormat(e));
			throw new ApplicationException();
		}
		return new ResponseDTO<>(false, ApplicationConstants.SUCCESS_CODE_200,
				CommonUtil.populateMessage(MessageConstants.SUCCESS_GET), applicationEntitlementList);

	}

	/**
	 * This method is used to parse the entitlement operations
	 * 
	 * @return map of entitlements
	 */
	private Map<String, Boolean> parsePermission(String operations) {
		Map<String, Boolean> permissionMap = new HashMap<>();
		try {
			if (!StringUtils.isBlank(operations) && operations.length() > 0) {
				operations.chars().forEach(permissionChar -> {
					if (permissionChar == ApplicationConstants.READ_ONLY_OPERATION) {
						permissionMap.put(ApplicationConstants.READ_ONLY_PERMISSION, true);
					} else if (permissionChar == ApplicationConstants.CREATE_OPERATION) {
						permissionMap.put(ApplicationConstants.CREATE_PERMISSION, false);
					} else if (permissionChar == ApplicationConstants.CREATE_MODIFY_OPERATION) {
						permissionMap.put(ApplicationConstants.CREATE_MODIFY_PERMISSION, false);
					} else if (permissionChar == ApplicationConstants.FULL_ACCESS_OPERATION) {
						permissionMap.put(ApplicationConstants.FULL_ACCESS_PERMISSION, false);
					}
				});
			}
		} catch (Exception e) {
			LOGGER.error(CommonUtil.errorFormat(e));
			throw new ApplicationException();
		}
		return permissionMap;
	}

	/**
	 * This method is used to create the role after confirming the role name is not
	 * available condition.
	 * 
	 * @param Object applicationEntitlementDTO object, String roleName, String
	 *               roleDescription
	 * @return OK status for a successful save
	 */
	@Override
	public ResponseDTO<Map<String, UUID>> createRole(List<ApplicationEntitlementDTO> applicationEntitlementDTO,
			String roleName, String roleDescription, String roleDisplayName) {
		LOGGER.debug("RoleServiceImpl createRole() Intiated :");
		Map<String, UUID> createdRole = new HashMap<>();
		try {
			roleName = roleName.toUpperCase();
			if (StringUtils.isNotEmpty(roleName.trim())) {

				roleDisplayName = roleDisplayName.toUpperCase();
				if (StringUtils.isNotEmpty(roleDisplayName.trim())) {
					UUID roleId = roleEntitlementPermissionDataProcessing(applicationEntitlementDTO, roleName,
							roleDescription, roleDisplayName);
					createdRole.put("roleId", roleId);
					LOGGER.debug("RoleServiceImpl createRole() Terminated :");
				} else {

					throw new BusinessException(MessageConstants.ROLE_DISPLAY_NAME_IS_REQUIRED);
				}

			} else {
				throw new BusinessException(MessageConstants.ROLE_NAME_IS_REQUIRED);
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
		return new ResponseDTO<>(false, ApplicationConstants.SUCCESS_CODE_201,
				CommonUtil.populateMessage(MessageConstants.SUCCESS_GET), createdRole);

	}

	/**
	 * @param applicationEntitlementDTO
	 * @param roleName
	 * @param roleDescription
	 */
	private UUID roleEntitlementPermissionDataProcessing(List<ApplicationEntitlementDTO> applicationEntitlementDTO,
			String roleName, String roleDescription, String roleDisplayName) {

		UUID roleId = null;
		Role role = new Role();
		role.setRoleName(roleName);
		role.setRoleDescription(roleDescription);
		role.setRoleDisplayName(roleDisplayName);
		role.setCreatedBy(UUID.randomUUID());
		if (!(ObjectUtils.isEmpty(roleRepository.save(role)))) {
			insertRoleEntitlementPermission(applicationEntitlementDTO, role.getId());
			roleId = role.getId();
		} else {
			throw new BusinessException(MessageConstants.OPERATION_ROLE_FAILED);
		}
		return roleId;
	}

	/**
	 * @param applicationEntitlementDTO
	 * @param roleId
	 */
	private void insertRoleEntitlementPermission(List<ApplicationEntitlementDTO> applicationEntitlementDTO,
			UUID roleId) {
		try {
			applicationEntitlementDTO.forEach(applicationEntitlement -> {
				if (applicationEntitlement.isSelected()) {
					applicationEntitlement.getEntitlementOperationsList().forEach(entilement -> {
						if (entilement.isSelected()) {
							RoleEntitlementPermission roleEntitlementPermission = new RoleEntitlementPermission();
							roleEntitlementPermission.setEntitlementId(entilement.getEntitlementId());
							roleEntitlementPermission.setRoleId(roleId);
							roleEntitlementPermission
									.setOperation(getOperationFromPermission(entilement.getPermissions()));
							roleEntitlementPermission.setCreatedBy(UUID.randomUUID());
							roleEntitlementPermissionRepository.save(roleEntitlementPermission);
						}
					});
				}
			});

		} catch (DataAccessException dae) {
			LOGGER.error(CommonUtil.errorFormat(dae));
			throw new DataRepositoryException();
		} catch (Exception e) {
			LOGGER.error(CommonUtil.errorFormat(e));
			throw new ApplicationException();
		}
	}

	/**
	 * This method is used to get the operations from the permission
	 * 
	 * @param String permission
	 * @return String Operation
	 */
	public String getOperationFromPermission(Map<String, Boolean> permission) {

		StringBuilder operation = new StringBuilder();
		for (Map.Entry<String, Boolean> entry : permission.entrySet()) {
			if (entry.getValue()) {
				if (entry.getKey().equals(ApplicationConstants.CREATE_PERMISSION)) {
					operation.append(ApplicationConstants.CREATE_OPERATION + "");
				}
				if (entry.getKey().equals(ApplicationConstants.READ_ONLY_PERMISSION)) {
					operation.append(ApplicationConstants.READ_ONLY_OPERATION + "");
				}
				if (entry.getKey().equals(ApplicationConstants.CREATE_MODIFY_PERMISSION)) {
					operation.append(ApplicationConstants.CREATE_MODIFY_OPERATION + "");
				}
				if (entry.getKey().equals(ApplicationConstants.FULL_ACCESS_PERMISSION)) {
					operation.append(ApplicationConstants.FULL_ACCESS_OPERATION + "");
				}
			}
		}
		return operation.toString();
	}

	/**
	 * This method is used to update the role
	 * 
	 * @param Object applicationEntitlementDTO object, String roleName, String
	 *               roleDescription
	 * @return OK status for a successful save
	 */
	@Override
	public ResponseDTO<HttpStatus> updateRole(List<ApplicationEntitlementDTO> applicationEntitlementDTO,
			String roleName, String roleDescription, UUID roleId, String roleDisplayName) {

		LOGGER.debug("RoleServiceImpl updateRole() Intiated :");
		try {
			roleName = roleName.toUpperCase();
			if (!ObjectUtils.isEmpty(roleId)) {
				Optional<Role> roleObj = roleRepository.findById(roleId);
				if (roleObj.isPresent()) {
					Role role = roleObj.get();
					role.setRoleName(roleName);
					role.setRoleDescription(roleDescription);
					role.setRoleDisplayName(roleDisplayName);
					role.setUpdatedBy(UUID.randomUUID());
					if (!(ObjectUtils.isEmpty(roleRepository.save(role)))) {
						deleteRoleandRoleMappingData(roleId, false);
						roleEntitlementPermissionDataProcessing(applicationEntitlementDTO, roleName, roleDescription,
								roleDisplayName);
						LOGGER.debug("RoleServiceImpl updateRole() Terminated :");

					} else {

						throw new BusinessException(MessageConstants.OPERATION_ROLE_FAILED);
					}
				} else {
					throw new BusinessException(MessageConstants.ROLE_NOT_AVAILABLE);
				}
			} else {
				throw new BusinessException(MessageConstants.ROLE_NOT_AVAILABLE);
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

		return new ResponseDTO<>(false, ApplicationConstants.SUCCESS_CODE_200,
				CommonUtil.populateMessage(MessageConstants.SUCCESS_GET), HttpStatus.OK);

	}

	/**
	 * This method is used to delete the role and roleentitlementpermission mapping
	 * by role id
	 * 
	 * @param UUID role id
	 * @return OK status for a successful delete
	 */
	@Override
	public ResponseDTO<HttpStatus> deleteRole(UUID roleId) {
		LOGGER.debug("RoleServiceImpl deleteRole() Intiated :");
		try {
			if (!ObjectUtils.isEmpty(roleId)) {
				Optional<Role> role = roleRepository.findById(roleId);
				if (role.isPresent()) {
					deleteRoleandRoleMappingData(roleId, true);
					LOGGER.debug("RoleServiceImpl deleteRole() Terminated :");

				} else {

					throw new BusinessException(MessageConstants.ROLE_NOT_AVAILABLE);
				}
			} else {
				throw new BusinessException(MessageConstants.ROLE_NOT_AVAILABLE);
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
		return new ResponseDTO<>(false, ApplicationConstants.SUCCESS_CODE_200,
				CommonUtil.populateMessage(MessageConstants.SUCCESS_GET), HttpStatus.OK);
	}

	private void deleteRoleandRoleMappingData(UUID roleId, Boolean calledFrom) {
		try {
			roleEntitlementPermissionRepository.deleteByRoleId(roleId);
			roleRepository.deleteById(roleId);
			if (calledFrom) {
				userRoleRepository.deleteByRoleId(roleId);
			}
		} catch (DataAccessException dae) {
			LOGGER.error(CommonUtil.errorFormat(dae));
			throw new DataRepositoryException();
		} catch (Exception e) {
			LOGGER.error(CommonUtil.errorFormat(e));
			throw new ApplicationException();
		}
	}

	/**
	 * This method is used to get the role details by role id
	 * 
	 * @param UUID role id
	 * @return role object
	 */
	@Override
	public ResponseDTO<Map<String, Object>> getRoleById(UUID roleId) {
		LOGGER.debug("RoleServiceImpl getRoleById() Intiated :");

		Map<String, Object> roleApplicationEntitlement = new HashMap<>();
		try {
			Optional<Role> roleobj = roleRepository.findById(roleId);
			if (roleobj.isPresent()) {
				roleApplicationEntitlement.put("role", roleobj.get());
				List<ApplicationEntitlementDTO> applicationEntitlementList = new ArrayList<>();
				applicationRepository.findAll().forEach(applciation -> {
					List<EntitlementOperationsDTO> entitlementOperationsList = new ArrayList<>();
					ApplicationEntitlementDTO applicationEntitlement = new ApplicationEntitlementDTO();
					applicationEntitlement.setApplicationId(applciation.getId());
					applicationEntitlement.setApplicationName(applciation.getApplicationName());
					applicationEntitlement.setSelected(checkRoleApplicationExists(roleId, applciation.getId()));
					entitlementRepository.findByApplicationId(applciation.getId()).forEach(entitlement -> {
						EntitlementOperationsDTO entitlementOperations = new EntitlementOperationsDTO();
						entitlementOperations.setEntitlementId(entitlement.getId());
						entitlementOperations.setEntitlementName(entitlement.getEntitlementName());
						entitlementOperations.setSelected(
								checkRoleApplicationExists(roleId, applciation.getId(), entitlement.getId()));
						entitlementOperations.setPermissions(parseEntitlePermission(roleRepository
								.getOperationOfEntitlement(roleId, applciation.getId(), entitlement.getId())));
						entitlementOperationsList.add(entitlementOperations);
					});
					applicationEntitlement.setEntitlementOperationsList(entitlementOperationsList);
					applicationEntitlementList.add(applicationEntitlement);
				});
				roleApplicationEntitlement.put("ApplicationEntitlementPermission", applicationEntitlementList);
				LOGGER.debug("RoleServiceImpl getRoleById() Terminated :");
			} else {

				throw new BusinessException(MessageConstants.ROLE_NOT_AVAILABLE);
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
		return new ResponseDTO<>(false, ApplicationConstants.SUCCESS_CODE_200,
				CommonUtil.populateMessage(MessageConstants.SUCCESS_GET), roleApplicationEntitlement);
	}

	/**
	 * This method is used to check the Role is Exists in Application
	 * 
	 * @param UUID role id, UUID applicationId
	 * @return boolean result
	 */
	private boolean checkRoleApplicationExists(UUID roleId, UUID applicationId) {

		int count = roleRepository.checkRoleApplicationExists(roleId, applicationId);
		return (count > 0);
	}

	/**
	 * This method is used to check the Role and Entitlement is Exists in
	 * Application
	 * 
	 * @param UUID role id, UUID applicationId, UUID entitlementId
	 * @return boolean result
	 */
	private boolean checkRoleApplicationExists(UUID roleId, UUID applicationId, UUID entitlementId) {

		int count = roleRepository.checkRoleApplicationEntitlementExists(roleId, applicationId, entitlementId);
		return (count > 0);
	}

	/**
	 * This method is used to set the entitlement operations
	 * 
	 * @return map of entitlements
	 */
	private Map<String, Boolean> parseEntitlePermission(String operations) {
		if (StringUtils.isBlank(operations)) {
			operations = "XXXX";
		}
		Map<String, Boolean> permissionMap = new HashMap<>();
		operations.chars().forEach(permissionChar -> {
			permissionMap.put(ApplicationConstants.READ_ONLY_PERMISSION,
					setPermission(permissionChar, ApplicationConstants.READ_ONLY_OPERATION));
			permissionMap.put(ApplicationConstants.CREATE_PERMISSION,
					setPermission(permissionChar, ApplicationConstants.CREATE_OPERATION));
			permissionMap.put(ApplicationConstants.CREATE_MODIFY_PERMISSION,
					setPermission(permissionChar, ApplicationConstants.CREATE_MODIFY_OPERATION));
			permissionMap.put(ApplicationConstants.FULL_ACCESS_PERMISSION,
					setPermission(permissionChar, ApplicationConstants.FULL_ACCESS_OPERATION));
		});
		return permissionMap;

	}

	/**
	 * This method is used to parse the entitlement operations
	 * 
	 * @return map of entitlements
	 */
	private Boolean setPermission(int permissionChar, char operation) {
		return (permissionChar == operation);
	}
}