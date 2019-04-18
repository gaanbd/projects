package com.tvse.uam.dto;

import java.util.List;
import java.util.UUID;

/**
 * MenuDTO, DTO Class of Role
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
public class MenuDTO {

	private UUID id;

	private String menuName;

	private String menuKey;

	private String menuIcon;

	private UUID parentId;

	private UUID entitlementId;

	private String sequenceNumber;

	private String entitlementUrl;
	
	private String routerUrl;

	private List<MenuDTO> subMenuList; // For UI Rendering

	public List<MenuDTO> getSubMenuList() {
		return subMenuList;
	}

	public void setSubMenuList(List<MenuDTO> subMenuList) {
		this.subMenuList = subMenuList;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public String getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNo(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public UUID getParentId() {
		return parentId;
	}

	public void setParentId(UUID parentId) {
		this.parentId = parentId;
	}

	public void setMenuKey(String menuKey) {
		this.menuKey = menuKey;
	}

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

	public String getEntitlementUrl() {
		return entitlementUrl;
	}

	public void setEntitlementUrl(String entitlementUrl) {
		this.entitlementUrl = entitlementUrl;
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

	public String getRouterUrl() {
		return routerUrl;
	}

	public void setRouterUrl(String routerUrl) {
		this.routerUrl = routerUrl;
	}

	public MenuDTO() {

	}

	public MenuDTO(UUID id, String menuName, String menuKey, String menuIcon, UUID parentId, UUID entitlementId,
			String sequenceNo, String entitlementUrl, String routerUrl) {
		super();
		this.id = id;
		this.menuName = menuName;
		this.menuKey = menuKey;
		this.menuIcon = menuIcon;
		this.parentId = parentId;
		this.entitlementId = entitlementId;
		this.sequenceNumber = sequenceNo;
		this.entitlementUrl = entitlementUrl;
		this.routerUrl = routerUrl;
	}

	@Override
	public String toString() {
		return "MenuDTO [id=" + id + ", menuName=" + menuName + ", menuKey=" + menuKey + ", menuIcon=" + menuIcon
				+ ", parentId=" + parentId + ", entitlementId=" + entitlementId + ", sequenceNumber=" + sequenceNumber
				+ ", entitlementUrl=" + entitlementUrl + ", subMenuList=" + subMenuList + "]";
	}

}
