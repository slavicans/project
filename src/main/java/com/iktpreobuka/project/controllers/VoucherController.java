package com.iktpreobuka.project.controllers;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.iktpreobuka.project.entities.OfferEntity;
import com.iktpreobuka.project.entities.Roles;
import com.iktpreobuka.project.entities.UserEntity;
import com.iktpreobuka.project.entities.VoucherEnitity;
import com.iktpreobuka.project.repositories.OfferRepository;
import com.iktpreobuka.project.repositories.UserRepository;
import com.iktpreobuka.project.repositories.VoucherRepository;

@RestController
@RequestMapping(path = "/project/vouchers")

public class VoucherController {
	@Autowired
	private VoucherRepository voucherRepository;
	
	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	//TODO GET - 4.3 vraca listu svih vaucera
	@RequestMapping(method=RequestMethod.GET)
	public List<VoucherEnitity> getAllVouchers(){
		return (List<VoucherEnitity>) voucherRepository.findAll();
	}
	
	//TODO POST - 4.6 dodaj vaucer
	@RequestMapping (method = RequestMethod.POST, value = "/{offerId}/buyer/{buyerId}")
	public VoucherEnitity addVoucher (@PathVariable Integer offerId, @PathVariable Integer buyerId,
			@RequestParam Date expirationDate,@RequestParam Boolean isUsed) {

		OfferEntity offer = offerRepository.findById(offerId).get();
		UserEntity user = userRepository.findById(buyerId).get();
		
		if(user.geteUserRole()==Roles.ROLE_CUSTOMER) {
			VoucherEnitity voucher= new VoucherEnitity();
			voucher.setExpirationDate(expirationDate); 
			voucher.setOffer(offer);
			voucher.setUser(user);
			voucher.setIsUsed(isUsed);
			return voucherRepository.save(voucher);			
		}
		
		return null;
	}
	//TODO PUT - 4.6 izmeni vaucer
	@RequestMapping (method = RequestMethod.PUT, value = "/{id}")
	public VoucherEnitity changeVoucher (@PathVariable Integer id, @RequestBody VoucherEnitity changedVoucher) {
		VoucherEnitity voucher=voucherRepository.findById(id).get();
		if(changedVoucher.getExpirationDate()!=null)
			voucher.setExpirationDate(changedVoucher.getExpirationDate());
		if(changedVoucher.getIsUsed()!=null)
			voucher.setIsUsed(changedVoucher.getIsUsed());
		if (changedVoucher.getOffer()!=null)
			voucher.setOffer(changedVoucher.getOffer());
		if(changedVoucher.getUser()!=null && changedVoucher.getUser().geteUserRole()==Roles.ROLE_CUSTOMER)
			voucher.setUser(changedVoucher.getUser());
		return null;
	}
	
	//TODO DELETE - 4.6 obrisi vaucer
	@RequestMapping(method=RequestMethod.DELETE, value= "/{id}")
	public VoucherEnitity deleteVoucher (@PathVariable Integer id) {
		voucherRepository.deleteById(id);
		return null;
	}
	
	//TODO GET - 4.7 pronalazak svih vaučera određenog kupca
	@RequestMapping(method=RequestMethod.GET, value = "/findByBuyer/{buyerId}")
	public List<VoucherEnitity> getVoucherByBuyer(@PathVariable Integer buyerId) {
//		List<VoucherEnitity> voucherByBuyer = new ArrayList<VoucherEnitity>();
		return voucherRepository.findAllByUserId(buyerId);
	//	return null;
	}
	/*
	 * 4.8 kreirati REST endpoint za pronalazak svih vaučera određene ponude •
	 * putanja /project/ findByOffer offerId
	 */

	@GetMapping(path = "/findByOffer/{offerId}")
	public List<VoucherEnitity> findVoucherByOffer(@PathVariable Integer offerId) {
		return voucherRepository.findByOfferId(offerId);
	}

	/*
	 * 4.9 kreirati REST endpoint za pronalazak svih vaučera koji nisu istekli •
	 * putanja /project/ findNonExpiredVoucher
	 */

	@GetMapping(path = "/findNonExpiredVoucher")
	public List<VoucherEnitity> findNonExpiredVouchers( @RequestParam Date date) {
		return voucherRepository.findByExpirationDateBefore(date);
	}
}
