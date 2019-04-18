package com.tvse.uam.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Base abstract class for entities which will hold definitions for created,
 * last modified by and created, last modified by date.
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAudit implements Serializable {

	private static final long serialVersionUID = -6409640978295863278L;

	@CreatedBy
	@Column(name = "createdBy", nullable = false)
	@Type(type = "uuid-char")
	private UUID createdBy;

	@CreatedDate
	@Column(name = "createdDate", nullable = false)
	private Date createdDate = new Date();

	@LastModifiedBy
	@Column(name = "updatedBy")
	@JsonIgnore
	@Type(type = "uuid-char")
	private UUID updatedBy;

	@LastModifiedDate
	@Column(name = "updatedDate")
	@JsonIgnore
	private Date updatedDate = new Date();

	public UUID getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UUID createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public UUID getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(UUID updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

}