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
 * Menu domain Class
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Entity
@Table(name = "Menu", schema = "tvse")
public class Menu {

	@Id
	@Type(type = "uuid-char")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "id")
	private UUID id;

	@Column(name = "menuName")
	private String menuName;

	@Column(name = "menuKey")
	private String menuKey;

	@Column(name = "menuIcon")
	private String menuIcon;

	@Column(name = "sequenceNumber")
	private String sequenceNumber;

	@Type(type = "uuid-char")
	@Column(name = "parentId", nullable = true)
	private UUID parentId;

	@Type(type = "uuid-char")
	@Column(name = "entitlementId", nullable = true)
	private UUID entitlementId;

	public UUID getEntitlementId() {
		return entitlementId;
	}

	public void setEntitlementId(UUID entitlementId) {
		this.entitlementId = entitlementId;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuKey() {
		return menuKey;
	}

	public void setMenuKey(String menuKey) {
		this.menuKey = menuKey;
	}

	public String getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public UUID getParentId() {
		return parentId;
	}

	public void setParentId(UUID parentId) {
		this.parentId = parentId;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", menuName=" + menuName + ", menuKey=" + menuKey + ", menuIcon=" + menuIcon
				+ ", sequenceNumber=" + sequenceNumber + ", parentId=" + parentId + ", entitlementId=" + entitlementId
				+ "]";
	}

}
