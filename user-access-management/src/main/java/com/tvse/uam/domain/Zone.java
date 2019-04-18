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
 * Zone domain Class
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Entity
@Table(name = "Zone", schema = "tvse")
public class Zone {

	@Id
	@Column(name = "id")
	@Type(type = "uuid-char")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private UUID id;

	@Column(name = "zoneName")
	private String zoneName;

	@Column(name = "zoneId")
	private Integer zoneId;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public Integer getZoneId() {
		return zoneId;
	}

	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}

	@Override
	public String toString() {
		return "Zone [id=" + id + ", zoneName=" + zoneName + ", zoneId=" + zoneId + "]";
	}

}