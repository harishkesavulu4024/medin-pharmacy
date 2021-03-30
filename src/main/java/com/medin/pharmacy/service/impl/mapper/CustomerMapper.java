package com.medin.pharmacy.service.impl.mapper;

import org.mapstruct.Mapper;

import com.medin.pharmacy.dto.CustomerDTO;
import com.medin.pharmacy.entities.Customer;

@Mapper(componentModel = "spring", uses = {})
public interface CustomerMapper {
	
	Customer customerDTOToCustomer(CustomerDTO customerDTO);
	
	CustomerDTO customerToCustomerDTO(Customer customer);

}
