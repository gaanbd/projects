package com.tvse.uam.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tvse.common.dto.ResponseDTO;
import com.tvse.uam.domain.City;
import com.tvse.uam.domain.Country;
import com.tvse.uam.domain.State;
import com.tvse.uam.dto.UserDTO;
import com.tvse.uam.dto.UserProfileDTO;

public interface UserService {

	ResponseDTO<Page<UserDTO>> getAllUserProfile(Pageable pageable, String searchParam);

	ResponseDTO<List<Country>> getAllCountry();

	ResponseDTO<List<State>> getAllStateByCountry(UUID countryId);

	ResponseDTO<List<City>> getAllCityByState(UUID stateId);

	ResponseDTO<String> loginNameCheck(String loginName);

	ResponseDTO<UserDTO> createUser(UserDTO userDTO);

	ResponseDTO<UserDTO> updateUser(UserDTO userDTO);

	ResponseDTO<UserDTO> deleteUser(UUID userId);

	ResponseDTO<UserDTO> getByUserId(UUID id);

	ResponseDTO<String> employeeIdUniqueCheck(String employeeId);

	ResponseDTO<UserDTO> updateUserStatus(UUID userId);

	ResponseDTO<UserProfileDTO> getUserProfile(UUID id);

	ResponseDTO<String> changePassword(UserDTO userDTO);
	
}
