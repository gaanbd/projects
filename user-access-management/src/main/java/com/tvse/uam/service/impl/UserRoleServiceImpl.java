package com.tvse.uam.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tvse.common.dto.ResponseDTO;
import com.tvse.common.exception.ApplicationException;
import com.tvse.common.exception.BusinessException;
import com.tvse.common.exception.DataRepositoryException;
import com.tvse.uam.constants.ApplicationConstants;
import com.tvse.uam.constants.MessageConstants;
import com.tvse.uam.domain.Application;
import com.tvse.uam.domain.Branch;
import com.tvse.uam.domain.Brand;
import com.tvse.uam.domain.User;
import com.tvse.uam.domain.UserRole;
import com.tvse.uam.domain.Zone;
import com.tvse.uam.dto.ApplicationDTO;
import com.tvse.uam.dto.BrandDTO;
import com.tvse.uam.dto.RoleDTO;
import com.tvse.uam.dto.UserRoleDTO;
import com.tvse.uam.mail.MailManagerUtil;
import com.tvse.uam.mapper.ApplicationMapper;
import com.tvse.uam.mapper.UserAccessMapper;
import com.tvse.uam.repository.ApplicationRepository;
import com.tvse.uam.repository.BranchRepository;
import com.tvse.uam.repository.BrandRepository;
import com.tvse.uam.repository.RoleRepository;
import com.tvse.uam.repository.UserRepository;
import com.tvse.uam.repository.UserRoleRepository;
import com.tvse.uam.repository.ZoneRepository;
import com.tvse.uam.service.UserRoleService;
import com.tvse.uam.util.CommonUtil;

