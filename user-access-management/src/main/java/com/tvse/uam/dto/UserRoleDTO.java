package com.tvse.uam.dto;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tvse.uam.domain.Application;
import com.tvse.uam.domain.Brand;

/**
 * UserRoleDTO, DTO Class of UserRole
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class UserRoleDTO {

	public UserRoleDTO() {
	}
	
	public UserRoleDTO(UUID userId, UUID roleId) {
		super();
		this.userId = userId;
		this.roleId = roleId;
	}

	@Type(type = "uuid-char")
	private UUID id;

	@Type(type = "uuid-char")
	private UUID userId;

	@Type(type = "uuid-char")
	private UUID roleId;

	@Type(type = "uuid-char")
	private UUID brandId;

	private String applicationName;

	private String roleName;

	private String brandName;

	private List<Application> applicationList;

	private Boolean activeFlag;

	private List<Brand> brandList;

	@Type(type = "uuid-char")
	private UUID applicationId;

	private String action;
	
	private String oldRole;

	private List<Brand> oldBrand;
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public UUID getRoleId() {
		return roleId;
	}

	public void setRoleId(UUID roleId) {
		this.roleId = roleId;
	}

	public UUID getBrandId() {
		return brandId;
	}

	public void setBrandId(UUID brandId) {
		this.brandId = brandId;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public List<Application> getApplicationList() {
		return applicationList;
	}

	public void setApplicationList(List<Application> applicationList) {
		this.applicationList = applicationList;
	}

	public List<Brand> getBrandList() {
		return brandList;
	}

	public void setBrandList(List<Brand> brandList) {
		this.brandList = brandList;
	}

	public Boolean getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	public UUID getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(UUID applicationId) {
		this.applicationId = applicationId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getOldRole() {
		return oldRole;
	}

	public void setOldRole(String oldRole) {
		this.oldRole = oldRole;
	}

	public List<Brand> getOldBrand() {
		return oldBrand;
	}

	public void setOldBrand(List<Brand> oldBrand) {
		this.oldBrand = oldBrand;
	}
	
}
