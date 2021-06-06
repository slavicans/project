package com.iktpreobuka.project.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.project.entities.BillEntity;
import com.iktpreobuka.project.entities.OfferEntity;
import com.iktpreobuka.project.entities.UserEntity;
import com.iktpreobuka.project.repositories.BillRepository;
import com.iktpreobuka.project.repositories.OfferRepository;
import com.iktpreobuka.project.repositories.UserRepository;


@RestController
@RequestMapping(path = "/project/bills")

public class BillController {
	@Autowired
	private BillRepository billRepository;
	@Autowired
	private OfferRepository offerRepository;
	@Autowired
	private UserRepository userRepository;
	
//	@DateTimeFormat(iso = ISO.DATE) 
	
	@DateTimeFormat(pattern = "yyyy-MM-dd") 
	
	
	
	
	//TODO GET - 3.3 koja vraća listu svih racuna 
	@RequestMapping(method=RequestMethod.GET)
	public List<BillEntity> getAllBills(){
		return (List<BillEntity>)billRepository.findAll();
	}

	//TODO POST - 3.6 dodavanje racuna sa oznakom ponude i kupca
//	@RequestMapping (method = RequestMethod.POST, value = "/{offerId}/buyer/{buyerId}")
//		public BillEntity addBill (@PathVariable Integer offerId, @PathVariable Integer buyerId,
//				@RequestParam Boolean paymentMade,@RequestParam Boolean paymentCanceled) {
//			OfferEntity offer = offerRepository.findById(offerId).get();
//			UserEntity user = userRepository.findById(buyerId).get();
//			BillEntity bill= new BillEntity();
//			bill.setPaymentCanceled(paymentCanceled); 
//			bill.setPaymentMade(paymentMade);
//			bill.setBillCreated(new Date());
//			bill.setOffer(offer);
//			bill.setUser(user);
//			return billRepository.save(bill);			
//		}
	/*
	 * 5. 1 proširiti metodu za dodavanje računa tako da se smanji broj dostupnih
	 * ponuda ponude sa računa , odnosno poveća broj kupljenih
	 */
	@PostMapping(path = "/{offerId}/buyer/{buyerId}")
	public BillEntity createBillWihtOfferAndBuyey(@PathVariable Integer offerId, @PathVariable Integer buyerId,
			 @RequestBody BillEntity bill) {
		if (offerRepository.existsById(offerId)) {
			if (userRepository.existsById(buyerId)) {
				UserEntity user = userRepository.findById(buyerId).get();
				OfferEntity offer = offerRepository.findById(offerId).get();
				bill.setUser(user);
				bill.setOffer(offer);
				bill.setPaymentCanceled(bill.getPaymentCanceled()); 
				bill.setPaymentMade(bill.getPaymentMade());
				bill.setBillCreated(new Date());
				bill.getOffer().setAvailableOffers(bill.getOffer().getAvailableOffers() - 1);
				bill.getOffer().setBoughtOffers(bill.getOffer().getBoughtOffers() + 1);
				offerRepository.save(offer);
				return billRepository.save(bill);
			}
		}
		return null;
	}
	
	//TODO PUT - 3.7 izmena racuna
	/*
	 * 5. 2 proširiti metodu za izmenu računa tako da ukoliko se račun proglašava
	 * otkazanim tada treba povećati broj dostupnih ponuda ponude sa računa ,
	 * odnosno smanjiti broj kupljenih
	 */
	@RequestMapping (method = RequestMethod.PUT, value = "/{id}")
	public BillEntity changeBill(@PathVariable Integer id, @RequestBody BillEntity changedBill ) {
		BillEntity bill = billRepository.findById(id).get();	
		
		if (billRepository.existsById(id)) {
			if(changedBill.getPaymentMade() !=null)
				bill.setPaymentMade(changedBill.getPaymentMade());
			if(changedBill.getPaymentCanceled() !=null)
				bill.setPaymentCanceled(changedBill.getPaymentCanceled());
				if (bill.getPaymentCanceled()) {
					bill.getOffer().setAvailableOffers(bill.getOffer().getAvailableOffers() + 1);
					bill.getOffer().setBoughtOffers(bill.getOffer().getBoughtOffers() - 1);
				}
				if(changedBill.getBillCreated() !=null)
					bill.setBillCreated(changedBill.getBillCreated());
				return billRepository.save(bill);
			}
			return null;
		}
	
	//TODO DELETE - 3.7 brisanje racuna
	@RequestMapping (method = RequestMethod.DELETE, value = "/{id}")
	public BillEntity deleteBill (@PathVariable Integer id) {
		billRepository.deleteById(id);
		return null;
	}

	
	//TODO GET - 3.8 pronadji sve racune odredjene kategorije
	@RequestMapping(method=RequestMethod.GET, value = "/findByCategory/{categoryId}")
	public List<BillEntity> getAllBillsCertainCategory(@PathVariable Integer categoryId){
		List<BillEntity> billsCertainCatgory =  new ArrayList<BillEntity>();
		Iterable<BillEntity> allBills =  new ArrayList<BillEntity>();
		allBills = billRepository.findAll();			
		for (BillEntity bill: allBills) {
			if (bill.getOffer().getCategory().getId() == categoryId)
				billsCertainCatgory.add(bill);			
		}
		return 		billsCertainCatgory;
		
	}
	
	//TODO GET - 3.9 pronalazak svih računa kojisu kreirani u odgovarajućem vremenskom periodu
	@RequestMapping(method=RequestMethod.GET, value = "/findByDate/{startDate}/and/{endDate}")
	public List<BillEntity> getAllBillsinCertanPeriod(@PathVariable Date startDate,
			@PathVariable Date endDate){
		List<BillEntity> billsBtwDates =  new ArrayList<BillEntity>();
		Iterable<BillEntity> allBills =  new ArrayList<BillEntity>();
		allBills = billRepository.findAll();		
		for (BillEntity bill: allBills) {
			if(bill.getBillCreated().after(startDate) && bill.getBillCreated().before(endDate))
				billsBtwDates.add(bill);	
		}
		return billsBtwDates;
	}
}
