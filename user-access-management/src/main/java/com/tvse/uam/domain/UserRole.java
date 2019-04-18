package com.tvse.uam.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**
 * UserRole domain Class
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Entity
@Table(name = "UserRole", schema = "tvse")
public class UserRole {

	@Id
	@Column(name = "id")
	@Type(type = "uuid-char")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private UUID id;

	@Type(type = "uuid-char")
	@Column(name = "userId")
	private UUID userId;

	@Type(type = "uuid-char")
	@Column(name = "roleId")
	private UUID roleId;

	@Type(type = "uuid-char")
	@Column(name = "brandId")
	private UUID brandId;

	@Transient
	private String brandIds;

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

	public String getBrandIds() {
		return brandIds;
	}

	public void setBrandIds(String brandIds) {
		this.brandIds = brandIds;
	}

}