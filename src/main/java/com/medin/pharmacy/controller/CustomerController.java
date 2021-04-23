package com.medin.pharmacy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medin.pharmacy.dto.CustomerDTO;
import com.medin.pharmacy.service.ICustomerService;
import com.medin.pharmacy.utils.CustomerValidationUtil;

@RestController
@RequestMapping(value="/customer")
public class CustomerController {
	
    @Autowired
	private ICustomerService customerService;
    
    @PostMapping(value="/add")
    public CustomerDTO addCustomer(@RequestBody CustomerDTO customerDTO){
    	String mobileNumber=customerDTO.getMobileNumber();
    	// Validate phoneNumber 
    	CustomerValidationUtil.validateMobileNumberFormat(mobileNumber);
    	return customerService.addCustomerDetails(customerDTO);
    }
    
    @GetMapping(value="/{mobileNumber}")
    public CustomerDTO getCustomerDetailsByPhoneNumber(@PathVariable("mobileNumber") String mobileNumber){
    	// Validate phoneNumber 
    	CustomerValidationUtil.validateMobileNumberFormat(mobileNumber);
    	return customerService.getCustomerDetailsByPhoneNumber(mobileNumber);
    }
    
    @GetMapping(value="/list")
    public List<CustomerDTO> getActiveCustomerDetails(){
    	return customerService.getActiveCustomerDetails();
    }
    
    
	
}
