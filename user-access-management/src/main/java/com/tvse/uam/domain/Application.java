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
 * Application domain Class
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Entity
@Table(name = "Application", schema = "tvse")
public class Application {

	@Id
	@Type(type = "uuid-char")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "id")
	private UUID id;

	@Column(name = "applicationName")
	private String applicationName;

	@Column(name = "applicationDescription")
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
		return "Application [id=" + id + ", applicationName=" + applicationName + ", applicationDescription="
				+ applicationDescription + "]";
	}

}