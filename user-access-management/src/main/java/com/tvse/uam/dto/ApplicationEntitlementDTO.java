package com.tvse.uam.dto;

import java.util.List;
import java.util.UUID;

/**
 * ApplicationEntitlementDTO Class
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
public class ApplicationEntitlementDTO {

	private UUID applicationId;

	private String applicationName;

	private boolean selected;

	private List<EntitlementOperationsDTO> entitlementOperationsList;

	public UUID getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(UUID applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public List<EntitlementOperationsDTO> getEntitlementOperationsList() {
		return entitlementOperationsList;
	}

	public void setEntitlementOperationsList(List<EntitlementOperationsDTO> entitlementOperationsList) {
		this.entitlementOperationsList = entitlementOperationsList;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public String toString() {
		return "ApplicationEntitlementDTO [applicationId=" + applicationId + ", applicationName=" + applicationName
				+ ", entitlementOperationsList=" + entitlementOperationsList + "]";
	}

}
