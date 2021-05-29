package com.iktpreobuka.project.entities;

import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CategoryEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer id;
	@Column(nullable = false,unique=true)
	protected String name;
	@Column(nullable = false)
	protected String category;
	@Version
	private Integer version;
	
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<OfferEntity> offers;
	
	
	public CategoryEntity() {
		super();
	
	}

	public CategoryEntity(Integer id, String name, String category) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	
	
	

}
