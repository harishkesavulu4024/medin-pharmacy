package com.medin.pharmacy.service.impl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.medin.pharmacy.dto.CustomerDTO;
import com.medin.pharmacy.dto.CustomerLoyaltyDTO;
import com.medin.pharmacy.entities.CustomerLoyalty;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class,LoyaltyCardMapper.class})
public interface CustomerLoyaltyMapper {

//	@Mapping(source = "customerLoyaltyDTO.customer", target = "customer")
	CustomerLoyalty customerLoyaltyDTOToCustomerLoyalty(CustomerLoyaltyDTO customerLoyaltyDTO
			//CustomerDTO customer
			);

	CustomerLoyaltyDTO customerLoyaltyToCustomerLoyaltyDTO(CustomerLoyalty customerLoyalty);

	
//	default CustomerLoyalty fromId(final Long id) {
//
//        if (id == null) {
//            return null;
//        }
//        final CustomerLoyalty customerLoyalty=new CustomerLoyalty();
//        customerLoyalty.setId(id);
//        return customerLoyalty;
//    }

}
