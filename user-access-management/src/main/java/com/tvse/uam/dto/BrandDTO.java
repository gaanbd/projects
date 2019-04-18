package com.tvse.uam.dto;

import java.util.UUID;

import org.hibernate.annotations.Type;

/**
 * BrandDTO, DTO Class of Brand
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
public class BrandDTO {

	public BrandDTO() {

	}

	public BrandDTO(UUID id, String brandName) {
		super();
		this.id = id;
		this.brandName = brandName;
	}

	@Type(type = "uuid-char")
	private UUID id;

	private String brandName;

	private Boolean isActive;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
