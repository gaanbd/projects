package com.tvse.uam.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.tvse.common.dto.ResponseDTO;
import com.tvse.common.exception.ApplicationException;
import com.tvse.common.exception.BusinessException;
import com.tvse.common.exception.DataRepositoryException;
import com.tvse.uam.constants.ApplicationConstants;
import com.tvse.uam.constants.MessageConstants;
import com.tvse.uam.domain.City;
import com.tvse.uam.domain.Country;
import com.tvse.uam.domain.Role;
import com.tvse.uam.domain.State;
import com.tvse.uam.domain.User;
import com.tvse.uam.domain.UserRole;
import com.tvse.uam.dto.ApplicationDTO;
import com.tvse.uam.dto.UserDTO;
import com.tvse.uam.dto.UserProfileDTO;
import com.tvse.uam.dto.UserRoleDTO;
import com.tvse.uam.mapper.ApplicationMapper;
import com.tvse.uam.mapper.UserAccessMapper;
import com.tvse.uam.mapper.UserMapper;
import com.tvse.uam.repository.ApplicationRepository;
import com.tvse.uam.repository.BrandRepository;
import com.tvse.uam.repository.CityRepository;
import com.tvse.uam.repository.CountryRepository;
import com.tvse.uam.repository.RoleRepository;
import com.tvse.uam.repository.StateRepository;
import com.tvse.uam.repository.UserRepository;
import com.tvse.uam.repository.UserRoleRepository;
import com.tvse.uam.service.UserService;
import com.tvse.uam.util.CommonUtil;

/**
 * @author techmango (https://www.techmango.net/)
 *
 */

