package com.iktpreobuka.project.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.project.entities.CategoryEntity;
import com.iktpreobuka.project.entities.OfferEntity;
import com.iktpreobuka.project.entities.Roles;
import com.iktpreobuka.project.entities.Status;
import com.iktpreobuka.project.entities.UserEntity;
import com.iktpreobuka.project.repositories.CategoryRepository;
import com.iktpreobuka.project.repositories.OfferRepository;
import com.iktpreobuka.project.repositories.UserRepository;

@RestController
@RequestMapping(path = "/project/offers")

public class OfferControler {
	
	@Autowired
	private OfferRepository offerRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "category")
	private CategoryEntity category;
	
	@DateTimeFormat(iso = ISO.DATE) 
	
	List<OfferEntity> offers = new ArrayList<>();
//	private List<OfferEntity> getDB() {
		
//	Calendar cal = Calendar.getInstance();
//	cal.setTime(new Date());
//	cal.add(Calendar.DATE, 5);
//	if(offers.size()==0) {
//		OfferEntity o1 = new OfferEntity(1,"2 tickets for Killers concert", "Enjoy!!!",			
//				new Date(), cal.getTime(), 100000.00, 6500.00, " ", 10, 0, Status.WAIT_FOR_APPROVING);
//	
//		OfferEntity o2 = new OfferEntity(2, "VIVAX 24LE76T2", "Don't miss this fantastic offer!", new
//				Date(),cal.getTime(), 200000.00, 16500.00, " ", 5, 0, Status.WAIT_FOR_APPROVING);
	
//		OfferEntity o3 = new OfferEntity(3, "Dinner for two in Aqua Doria", "Excellent offer", new
//				Date(), cal.getTime(), 6000.00, 3500.00, " ", 4, 0, Status.WAIT_FOR_APPROVING);
	
