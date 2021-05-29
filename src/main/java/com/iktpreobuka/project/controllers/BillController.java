package com.iktpreobuka.project.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
//import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	//TODO GET - 3.3 koja vraća listu svih racuna 
	@RequestMapping(method=RequestMethod.GET)
	public List<BillEntity> getAllBills(){
		return (List<BillEntity>)billRepository.findAll();
	}

	//TODO POST - dodavanje racuna
	@RequestMapping (method = RequestMethod.POST, value = "/{offerId}/buyer/{buyerId}")
		public BillEntity addBill (@PathVariable Integer offerId, @PathVariable Integer buyerId,
				@RequestParam Boolean paymentMade,@RequestParam Boolean paymentCanceled) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
			OfferEntity offer = offerRepository.findById(offerId).get();
			UserEntity user = userRepository.findById(buyerId).get();
			BillEntity bill= new BillEntity();
			bill.setPaymentCanceled(paymentCanceled); 
			bill.setPaymentMade(paymentMade);
			bill.setBillCreated(new Date());
			bill.setOffer(offer);
			bill.setUser(user);
			return billRepository.save(bill);			
		}
	
	//TODO PUT - izmena racuna
	@RequestMapping (method = RequestMethod.PUT, value = "/{id}")
	public BillEntity changeBill(@PathVariable Integer id, @RequestBody BillEntity changedBill ) {
		BillEntity bill = billRepository.findById(id).get();	
		if(bill.getPaymentMade() !=null)
			bill.setPaymentMade(changedBill.getPaymentMade());
		if(bill.getPaymentCanceled() !=null)
			bill.setPaymentCanceled(changedBill.getPaymentCanceled());
		if(bill.getBillCreated() !=null)
			bill.setBillCreated(changedBill.getBillCreated());
		return billRepository.save(bill);
	}
	
	//TODO DELETE - brisanje racuna
	@RequestMapping (method = RequestMethod.DELETE, value = "/{id}")
	public BillEntity deleteBill (@PathVariable Integer id) {
		billRepository.deleteById(id);
		return null;
	}

}
