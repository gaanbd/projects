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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * EmailConfiguration domain Class
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Entity
@Table(name = "EmailConfiguration", schema = "tvse")
@JsonInclude(value = Include.NON_NULL)
public class EmailConfiguration extends AbstractAudit implements Serializable {

	private static final long serialVersionUID = -6599885253007138479L;

	@Id
	@Column(name = "id")
	@Type(type = "uuid-char")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private UUID id;

	@Column(name = "emailConfigName")
	private String emailConfigName;

	@Column(name = "subject")
	private String subject;

	@Column(name = "emailContent")
	private String emailContent;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getEmailConfigName() {
		return emailConfigName;
	}

	public void setEmailConfigName(String emailConfigName) {
		this.emailConfigName = emailConfigName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}

}
