package com.tvse.uam.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**
 * Role domain Class
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Entity
@Table(name = "Role", schema = "tvse")
public class Role extends AbstractAudit {

	private static final long serialVersionUID = 1614969459207050572L;

	@Id
	@Column(name = "id")
	@Type(type = "uuid-char")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private UUID id;

	@Column(name = "roleName")
	private String roleName;

	@Column(name = "roleDescription")
	private String roleDescription;

	@Column(name = "roleDisplayName")
	private String roleDisplayName;

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
		return "Role [id=" + id + ", roleName=" + roleName + ", roleDescription=" + roleDescription
				+ ", roleDisplayName=" + roleDisplayName + "]";
	}

}