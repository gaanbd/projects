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
 * Country domain Class
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Entity
@Table(name = "Country", schema = "tvse")
public class Country {

	@Id
	@Type(type = "uuid-char")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "id")
	private UUID id;

	@Column(name = "countryName")
	private String countryName;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

}
