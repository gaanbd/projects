package com.tvse.uam.dto;

import java.util.UUID;

import org.hibernate.annotations.Type;

/**
 * ApplicationConfig DTO Class
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
public class ApplicationDTO {

	public ApplicationDTO() {

	}

	public ApplicationDTO(UUID id, String applicationName, String applicationDescription) {
		super();
		this.id = id;
		this.applicationName = applicationName;
		this.applicationDescription = applicationDescription;
	}

	@Type(type = "uuid-char")
	private UUID id;

	private String applicationName;

	private String applicationDescription;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getApplicationDescription() {
		return applicationDescription;
	}

	public void setApplicationDescription(String applicationDescription) {
		this.applicationDescription = applicationDescription;
	}

	@Override
	public String toString() {
		return "ApplicationDTO [id=" + id + ", applicationName=" + applicationName + ", applicationDescription="
				+ applicationDescription + "]";
	}

}
