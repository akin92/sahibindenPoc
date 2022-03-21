package com.example.sahibiden.codecase.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "notice")
public class Notice implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8255425883767436928L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Size(min=2, max=30)
	@Column(name = "title")
	private String title;
	
	@Size(min=20, max=200)
	@Column(name = "detail")
	private String detail;
	
	@Column(name = "repeating")
	private Boolean repeating;
	
	@ManyToOne(cascade = {CascadeType.MERGE},fetch= FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;
	
	@OneToOne(cascade = {CascadeType.MERGE},fetch= FetchType.LAZY)
	@JoinColumn(name = "status_id", referencedColumnName = "id")
	private Status status;
	
	@Column(name = "start_date")
    LocalDate startDate;
	
	@Column(name = "finish_date")
	LocalDate finihDate;

	public Notice() {
		super();
	}

	

	public Notice(Integer id, String title, String detail, Boolean repeating, Category category, Status status,
			LocalDate startDate, LocalDate finihDate) {
		super();
		this.id = id;
		this.title = title;
		this.detail = detail;
		this.repeating = repeating;
		this.category = category;
		this.status = status;
		this.startDate = startDate;
		this.finihDate = finihDate;
	}
	
	public Notice( String title, String detail) {
		super();
		this.title = title;
		this.detail = detail;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Boolean getRepeating() {
		return repeating;
	}

	public void setRepeating(Boolean repeating) {
		this.repeating = repeating;
	}


	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}



	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getFinihDate() {
		return finihDate;
	}

	public void setFinihDate(LocalDate finihDate) {
		this.finihDate = finihDate;
	}
	
	
}
