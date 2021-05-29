package com.iktpreobuka.project.entities;


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
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OfferEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer id;
	@Column(nullable = false,unique=true)
	protected String offerName;
	@Column(nullable = false)
	protected String offerDescription;
	@Column(nullable = false)
	protected Date offerCreated;
	@Column(nullable = false)
	protected Date offerExpires;
	@Column(nullable = false)
	protected Double regularPrice;
	protected Double actionPrice;
	protected String imagePath;
	protected Integer availableOffers;
	protected Integer boughtOffers;
	@Column(nullable = false)
	protected Status offerStatus;
	
	@Version
	private Integer version;
	
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "category")
	private CategoryEntity category;
	
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	private UserEntity user;
	
	//jedna ponuda se može nalaziti na više računa
	@OneToMany(mappedBy = "offer", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<BillEntity> bills;
	
	
	public OfferEntity() {
		super();
	}

	public OfferEntity(Integer id, String offerName, String offerDescription, Date offerCreated, Date offerExpires,
			Double regularPrice, Double actionPrice, String imagePath, Integer availableOffers, Integer boughtOffers,
			Status offerStatus) {
		super();
		this.id = id;
		this.offerName = offerName;
		this.offerDescription = offerDescription;
		this.offerCreated = offerCreated;
		this.offerExpires = offerExpires;
		this.regularPrice = regularPrice;
		this.actionPrice = actionPrice;
		this.imagePath = imagePath;
		this.availableOffers = availableOffers;
		this.boughtOffers = boughtOffers;
		this.offerStatus = offerStatus;
	}

	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public String getOfferDescription() {
		return offerDescription;
	}

	public void setOfferDescription(String offerDescription) {
		this.offerDescription = offerDescription;
	}

	public Date getOfferCreated() {
		return offerCreated;
	}

	public void setOfferCreated(Date offerCreated) {
		this.offerCreated = offerCreated;
	}

	public Date getOfferExpires() {
		return offerExpires;
	}

	public void setOfferExpires(Date offerExpires) {
		this.offerExpires = offerExpires;
	}

	public Double getRegularPrice() {
		return regularPrice;
	}

	public void setRegularPrice(Double regularPrice) {
		this.regularPrice = regularPrice;
	}

	public Double getActionPrice() {
		return actionPrice;
	}

	public void setActionPrice(Double actionPrice) {
		this.actionPrice = actionPrice;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Integer getAvailableOffers() {
		return availableOffers;
	}

	public void setAvailableOffers(Integer availableOffers) {
		this.availableOffers = availableOffers;
	}

	public Integer getBoughtOffers() {
		return boughtOffers;
	}

	public void setBoughtOffers(Integer boughtOffers) {
		this.boughtOffers = boughtOffers;
	}

	public Status getOfferStatus() {
		return offerStatus;
	}

	public void setOfferStatus(Status offerStatus) {
		this.offerStatus = offerStatus;
	}

	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	
}
