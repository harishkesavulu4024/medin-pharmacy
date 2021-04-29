package com.medin.pharmacy.service;

import com.medin.pharmacy.dto.CustomerLoyaltyDTO;
import com.medin.pharmacy.entities.CustomerLoyalty;

public interface ICustomerLoyaltyService {

	CustomerLoyaltyDTO createCustomerLoyalty(CustomerLoyalty customerLoyalty);

	CustomerLoyaltyDTO getCustomerLoyaltyDetailsByMobileNumber(String mobileNumber);

	CustomerLoyaltyDTO createCustomerLoyalty(CustomerLoyaltyDTO customerLoyaltyDTO);

	CustomerLoyaltyDTO getCustomerLoyaltyDetailsByCustomerId(Long customnerId);

}
