package com.tvse.uam.dto;

import java.util.UUID;

import com.tvse.uam.domain.AbstractAudit;

/**
 * RoleDTO, DTO Class of Role
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
public class RoleDTO extends AbstractAudit {

	private static final long serialVersionUID = 5207899738885080150L;

	private UUID id;

	private String roleName;

	private String roleDescription;

	private String roleDisplayName;

	public RoleDTO() {
	}

	public RoleDTO(String roleName, UUID id, String roleDescription, String roleDisplayName) {
		super();

		this.roleName = roleName;
		this.id = id;
		this.roleDescription = roleDescription;
		this.roleDisplayName = roleDisplayName;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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

	public String getRoleDisplayName() {
		return roleDisplayName;
	}

	public void setRoleDisplayName(String roleDisplayName) {
		this.roleDisplayName = roleDisplayName;
	}

	@Override
	public String toString() {
		return "RoleDTO [id=" + id + ", roleName=" + roleName + ", roleDescription=" + roleDescription
				+ ", roleDisplayName=" + roleDisplayName + "]";
	}

}
