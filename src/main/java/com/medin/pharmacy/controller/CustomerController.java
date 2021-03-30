package com.medin.pharmacy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medin.pharmacy.dto.CustomerDTO;
import com.medin.pharmacy.service.ICustomerService;

@RestController
@RequestMapping(value="/customer")
public class CustomerController {
	
    @Autowired
	private ICustomerService customerService;
    
    @PostMapping(value="/add")
    public CustomerDTO addCustomer(@RequestBody CustomerDTO customerDTO){
    	return customerService.addCustomerDetails(customerDTO);
    }
    
    
	
}