@Service(value = "userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Value("${amazon.s3.default-bucket}")
	private String s3Bucket;

	private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class.getName());

	private UserRepository userRepository;
	private CityRepository cityRepository;
	private CountryRepository countryRepository;
	private StateRepository stateRepository;
	private UserRoleRepository userRoleRepository;
	private BrandRepository brandRepository;
	private ApplicationRepository applicationRepository;
	private RoleRepository roleRepository;

	@Inject
	public UserServiceImpl(@NotNull UserRepository userRepository, @NotNull StateRepository stateRepository,
			@NotNull CountryRepository countryRepository, @NotNull CityRepository cityRepository,
			@NotNull UserRoleRepository userRoleRepository, @NotNull BrandRepository brandRepository,
			@NotNull ApplicationRepository applicationRepository, @NotNull RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.cityRepository = cityRepository;
		this.countryRepository = countryRepository;
		this.stateRepository = stateRepository;
		this.userRoleRepository = userRoleRepository;
		this.brandRepository = brandRepository;
		this.applicationRepository = applicationRepository;
		this.roleRepository = roleRepository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tvse.uam.service.UserAccessService#getAllUserProfile(org.springframework.
	 * data.domain.Pageable)
	 */
	public ResponseDTO<Page<UserDTO>> getAllUserProfile(Pageable pageable, String searchParam) {
		List<UserDTO> userListDto = new ArrayList<>();
		List<UserDTO> userListSearchList = new ArrayList<>();
		Page<User> userList = null;
		try {
			LOGGER.info("Start the GetAll userProfile service");
			Pageable pageableRequest = pageable;
			userList = prepareUserList(pageableRequest);
			if (CollectionUtils.isNotEmpty(userList.getContent())) {
				userListDto = UserMapper.INSTANCE.userProfileListToUserProfileDTOList(userList.getContent());
				userListDto.forEach(user -> {
					user.setRoles(userRepository.getUserRoles(user.getId()));
					user.setBrands(userRepository.getUserBrands(user.getId()));
					user.setCityName(cityRepository.findCityNameByCityId(user.getCityId()));
					user.setFullName(constructFullName(user.getFirstName(), user.getMiddleName(), user.getLastName()));
				});
				if (!StringUtils.isBlank(searchParam)) {
					userListDto.forEach(user -> {
						if (user.getUserName().toLowerCase().contains(searchParam.toLowerCase())
								|| user.getFullName().toLowerCase().contains(searchParam.toLowerCase())
								|| user.getEmployeeId().toLowerCase().contains(searchParam.toLowerCase())
								|| user.getRoles().contains(searchParam.toLowerCase())
								|| user.getBrands().contains(searchParam.toLowerCase())
								|| (StringUtils.isNotBlank(user.getCityName())
										&& user.getCityName().toLowerCase().contains(searchParam.toLowerCase()))) {
							userListSearchList.add(user);
						}
					});
					return new ResponseDTO<>(false, ApplicationConstants.SUCCESS_CODE_200,
							CommonUtil.populateMessage(MessageConstants.USER_SHOWED),
							new PageImpl<>(userListSearchList, pageable, userListSearchList.size()));
				}
			} else {
				throw new BusinessException(MessageConstants.USERS_NOT_AVAILABLE);
			}

			LOGGER.info("End the GetAll userProfile service");
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
				CommonUtil.populateMessage(MessageConstants.USER_SHOWED),
				new PageImpl<>(userListDto, pageable, userList.getTotalElements()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tvse.uam.service.UserAccessService#getAllCountry()
	 */
	@Override
	public ResponseDTO<List<Country>> getAllCountry() {
		return new ResponseDTO<>(false, ApplicationConstants.SUCCESS_CODE_200,
				CommonUtil.populateMessage(MessageConstants.DETAILS_FETCHED_SUCCESS), countryRepository.findAll());
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tvse.uam.service.UserAccessService#getAllStateByCountry(java.util.UUID)
	 */

	@Override
	public ResponseDTO<List<State>> getAllStateByCountry(UUID countryId) {
		try {
			return new ResponseDTO<>(false, ApplicationConstants.SUCCESS_CODE_200,
					CommonUtil.populateMessage(MessageConstants.DETAILS_FETCHED_SUCCESS),
					stateRepository.findAllByCountryId(countryId));
		} catch (DataAccessException dae) {
			LOGGER.error(CommonUtil.errorFormat(dae));
			throw new DataRepositoryException();
		} catch (Exception e) {
			LOGGER.error(CommonUtil.errorFormat(e));
			throw new ApplicationException();
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tvse.uam.service.UserAccessService#getAllCityByState(java.util.UUID)
	 */

	@Override
	public ResponseDTO<List<City>> getAllCityByState(UUID stateId) {
		try {
			return new ResponseDTO<>(false, ApplicationConstants.SUCCESS_CODE_200,
					CommonUtil.populateMessage(MessageConstants.DETAILS_FETCHED_SUCCESS),
					cityRepository.findAllByStateId(stateId));
		} catch (DataAccessException dae) {
			LOGGER.error(CommonUtil.errorFormat(dae));
			throw new DataRepositoryException();
		} catch (Exception e) {
			LOGGER.error(CommonUtil.errorFormat(e));
			throw new ApplicationException();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tvse.uam.service.UserAccessService#loginNameCheck(java.lang.String)
	 */
	@Override
	public ResponseDTO<String> loginNameCheck(String loginName) {
		try {
			LOGGER.info("Start User loginNameCheck() in ServiceImpl");
			User user = userRepository.findByUserName(loginName);
			if (!ObjectUtils.isEmpty(user)) {
				LOGGER.debug("Start Username not empty check in ServiceImpl");
				return new ResponseDTO<>(true, ApplicationConstants.ERROR_CODE_409,
						CommonUtil.populateMessage(MessageConstants.USERS_NOT_AVAILABLE), null);
			} else {
				return new ResponseDTO<>(false, ApplicationConstants.SUCCESS_CODE_200,
						CommonUtil.populateMessage(MessageConstants.USER_AVAILABLE), null);
			}
		} catch (DataAccessException dae) {
			LOGGER.error(CommonUtil.errorFormat(dae));
			throw new DataRepositoryException();
		} catch (Exception e) {
			LOGGER.error(CommonUtil.errorFormat(e));
			throw new ApplicationException();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tvse.uam.service.UserService#employeeIdUniqueCheck(java.lang.String)
	 */
	@Override
	public ResponseDTO<String> employeeIdUniqueCheck(String employeeId) {
		try {
			LOGGER.info("Start User employeeIdUniqueCheck() in ServiceImpl");
			User user = userRepository.findByEmployeeId(employeeId);
			if (!ObjectUtils.isEmpty(user)) {
				LOGGER.debug("Start employeeId not empty check in ServiceImpl");
				return new ResponseDTO<>(true, ApplicationConstants.ERROR_CODE_409,
						CommonUtil.populateMessage(MessageConstants.EMPLOYEE_ID_ALREADY_EXISTS), null);
			} else {
				return new ResponseDTO<>(false, ApplicationConstants.SUCCESS_CODE_200,
						CommonUtil.populateMessage(MessageConstants.EMPLOYEE_ID_AVAILABLE), null);
			}
		} catch (DataAccessException dae) {
			LOGGER.error(CommonUtil.errorFormat(dae));
			throw new DataRepositoryException();
		} catch (Exception e) {
			LOGGER.error(CommonUtil.errorFormat(e));
			throw new ApplicationException();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tvse.uam.service.UserService#createUser(com.tvse.uam.dto.UserDTO)
	 */
	@Override
	public ResponseDTO<UserDTO> createUser(UserDTO userDTO) {
		LOGGER.info("Start User createUser() in ServiceImpl");
		User user = null;
		try {
			LOGGER.debug("Validation User createUser() in ServiceImpl");
			validateUser(userDTO); // validating user object
			user = UserMapper.INSTANCE.userDTOtoUser(userDTO);

			user.setCreatedBy(ApplicationConstants.SUPER_ADMIN);
			LOGGER.debug("Start User createUser() in ServiceImpl");
			user = userRepository.save(user);
			LOGGER.debug("Completed User createUser() in ServiceImpl");
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
				CommonUtil.populateMessage(MessageConstants.USER_CREATED), UserMapper.INSTANCE.userToUserDTO(user));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tvse.uam.service.UserService#updateUser(com.tvse.uam.dto.UserDTO)
	 */
	@Override
	public ResponseDTO<UserDTO> updateUser(UserDTO userDTO) {
		LOGGER.info("Validation User updateUser() in ServiceImpl");
		User user = null;
		try {
			validateUpdateUser(userDTO);
			user = userRepository.findbyId(userDTO.getId());
			if (ObjectUtils.isEmpty(user)) {
				throw new BusinessException(MessageConstants.USER_DETAILS_NOT_FOUND);
			}
			if (!StringUtils.equalsIgnoreCase(userDTO.getAction(), ApplicationConstants.PROFILE_IMAGE_UPLOAD)) {
				userDTO.setCreatedBy(ApplicationConstants.SUPER_ADMIN);
				userDTO.setUpdatedBy(ApplicationConstants.SUPER_ADMIN);
				userDTO.setUserName(user.getUserName());
				userDTO.setEmployeeId(user.getEmployeeId());
				userDTO.setUserPassword(user.getUserPassword());
				LOGGER.debug("Start User updateUser() in ServiceImpl");
				userRepository.save(UserMapper.INSTANCE.userDTOtoUser(userDTO));
				LOGGER.debug("Completed User updateUser() in ServiceImpl");
			}else {
				user.setProfileImageS3Path(userDTO.getProfileImageS3Path());
				userRepository.save(user);
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
				CommonUtil.populateMessage(MessageConstants.USER_UPDATED), UserMapper.INSTANCE.userToUserDTO(user));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tvse.uam.service.UserService#getByUserId(java.util.UUID)
	 */
	@Override
	public ResponseDTO<UserDTO> getByUserId(UUID id) {
		LOGGER.info("start User getByUserId() in ServiceImpl");
		UserDTO userDto = null;
		try {
			User user = userRepository.findbyId(id);
			userDto = UserMapper.INSTANCE.userToUserDTO(user);
			LOGGER.debug("Validation User getByUserId() in ServiceImpl");
			if (ObjectUtils.isEmpty(user)) {
				throw new BusinessException(MessageConstants.USER_DETAILS_NOT_FOUND);
			}
			if (!ObjectUtils.isEmpty(user.getCityId())) {
				City city = cityRepository.findByCityId(user.getCityId());
				State state = stateRepository.findByStateId(city.getStateId());
				Country country = countryRepository.findByCountryId(state.getCountryId());
				userDto.setCityName(city.getCityName());
				userDto.setState(state.getId());
				userDto.setStateName(state.getStateName());
				userDto.setCountry(country.getId());
				userDto.setCountryName(country.getCountryName());
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
				CommonUtil.populateMessage(MessageConstants.DETAILS_FETCHED_SUCCESS), userDto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tvse.uam.service.UserService#deleteUser(java.util.UUID)
	 */
	@Override
	public ResponseDTO<UserDTO> deleteUser(UUID id) {
		LOGGER.info("start User deleteUser() in ServiceImpl");
		try {
			User user = userRepository.findbyId(id);
			if (!Objects.isNull(user)) {
				LOGGER.debug("Start updateUser deleteUser() in ServiceImpl");
				userRoleRepository.deleteByUserId(user.getId());
				userRepository.delete(user);
				LOGGER.debug("Completed updateUser deleteUser() in ServiceImpl");
			} else {
				throw new BusinessException(MessageConstants.USER_DETAILS_NOT_FOUND);
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
				CommonUtil.populateMessage(MessageConstants.USER_DELETED), null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tvse.uam.service.UserService#updateUserStatus(java.util.UUID)
	 */
	@Override
	public ResponseDTO<UserDTO> updateUserStatus(UUID userId) {
		try {
			LOGGER.info("UserServiceImpl updateUserStatus() Intiated :");
			User user = userRepository.findbyId(userId);
			List<UserRole> userRole = userRoleRepository.findByUserId(userId);
			if (ObjectUtils.isEmpty(user)) {
				throw new BusinessException(MessageConstants.USER_DETAILS_NOT_FOUND);
			} else {
				userRepository.updateUserStatus(userId, UUID.randomUUID(), false);
				if (CollectionUtils.isNotEmpty(userRole)) {
					userRoleRepository.deleteByUserId(userId);
				}
				LOGGER.info("UserServiceImpl updateUserStatus() Terminated :");
			}
			return new ResponseDTO<>(false, ApplicationConstants.SUCCESS_CODE_200,
					CommonUtil.populateMessage(MessageConstants.USER_INACTIVATED_SUCCESSFULLY),
					UserAccessMapper.INSTANCE.userToUserDTO(user));
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tvse.uam.service.UserService#getUserProfile(java.util.UUID)
	 */
	@Override
	public ResponseDTO<UserProfileDTO> getUserProfile(UUID userId) {
		LOGGER.info("UserServiceImpl getUserProfile() Intiated :");
		User user = null;
		UserDTO userDto = null;
		UserProfileDTO userProfileDTO = new UserProfileDTO();
		List<UserRoleDTO> userRoleDTONewList = new ArrayList<>();
		try {
			LOGGER.debug("UserServiceImpl getUserProfile() Intiated :");
			user = userRepository.findbyId(userId);
			if (ObjectUtils.isEmpty(user)) {
				throw new BusinessException(MessageConstants.USER_DETAILS_NOT_FOUND);
			} else {
				userDto = UserMapper.INSTANCE.userToUserDTO(user);
				if (!ObjectUtils.isEmpty(user.getCityId())) {
					City city = cityRepository.findByCityId(user.getCityId());
					State state = stateRepository.findByStateId(city.getStateId());
					Country country = countryRepository.findByCountryId(state.getCountryId());
					userDto.setCityName(city.getCityName());
					userDto.setState(state.getId());
					userDto.setStateName(state.getStateName());
					userDto.setCountry(country.getId());
					userDto.setCountryName(country.getCountryName());
				}
				if(StringUtils.isNotBlank(user.getProfileImageS3Path())) {
					byte[] profileImage = getImage(user.getProfileImageS3Path());
					userDto.setProfileImage(profileImage);
				}
				userProfileDTO.setUserDTO(userDto);
				getUserRoleForProfile(userId, userProfileDTO, userRoleDTONewList);
			}
			LOGGER.info("UserServiceImpl getUserProfile() Ends :");
			return new ResponseDTO<>(false, ApplicationConstants.SUCCESS_CODE_200,
					CommonUtil.populateMessage(MessageConstants.SUCCESS_GET), userProfileDTO);
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

	private void getUserRoleForProfile(UUID userId, UserProfileDTO userProfileDTO,
			List<UserRoleDTO> userRoleDTONewList) {
		List<UserRoleDTO> userRoleDTOList = userRoleRepository.getUserRoleByUserId(userId);
		if (CollectionUtils.isEmpty(userRoleDTOList)) {
			throw new BusinessException(MessageConstants.USERROLE_NOT_EXIST);
		} else {
			userRoleDTOList.forEach(userRole -> {
				UserRoleDTO userRoleDTO = new UserRoleDTO();
				userRoleDTO.setRoleId(userRole.getRoleId());
				Role role = roleRepository.getOne(userRole.getRoleId());
				if (!ObjectUtils.isEmpty(role)) {
					userRoleDTO.setRoleName(role.getRoleName());
				}
				List<ApplicationDTO> applicationList = applicationRepository
						.getApplicationsByRoleId(userRole.getRoleId());
				userRoleDTO.setApplicationList(
						ApplicationMapper.INSTANCE.applicationDTOListToApplicationList(applicationList));
				List<Object> userRoleList = userRoleRepository.getUserRole(userId.toString(),
						userRole.getRoleId().toString());
				List<String> brandIds = new ArrayList<>();
				getBrandForUserRole(userRoleDTONewList, userRoleDTO, userRoleList, brandIds);
			});
			userProfileDTO.setUserRoleDTOList(userRoleDTONewList);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tvse.uam.service.UserService#changePassword(com.tvse.uam.dto.UserDTO)
	 */
	@Override
	public ResponseDTO<String> changePassword(UserDTO userDTO) {
		LOGGER.info("UserServiceImpl changePassword() Intiated :");
		try {
			LOGGER.debug("UserServiceImpl changePassword() Intiated :");
			User user = userRepository.findbyId(userDTO.getId());
			if (ObjectUtils.isEmpty(user)) {
				throw new BusinessException(MessageConstants.USER_DETAILS_NOT_FOUND);
			}
			currentPasswordCheck(userDTO); // current password check
			if (!StringUtils.equals(userDTO.getNewPassword(), userDTO.getConfirmNewPassword())) {
				throw new BusinessException(MessageConstants.NEW_AND_CONFIRM_PASSWRD_CHECK);
			}
			user.setUserPassword(userDTO.getNewPassword()); // new password set
			userRepository.save(user);
			LOGGER.debug("UserServiceImpl changePassword() Ends :");
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
				CommonUtil.populateMessage(MessageConstants.PASSWRD_CHANGE), null);
	}

	// current password check in userProfile
	private void currentPasswordCheck(UserDTO userDTO) {
		User user = userRepository.findbyId(userDTO.getId());
		if (!StringUtils.equals(user.getUserPassword(), userDTO.getUserPassword())) {
			throw new BusinessException(MessageConstants.OLDPASSWRD_CHECK);
		}
	}

	private Page<User> prepareUserList(Pageable pageableRequest) {
		return userRepository.getAllUsers(pageableRequest);
	}

	private String constructFullName(String firstName, String middleName, String lastName) {
		String fullname = "";
		fullname = firstName;
		if (StringUtils.isNotBlank(middleName)) {
			fullname = fullname + " " + middleName;
		}
		if (StringUtils.isNotBlank(lastName)) {
			fullname = fullname + " " + lastName;
		}
		return fullname;
	}

	private void validateUser(UserDTO userDTO) {
		LOGGER.info("Validation User validateUser() in ServiceImpl");
		if (ObjectUtils.isEmpty(userDTO)) {
			throw new BusinessException(MessageConstants.INPUT_SHOULD_BE_GIVEN);
		}
		if (StringUtils.isBlank(userDTO.getUserName())) {
			throw new BusinessException(MessageConstants.USER_NAME_IS_REQUIRED);
		}
		int count = userDTO.getUserName().split(ApplicationConstants.EMPTY_SPACE).length;
		if (count >= 2) {
			throw new BusinessException(MessageConstants.USERNAME_IS_INVALID);
		}
		if (StringUtils.isBlank(userDTO.getEmployeeId())) {
			throw new BusinessException(MessageConstants.EMPLOYEE_ID_REQUIRED);
		}
		if (ObjectUtils.isEmpty(userDTO.getIsActive())) {
			throw new BusinessException(MessageConstants.ACTIVE_STATUS_REQUIRED);
		}
	}

	/**
	 * Validate update user
	 * 
	 * @param userDTO
	 */
	private void validateUpdateUser(UserDTO userDTO) {
		if (ObjectUtils.isEmpty(userDTO)) {
			throw new BusinessException(MessageConstants.INPUT_SHOULD_BE_GIVEN);
		}
		if (!StringUtils.isBlank(userDTO.getUserName())) {
			throw new BusinessException(MessageConstants.USERNAME_CAN_NOT_EDIT);
		}

		if (!StringUtils.isBlank(userDTO.getEmployeeId())) {
			throw new BusinessException(MessageConstants.EMPLOYEE_ID_CANNOT_EDIT);
		}
	}

	private void getBrandForUserRole(List<UserRoleDTO> userRoleDTONewList, UserRoleDTO userRoleDTO,
			List<Object> userRoleList, List<String> brandIds) {
		if (CollectionUtils.isNotEmpty(userRoleList)) {
			for (Object userRoleObject : userRoleList) {
				if (userRoleObject instanceof Object[]) {
					Object[] userRoleObj = (Object[]) userRoleObject;
					if (userRoleObj[2] != null) {
						brandIds = Arrays.asList((userRoleObj[2].toString()).split("\\s*,\\s*"));
					}
				}
			}
			List<UUID> brandIdUUIDs = new ArrayList<>();
			if (CollectionUtils.isNotEmpty(brandIds)) {
				brandIds.forEach(id -> brandIdUUIDs.add(UUID.fromString(id)));
				if (CollectionUtils.isNotEmpty(brandIdUUIDs)) {
					userRoleDTO.setBrandList(brandRepository.getBrands(brandIdUUIDs));
				}
			}
		}
		userRoleDTONewList.add(userRoleDTO);
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException 
	 */
	private byte[] getImage(String fileName) throws IOException {
		AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
		ByteArrayOutputStream baos = null;
		InputStream is = null;
		try (S3Object s3Object = s3Client.getObject(s3Bucket, fileName)) {
			is = s3Object.getObjectContent();
			baos = new ByteArrayOutputStream();
			int len;
			byte[] buffer = new byte[4096];
			while ((len = is.read(buffer, 0, buffer.length)) != -1) {
				baos.write(buffer, 0, len);
			}
		} catch (Exception e) {
			LOGGER.error(CommonUtil.errorFormat(e));
			throw new ApplicationException();
		} finally {
			if (is != null)
				is.close();
		}
		return baos.toByteArray();
	}
	
}
