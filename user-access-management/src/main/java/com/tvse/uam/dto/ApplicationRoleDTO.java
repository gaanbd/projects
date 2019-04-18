package com.tvse.uam.dto;

import java.util.List;
import java.util.UUID;

/**
 * ApplicationRoleDTO Class
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
public class ApplicationRoleDTO {

	private UUID roleId;

	private String roleName;

	private String roleDescription;

	private List<String> applicationNames;

	private String roleDisplayName;

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

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public List<String> getApplicationNames() {
		return applicationNames;
	}

	public void setApplicationNames(List<String> applicationNames) {
		this.applicationNames = applicationNames;
	}

	public String getRoleDisplayName() {
		return roleDisplayName;
	}

	public void setRoleDisplayName(String roleDisplayName) {
		this.roleDisplayName = roleDisplayName;
	}

	@Override
	public String toString() {
		return "ApplicationRoleDTO [roleId=" + roleId + ", roleName=" + roleName + ", roleDescription="
				+ roleDescription + ", applicationNames=" + applicationNames + ", roleDisplayName=" + roleDisplayName
				+ "]";
	}

}
