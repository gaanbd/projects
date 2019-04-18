package com.tvse.uam.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**
 * EmailSettings domain Class
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Entity
@Table(name = "EmailSettings", schema = "tvse")
public class EmailSettings extends AbstractAudit implements Serializable {

	private static final long serialVersionUID = -8463984284483845424L;

	@Id
	@Column(name = "id")
	@Type(type = "uuid-char")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private UUID id;

	@Column(name = "applicationId")
	@Type(type = "uuid-char")
	private UUID applicationId;

	@Column(name = "host")
	private String host;

	@Column(name = "port")
	private String port;

	@Column(name = "senderEmail")
	private String senderEmail;

	@Column(name = "senderName")
	private String senderName;

	@Column(name = "senderPassword")
	private String senderPassword;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(UUID applicationId) {
		this.applicationId = applicationId;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderPassword() {
		return senderPassword;
	}

	public void setSenderPassword(String senderPassword) {
		this.senderPassword = senderPassword;
	}

}
