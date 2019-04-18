package com.tvse.uam.controller;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.tvse.uam.domain.City;
import com.tvse.uam.domain.Country;
import com.tvse.uam.domain.State;
import com.tvse.uam.dto.UserDTO;
import com.tvse.uam.dto.UserProfileDTO;
import com.tvse.uam.service.UserService;

/**
 * @author techmango
 *
 */
@RestController
@RequestMapping(ApplicationConstants.USERS)
public class UserController {

	private static final Logger LOGGER = LogManager.getLogger(UserController.class.getName());

	private UserService userService;

	@Inject
	public UserController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * To Get the user details in grid
	 * 
	 * @param pageable
	 * @return
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = ApplicationConstants.GETALLUSERDETAILS)
	public ResponseDTO<Page<UserDTO>> getUsers(@RequestParam("searchParam") String searchParam,Pageable pageable) {
		LOGGER.info("Start the get user resource");
		return userService.getAllUserProfile(pageable,searchParam);
	}

	/**
	 * To Get all the country
	 * 
	 * @return
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = ApplicationConstants.GETALLCOUNTRY)
	public ResponseEntity<ResponseDTO<List<Country>>> getAllCountry() {
		LOGGER.info("Start the get Country resource");
		return new ResponseEntity<>(userService.getAllCountry(), HttpStatus.OK);
	}

	/**
	 * To Get the State by Country Id
	 * 
	 * @param countryId
	 * @return
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = ApplicationConstants.GETALLSTATEBYCOUNTRY)
	public ResponseEntity<ResponseDTO<List<State>>> getAllStateByCountry(
			@PathVariable(ApplicationConstants.COUNTRYID) UUID countryId) {
		LOGGER.info("Start the get State resource");
		return new ResponseEntity<>(userService.getAllStateByCountry(countryId), HttpStatus.OK);
	}

	/**
	 * To Get the City by state id
	 * 
	 * @param stateId
	 * @return
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = ApplicationConstants.GETALLCITYBYSTATE)
	public ResponseEntity<ResponseDTO<List<City>>> getAllCityByState(
			@PathVariable(ApplicationConstants.STATEID) UUID stateId) {
		LOGGER.info("Start the get city resource");
		return new ResponseEntity<>(userService.getAllCityByState(stateId), HttpStatus.OK);
	}

	/**
	 * To check the login name
	 * 
	 * @param loginName
	 * @return
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = ApplicationConstants.LOGINNAMECHECK)
	public ResponseDTO<String> loginNameCheck(@PathVariable(ApplicationConstants.LOGINNAME) String loginName) {
		LOGGER.info("Start Login Duplicate Check");
		return userService.loginNameCheck(loginName);
	}

	/**
	 * To check whether the employeeId is unique or not
	 * 
	 * @param employeeId
	 * @return
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = ApplicationConstants.EMPLOYEEID_UNIQUE)
	public ResponseDTO<String> employeeIdUniqueCheck(@PathVariable(ApplicationConstants.EMPLOYEEID) String employeeId) {
		LOGGER.info("Start UserController employeeIdUniqueCheck ");
		return userService.employeeIdUniqueCheck(employeeId);
	}

	/**
	 * Used To Create the User
	 * 
	 * @param userProfileDTO
	 * @return
	 */
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<UserDTO>> createUser(@Valid @RequestBody UserDTO userProfileDTO) {
		LOGGER.info("Start UserController createUser() in Controller");
		return new ResponseEntity<>(userService.createUser(userProfileDTO), HttpStatus.OK);
	}

	/**
	 * Used to Update the User
	 * 
	 * @param userProfileDTO
	 * @return
	 */
	@PostMapping(value = ApplicationConstants.USER, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<UserDTO>> updateUser(@Valid @RequestBody UserDTO userProfileDTO) {
		LOGGER.info("Start UserController updateUser() in Controller");
		return new ResponseEntity<>(userService.updateUser(userProfileDTO), HttpStatus.OK);
	}

	/**
	 * Used to Delete the User
	 * 
	 * @param userId
	 * @return
	 */
	@DeleteMapping(value = ApplicationConstants.USERROLES_USERID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<UserDTO>> deleteUser(@PathVariable(ApplicationConstants.USERID) UUID userId) {
		LOGGER.info("Start UserController deleteUser() in Controller");
		return new ResponseEntity<>(userService.deleteUser(userId), HttpStatus.OK);
	}

	/**
	 * To Get the User Details By Id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = ApplicationConstants.GETBYID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<UserDTO>> getByUserId(@PathVariable(ApplicationConstants.ID) UUID id) {
		LOGGER.info("Start UserController getUserById() in Controller");
		return new ResponseEntity<>(userService.getByUserId(id), HttpStatus.OK);
	}

	/**
	 * To update the user status to Inactive
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping(value = ApplicationConstants.ID_STATUS, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<UserDTO>> updateUserStatus(@PathVariable(ApplicationConstants.ID) UUID id) {
		LOGGER.info("Start UserController updateUserStatus() in Controller");
		return new ResponseEntity<>(userService.updateUserStatus(id), HttpStatus.OK);
	}

	/**
	 * This method is to get the user profile details
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = ApplicationConstants.USER_PROFILE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<UserProfileDTO>> getUserProfile(@PathVariable(ApplicationConstants.ID) UUID id) {
		LOGGER.info("Start UserController getUserProfile() in Controller");
		return new ResponseEntity<>(userService.getUserProfile(id), HttpStatus.OK);
	}

	/**
	 * This method is to change the password
	 * 
	 * @param userDTO
	 * @return
	 */
	@PostMapping(value = ApplicationConstants.CHANGE_PASSWRD, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<String>> changePassword(@RequestBody UserDTO userDTO) {
		LOGGER.info("Start UserController changePassword() in Controller");
		return new ResponseEntity<>(userService.changePassword(userDTO), HttpStatus.OK);
	}

}
