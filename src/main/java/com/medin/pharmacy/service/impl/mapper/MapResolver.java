package com.medin.pharmacy.service.impl.mapper;

import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import org.springframework.stereotype.Component;

import com.medin.pharmacy.dto.CustomerDTO;
import com.medin.pharmacy.entities.Customer;

@Component
public class MapResolver {

	@ObjectFactory
    public Customer resolve(CustomerDTO customerDTO, @TargetType Class<Customer> type) {
		Customer customer=null;
		if(customerDTO != null && customerDTO.getId() != null ){
			customer=new Customer();
			customer.setId(customerDTO.getId());
		}
		return customer;
        
    }

}