/**
 * UserRoleServiceImpl class to implements UserAccessService interface methods
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Service(value = "userRoleService")
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

	private static final Logger LOGGER = LogManager.getLogger(UserRoleServiceImpl.class.getName());

	private static final String USER_CREATE_MAIL = "CreateUser";
	private static final String ADD = "add";
	private static final String UTF8 = "utf-8";

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private UserRoleRepository userRoleRepository;
	private BrandRepository brandRepository;
	private ApplicationRepository applicationRepository;
	private MailManagerUtil mailManagerUtil;
	private ZoneRepository zoneRepository;
	private BranchRepository branchRepository;

	@Inject
	public UserRoleServiceImpl(@NotNull UserRepository userRepository, @NotNull RoleRepository roleRepository,
			@NotNull UserRoleRepository userRoleRepository, @NotNull BrandRepository brandRepository,
			@NotNull ApplicationRepository applicationRepository, @NotNull MailManagerUtil mailManagerUtil,
			@NotNull ZoneRepository zoneRepository, @NotNull BranchRepository branchRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.userRoleRepository = userRoleRepository;
		this.brandRepository = brandRepository;
		this.applicationRepository = applicationRepository;
		this.mailManagerUtil = mailManagerUtil;
		this.zoneRepository = zoneRepository;
		this.branchRepository = branchRepository;
	}

	/*
	 * To list all the Brands
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.tvse.uam.service.UserAccessService#getAllBrands()
	 */
	@Override
	public ResponseDTO<List<BrandDTO>> getAllBrands() {
		try {
			LOGGER.info("UserRoleServiceImpl getAllBrands() Intiated :");
			List<Brand> brandList = brandRepository.findAllBrandDetails();
			if (CollectionUtils.isEmpty(brandList)) {
				throw new BusinessException(MessageConstants.BRAND_NOT_AVAILABLE);
			}
			LOGGER.info("UserRoleServiceImpl getAllBrands() Ends :");
			return new ResponseDTO<>(false, ApplicationConstants.SUCCESS_CODE_200,
					CommonUtil.populateMessage(MessageConstants.SUCCESS_GET),
					UserAccessMapper.INSTANCE.brandListToBrandDTOList(brandList));
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
	 * To list all the Applications
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.tvse.uam.service.UserAccessService#getAllApplications()
	 */
	@Override
	public ResponseDTO<List<ApplicationDTO>> getAllApplications() {
		try {
			LOGGER.info("UserRoleServiceImpl getAllApplications() Intiated :");
			List<Application> applicationList = applicationRepository.findAll();
			if (CollectionUtils.isEmpty(applicationList)) {
				throw new BusinessException(MessageConstants.APPLICATION_NOT_AVAILABLE);
			}
			LOGGER.info("UserRoleServiceImpl getAllApplications() Ends :");
			return new ResponseDTO<>(false, ApplicationConstants.SUCCESS_CODE_200,
					CommonUtil.populateMessage(MessageConstants.SUCCESS_GET),
					UserAccessMapper.INSTANCE.applicationListToApplicationDTOList(applicationList));
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
	 * To list all the Roles for the application
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tvse.uam.service.UserAccessService#getRoleByApplicationId(java.util.List)
	 */
	@Override
	public ResponseDTO<List<RoleDTO>> getRolesByApplicationId(List<UUID> applicationId) {
		try {
			LOGGER.info("UserRoleServiceImpl getRolesByApplicationId() Intiated :");
			List<RoleDTO> roleDTOList;
			List<RoleDTO> resultRoleDTOList = new ArrayList<>();
			if (CollectionUtils.isNotEmpty(applicationId)) {
				if (applicationId.size() == 1) {
					roleDTOList = roleRepository.findRoleByApplicationId(applicationId.get(0));
					if (CollectionUtils.isEmpty(roleDTOList)) {
						throw new BusinessException(MessageConstants.ROLE_NOT_AVAIL_FOR_APPLICATION);
					}else {
						resultRoleDTOList=roleDTOList;
					}
				} else {
					roleDTOList = roleRepository.findRoleByApplicationId(applicationId);
					checkForMultipleApp(roleDTOList, resultRoleDTOList);
					if (CollectionUtils.isEmpty(resultRoleDTOList)) {
						throw new BusinessException(MessageConstants.ROLE_NOT_AVAIL_FOR_APPLICATION);
					}
				}				
			}			
			LOGGER.info("UserRoleServiceImpl getRolesByApplicationId() Ends :");
			return new ResponseDTO<>(false, ApplicationConstants.SUCCESS_CODE_200,
					CommonUtil.populateMessage(MessageConstants.SUCCESS_GET), resultRoleDTOList);

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

	private void checkForMultipleApp(List<RoleDTO> roleDTOList, List<RoleDTO> resultRoleDTOList) {
		if (CollectionUtils.isEmpty(roleDTOList)) {
			throw new BusinessException(MessageConstants.ROLE_NOT_AVAIL_FOR_APPLICATION);
		} else {
			setResultDTOValue(roleDTOList, resultRoleDTOList);
		}
	}

	private void setResultDTOValue(List<RoleDTO> roleDTOList, List<RoleDTO> resultRoleDTOList) {
		for (int i = 0; i < roleDTOList.size(); i++) {
			for (int j = i + 1; j < roleDTOList.size(); j++) {
				if (roleDTOList.get(i).getId().equals(roleDTOList.get(j).getId())) {
					resultRoleDTOList.add(roleDTOList.get(i));
				}
			}
		}
	}

	/*
	 * To create the user role mapping
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.tvse.uam.service.UserAccessService#createUserRole(com.tvse.uam.dto.
	 * UserRoleDTO)
	 */
	@Override
	public ResponseDTO<UserRoleDTO> createUserRole(List<UserRoleDTO> userRoleDTOList) {
		try {
			LOGGER.info("UserRoleServiceImpl createUserRole() Intiated :");
			if (CollectionUtils.isNotEmpty(userRoleDTOList)) {
				userRoleDTOList.forEach(userRoleData -> {
					if (userRoleData.getRoleId() == null
							&& !StringUtils.equalsIgnoreCase(userRoleData.getAction(), ApplicationConstants.DELETE)) {
						throw new BusinessException(MessageConstants.ROLE_NOT_MAPPED_FOR_USER);
					}
					validateApplicationList(userRoleData);
				});
				sendMailToUser(userRoleDTOList.get(0));
			} else {
				throw new BusinessException(MessageConstants.ROLE_NOT_MAPPED_FOR_USER);
			}
			LOGGER.info("UserRoleServiceImpl createUserRole() Ends");
			return new ResponseDTO<>(false, ApplicationConstants.SUCCESS_CODE_201, MessageConstants.USER_ROLE_CREATED,
					null);
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
	 * To get the user role mapping by user id
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.tvse.uam.service.UserRoleService#getUserRoleByUserId(java.util.UUID)
	 */
	@Override
	public ResponseDTO<List<UserRoleDTO>> getUserRoleByUserId(UUID userId) {
		try {
			LOGGER.info("UserRoleServiceImpl getUserRoleByUserId() Intiated :");
			List<UserRoleDTO> userRoleDTONewList = new ArrayList<>();
			List<UserRoleDTO> userRoleDTOList = userRoleRepository.getUserRoleByUserId(userId);
			if (CollectionUtils.isEmpty(userRoleDTOList)) {
				throw new BusinessException(MessageConstants.USERROLE_NOT_EXIST);
			} else {
				userRoleDTOList.forEach(userRole -> {
					UserRoleDTO userRoleDTO = new UserRoleDTO();
					userRoleDTO.setRoleId(userRole.getRoleId());
					userRoleDTO.setUserId(userId);
					List<ApplicationDTO> applicationList = applicationRepository
							.getApplicationsByRoleId(userRole.getRoleId());
					userRoleDTO.setApplicationList(
							ApplicationMapper.INSTANCE.applicationDTOListToApplicationList(applicationList));
					List<Object> userRoleList = userRoleRepository.getUserRole(userId.toString(),
							userRole.getRoleId().toString());
					List<String> brandIds = new ArrayList<>();
					getBrandForUserRole(userRoleDTONewList, userRoleDTO, userRoleList, brandIds);
				});
			}
			LOGGER.info("UserRoleServiceImpl getUserRoleByUserId() Ends :");
			return new ResponseDTO<>(false, ApplicationConstants.SUCCESS_CODE_200,
					CommonUtil.populateMessage(MessageConstants.SUCCESS_GET), userRoleDTONewList);
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
	 * To delete the user role mapping by user id
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.tvse.uam.service.UserRoleService#deleteUserRole(java.util.UUID)
	 */
	@Override
	public ResponseDTO<HttpStatus> deleteUserRole(UUID userId) {
		LOGGER.info("UserRoleServiceImpl deleteUserRole() Intiated :");
		userRoleRepository.deleteByUserId(userId);
		LOGGER.info("UserRoleServiceImpl deleteUserRole() Ends :");
		return new ResponseDTO<>(false, ApplicationConstants.SUCCESS_CODE_200,
				CommonUtil.populateMessage(MessageConstants.SUCCESS_GET), HttpStatus.OK);
	}
	
	@Override
	public ResponseDTO<List<Zone>> getAllZones() {
		try {
			LOGGER.info("UserRoleServiceImpl getAllZones() Intiated :");
			List<Zone> zoneList = zoneRepository.findAll();
			if (CollectionUtils.isEmpty(zoneList)) {
				throw new BusinessException(MessageConstants.ZONE_NOT_AVAILABLE);
			}
			LOGGER.info("UserRoleServiceImpl getAllZones() Ends :");
			return new ResponseDTO<>(false, ApplicationConstants.SUCCESS_CODE_200,
					CommonUtil.populateMessage(MessageConstants.SUCCESS_GET),zoneList);
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
	
	@Override
	public ResponseDTO<List<Branch>> getBranchesByZoneId(UUID zoneId) {
		try {
			LOGGER.info("UserRoleServiceImpl getBranchesByZoneId() Intiated :");
			List<Branch> branchList = branchRepository.findByZoneId(zoneId);
			if (CollectionUtils.isEmpty(branchList)) {
				throw new BusinessException(MessageConstants.BRAND_NOT_AVAILABLE);
			}
			LOGGER.info("UserRoleServiceImpl getBranchesByZoneId() Ends :");
			return new ResponseDTO<>(false, ApplicationConstants.SUCCESS_CODE_200,
					CommonUtil.populateMessage(MessageConstants.SUCCESS_GET),branchList);
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

	private void sendMailToUser(UserRoleDTO userRoleData) throws UnsupportedEncodingException {
		try {
			if (StringUtils.equalsIgnoreCase(userRoleData.getAction(), ADD)) {
				User user = userRepository.findbyId(userRoleData.getUserId());
				if (user.getIsActive() && StringUtils.isBlank(user.getUserPassword())) {
					String password = RandomStringUtils.randomAlphanumeric(6);
					String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes(UTF8));
					user.setUserPassword(encodedPassword);
					user.setIsActive(true);
					userRepository.save(user);
					mailManagerUtil.sendUserCreationNotificationMail(user.getEmail(), password, USER_CREATE_MAIL,
							user.getUserName());
				} else {
					user.setIsActive(true);
					userRepository.save(user);
				}
			}
		} catch (UnsupportedEncodingException use) {
			LOGGER.error(CommonUtil.errorFormat(use));
			throw new ApplicationException();
		}
	}

	private void validateApplicationList(UserRoleDTO userRoleData) {
		if (CollectionUtils.isNotEmpty(userRoleData.getApplicationList())) {
			saveApplicationAndBrand(userRoleData, userRoleData.getApplicationList().size());
		} else {// It calls when role deleted for the user
			if (StringUtils.isNotBlank(userRoleData.getOldRole())) {
				userRoleRepository.deleteByRoleId(UUID.fromString(userRoleData.getOldRole()));
			}
		}
	}

	private void saveApplicationAndBrand(UserRoleDTO userRoleData, int applicationCount) {
		if (applicationCount == 1) { // Either B2B or B2C application
			userRoleData.getApplicationList().forEach(application -> setUserRoleValues(userRoleData.getUserId(),
					userRoleData, getApplicationName(application)));
		} else { // Both B2B and B2C application selected
			Long userRoleCount = checkUserRoleExistForApp(userRoleData.getRoleId(), userRoleData.getUserId(),
					userRoleData.getId());
			if (userRoleCount > 0) {
				throw new BusinessException(MessageConstants.USERROLE_EXIST);
			}
			// B2B and B2C Validation
			checkBrandDetails(userRoleData.getUserId(), userRoleData);
		}
	}

	private void setUserRoleValues(UUID userId, UserRoleDTO userRoleData, String applicationName) {
		LOGGER.info("UserRoleServiceImpl setUserRoleValues() Intiated");
		if (StringUtils.isNotEmpty(applicationName)
				&& StringUtils.equalsIgnoreCase(applicationName, ApplicationConstants.B2B_APPLICATION)) {
			// ONLY B2B Validation
			if (StringUtils.equalsIgnoreCase(userRoleData.getAction(), ApplicationConstants.EDIT)
					|| (StringUtils.equalsIgnoreCase(userRoleData.getAction(), ApplicationConstants.DELETE))
					|| (StringUtils.isNotBlank(userRoleData.getOldRole()) && (!StringUtils
							.equalsIgnoreCase(userRoleData.getOldRole(), userRoleData.getRoleId().toString())))) {
				userRoleRepository.deleteByRoleId(UUID.fromString(userRoleData.getOldRole()));
			}
			checkBrandDetails(userId, userRoleData);
		} else {// ONLY B2C Validation
			saveB2CApplication(userRoleData);
		}
		LOGGER.info("UserRoleServiceImpl setUserRoleValues() Ends");
	}

	private void saveB2CApplication(UserRoleDTO userRoleData) {
		if (StringUtils.equalsIgnoreCase(userRoleData.getAction(), ApplicationConstants.EDIT)
				|| (StringUtils.equalsIgnoreCase(userRoleData.getAction(), ApplicationConstants.DELETE))
				|| StringUtils.isNotBlank(userRoleData.getOldRole()) && !StringUtils
						.equalsIgnoreCase(userRoleData.getOldRole(), userRoleData.getRoleId().toString())) {
			userRoleRepository.deleteByRoleId(UUID.fromString(userRoleData.getOldRole()));
		} else {
			// B2C Application Save
			Long userRoleCount = checkUserRoleExistForApp(userRoleData.getRoleId(), userRoleData.getUserId(),
					userRoleData.getId());
			if (userRoleCount > 0) {
				throw new BusinessException(MessageConstants.USERROLE_EXIST);
			}
			userRoleRepository.save(UserAccessMapper.INSTANCE.userRoleDTOtoUserRole(userRoleData));
		}
	}

	private void checkBrandDetails(UUID userId, UserRoleDTO userRoleData) {
		LOGGER.info("UserRoleServiceImpl checkBrandDetails() Intiated ");
		if (CollectionUtils.isNotEmpty(userRoleData.getBrandList())) {
			List<UUID> oldBrandList = new ArrayList<>();
			List<UUID> newBrandList = new ArrayList<>();
			if (StringUtils.equalsIgnoreCase(userRoleData.getAction(), ApplicationConstants.EDIT)
					&& CollectionUtils.isNotEmpty(userRoleData.getOldBrand())) {
				userRoleData.getOldBrand().forEach(d -> oldBrandList.add(d.getId()));
				userRoleData.getBrandList().forEach(d -> newBrandList.add(d.getId()));
				deleteUserRoleByBrandId(oldBrandList, newBrandList);
			}
			userRoleData.getBrandList().forEach(brand -> {
				userRoleData.setBrandId(brand.getId());
				// B2B Edit
				saveOrUpdateBrand(userId, userRoleData, brand);
			});
		} else {
			throw new BusinessException(MessageConstants.ATLEAST_ONE_BRAND_MANDATORY);
		}
		LOGGER.info("UserRoleServiceImpl checkBrandDetails() Ends ");
	}

	private void saveOrUpdateBrand(UUID userId, UserRoleDTO userRoleData, Brand brand) {
		if (StringUtils.equalsIgnoreCase(userRoleData.getAction(), ApplicationConstants.EDIT)) {
			UserRole userrole = userRoleRepository.checkBrandExists(userId, userRoleData.getRoleId(), brand.getId());
			if (userrole == null) {
				userRoleRepository.save(UserAccessMapper.INSTANCE.userRoleDTOtoUserRole(userRoleData));
			}
		} else {
			// B2B Save
			if (!StringUtils.equalsIgnoreCase(userRoleData.getAction(), ApplicationConstants.DELETE)) {
				Long userRoleCount = checkUserRoleExist(userRoleData.getRoleId(), brand.getId(),
						userRoleData.getUserId(), userRoleData.getId());
				if (userRoleCount > 0) {
					throw new BusinessException(MessageConstants.USERROLE_EXIST);
				}
				userRoleRepository.save(UserAccessMapper.INSTANCE.userRoleDTOtoUserRole(userRoleData));
			}
		}
	}

	private void deleteUserRoleByBrandId(List<UUID> oldBrandList, List<UUID> newBrandList) {
		if (oldBrandList.size() == 1 && newBrandList.size() == 1
				&& !StringUtils.equals(oldBrandList.get(0).toString(), newBrandList.get(0).toString())) {
			userRoleRepository.deleteUserRoleByBrandId(oldBrandList);
		}
		oldBrandList.removeAll(newBrandList);
		if (CollectionUtils.isNotEmpty(oldBrandList)) {
			userRoleRepository.deleteUserRoleByBrandId(oldBrandList);
		}
	}

	private String getApplicationName(Application application) {
		LOGGER.info("UserRoleServiceImpl getApplicationName() Intiated ");
		String applicationName = null;
		Optional<Application> app = applicationRepository.findById(application.getId());
		if (app.isPresent()) {
			applicationName = app.get().getApplicationName();
		} else {
			throw new BusinessException(MessageConstants.APPLICATION_NOT_AVAILABLE);
		}
		LOGGER.info("UserRoleServiceImpl getApplicationName() Ends ");
		return applicationName;
	}

	private Long checkUserRoleExistForApp(UUID roleId, UUID userId, UUID id) {
		LOGGER.info("UserRoleServiceImpl checkUserRoleExistForApp() Intiated ");
		Long userRoleCount;
		if (id != null) {
			userRoleCount = userRoleRepository.checkAppUserRoleDetailsById(userId, roleId, id);
		} else {
			userRoleCount = userRoleRepository.checkAppUserRoleDetails(userId, roleId);
		}
		LOGGER.info("UserRoleServiceImpl checkUserRoleExistForApp() Ends ");
		return userRoleCount;
	}

	private Long checkUserRoleExist(UUID roleId, UUID brandId, UUID userId, UUID id) {
		LOGGER.info("UserRoleServiceImpl checkUserRoleExist() Intiated ");
		Long userRoleCount;
		if (id != null) {
			userRoleCount = userRoleRepository.checkUserRoleDetailsById(userId, roleId, brandId, id);
		} else {
			userRoleCount = userRoleRepository.checkUserRoleDetails(userId, roleId, brandId);
		}
		LOGGER.info("UserRoleServiceImpl checkUserRoleExist() Ends ");
		return userRoleCount;
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

}