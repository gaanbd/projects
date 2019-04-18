package com.tvse.uam.dto;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tvse.uam.domain.Application;
import com.tvse.uam.domain.Brand;
import com.tvse.uam.domain.Role;

/**
 * UserProfileDTO, DTO Class of UserProfile
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class UserProfileDTO {

	@Type(type = "uuid-char")
	private UUID id;

	private List<Application> applicationList;

	private Boolean activeFlag;

	private List<Brand> brandList;

	private List<Role> roleList;

	private UserDTO userDTO;

	@Type(type = "uuid-char")
	private UUID roleId;

	private String roleName;
	
	private List<UserRoleDTO> userRoleDTOList;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public List<Application> getApplicationList() {
		return applicationList;
	}

	public void setApplicationList(List<Application> applicationList) {
		this.applicationList = applicationList;
	}

	public Boolean getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	public List<Brand> getBrandList() {
		return brandList;
	}

	public void setBrandList(List<Brand> brandList) {
		this.brandList = brandList;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public UUID getRoleId() {
		return roleId;
	}

	public void setRoleId(UUID roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<UserRoleDTO> getUserRoleDTOList() {
		return userRoleDTOList;
	}

	public void setUserRoleDTOList(List<UserRoleDTO> userRoleDTOList) {
		this.userRoleDTOList = userRoleDTOList;
	}

	
}

