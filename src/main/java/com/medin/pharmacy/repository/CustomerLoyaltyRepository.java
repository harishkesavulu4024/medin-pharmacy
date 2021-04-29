package com.medin.pharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.medin.pharmacy.entities.CustomerLoyalty;

@Repository
public interface CustomerLoyaltyRepository extends JpaRepository<CustomerLoyalty, Long>{

	@Query("select customerLoyalty from CustomerLoyalty customerLoyalty where customerLoyalty.customer.mobileNumber=:mobileNumber")
	CustomerLoyalty findCustomerLoyaltyByMobileNumber(String mobileNumber);

	CustomerLoyalty findByCustomerId(Long customnerId);

}
