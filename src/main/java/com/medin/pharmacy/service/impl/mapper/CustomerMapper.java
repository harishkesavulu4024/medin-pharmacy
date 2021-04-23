package com.medin.pharmacy.service.impl.mapper;

import org.mapstruct.Mapper;

import com.medin.pharmacy.dto.CustomerDTO;
import com.medin.pharmacy.entities.Customer;
import com.medin.pharmacy.entities.CustomerLoyalty;

@Mapper(componentModel = "spring", uses = {MapResolver.class})
public interface CustomerMapper {
	
	Customer customerDTOToCustomer(CustomerDTO customerDTO);
	
	CustomerDTO customerToCustomerDTO(Customer customer);
	
	default Customer fromId(final Long id) {

        if (id == null) {
            return null;
        }
        final Customer customer=new Customer();
        customer.setId(id);
        return customer;
    }

}
