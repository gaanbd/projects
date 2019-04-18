package com.tvse.oauth.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User domain Class
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Entity
@Table(name = "User", schema = "tvse")
public class User extends AbstractAudit {

	private static final long serialVersionUID = -8680812523504049823L;

	@Id
	@Column(name = "id")
	@Type(type = "uuid-char")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private UUID id;
	
	@Column(name = "employeeId")
	private String employeeId;

	@Column(name = "userName")
	private String userName;
	
	@Column(name = "userPassword")
	private String userPassword;

	@Column(name = "userEmail")
	private String email;
	
	@Column(name = "firstName")
	private String firstName;
	
	@Column(name = "middleName")
	private String middleName;
	
	@Column(name = "lastName")
	private String lastName;
	
	@Column(name = "addressLine1")
	private String addressLine1;
	
	@Column(name = "addressLine2")
	private String addressLine2;
	
	@Column(name = "cityId")
	@Type(type = "uuid-char")
	private UUID cityId;
	
	@Column(name = "mobile")
	private String mobile;

	@Column(name = "isActive")
	private boolean isActive;

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

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", employeeId=" + employeeId + ", userName=" + userName + ", userPassword="
				+ userPassword + ", email=" + email + ", firstName=" + firstName + ", middleName=" + middleName
				+ ", lastName=" + lastName + ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2
				+ ", cityId=" + cityId + ", mobile=" + mobile + ", isActive=" + isActive + "]";
	}

}