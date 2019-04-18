package com.tvse.uam.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * City domain Class
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Entity
@Table(name = "City", schema = "tvse")
public class City {

	@Id
	@Column(name = "id")
	@Type(type = "uuid-char")
	private UUID id;

	@Column(name = "cityName")
	private String cityName;

	@Column(name = "stateId")
	@Type(type = "uuid-char")
	private UUID stateId;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public UUID getStateId() {
		return stateId;
	}

	public void setStateId(UUID stateId) {
		this.stateId = stateId;
	}

}
