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
 * Branch domain Class
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Entity
@Table(name = "Branch", schema = "tvse")
public class Branch {

	@Id
	@Column(name = "id")
	@Type(type = "uuid-char")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private UUID id;

	@Column(name = "branchName")
	private String branchName;

	@Column(name = "zoneId")
	@Type(type = "uuid-char")
	private UUID zoneId;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public UUID getZoneId() {
		return zoneId;
	}

	public void setZoneId(UUID zoneId) {
		this.zoneId = zoneId;
	}

	@Override
	public String toString() {
		return "Branch [branchName=" + branchName + ", zoneId=" + zoneId + "]";
	}

}