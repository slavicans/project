package com.iktpreobuka.project.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BillEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer id;
	@Column(nullable = false)
	protected Boolean paymentMade;
	@Column(nullable = false)
	protected Boolean paymentCanceled;
	@Column(nullable = false)
	protected  Date billCreated;
	@Version
	private Integer version;
	
	//jedan racun predstavlja kupovinu jedne ponude
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "offer")
	private OfferEntity offer;
	
	//jedan racun predstavlja kupovinu jedne ponude od strane jednog kupca
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	private UserEntity user;
	
	public BillEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public BillEntity(Boolean paymentMade, Boolean paymentCanceled, Date billCreated) {
		super();
		this.paymentMade = paymentMade;
		this.paymentCanceled = paymentCanceled;
		this.billCreated = billCreated;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getPaymentMade() {
		return paymentMade;
	}
	public void setPaymentMade(Boolean paymentMade) {
		this.paymentMade = paymentMade;
	}
	public Boolean getPaymentCanceled() {
		return paymentCanceled;
	}
	public void setPaymentCanceled(Boolean paymentCanceled) {
		this.paymentCanceled = paymentCanceled;
	}
	public Date getBillCreated() {
		return billCreated;
	}
	public void setBillCreated(Date billCreated) {
		this.billCreated = billCreated;
	}

	public OfferEntity getOffer() {
		return offer;
	}

	public void setOffer(OfferEntity offer) {
		this.offer = offer;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}
	
	
}
