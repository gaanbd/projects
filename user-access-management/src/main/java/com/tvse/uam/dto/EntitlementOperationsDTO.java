package com.tvse.uam.dto;

import java.util.Map;
import java.util.UUID;

/**
 * EntitlementOperationsDTO Class
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
public class EntitlementOperationsDTO {

	private UUID entitlementId;

	private String entitlementName;

	private Map<String,Boolean> permissions;
	
	private boolean selected;	
	

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public UUID getEntitlementId() {
		return entitlementId;
	}

	public void setEntitlementId(UUID entitlementId) {
		this.entitlementId = entitlementId;
	}

	public String getEntitlementName() {
		return entitlementName;
	}

	public void setEntitlementName(String entitlementName) {
		this.entitlementName = entitlementName;
	}

	public Map<String,Boolean> getPermissions() {
		return permissions;
	}

	public void setPermissions(Map<String,Boolean> permissions) {
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return "EntitlementOperationsDTO [entitlementId=" + entitlementId + ", entitlementName=" + entitlementName
				+ ", permissions=" + permissions + "]";
	}

}
