package com.example.sahibiden.codecase.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "category")
@JsonIgnoreProperties (value = { "hibernateLazyInitializer", "handler"})
public class Category implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7608616532925446296L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "expire")
	private Integer expire;

	public Category() {
		super();
	}

	public Category(Integer id, String name, Integer expire) {
		super();
		this.id = id;
		this.name = name;
		this.expire = expire;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getExpire() {
		return expire;
	}

	public void setExpire(Integer expire) {
		this.expire = expire;
	}
	
	
}
