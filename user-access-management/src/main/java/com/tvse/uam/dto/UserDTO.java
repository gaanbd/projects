package com.tvse.uam.dto;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tvse.uam.domain.AbstractAudit;

/**
 * UserProfileDTO, DTO Class of UserProfile
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class UserDTO extends AbstractAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6984261196103350833L;

	private UUID id;

	private String employeeId;

	private String userName;

	private String userPassword;

	private String email;
	
	private String firstName;

	private String middleName;

	private String lastName;

	private String addressLine1;

	private String addressLine2;

	private String mobile;

	private Boolean isActive;
	
	private String pincode;

	@Type(type = "uuid-char")
	private UUID cityId;

	private String cityName;

	private UUID state;

	private String stateName;

	private UUID country;

	private String countryName;

	private String fullName;

	private List<String> roles;

	private List<String> brands;
	
	private String newPassword;
	
	private String confirmNewPassword;
	
	private String profileImageS3Path;
	
	private byte[] profileImage;
	
	private String action;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonIgnore
	public String getUserPassword() {
		return userPassword;
	}

	@JsonProperty
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public UUID getCityId() {
		return cityId;
	}

	public void setCityId(UUID cityId) {
		this.cityId = cityId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public UUID getState() {
		return state;
	}

	public void setState(UUID state) {
		this.state = state;
	}

	public UUID getCountry() {
		return country;
	}

	public void setCountry(UUID country) {
		this.country = country;
	}

	public List<String> getBrands() {
		return brands;
	}

	public void setBrands(List<String> brands) {
		this.brands = brands;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

	public String getProfileImageS3Path() {
		return profileImageS3Path;
	}

	public void setProfileImageS3Path(String profileImageS3Path) {
		this.profileImageS3Path = profileImageS3Path;
	}

	public byte[] getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(byte[] profileImage) {
		this.profileImage = profileImage;
	}
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", employeeId=" + employeeId + ", userName=" + userName + ", userPassword="
				+ userPassword + ", email=" + email + ", firstName=" + firstName + ", middleName=" + middleName
				+ ", lastName=" + lastName + ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2
				+ ", mobile=" + mobile + ", isActive=" + isActive + ", pincode=" + pincode + ", cityId=" + cityId
				+ ", cityName=" + cityName + ", state=" + state + ", stateName=" + stateName + ", country=" + country
				+ ", countryName=" + countryName + ", fullName=" + fullName + ", roles=" + roles + ", brands=" + brands
				+ ", newPassword=" + newPassword + ", confirmNewPassword=" + confirmNewPassword
				+ ", profileImageS3Path=" + profileImageS3Path + ", profileImage=" + Arrays.toString(profileImage)
				+ ", action=" + action + "]";
	}

}