//		offers.add(o1);
//		offers.add(o2);
//		offers.add(o3);
//	}
//	return offers;
//	}
	Calendar cal = Calendar.getInstance();

	
	//TODO POST - 1.5 dodavanje nove ponude
	@RequestMapping(value = "/",method = RequestMethod.POST)
	public OfferEntity newOffer(@RequestBody OfferEntity newOffer) {	
		cal.setTime(new Date());
		cal.add(Calendar.DATE, 10);
		
		OfferEntity offer = new OfferEntity();
		if(newOffer.getOfferName() !=null) 
			offer.setOfferName(newOffer.getOfferName());
		if(newOffer.getOfferDescription() !=null)
			offer.setOfferDescription(newOffer.getOfferDescription());
			offer.setOfferCreated(new Date());	
			offer.setOfferExpires(cal.getTime());
		if(newOffer.getRegularPrice() !=null)
			offer.setRegularPrice(newOffer.getRegularPrice());
		if(newOffer.getActionPrice() !=null)
			offer.setActionPrice(newOffer.getActionPrice());
		if(newOffer.getImagePath() !=null)
			offer.setImagePath(newOffer.getImagePath());
		if(newOffer.getAvailableOffers() !=null)
			offer.setAvailableOffers(newOffer.getAvailableOffers());
		if(newOffer.getBoughtOffers() !=null)
			offer.setBoughtOffers(newOffer.getBoughtOffers());
		if(newOffer.getOfferStatus() !=null)
			offer.setOfferStatus(newOffer.getOfferStatus());
		return offerRepository.save(offer);
	}

	//TODO POST - 2.3 dodavanje kategorije i korisnika koji je kreirao ponudu u offerEntity
	@RequestMapping(value = "/{categoryId}/seller/{sellerId}",method = RequestMethod.POST)
	public OfferEntity newOffer2(@RequestBody OfferEntity newOffer, @PathVariable Integer sellerId,
			@PathVariable Integer categoryId ) {	
		cal.setTime(new Date());
		cal.add(Calendar.DATE, 10);

		UserEntity user = userRepository.findById(sellerId).get();
		CategoryEntity category = categoryRepository.findById(categoryId).get();
		OfferEntity offer = new OfferEntity();
		if(user.geteUserRole()==Roles.ROLE_SELLER) {					
			offer.setUser(user);
			offer.setCategory(category);
			offer.setOfferCreated(new Date());	
			offer.setOfferExpires(cal.getTime());
			if(newOffer.getOfferName() !=null) 
				offer.setOfferName(newOffer.getOfferName());
			if(newOffer.getOfferDescription() !=null)
				offer.setOfferDescription(newOffer.getOfferDescription());			
			if(newOffer.getRegularPrice() !=null)
				offer.setRegularPrice(newOffer.getRegularPrice());
			if(newOffer.getActionPrice() !=null)
				offer.setActionPrice(newOffer.getActionPrice());
			if(newOffer.getImagePath() !=null)
				offer.setImagePath(newOffer.getImagePath());
			if(newOffer.getAvailableOffers() !=null)
				offer.setAvailableOffers(newOffer.getAvailableOffers());
			if(newOffer.getBoughtOffers() !=null)
				offer.setBoughtOffers(newOffer.getBoughtOffers());
			if(newOffer.getOfferStatus() !=null)
				offer.setOfferStatus(newOffer.getOfferStatus());		
		}	
		return offerRepository.save(offer);
	}
	//TODO GET - 3.3 koja vraća listu svih ponuda - /project/offers
	@RequestMapping(method=RequestMethod.GET, value="/")
	public List<OfferEntity> getAllOffers(){
		return (List<OfferEntity>)offerRepository.findAll();
	}
	
	
	//TODO PUT - 3.5 omogućava izmenu postojeće ponude - /project/offers/{id}
	@RequestMapping(method=RequestMethod.PUT, value="/{id}")
	public OfferEntity changeOffer (@PathVariable Integer id, @RequestBody OfferEntity changedOffer) {
		OfferEntity offer = offerRepository.findById(id).get();	
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
				return offerRepository.save(offer);	
	}

	//TODO PUT - 2.4 izmena kategorije ponude
	@RequestMapping(method=RequestMethod.PUT, value="/{id}/category/{categoryId}")
	public OfferEntity changeOfferCategory (@PathVariable Integer id, @PathVariable Integer categoryId) {
		OfferEntity offer = offerRepository.findById(id).get();	
		CategoryEntity category = categoryRepository.findById(categoryId).get();
		 offer.setCategory(category);
		 return offerRepository.save(offer);	
	}
	
	//TODO DELETE - 3.6 omogućava brisanje postojeće ponude - /project/offers/{id}
	@RequestMapping (method=RequestMethod.DELETE, value="/{id}")
	public OfferEntity deleteOffer (@PathVariable Integer id) {
		offerRepository.deleteById(id);
		return null;
//		List <OfferEntity> offers =getDB();
//		Iterator <OfferEntity> it = offers.iterator();
//		while(it.hasNext()) {
//			OfferEntity offer = it.next();
//			if(offer.getId().equals(id)) {
//				it.remove();
//				return offer;
//			}				
//		}
//		return null;
	}
	
	// TODO GET - 3.7 - vraća ponudu po vrednosti prosleđenog ID-a- /project/offers/{id}.
	@RequestMapping (method=RequestMethod.GET, value="/{id}")
	public Optional<OfferEntity> getOne (@PathVariable Integer id) {
		return offerRepository.findById(id);
		
//		List <OfferEntity> offers = getDB();
//		for (OfferEntity offer:offers) {
//			if(offer.getId().equals(id))
//				return offer;
//		}
//		return null;
	}
	
	//TODO PUT - 3.8 omogućava promenu vrednosti atributa offer status postojeće ponude - /project/offers/changeOffer/{id}/status/{status}
	@RequestMapping(method=RequestMethod.PUT, value="/changeOffer/{id}/status/{status}")
	public OfferEntity changeOfferStatus (@PathVariable Integer id, @PathVariable Status status) {
		OfferEntity offer =  offerRepository.findById(id).get();
			if (offer.getId().equals(id)) {
				offer.setOfferStatus(status);				
			}
			return offerRepository.save(offer);	
		}

	
	//TODO GET - 3.9 omogućava pronalazak svih ponuda čija se akcijska cena nalazi u odgovarajućem rasponu - /project/offers/findByPrice/{lowerPrice}/and/{upperPrice}
	@RequestMapping (method=RequestMethod.GET, value="/findByPrice/{lowerPrice}/and/{upperPrice}")
	public List<OfferEntity> changeOfferStatus (@PathVariable Double lowerPrice, @PathVariable Double upperPrice) {
		List <OfferEntity> offers = (List<OfferEntity>) offerRepository.findAll();
		List <OfferEntity> offersPriceRange = new ArrayList<OfferEntity>();	
		for(OfferEntity offer:offers) {
			if(offer.getActionPrice() >lowerPrice  && offer.getActionPrice()< upperPrice) {
				offersPriceRange.add(offer);
			}				
		}
		return offersPriceRange;
	}	

}
