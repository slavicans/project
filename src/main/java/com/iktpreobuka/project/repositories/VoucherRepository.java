package com.iktpreobuka.project.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;


import com.iktpreobuka.project.entities.VoucherEnitity;

public interface VoucherRepository extends CrudRepository<VoucherEnitity, Integer > {
	public List<VoucherEnitity> findAllByUserId(Integer buyerId);

	public List<VoucherEnitity> findByOfferId(Integer offerId);

	public List<VoucherEnitity> findByExpirationDateBefore(Date date);

}
