package com.iktpreobuka.project.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.iktpreobuka.project.entities.OfferEntity;
import com.iktpreobuka.project.entities.Status;

@RestController
@RequestMapping(path = "/project/offers")

public class OfferControler {
	
	List<OfferEntity> offers = new ArrayList<>();
	private List<OfferEntity> getDB() {
		
	Calendar cal = Calendar.getInstance();
	cal.setTime(new Date());
	cal.add(Calendar.DATE, 5);
	if(offers.size()==0) {
		OfferEntity o1 = new OfferEntity(1,"2 tickets for Killers concert", "Enjoy!!!",			
				new Date(), cal.getTime(), 100000.00, 6500.00, " ", 10, 0, Status.WAIT_FOR_APPROVING);
	
		OfferEntity o2 = new OfferEntity(2, "VIVAX 24LE76T2", "Don't miss this fantastic offer!", new
				Date(),cal.getTime(), 200000.00, 16500.00, " ", 5, 0, Status.WAIT_FOR_APPROVING);
	
		OfferEntity o3 = new OfferEntity(3, "Dinner for two in Aqua Doria", "Excellent offer", new
				Date(), cal.getTime(), 6000.00, 3500.00, " ", 4, 0, Status.WAIT_FOR_APPROVING);
	
		offers.add(o1);
		offers.add(o2);
		offers.add(o3);
	}
	return offers;
	}
	
	
	
	//TODO GET - 3.3 koja vraća listu svih ponuda - /project/offers
	@RequestMapping(method=RequestMethod.GET, value="/")
	public List<OfferEntity> getAllOffers(){
		return getDB();
	}
	
	//TODO POST - 3.4 omogućava dodavanje nove ponude - /project/offers
	@RequestMapping(method=RequestMethod.POST, value="/")
	public OfferEntity addOffer (@RequestBody OfferEntity newOffer) {
		List<OfferEntity> offers = getDB();
		boolean ima = false;
		for(OfferEntity offer:offers) {
			if(offer.getOfferName().equals(newOffer.getOfferName()) &&
				offer.getOfferDescription().equals(newOffer.getOfferDescription())) {
				ima=true;
			}
			if (ima==false) {
				newOffer.setId((new Random()).nextInt());
				offers.add(newOffer);
				return offer;
			}
		}
		return null;
	}
	
	//TODO PUT - 3.5 omogućava izmenu postojeće ponude - /project/offers/{id}
	@RequestMapping(method=RequestMethod.PUT, value="/{id}")
	public OfferEntity changeOffer (@PathVariable Integer id, @RequestBody OfferEntity changedOffer) {
		List<OfferEntity> offers = getDB();
		for(OfferEntity offer: offers) {
			if(offer.getId().equals(id)) {
				if(offer.getOfferName() !=null)
					offer.setOfferName(changedOffer.getOfferName());
				if(offer.getOfferDescription() !=null)
					offer.setOfferDescription(changedOffer.getOfferDescription());
				if(offer.getOfferCreated() !=null)
					offer.setOfferCreated(changedOffer.getOfferCreated());
				if(offer.getOfferExpires() !=null)
					offer.setOfferExpires(changedOffer.getOfferExpires());
				if(offer.getRegularPrice() !=null)
					offer.setRegularPrice(changedOffer.getRegularPrice());
				if(offer.getActionPrice() !=null)
					offer.setActionPrice(changedOffer.getActionPrice());
				if(offer.getImagePath() !=null)
					offer.setImagePath(changedOffer.getImagePath());
				if(offer.getAvailableOffers() !=null)
					offer.setAvailableOffers(changedOffer.getAvailableOffers());
				if(offer.getBoughtOffers() !=null)
					offer.setBoughtOffers(changedOffer.getBoughtOffers());
				return offer;
				}
			}
		return null;
	}

	//TODO POST - 3.6 omogućava brisanje postojeće ponude - /project/offers/{id}
	@RequestMapping (method=RequestMethod.DELETE, value="/{id}")
	public OfferEntity deleteOffer (@PathVariable Integer id) {
		List <OfferEntity> offers =getDB();
		Iterator <OfferEntity> it = offers.iterator();
		while(it.hasNext()) {
			OfferEntity offer = it.next();
			if(offer.getId().equals(id)) {
				it.remove();
				return offer;
			}				
		}
		return null;
	}
	
	// TODO GET - 3.7 - vraća ponudu po vrednosti prosleđenog ID-a- /project/offers/{id}.
	@RequestMapping (method=RequestMethod.GET, value="/{id}")
	public OfferEntity getOne (@PathVariable Integer id) {
		List <OfferEntity> offers = getDB();
		for (OfferEntity offer:offers) {
			if(offer.getId().equals(id))
				return offer;
		}
		return null;
	}
	
	//TODO PUT - 3.8 omogućava promenu vrednosti atributa offer status postojeće ponude - /project/offers/changeOffer/{id}/status/{status}
	@RequestMapping(method=RequestMethod.PUT, value="/changeOffer/{id}/status/{status}")
	public OfferEntity changeOfferStatus (@PathVariable Integer id, @PathVariable Status status) {
		List <OfferEntity> offers = getDB();
		for (OfferEntity offer:offers) {
			if (offer.getId().equals(id)) {
				offer.setOfferStatus(status);
				return offer;
			}
		}
		return null;
	}
	
	//TODO GET - 3.9 omogućava pronalazak svih ponuda čija se akcijska cena nalazi u odgovarajućem rasponu - /project/offers/findByPrice/{lowerPrice}/and/{upperPrice}
	@RequestMapping (method=RequestMethod.GET, value="/findByPrice/{lowerPrice}/and/{upperPrice}")
	public List<OfferEntity> changeOfferStatus (@PathVariable Double lowerPrice, @PathVariable Double upperPrice) {
		List <OfferEntity> offers = getDB();
		List <OfferEntity> offersPriceRange = new ArrayList<OfferEntity>();
		for(OfferEntity offer:offers) {
			if(offer.getActionPrice() >lowerPrice  && offer.getActionPrice()< upperPrice) {
				offersPriceRange.add(offer);
			}				
		}
		return offersPriceRange;
	}	

}
