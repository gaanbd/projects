package com.tvse.uam.controller;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.tvse.uam.domain.Branch;
import com.tvse.uam.domain.Zone;
import com.tvse.uam.dto.ApplicationDTO;
import com.tvse.uam.dto.BrandDTO;
import com.tvse.uam.dto.RoleDTO;
import com.tvse.uam.dto.UserRoleDTO;
import com.tvse.uam.service.UserRoleService;

/**
 * UserRoleController Class to write our UserRole Rest API
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@RestController
@RequestMapping(ApplicationConstants.USERROLES)
public class UserRoleController {

	private static final Logger LOGGER = LogManager.getLogger(UserRoleController.class.getName());

	private UserRoleService userRoleService;

	@Inject
	public UserRoleController(UserRoleService userRoleService) {
		this.userRoleService = userRoleService;
	}

	/**
	 * @return
	 */
	@GetMapping(ApplicationConstants.BRANDS)
	public ResponseEntity<ResponseDTO<List<BrandDTO>>> getAllBrands() {
		LOGGER.info("Start UserRoleController getAllBrands() in Controller");
		return new ResponseEntity<>(userRoleService.getAllBrands(), HttpStatus.OK);
	}

	/**
	 * @return
	 */
	@GetMapping(ApplicationConstants.APPLICATIONS)
	public ResponseEntity<ResponseDTO<List<ApplicationDTO>>> getAllApplications() {
		LOGGER.info("Start UserRoleController getAllApplications() in Controller");
		return new ResponseEntity<>(userRoleService.getAllApplications(), HttpStatus.OK);
	}

	/**
	 * @param id
	 * @return
	 */
	@GetMapping(ApplicationConstants.APPLICATIONROLES)
	public ResponseEntity<ResponseDTO<List<RoleDTO>>> getRolesByApplication(
			@RequestParam(value = ApplicationConstants.ID) List<UUID> id) {
		LOGGER.info("Start UserRoleController getRolesByApplication() in Controller");
		return new ResponseEntity<>(userRoleService.getRolesByApplicationId(id), HttpStatus.OK);
	}

	/**
	 * @param userRoleDTOList
	 * @return
	 */
	@PostMapping
	public ResponseEntity<ResponseDTO<UserRoleDTO>> createUserRole(@RequestBody List<UserRoleDTO> userRoleDTOList) {
		LOGGER.info("Start UserRoleController createUserRole() in Controller");
		return new ResponseEntity<>(userRoleService.createUserRole(userRoleDTOList), HttpStatus.OK);
	}

	/**
	 * @param userId
	 * @return
	 */
	@GetMapping(value = ApplicationConstants.USERROLES_USERID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<List<UserRoleDTO>>> getUserRoleById(
			@PathVariable(ApplicationConstants.USERID) UUID userId) {
		LOGGER.info("Start UserRoleController getUserRoleById() in Controller");
		return new ResponseEntity<>(userRoleService.getUserRoleByUserId(userId), HttpStatus.OK);
	}

	/**
	 * @param userId
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = ApplicationConstants.USERROLES_DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<HttpStatus>> deleteUserRole(@PathVariable(ApplicationConstants.USERID) UUID userId) {
		LOGGER.debug("Start UserRoleController deleteUserRole() in Controller");
		return new ResponseEntity<>(userRoleService.deleteUserRole(userId), HttpStatus.OK);
	}
	
	/**
	 * @return
	 */
	@GetMapping(ApplicationConstants.ZONE)
	public ResponseEntity<ResponseDTO<List<Zone>>> getAllZones() {
		LOGGER.info("Start UserRoleController getAllZones() in Controller");
		return new ResponseEntity<>(userRoleService.getAllZones(), HttpStatus.OK);
	}

	/**
	 * @param zoneId
	 * @return
	 */
	@GetMapping(ApplicationConstants.BRANCHES_BY_ZONE)
	public ResponseEntity<ResponseDTO<List<Branch>>> getBranchesByZoneId(@PathVariable(ApplicationConstants.ZONEID) UUID zoneId) {
		LOGGER.info("Start UserRoleController getBranchesByZoneId() in Controller");
		return new ResponseEntity<>(userRoleService.getBranchesByZoneId(zoneId), HttpStatus.OK);
	}
	
}
