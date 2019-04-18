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
 * Entitlement domain Class
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Entity
@Table(name = "EntitlementEvent", schema = "tvse")
public class EntitlementEvent {

	@Id
	@Column(name = "id")
	@Type(type = "uuid-char")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private UUID id;

	@Type(type = "uuid-char")
	@Column(name = "entitlementId")
	private UUID entitlementId;

	@Type(type = "uuid-char")
	@Column(name = "nextEntitlementId")
	private UUID nextEntitlementId;

	@Column(name = "apiUrl")
	private String apiUrl;

	@Column(name = "routerUrl")
	private String routerUrl;

	@Column(name = "event")
	private String event;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getEntitlementId() {
		return entitlementId;
	}

	public void setEntitlementId(UUID entitlementId) {
		this.entitlementId = entitlementId;
	}

	public UUID getNextEntitlementId() {
		return nextEntitlementId;
	}

	public void setNextEntitlementId(UUID nextEntitlementId) {
		this.nextEntitlementId = nextEntitlementId;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getApiUrl() {
		return apiUrl;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public String getRouterUrl() {
		return routerUrl;
	}

	public void setRouterUrl(String routerUrl) {
		this.routerUrl = routerUrl;
	}

	@Override
	public String toString() {
		return "EntitlementEvent [id=" + id + ", entitlementId=" + entitlementId + ", nextEntitlementId="
				+ nextEntitlementId + ", apiUrl=" + apiUrl + ", routerUrl=" + routerUrl + ", event=" + event + "]";
	}

}