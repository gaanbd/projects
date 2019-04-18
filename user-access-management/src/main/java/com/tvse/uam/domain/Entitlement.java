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
 * Entitlement domain Class
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Entity
@Table(name = "Entitlement", schema = "tvse")
public class Entitlement {

	@Id
	@Column(name = "id")
	@Type(type = "uuid-char")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private UUID id;

	@Column(name = "entitlementName")
	private String entitlementName;

	@Type(type = "uuid-char")
	@Column(name = "applicationId")
	private UUID applicationId;

	@Column(name = "apiUrl")
	private String apiUrl;

	@Column(name = "routerUrl")
	private String routerUrl;

	@Column(name = "entitlementDescription")
	private String description;

	@Column(name = "entitlementOperation")
	private String operation;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getEntitlementName() {
		return entitlementName;
	}

	public void setEntitlementName(String entitlementName) {
		this.entitlementName = entitlementName;
	}

	public UUID getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(UUID applicationId) {
		this.applicationId = applicationId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getApiUrl() {
		return apiUrl;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public String getRouterUrl() {
		return routerUrl;
	}

	public void setRouterUrl(String routerUrl) {
		this.routerUrl = routerUrl;
	}

	@Override
	public String toString() {
		return "Entitlement [id=" + id + ", entitlementName=" + entitlementName + ", applicationId=" + applicationId
				+ ", apiUrl=" + apiUrl + ", routerUrl=" + routerUrl + ", description=" + description + ", operation="
				+ operation + "]";
	}

}