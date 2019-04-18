package com.tvse.uam.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import com.tvse.common.dto.ResponseDTO;
import com.tvse.uam.dto.ApplicationEntitlementDTO;
import com.tvse.uam.dto.ApplicationRoleDTO;

/**
 * RoleService interface to declare UAM Role methods
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
public interface RoleService {

	ResponseDTO<List<ApplicationEntitlementDTO>> getAllEntitlements();

	ResponseDTO<Page<ApplicationRoleDTO>> getAllRoles(String searchParam,Pageable page);

	ResponseDTO<HttpStatus> validateRole(UUID applicationId, String role, String from);

	ResponseDTO<Map<String, UUID>> createRole(List<ApplicationEntitlementDTO> applicationEntitlementDTO,
			String roleName, String roleDescription, String roleDisplayName);

	ResponseDTO<HttpStatus> updateRole(List<ApplicationEntitlementDTO> applicationEntitlementDTO, String roleName,
			String roleDescription, UUID roleId, String roleDisplayName);

	ResponseDTO<HttpStatus> deleteRole(UUID roleId);

	ResponseDTO<Map<String, Object>> getRoleById(UUID roleId);

	ResponseDTO<Map<String, Boolean>> getUserRoleByRoleId(UUID roleId);
}
