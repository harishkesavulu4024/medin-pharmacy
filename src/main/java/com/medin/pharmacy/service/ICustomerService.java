package com.medin.pharmacy.service;

import java.util.List;

import com.medin.pharmacy.dto.CustomerDTO;

public interface ICustomerService {

	CustomerDTO addCustomerDetails(CustomerDTO customerDTO);

	CustomerDTO getCustomerDetailsByPhoneNumber(String mobileNumber);

	List<CustomerDTO> getActiveCustomerDetails();

}
