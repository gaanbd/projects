package com.tvse.uam.domain;

import java.util.List;
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
 * Brand domain Class
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Entity
@Table(name = "Brand", schema = "tvse")
public class Brand {

	@Id
	@Column(name = "id")
	@Type(type = "uuid-char")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private UUID id;

	@Column(name = "brandName")
	private String brandName;

	@Column(name = "isActive")
	private Boolean isActive;

	@Column(name = "brandDistance")
	private Integer brandDistance;

	@Column(name = "businessModel")
	private String businessModel;

	@Transient
	private List<UUID> brandIds;

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

	public Integer getBrandDistance() {
		return brandDistance;
	}

	public void setBrandDistance(Integer brandDistance) {
		this.brandDistance = brandDistance;
	}

	public List<UUID> getBrandIds() {
		return brandIds;
	}

	public void setBrandIds(List<UUID> brandIds) {
		this.brandIds = brandIds;
	}

	public String getBusinessModel() {
		return businessModel;
	}

	public void setBusinessModel(String businessModel) {
		this.businessModel = businessModel;
	}

	@Override
	public String toString() {
		return "Brand [id=" + id + ", brandName=" + brandName + ", isActive=" + isActive + ", brandDistance="
				+ brandDistance + ", brandIds=" + brandIds + "]";
	}

}