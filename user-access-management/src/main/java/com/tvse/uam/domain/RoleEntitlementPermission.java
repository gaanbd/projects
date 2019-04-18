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
 * RoleEntitlementPermission domain Class
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Entity
@Table(name = "RoleEntitlementPermission", schema = "tvse")
public class RoleEntitlementPermission extends AbstractAudit {

	private static final long serialVersionUID = -2350165435462741081L;

	@Id
	@Column(name = "id")
	@Type(type = "uuid-char")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private UUID id;

	@Type(type = "uuid-char")
	@Column(name = "roleId")
	private UUID roleId;

	@Type(type = "uuid-char")
	@Column(name = "entitlementId")
	private UUID entitlementId;

	@Column(name = "operation")
	private String operation;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getRoleId() {
		return roleId;
	}

	public void setRoleId(UUID roleId) {
		this.roleId = roleId;
	}

	public UUID getEntitlementId() {
		return entitlementId;
	}

	public void setEntitlementId(UUID entitlementId) {
		this.entitlementId = entitlementId;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	@Override
	public String toString() {
		return "RoleEntitlementPermission [id=" + id + ", roleId=" + roleId + ", entitlementId=" + entitlementId
				+ ", operation=" + operation + "]";
	}

}