package com.tvse.uam.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tvse.common.dto.ResponseDTO;
import com.tvse.uam.constants.ApplicationConstants;
import com.tvse.uam.dto.ApplicationEntitlementDTO;
import com.tvse.uam.dto.ApplicationRoleDTO;
import com.tvse.uam.service.RoleService;

/**
 * RoleController Class to write our UAM ROLE related rest api's
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@RestController
@RequestMapping(ApplicationConstants.ROLES)
public class RoleController {

	private static final Logger LOGGER = LogManager.getLogger(RoleController.class.getName());

	private RoleService roleService;

	@Inject
	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}

	/**
	 * get api to get all the roles and its application
	 */
	@GetMapping
	public ResponseEntity<ResponseDTO<Page<ApplicationRoleDTO>>> getAllRoles(@RequestParam("searchParam") String searchParam, Pageable page) {
		LOGGER.info("Start getAllRoles() in Controller");
		return new ResponseEntity<>(roleService.getAllRoles(searchParam,page), HttpStatus.OK);
	}

	/**
	 * get api to check role name/ role display name already exist or not
	 */
	@GetMapping(ApplicationConstants.ROLE_VALIDATION)
	public ResponseEntity<ResponseDTO<HttpStatus>> roleValidation(
			@PathVariable(ApplicationConstants.APPLICATIONID) UUID applicationId, @PathVariable("role") String role,
			@PathVariable("from") String from) {
		LOGGER.info("Start roleValidation() in Controller");
		return new ResponseEntity<>(roleService.validateRole(applicationId, role, from), HttpStatus.OK);
	}

	/**
	 * get api to get all application and its entitlement permissions
	 */
	@GetMapping(ApplicationConstants.GET_ALL_ENTITLEMENTS)
	public ResponseEntity<ResponseDTO<List<ApplicationEntitlementDTO>>> getAllEntitlements() {
		LOGGER.info("Start getAllEntitlements() in Controller");
		return new ResponseEntity<>(roleService.getAllEntitlements(), HttpStatus.OK);
	}

	/**
	 * post api to save a new role
	 */
	@PostMapping(ApplicationConstants.CREATE_ROLE)
	public ResponseEntity<ResponseDTO<Map<String, UUID>>> createRole(
			@RequestBody List<ApplicationEntitlementDTO> applicationEntitlementDTO,
			@RequestParam(ApplicationConstants.ROLENAME) String roleName,
			@RequestParam(ApplicationConstants.ROLEDESCRIPTION) String roleDescription,
			@RequestParam(ApplicationConstants.ROLEDISPLAYNAME) String roleDisplayName) {
		LOGGER.info("Start createRole() in Controller");
		return new ResponseEntity<>(
				roleService.createRole(applicationEntitlementDTO, roleName, roleDescription, roleDisplayName),
				HttpStatus.CREATED);
	}

	/**
	 * put api to update a already existed role
	 */
	@PostMapping(ApplicationConstants.UPDATE_ROLE)
	public ResponseEntity<ResponseDTO<HttpStatus>> updateRole(
			@RequestBody List<ApplicationEntitlementDTO> applicationEntitlementDTO,
			@RequestParam(ApplicationConstants.ROLENAME) String roleName,
			@RequestParam(ApplicationConstants.ROLEDESCRIPTION) String roleDescription,
			@RequestParam(ApplicationConstants.ROLEID) UUID roleId,
			@RequestParam(ApplicationConstants.ROLEDISPLAYNAME) String roleDisplayName) {
		LOGGER.info("Start updateRole() in Controller");
		return new ResponseEntity<>(
				roleService.updateRole(applicationEntitlementDTO, roleName, roleDescription, roleId, roleDisplayName),
				HttpStatus.OK);
	}

	/**
	 * delete api to delete a already existed role
	 */
	@DeleteMapping(ApplicationConstants.ROLE_ID)
	public ResponseEntity<ResponseDTO<HttpStatus>> deleteRole(@PathVariable(ApplicationConstants.ROLEID) UUID roleId) {
		LOGGER.info("Start deleteRole() in Controller");
		return new ResponseEntity<>(roleService.deleteRole(roleId), HttpStatus.OK);
	}

	/**
	 * post api to get a role by given role id
	 */
	@GetMapping("/candelete" + ApplicationConstants.ROLE_ID)
	public ResponseEntity<ResponseDTO<Map<String, Boolean>>> getUserRoleByRoleId(
			@PathVariable(ApplicationConstants.ROLEID) UUID roleId) {
		LOGGER.info("Start getUserRoleByRoleId() in Controller");
		return new ResponseEntity<>(roleService.getUserRoleByRoleId(roleId), HttpStatus.OK);
	}

	/**
	 * post api to get a role by given role id
	 */
	@GetMapping(ApplicationConstants.ROLE_ID)
	public ResponseEntity<ResponseDTO<Map<String, Object>>> getRoleById(
			@PathVariable(ApplicationConstants.ROLEID) UUID roleId) {
		LOGGER.info("Start getRoleById() in Controller");
		return new ResponseEntity<>(roleService.getRoleById(roleId), HttpStatus.OK);
	}
}
