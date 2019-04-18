package com.tvse.uam.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * State domain Class
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Entity
@Table(name = "State", schema = "tvse")
public class State {

	@Id
	@Column(name = "id")
	@Type(type = "uuid-char")
	private UUID id;

	@Column(name = "stateName")
	private String stateName;

	@Column(name = "countryId")
	@Type(type = "uuid-char")
	private UUID countryId;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public UUID getCountryId() {
		return countryId;
	}

	public void setCountryId(UUID countryId) {
		this.countryId = countryId;
	}

}
