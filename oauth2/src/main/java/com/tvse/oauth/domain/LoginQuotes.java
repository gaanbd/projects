package com.tvse.oauth.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * LoginQuotes Domain Class
 * 
 * @author techmango
 *
 */
@Entity
@Table(name="LoginQuotes", schema = "tvse")
public class LoginQuotes {

	@Id
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "quotes")
	private String quotes;
	
	@Column(name = "author")
	private String author;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuotes() {
		return quotes;
	}

	public void setQuotes(String quotes) {
		this.quotes = quotes;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	
}
