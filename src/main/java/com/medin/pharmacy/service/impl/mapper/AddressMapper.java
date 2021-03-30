package com.medin.pharmacy.service.impl.mapper;

import org.mapstruct.Mapper;

import com.medin.pharmacy.dto.AddressDTO;
import com.medin.pharmacy.entities.Address;

@Mapper(componentModel = "spring", uses = {})
public interface AddressMapper {
	
	Address addressDTOToAddress(AddressDTO addressDTO);
	
	AddressDTO addressToAddressDTO(Address address);

}
