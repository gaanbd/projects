package com.tvse.uam.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;

import com.tvse.common.dto.ResponseDTO;
import com.tvse.uam.domain.Branch;
import com.tvse.uam.domain.Zone;
import com.tvse.uam.dto.ApplicationDTO;
import com.tvse.uam.dto.BrandDTO;
import com.tvse.uam.dto.RoleDTO;
import com.tvse.uam.dto.UserRoleDTO;

/**
 * UserRoleService interface to declare UserRole methods
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
public interface UserRoleService {

	ResponseDTO<List<BrandDTO>> getAllBrands();

	ResponseDTO<List<ApplicationDTO>> getAllApplications();

	ResponseDTO<List<RoleDTO>> getRolesByApplicationId(List<UUID> id);

	ResponseDTO<UserRoleDTO> createUserRole(List<UserRoleDTO> userRoleDTOList);

	ResponseDTO<List<UserRoleDTO>> getUserRoleByUserId(UUID userId);

	ResponseDTO<HttpStatus> deleteUserRole(UUID userId);

	ResponseDTO<List<Zone>> getAllZones();

	ResponseDTO<List<Branch>> getBranchesByZoneId(UUID zoneId);

}
